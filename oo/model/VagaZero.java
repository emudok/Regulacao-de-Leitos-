package model;

public class VagaZero extends Solicitacao {
    public VagaZero(Paciente paciente, String especialidade) {
        super(paciente, especialidade, 3);
    }

    public VagaZero(Paciente paciente, String especialidade, Hospital hospital) {
        super(paciente, especialidade, 3, hospital);
    }
}
