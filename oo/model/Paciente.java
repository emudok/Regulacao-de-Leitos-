package model;

public class Paciente {
    private final String nome;
    private final String documento;
    private final String tipoDocumento;
    private final int idade;
    private final String sintomas;

    public Paciente(String nome, String tipoDocumento, String documento, int idade,
            String sintomas) {
        if (nome == null || nome.isBlank() || documento == null || documento.isBlank()
                || idade < 0 || idade > 130 || sintomas == null || sintomas.isBlank()
                || tipoDocumento == null || tipoDocumento.isBlank()) {
            throw new IllegalArgumentException(
                    "Informe nome, documento, idade válida e os sintomas do paciente.");
        }
        validarDocumento(tipoDocumento, documento);
        this.nome = nome;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.idade = idade;
        this.sintomas = sintomas;
    }

    public String getNome() { return nome; }
    public String getDocumento() { return documento; }
    public String getTipoDocumento() { return tipoDocumento; }
    public int getIdade() { return idade; }
    public String getSintomas() { return sintomas; }

    private void validarDocumento(String tipo, String valor) {
        String numeros = valor.replaceAll("\\D", "");
        if ("CPF".equals(tipo) && !cpfValido(numeros)) {
            throw new IllegalArgumentException("Informe um CPF válido.");
        }
        if ("RG".equals(tipo) && (numeros.length() < 5 || numeros.length() > 12)) {
            throw new IllegalArgumentException("Informe um RG válido.");
        }
    }

    private boolean cpfValido(String cpf) {
        if (cpf.length() != 11 || cpf.chars().distinct().count() == 1) {
            return false;
        }
        int primeiro = calcularDigito(cpf, 9);
        int segundo = calcularDigito(cpf, 10);
        return primeiro == Character.digit(cpf.charAt(9), 10)
                && segundo == Character.digit(cpf.charAt(10), 10);
    }

    private int calcularDigito(String cpf, int posicao) {
        int soma = 0;
        int peso = posicao + 1;
        for (int indice = 0; indice < posicao; indice++) {
            soma += Character.digit(cpf.charAt(indice), 10) * peso--;
        }
        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }

    @Override
    public String toString() {
        return nome + " (" + tipoDocumento + ": " + documento + ")";
    }
}
