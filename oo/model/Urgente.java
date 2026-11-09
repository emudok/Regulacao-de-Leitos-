package model;

public class Urgente extends Solicitacao {
    public Urgente(Paciente paciente, String especialidade) {
        super(paciente, especialidade, 2);
    }

    public Urgente(Paciente paciente, String especialidade, Hospital hospital) {
        super(paciente, especialidade, 2, hospital);
    }
}
