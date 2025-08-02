package faculdade.projetoparapoo.view;

import faculdade.projetoparapoo.controlador.ObraControle;
import faculdade.projetoparapoo.modelo.Obra;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Painel para gerenciar obras (livros e artigos).
 */
public class GerenciarObrasPanel extends JPanel {

    private final ObraControle obraControle;
    private DefaultTableModel tableModel;
    private JTable tabelaObras;
    private JTabbedPane abasObras;
    private JButton botaoRemover;

    public GerenciarObrasPanel(ObraControle obraControle) {
        this.obraControle = obraControle;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Abas para diferentes tipos de cadastro
        abasObras = new JTabbedPane();
        abasObras.addTab("Adicionar Livro", criarPainelAdicionarLivro());
        abasObras.addTab("Adicionar Artigo", criarPainelAdicionarArtigo());

        // Painel de listagem de obras
        JPanel painelTabela = new JPanel(new BorderLayout(5, 5));
        painelTabela.setBorder(BorderFactory.createTitledBorder("Obras Cadastradas"));

        String[] colunas = {"Código", "Título", "Autor", "Ano", "Quantidade"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaObras = new JTable(tableModel);
        painelTabela.add(new JScrollPane(tabelaObras), BorderLayout.CENTER);

        botaoRemover = new JButton("Remover Obra Selecionada");
        painelTabela.add(botaoRemover, BorderLayout.SOUTH);

        add(abasObras, BorderLayout.NORTH);
        add(painelTabela, BorderLayout.CENTER);

        // Ação do botão de remover
        botaoRemover.addActionListener(e -> removerObra());

        carregarObras();
    }

    private JPanel criarPainelAdicionarLivro() {
        JPanel painel = new JPanel(new GridLayout(6, 2, 5, 5));
        painel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField campoTitulo = new JTextField();
        JTextField campoAutor = new JTextField();
        JTextField campoIsbn = new JTextField();
        JTextField campoAno = new JTextField();
        JTextField campoQuantidade = new JTextField();

        painel.add(new JLabel("Título:"));
        painel.add(campoTitulo);
        painel.add(new JLabel("Autor:"));
        painel.add(campoAutor);
        painel.add(new JLabel("ISBN:"));
        painel.add(campoIsbn);
        painel.add(new JLabel("Ano de Publicação:"));
        painel.add(campoAno);
        painel.add(new JLabel("Quantidade:"));
        painel.add(campoQuantidade);

        JButton botaoAdicionar = new JButton("Adicionar Livro");
        painel.add(botaoAdicionar);

        botaoAdicionar.addActionListener(e -> {
            try {
                String titulo = campoTitulo.getText();
                String autor = campoAutor.getText();
                String isbn = campoIsbn.getText();
                int ano = Integer.parseInt(campoAno.getText());
                int quantidade = Integer.parseInt(campoQuantidade.getText());

                obraControle.adicionarObra("livro", titulo, autor, isbn, ano, quantidade);
                JOptionPane.showMessageDialog(this, "Livro adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarObras();
                campoTitulo.setText(""); campoAutor.setText(""); campoIsbn.setText("");
                campoAno.setText(""); campoQuantidade.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, insira valores válidos para Ano e Quantidade.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        return painel;
    }

    private JPanel criarPainelAdicionarArtigo() {
        JPanel painel = new JPanel(new GridLayout(5, 2, 5, 5));
        painel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField campoTitulo = new JTextField();
        JTextField campoAutor = new JTextField();
        JTextField campoDoi = new JTextField();
        JTextField campoAno = new JTextField();

        painel.add(new JLabel("Título:"));
        painel.add(campoTitulo);
        painel.add(new JLabel("Autor:"));
        painel.add(campoAutor);
        painel.add(new JLabel("DOI:"));
        painel.add(campoDoi);
        painel.add(new JLabel("Ano de Publicação:"));
        painel.add(campoAno);

        JButton botaoAdicionar = new JButton("Adicionar Artigo");
        painel.add(botaoAdicionar);

        botaoAdicionar.addActionListener(e -> {
            try {
                String titulo = campoTitulo.getText();
                String autor = campoAutor.getText();
                String doi = campoDoi.getText();
                int ano = Integer.parseInt(campoAno.getText());

                obraControle.adicionarObra("artigo", titulo, autor, doi, ano, 1);
                JOptionPane.showMessageDialog(this, "Artigo adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarObras();
                campoTitulo.setText(""); campoAutor.setText(""); campoDoi.setText(""); campoAno.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um valor válido para o Ano.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        return painel;
    }

    private void removerObra() {
        int linhaSelecionada = tabelaObras.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma obra para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String codigo = (String) tabelaObras.getValueAt(linhaSelecionada, 0);
        obraControle.removerObraPorCodigo(codigo);
        JOptionPane.showMessageDialog(this, "Obra removida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        carregarObras();
    }

    private void carregarObras() {
        tableModel.setRowCount(0); // Limpa a tabela
        List<Obra> obras = obraControle.buscarTodasObras();
        for (Obra o : obras) {
            tableModel.addRow(new Object[]{o.getCodigo(), o.getTitulo(), o.getAutor(), o.getAnoPublicacao(), o.getQuantidade()});
        }
    }
}