package model;

public abstract class Solicitacao implements Comparable<Solicitacao> {
    private final Paciente paciente;
    private final String especialidade;
    private final int prioridade;
    private final Hospital hospital;

    protected Solicitacao(Paciente paciente, String especialidade, int prioridade) {
        this(paciente, especialidade, prioridade,
                new Hospital("Hospital Central", "Cidade"));
    }

    protected Solicitacao(Paciente paciente, String especialidade, int prioridade,
            Hospital hospital) {
        if (paciente == null || especialidade == null || especialidade.isBlank()
                || hospital == null) {
            throw new IllegalArgumentException("Paciente e especialidade são obrigatórios.");
        }
        this.paciente = paciente;
        this.especialidade = especialidade;
        this.prioridade = prioridade;
        this.hospital = hospital;
    }

    public Paciente getPaciente() { return paciente; }
    public String getEspecialidade() { return especialidade; }
    public int getPrioridade() { return prioridade; }
    public Hospital getHospital() { return hospital; }

    @Override
    public int compareTo(Solicitacao outra) {
        return Integer.compare(outra.prioridade, prioridade);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " - " + paciente.getNome()
                + " - " + especialidade + " - destino: " + hospital.getNome();
    }
}
