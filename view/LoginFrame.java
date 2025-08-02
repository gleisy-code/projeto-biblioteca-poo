package faculdade.projetoparapoo.view;

import faculdade.projetoparapoo.controlador.EmprestimoControle;
import faculdade.projetoparapoo.controlador.ObraControle;
import faculdade.projetoparapoo.controlador.PagamentoControle;
import faculdade.projetoparapoo.controlador.UsuarioControle;
import faculdade.projetoparapoo.modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

/**
 * Frame para a tela de login.
 */
public class LoginFrame extends JFrame {
    private final UsuarioControle usuarioControle;
    private JTextField campoMatricula;
    private JPasswordField campoSenha;
    private JButton botaoLogin;

    public LoginFrame(UsuarioControle usuarioControle) {
        this.usuarioControle = usuarioControle;

        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza o frame

        // Usando GridBagLayout para um layout mais organizado
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campo de Matrícula
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Matrícula:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        campoMatricula = new JTextField(15);
        add(campoMatricula, gbc);

        // Campo de Senha
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        campoSenha = new JPasswordField(15);
        add(campoSenha, gbc);

        // Botão de Login
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        botaoLogin = new JButton("Entrar");
        add(botaoLogin, gbc);

        // Ação do botão de login
        botaoLogin.addActionListener(e -> {
            String matricula = campoMatricula.getText();
            String senha = new String(campoSenha.getPassword());

            Optional<Usuario> usuarioLogado = this.usuarioControle.login(matricula, senha);

            if (usuarioLogado.isPresent()) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                this.dispose(); // Fecha a tela de login

                // Inicializa o MainFrame
                ObraControle obraControle = new ObraControle();
                EmprestimoControle emprestimoControle = new EmprestimoControle(usuarioControle, obraControle);
                PagamentoControle pagamentoControle = new PagamentoControle(emprestimoControle);
                MainFrame mainFrame = new MainFrame(usuarioControle, obraControle, emprestimoControle, pagamentoControle);
                mainFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Matrícula ou senha inválida.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}