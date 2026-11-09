package model;

public class Leito {
    private final int numero;
    private final String tipo;
    private final Hospital hospital;
    private Paciente paciente;

    public Leito(int numero, String tipo) {
        this(numero, tipo, new Hospital("Hospital Central", "Cidade"));
    }

    public Leito(int numero, String tipo, Hospital hospital) {
        if (numero <= 0 || tipo == null || tipo.isBlank() || hospital == null) {
            throw new IllegalArgumentException("Número e tipo do leito são obrigatórios.");
        }
        this.numero = numero;
        this.tipo = tipo;
        this.hospital = hospital;
    }

    public int getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public boolean estaDisponivel() {
        return paciente == null;
    }

    public void ocupar(Paciente paciente) {
        if (paciente == null || !estaDisponivel()) {
            throw new IllegalStateException("O leito não está disponível.");
        }
        this.paciente = paciente;
    }

    public void liberar() {
        paciente = null;
    }

    @Override
    public String toString() {
        return "Leito " + numero + " - " + hospital.getNome() + " (" + tipo + ") - "
                + (estaDisponivel() ? "disponível" : "ocupado por " + paciente.getNome());
    }
}
