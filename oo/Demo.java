import model.CentralRegulacao;
import model.Leito;
import model.Paciente;
import model.Urgente;
import model.VagaZero;

public class Demo {
    public static void main(String[] args) {
        CentralRegulacao central = new CentralRegulacao();
        central.adicionarLeito(new Leito(101, "Clínico"));
        central.adicionarLeito(new Leito(102, "UTI"));
        central.adicionarSolicitacao(
                new Urgente(new Paciente("Maria", "CPF", "529.982.247-25", 34,
                        "Dor no peito"), "Clínica médica"));
        central.adicionarSolicitacao(
                new VagaZero(new Paciente("João", "RG", "12.345.678-9", 58,
                        "Falta de ar"), "UTI"));

        central.regularSolicitacoes();
        central.getLeitos().forEach(System.out::println);
        central.liberarLeito(101);
        System.out.println("Após a liberação:");
        central.getLeitos().forEach(System.out::println);
    }
}
