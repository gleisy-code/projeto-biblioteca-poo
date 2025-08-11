package faculdade.projetoparapoo.view;

import faculdade.projetoparapoo.controlador.EmprestimoControle;
import faculdade.projetoparapoo.controlador.ObraControle;
import faculdade.projetoparapoo.controlador.PagamentoControle;
import faculdade.projetoparapoo.controlador.UsuarioControle;
import faculdade.projetoparapoo.modelo.Usuario;

import javax.swing.*;
import java.awt.*;

/**
 * Frame principal da aplicação que contém as abas de gerenciamento.
 */
public class MainFrame extends JFrame {
    private Usuario usuarioLogado;
    public MainFrame(Usuario usuarioLogado, UsuarioControle usuarioControle, ObraControle obraControle, EmprestimoControle emprestimoControle, PagamentoControle pagamentoControle) {
        this.usuarioLogado=usuarioLogado;
        String perfil = usuarioLogado.getMatricula();

        setTitle("Sistema de Gerenciamento de Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel gerenciarUsuariosPanel = new GerenciarUsuariosPanel(usuarioControle, perfil);
        JPanel gerenciarObrasPanel = new GerenciarObrasPanel(obraControle,perfil);

        GerirMultasPanel painelMultas = new GerirMultasPanel(pagamentoControle, emprestimoControle,perfil);

        GerirEmprestimosPanel gerirEmprestimosPanel = new GerirEmprestimosPanel(
            emprestimoControle,
            obraControle,
            usuarioControle,
            painelMultas, 
            perfil
        );

        tabbedPane.addTab("Gerenciar Usuários", gerenciarUsuariosPanel);
        tabbedPane.addTab("Gerenciar Obras", gerenciarObrasPanel);
        tabbedPane.addTab("Gerir Empréstimos", gerirEmprestimosPanel);
        tabbedPane.addTab("Gerir Multas", painelMultas);


        if ("Admin".equalsIgnoreCase(perfil)) {
            tabbedPane.addTab("Gerenciar Usuários", gerenciarUsuariosPanel);
            tabbedPane.addTab("Gerenciar Obras", gerenciarObrasPanel);
            tabbedPane.addTab("Gerir Empréstimos", gerirEmprestimosPanel);
            tabbedPane.addTab("Gerir Multas", painelMultas);
        } else if ("Bibl".equalsIgnoreCase(perfil)) {
            tabbedPane.addTab("Gerir Empréstimos", gerirEmprestimosPanel);
            tabbedPane.addTab("Gerir Multas", painelMultas);
            // Não adiciona "Gerenciar Usuários" nem "Gerenciar Obras"
        } else if ("Estag".equalsIgnoreCase(perfil)) {
        // Apenas devoluções — como só tem a aba de empréstimos, pode desabilitar o botão de empréstimo e habilitar só devolução
            tabbedPane.addTab("Gerir Empréstimos", gerirEmprestimosPanel);
            // Remover ou esconder botões em gerirEmprestimosPanel para bloquear empréstimos
        } else {
        // Caso não reconheça perfil, não deixa mexer em nada (ou fecha o sistema)
        JOptionPane.showMessageDialog(null, "Perfil de usuário inválido. Encerrando o sistema.");
        System.exit(0);
        }
        add(tabbedPane);

        // Listener para reabrir o login ao fechar o MainFrame
        this.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
            LoginFrame loginFrame = new LoginFrame(
                usuarioControle,
                obraControle,
                emprestimoControle,
                pagamentoControle
            );
            loginFrame.setVisible(true);
        }
    });

    }
    
}
    