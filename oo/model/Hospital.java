package model;

public class Hospital {
    private final String nome;
    private final String cidade;

    public Hospital(String nome, String cidade) {
        if (nome == null || nome.isBlank() || cidade == null || cidade.isBlank()) {
            throw new IllegalArgumentException("Nome e cidade do hospital são obrigatórios.");
        }
        this.nome = nome;
        this.cidade = cidade;
    }

    public String getNome() {
        return nome;
    }

    public String getCidade() {
        return cidade;
    }

    @Override
    public String toString() {
        return nome + " - " + cidade;
    }
}
