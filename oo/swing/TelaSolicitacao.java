package swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.CentralRegulacao;
import model.Hospital;
import model.Normal;
import model.Paciente;
import model.Solicitacao;
import model.Urgente;
import model.VagaZero;

public class TelaSolicitacao extends JFrame {
    private final CentralRegulacao central;
    private final JTextField nome = new JTextField();
    private final JTextField documento = new JTextField();
    private final JTextField idade = new JTextField();
    private final JTextField sintomas = new JTextField();
    private final JComboBox<String> tipoDocumento =
            new JComboBox<>(new String[]{"CPF", "RG"});
    private final JComboBox<String> especialidade = new JComboBox<>(
            new String[]{"Clínica médica", "Cardiologia", "Ortopedia",
                    "Pediatria", "Neurologia", "UTI"});
    private final JComboBox<Hospital> hospital = new JComboBox<>(new Hospital[]{
            new Hospital("Hospital Central", "Cidade"),
            new Hospital("Hospital Regional", "Cidade")
    });
    private final JComboBox<String> prioridade =
            new JComboBox<>(new String[]{"Normal", "Urgente", "Vaga Zero"});

    public TelaSolicitacao(CentralRegulacao central) {
        super("Tela 1 - Lançamento de solicitação");
        this.central = central;
        setLayout(new BorderLayout(10, 10));

        JPanel campos = new JPanel(new GridLayout(8, 2, 8, 8));
        campos.add(new JLabel("Paciente:"));
        campos.add(nome);
        campos.add(new JLabel("Tipo de documento:"));
        campos.add(tipoDocumento);
        campos.add(new JLabel("Documento:"));
        campos.add(documento);
        campos.add(new JLabel("Especialidade:"));
        campos.add(especialidade);
        campos.add(new JLabel("Idade:"));
        campos.add(idade);
        campos.add(new JLabel("O que o paciente está sentindo?"));
        campos.add(sintomas);
        campos.add(new JLabel("Prioridade:"));
        campos.add(prioridade);
        campos.add(new JLabel("Hospital de destino:"));
        campos.add(hospital);
        add(campos, BorderLayout.CENTER);

        JButton lancar = new JButton("Lançar solicitação");
        lancar.addActionListener(event -> lancarSolicitacao());
        JButton acompanhar = new JButton("Abrir acompanhamento");
        acompanhar.addActionListener(event -> abrirAcompanhamento());
        JPanel botoes = new JPanel();
        botoes.add(lancar);
        botoes.add(acompanhar);
        add(botoes, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(620, 420);
        setLocationRelativeTo(null);
    }

    private void lancarSolicitacao() {
        try {
            int idadeInformada = Integer.parseInt(idade.getText().trim());
            Paciente paciente = new Paciente(nome.getText(),
                    (String) tipoDocumento.getSelectedItem(), documento.getText(),
                    idadeInformada, sintomas.getText());
            Solicitacao solicitacao = criarSolicitacao(paciente);
            central.adicionarSolicitacao(solicitacao);
            JOptionPane.showMessageDialog(this, "Lançamento realizado com sucesso.");
            nome.setText("");
            documento.setText("");
            idade.setText("");
            sintomas.setText("");
        } catch (NumberFormatException error) {
            JOptionPane.showMessageDialog(this, "A idade deve ser um número.",
                    "Dados inválidos", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException error) {
            JOptionPane.showMessageDialog(this, error.getMessage(), "Dados inválidos",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private Solicitacao criarSolicitacao(Paciente paciente) {
        String tipo = (String) prioridade.getSelectedItem();
        Hospital destino = (Hospital) hospital.getSelectedItem();
        if ("Vaga Zero".equals(tipo)) {
            return new VagaZero(paciente, (String) especialidade.getSelectedItem(), destino);
        }
        if ("Urgente".equals(tipo)) {
            return new Urgente(paciente, (String) especialidade.getSelectedItem(), destino);
        }
        return new Normal(paciente, (String) especialidade.getSelectedItem(), destino);
    }

    private void abrirAcompanhamento() {
        new TelaAcompanhamento(central, this).setVisible(true);
    }

    public static void main(String[] args) {
        CentralRegulacao central = new CentralRegulacao();
        Hospital centralHospital = new Hospital("Hospital Central", "Cidade");
        central.adicionarLeito(new model.Leito(101, "Clínico", centralHospital));
        central.adicionarLeito(new model.Leito(102, "UTI", centralHospital));
        central.adicionarLeito(new model.Leito(201, "Clínico",
                new Hospital("Hospital Regional", "Cidade")));
        new TelaSolicitacao(central).setVisible(true);
    }
}
