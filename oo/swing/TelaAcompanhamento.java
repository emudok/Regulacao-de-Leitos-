package swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.CentralRegulacao;
import model.Leito;

public class TelaAcompanhamento extends JFrame {
    private final CentralRegulacao central;
    private final JFrame telaSolicitacao;
    private final DefaultListModel<String> modeloLeitos = new DefaultListModel<>();
    private final DefaultListModel<String> modeloLancamentos = new DefaultListModel<>();
    private final DefaultListModel<String> modeloPendentes = new DefaultListModel<>();
    private final JList<String> listaLeitos = new JList<>(modeloLeitos);
    private final JLabel resumo = new JLabel();

    public TelaAcompanhamento(CentralRegulacao central, JFrame telaSolicitacao) {
        super("Tela 2 - Acompanhamento da regulação");
        this.central = central;
        this.telaSolicitacao = telaSolicitacao;
        setLayout(new BorderLayout(10, 10));

        JPanel topo = new JPanel(new GridLayout(1, 3, 8, 8));
        JButton regular = new JButton("Processar fila");
        regular.addActionListener(event -> processarFila());
        JButton liberar = new JButton("Liberar leito selecionado");
        liberar.addActionListener(event -> liberarSelecionado());
        JButton novoLancamento = new JButton("Voltar para lançamentos");
        novoLancamento.addActionListener(event -> {
            telaSolicitacao.setVisible(true);
            dispose();
        });
        topo.add(regular);
        topo.add(liberar);
        topo.add(novoLancamento);
        add(topo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(1, 3, 10, 10));
        centro.add(new JScrollPane(listaLeitos));
        centro.add(new JScrollPane(new JList<>(modeloLancamentos)));
        centro.add(new JScrollPane(new JList<>(modeloPendentes)));
        add(centro, BorderLayout.CENTER);
        add(new JLabel("Leitos (selecione para liberar) | Histórico | Aguardando vaga"),
                BorderLayout.SOUTH);
        add(resumo, BorderLayout.WEST);

        atualizar();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(780, 360);
        setLocationRelativeTo(null);
    }

    private void processarFila() {
        int total = central.regularSolicitacoes();
        atualizar();
        JOptionPane.showMessageDialog(this, total + " solicitação(ões) regulada(s).");
    }

    private void liberarSelecionado() {
        int indice = listaLeitos.getSelectedIndex();
        if (indice < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um leito.");
            return;
        }
        Leito leito = central.getLeitos().get(indice);
        if (!central.liberarLeito(leito.getNumero())) {
            JOptionPane.showMessageDialog(this, "Esse leito já está disponível.");
            return;
        }
        atualizar();
        JOptionPane.showMessageDialog(this,
                "Leito liberado. A fila foi processada automaticamente.");
    }

    private void atualizar() {
        modeloLeitos.clear();
        central.getLeitos().forEach(leito -> modeloLeitos.addElement(leito.toString()));
        modeloLancamentos.clear();
        central.getLancamentos().forEach(modeloLancamentos::addElement);
        modeloLancamentos.addElement("Pendentes: "
                + central.getSolicitacoesPendentes().size());
        modeloPendentes.clear();
        central.getSolicitacoesPendentes().forEach(solicitacao ->
                modeloPendentes.addElement(solicitacao.toString()));
        resumo.setText(String.format("Disponíveis: %d | Ocupados: %d | Pendentes: %d",
                central.totalLeitosDisponiveis(), central.totalLeitosOcupados(),
                central.getSolicitacoesPendentes().size()));
    }
}
