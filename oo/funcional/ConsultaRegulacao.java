package funcional;

import java.util.List;
import java.util.stream.Collectors;

import model.Solicitacao;

public final class ConsultaRegulacao {
    private ConsultaRegulacao() {
    }

    public static List<Solicitacao> ordenarPorPrioridade(List<Solicitacao> solicitacoes) {
        return solicitacoes.stream()
                .sorted((primeira, segunda) -> Integer.compare(
                        segunda.getPrioridade(), primeira.getPrioridade()))
                .collect(Collectors.toList());
    }

    public static List<Solicitacao> filtrarPorEspecialidade(
            List<Solicitacao> solicitacoes, String especialidade) {
        return solicitacoes.stream()
                .filter(solicitacao -> solicitacao.getEspecialidade().equalsIgnoreCase(especialidade))
                .collect(Collectors.toList());
    }
}
