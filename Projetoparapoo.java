package faculdade.projetoparapoo;

import faculdade.projetoparapoo.controlador.EmprestimoControle;
import faculdade.projetoparapoo.controlador.ObraControle;
import faculdade.projetoparapoo.controlador.PagamentoControle;
import faculdade.projetoparapoo.controlador.UsuarioControle;
import faculdade.projetoparapoo.modelo.Usuario;
import faculdade.projetoparapoo.view.LoginFrame;
import faculdade.projetoparapoo.view.MainFrame;

import javax.swing.*;

/**
 * Classe principal para inicializar a aplicação de gerenciamento de biblioteca.
 */
public class Projetoparapoo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Inicializando os controladores
            UsuarioControle usuarioControle = new UsuarioControle();
            ObraControle obraControle = new ObraControle();
            EmprestimoControle emprestimoControle = new EmprestimoControle(usuarioControle, obraControle);
            PagamentoControle pagamentoControle = new PagamentoControle(emprestimoControle);

            // Adicionando alguns dados de exemplo
            usuarioControle.adicionarUsuario(new Usuario("admin", "123", "Administrador"));
            obraControle.adicionarObra("livro", "O Senhor dos Anéis", "J.R.R. Tolkien", "978-0618053267", 1954, 5);

            LoginFrame loginFrame = new LoginFrame(usuarioControle);
            loginFrame.setVisible(true);

            // O MainFrame será exibido após o login bem-sucedido.
        });
    }
}
