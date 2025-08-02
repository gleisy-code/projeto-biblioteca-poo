package faculdade.projetoparapoo.view;

import faculdade.projetoparapoo.controlador.EmprestimoControle;
import faculdade.projetoparapoo.controlador.PagamentoControle;
import faculdade.projetoparapoo.modelo.Emprestimo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Painel para a gestão e pagamento de multas.
 */
public class GerirMultasPanel extends JPanel {

    private final PagamentoControle pagamentoControle;
    private final EmprestimoControle emprestimoControle;
    
    private JList<Emprestimo> listaMultas;
    private DefaultListModel<Emprestimo> modeloLista;
    
    private JButton botaoPagarMulta;

    public GerirMultasPanel(PagamentoControle pagamentoControle, EmprestimoControle emprestimoControle) {
        this.pagamentoControle = pagamentoControle;
        this.emprestimoControle = emprestimoControle;

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        setBorder(BorderFactory.createTitledBorder("Gerir Multas Pendentes"));
        
        modeloLista = new DefaultListModel<>();
        listaMultas = new JList<>(modeloLista);
        listaMultas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaMultas.setCellRenderer(new MultaListCellRenderer());
        JScrollPane scrollPane = new JScrollPane(listaMultas);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botaoPagarMulta = new JButton("Marcar Multa como Paga");
        painelBotoes.add(botaoPagarMulta);
        
        add(scrollPane, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
        
        // Ação do botão
        botaoPagarMulta.addActionListener(e -> {
            Emprestimo emprestimoSelecionado = listaMultas.getSelectedValue();
            if (emprestimoSelecionado != null) {
                pagarMulta(emprestimoSelecionado);
            } else {
                JOptionPane.showMessageDialog(GerirMultasPanel.this, "Por favor, selecione um empréstimo com multa.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        carregarMultasPendentes();
    }
    
    public void pagarMulta(Emprestimo emprestimo) {
        if (pagamentoControle.registrarPagamento(emprestimo.getId())) {
            JOptionPane.showMessageDialog(this, "Multa do empréstimo #" + emprestimo.getId() + " paga com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarMultasPendentes();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao registrar o pagamento da multa. O valor da multa é 0 ou já foi pago.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void carregarMultasPendentes() {
        modeloLista.clear();
        List<Emprestimo> todosEmprestimos = emprestimoControle.buscarTodosEmprestimos();
        List<Emprestimo> multasPendentes = todosEmprestimos.stream()
                .filter(e -> e.getMulta() > 0 && !e.isMultaPaga())
                .collect(Collectors.toList());
                
        for (Emprestimo e : multasPendentes) {
            modeloLista.addElement(e);
        }
    }

    /**
     * Renderizador de células personalizado para a lista de multas.
     */
    private static class MultaListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Emprestimo) {
                Emprestimo emprestimo = (Emprestimo) value;
                String texto = "ID #" + emprestimo.getId() + 
                               " | Usuário: " + emprestimo.getUsuario().getNome() +
                               " | Obra: " + emprestimo.getObraEmprestada().getTitulo() +
                               " | Multa: R$" + String.format("%.2f", emprestimo.getMulta());
                setText(texto);
            }
            return this;
        }
    }
}