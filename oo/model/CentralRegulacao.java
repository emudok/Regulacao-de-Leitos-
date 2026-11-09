package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Comparator;

public class CentralRegulacao {
    private final List<Leito> leitos = new ArrayList<>();
    private final Queue<Solicitacao> fila = new PriorityQueue<>();
    private final List<String> lancamentos = new ArrayList<>();

    public void adicionarLeito(Leito leito) {
        leitos.add(leito);
    }

    public void adicionarSolicitacao(Solicitacao solicitacao) {
        boolean documentoCadastrado = leitos.stream()
                .filter(leito -> !leito.estaDisponivel())
                .anyMatch(leito -> leito.getPaciente().getDocumento()
                        .equals(solicitacao.getPaciente().getDocumento()));
        boolean documentoPendente = fila.stream()
                .anyMatch(item -> item.getPaciente().getDocumento()
                        .equals(solicitacao.getPaciente().getDocumento()));
        if (documentoCadastrado || documentoPendente) {
            throw new IllegalArgumentException(
                    "Já existe uma solicitação ou internação para este documento.");
        }
        fila.add(solicitacao);
        lancamentos.add("Solicitação lançada: " + solicitacao);
    }

    public int regularSolicitacoes() {
        int total = 0;
        while (!fila.isEmpty()) {
            Solicitacao solicitacao = fila.stream()
                    .filter(this::existeLeitoCompatível)
                    .min(Comparator.comparingInt(Solicitacao::getPrioridade)
                            .reversed())
                    .orElse(null);
            if (solicitacao == null) {
                break;
            }

            fila.remove(solicitacao);
            Leito leito = leitos.stream()
                    .filter(Leito::estaDisponivel)
                    .filter(item -> mesmoHospital(item, solicitacao))
                    .findFirst()
                    .orElseThrow();
            leito.ocupar(solicitacao.getPaciente());
            lancamentos.add("Paciente " + solicitacao.getPaciente().getNome()
                    + " encaminhado para " + leito.getHospital().getNome()
                    + ", leito " + leito.getNumero());
            total++;
        }
        return total;
    }

    private boolean existeLeitoCompatível(Solicitacao solicitacao) {
        return leitos.stream()
                .filter(Leito::estaDisponivel)
                .anyMatch(leito -> mesmoHospital(leito, solicitacao));
    }

    private boolean mesmoHospital(Leito leito, Solicitacao solicitacao) {
        return leito.getHospital().getNome().equals(solicitacao.getHospital().getNome());
    }

    public boolean liberarLeito(int numero) {
        boolean liberado = leitos.stream()
                .filter(leito -> leito.getNumero() == numero && !leito.estaDisponivel())
                .findFirst()
                .map(leito -> {
                    lancamentos.add("Leito " + numero + " liberado.");
                    leito.liberar();
                    return true;
                })
                .orElse(false);
        if (liberado) {
            regularSolicitacoes();
        }
        return liberado;
    }

    public long totalLeitosDisponiveis() {
        return leitos.stream().filter(Leito::estaDisponivel).count();
    }

    public long totalLeitosOcupados() {
        return leitos.stream().filter(leito -> !leito.estaDisponivel()).count();
    }

    public List<Leito> getLeitos() {
        return Collections.unmodifiableList(leitos);
    }

    public List<Solicitacao> getSolicitacoesPendentes() {
        return List.copyOf(fila);
    }

    public List<String> getLancamentos() {
        return Collections.unmodifiableList(lancamentos);
    }
}
