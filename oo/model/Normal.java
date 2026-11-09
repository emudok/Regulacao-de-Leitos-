package model;

public class Normal extends Solicitacao {
    public Normal(Paciente paciente, String especialidade) {
        super(paciente, especialidade, 1);
    }

    public Normal(Paciente paciente, String especialidade, Hospital hospital) {
        super(paciente, especialidade, 1, hospital);
    }
}
