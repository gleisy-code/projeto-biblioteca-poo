package faculdade.projetoparapoo.view;

import faculdade.projetoparapoo.controlador.UsuarioControle;
import faculdade.projetoparapoo.modelo.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Painel para gerenciar usuários (adicionar, listar, remover).
 */
public class GerenciarUsuariosPanel extends JPanel {

    private final UsuarioControle usuarioControle;
    private DefaultTableModel tableModel;
    private JTable tabelaUsuarios;
    private JTextField campoMatricula, campoNome;
    private JPasswordField campoSenha;
    private JButton botaoAdicionar, botaoRemover;

    public GerenciarUsuariosPanel(UsuarioControle usuarioControle,String perfilUsuario) {
        this.usuarioControle = usuarioControle;

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Painel para o formulário de cadastro de usuário
        JPanel painelFormulario = new JPanel(new GridLayout(4, 2, 5, 5));
        painelFormulario.setBorder(BorderFactory.createTitledBorder("Cadastrar Novo Usuário"));

        painelFormulario.add(new JLabel("Matrícula:"));
        campoMatricula = new JTextField(15);
        painelFormulario.add(campoMatricula);

        painelFormulario.add(new JLabel("Nome:"));
        campoNome = new JTextField(15);
        painelFormulario.add(campoNome);

        painelFormulario.add(new JLabel("Senha:"));
        campoSenha = new JPasswordField(15);
        painelFormulario.add(campoSenha);

        botaoAdicionar = new JButton("Adicionar Usuário");
        painelFormulario.add(botaoAdicionar);

        // Painel de listagem de usuários
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createTitledBorder("Usuários Cadastrados"));

        String[] colunas = {"Matrícula", "Nome"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaUsuarios = new JTable(tableModel);
        painelTabela.add(new JScrollPane(tabelaUsuarios), BorderLayout.CENTER);

        botaoRemover = new JButton("Remover Usuário Selecionado");
        painelTabela.add(botaoRemover, BorderLayout.SOUTH);

        add(painelFormulario, BorderLayout.NORTH);
        add(painelTabela, BorderLayout.CENTER);

        // Ações dos botões
        botaoAdicionar.addActionListener(e -> adicionarUsuario());
        botaoRemover.addActionListener(e -> removerUsuario());

        if ("Estag".equalsIgnoreCase( perfilUsuario)||"bibl".equalsIgnoreCase(perfilUsuario)) {
            botaoAdicionar.setEnabled(false); //tira o direito de adicionar e tirar
            botaoRemover.setEnabled(false);
        }
        carregarUsuarios();
    }

    private void adicionarUsuario() {
        String matricula = campoMatricula.getText();
        String nome = campoNome.getText();
        String senha = new String(campoSenha.getPassword());

        if (matricula.isEmpty() || nome.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario novoUsuario = new Usuario(matricula, senha, nome);
        usuarioControle.adicionarUsuario(novoUsuario);
        JOptionPane.showMessageDialog(this, "Usuário adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        limparCampos();
        carregarUsuarios();
    }

    private void removerUsuario() {
        int linhaSelecionada = tabelaUsuarios.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String matricula = (String) tabelaUsuarios.getValueAt(linhaSelecionada, 0);
        usuarioControle.removerUsuarioPorMatricula(matricula);
        JOptionPane.showMessageDialog(this, "Usuário removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        carregarUsuarios();
    }

    private void carregarUsuarios() {
        tableModel.setRowCount(0); // Limpa a tabela
        List<Usuario> usuarios = usuarioControle.buscarTodosUsuarios();
        for (Usuario u : usuarios) {
            tableModel.addRow(new Object[]{u.getMatricula(), u.getNome()});
        }
    }

    private void limparCampos() {
        campoMatricula.setText("");
        campoNome.setText("");
        campoSenha.setText("");
    }
}
