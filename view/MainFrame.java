package faculdade.projetoparapoo.view;

import faculdade.projetoparapoo.controlador.EmprestimoControle;
import faculdade.projetoparapoo.controlador.ObraControle;
import faculdade.projetoparapoo.controlador.PagamentoControle;
import faculdade.projetoparapoo.controlador.UsuarioControle;

import javax.swing.*;
import java.awt.*;

/**
 * Frame principal da aplicação que contém as abas de gerenciamento.
 */
public class MainFrame extends JFrame {
    public MainFrame(UsuarioControle usuarioControle, ObraControle obraControle, EmprestimoControle emprestimoControle, PagamentoControle pagamentoControle) {
        setTitle("Sistema de Gerenciamento de Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza o frame na tela

        JTabbedPane tabbedPane = new JTabbedPane();

        // Criação dos painéis
        JPanel gerenciarUsuariosPanel = new GerenciarUsuariosPanel(usuarioControle);
        JPanel gerenciarObrasPanel = new GerenciarObrasPanel(obraControle);
        JPanel gerirEmprestimosPanel = new GerirEmprestimosPanel(emprestimoControle, obraControle, usuarioControle);
        JPanel gerirMultasPanel = new GerirMultasPanel(pagamentoControle, emprestimoControle);

        // Adicionando os painéis como abas
        tabbedPane.addTab("Gerenciar Usuários", gerenciarUsuariosPanel);
        tabbedPane.addTab("Gerenciar Obras", gerenciarObrasPanel);
        tabbedPane.addTab("Gerir Empréstimos", gerirEmprestimosPanel);
        tabbedPane.addTab("Gerir Multas", gerirMultasPanel);

        add(tabbedPane);
    }
}