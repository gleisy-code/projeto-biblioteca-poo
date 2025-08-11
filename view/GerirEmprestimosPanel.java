package faculdade.projetoparapoo.view;

import faculdade.projetoparapoo.controlador.EmprestimoControle;
import faculdade.projetoparapoo.controlador.ObraControle;
import faculdade.projetoparapoo.controlador.UsuarioControle;
import faculdade.projetoparapoo.exceptions.ObraNaoEncontradaException;
import faculdade.projetoparapoo.exceptions.UsuarioNaoEncontradoException;
import faculdade.projetoparapoo.modelo.Emprestimo;
import faculdade.projetoparapoo.modelo.Obra;
import faculdade.projetoparapoo.modelo.Usuario;
import faculdade.projetoparapoo.view.GerirMultasPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

/**
 * Painel para a gestão de empréstimos e devoluções.
 */
public class GerirEmprestimosPanel extends JPanel {

    private final EmprestimoControle emprestimoControle;
    private final ObraControle obraControle;
    private final UsuarioControle usuarioControle;
    private final GerirMultasPanel painelMultas;

    private JTextField campoMatriculaEmprestimo;
    private JTextField campoCodigoObraEmprestimo;
    private JButton botaoRealizarEmprestimo;

    private JTextField campoIdEmprestimoDevolucao;
    private JTextField campoDataDevolucaoReal;
    private JButton botaoDevolverEmprestimo;

    private JTextArea areaEmprestimos;

    public GerirEmprestimosPanel(EmprestimoControle emprestimoControle, ObraControle obraControle, UsuarioControle usuarioControle, GerirMultasPanel painelMultas, String perfilUsuario) {
        this.emprestimoControle = emprestimoControle;
        this.obraControle = obraControle;
        this.usuarioControle = usuarioControle;
        this.painelMultas=painelMultas;

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Painel superior com os formulários de empréstimo e devolução
        JPanel painelEntrada = new JPanel(new GridLayout(1, 2, 10, 10));

        // Seção para Realizar Empréstimo
        JPanel painelEmprestimo = new JPanel(new GridBagLayout());
        painelEmprestimo.setBorder(BorderFactory.createTitledBorder("Realizar Novo Empréstimo"));
        GridBagConstraints gbcEmprestimo = new GridBagConstraints();
        gbcEmprestimo.insets = new Insets(5, 5, 5, 5);
        gbcEmprestimo.anchor = GridBagConstraints.WEST;

        gbcEmprestimo.gridx = 0; gbcEmprestimo.gridy = 0;
        painelEmprestimo.add(new JLabel("Matrícula do Utilizador:"), gbcEmprestimo);
        gbcEmprestimo.gridx = 1;
        campoMatriculaEmprestimo = new JTextField(10);
        painelEmprestimo.add(campoMatriculaEmprestimo, gbcEmprestimo);

        gbcEmprestimo.gridx = 0; gbcEmprestimo.gridy = 1;
        painelEmprestimo.add(new JLabel("Código da Obra:"), gbcEmprestimo);
        gbcEmprestimo.gridx = 1;
        campoCodigoObraEmprestimo = new JTextField(10);
        painelEmprestimo.add(campoCodigoObraEmprestimo, gbcEmprestimo);

        gbcEmprestimo.gridx = 0; gbcEmprestimo.gridy = 2; gbcEmprestimo.gridwidth = 2;
        gbcEmprestimo.fill = GridBagConstraints.HORIZONTAL;
        botaoRealizarEmprestimo = new JButton("Realizar Empréstimo");
        painelEmprestimo.add(botaoRealizarEmprestimo, gbcEmprestimo);

        // Seção para Devolução
        JPanel painelDevolucao = new JPanel(new GridBagLayout());
        painelDevolucao.setBorder(BorderFactory.createTitledBorder("Devolução de Obra"));
        GridBagConstraints gbcDevolucao = new GridBagConstraints();
        gbcDevolucao.insets = new Insets(5, 5, 5, 5);
        gbcDevolucao.anchor = GridBagConstraints.WEST;

        gbcDevolucao.gridx = 0; gbcDevolucao.gridy = 0;
        painelDevolucao.add(new JLabel("ID do Empréstimo:"), gbcDevolucao);
        gbcDevolucao.gridx = 1;
        campoIdEmprestimoDevolucao = new JTextField(5);
        painelDevolucao.add(campoIdEmprestimoDevolucao, gbcDevolucao);

        gbcDevolucao.gridx = 0; gbcDevolucao.gridy = 1;
        painelDevolucao.add(new JLabel("Data de Devolução (AAAA-MM-DD):"), gbcDevolucao);
        gbcDevolucao.gridx = 1;
        campoDataDevolucaoReal = new JTextField(10);
        painelDevolucao.add(campoDataDevolucaoReal, gbcDevolucao);

        gbcDevolucao.gridx = 0; gbcDevolucao.gridy = 2; gbcDevolucao.gridwidth = 2;
        gbcDevolucao.fill = GridBagConstraints.HORIZONTAL;
        botaoDevolverEmprestimo = new JButton("Devolver");
        painelDevolucao.add(botaoDevolverEmprestimo, gbcDevolucao);

        painelEntrada.add(painelEmprestimo);
        painelEntrada.add(painelDevolucao);

        // Área de texto para exibir a lista de empréstimos
        areaEmprestimos = new JTextArea();
        areaEmprestimos.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaEmprestimos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("RELATORIO: Empréstimos Ativos e Devolvidos"));

        add(painelEntrada, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        if ("Estag".equalsIgnoreCase(perfilUsuario)||"admin".equalsIgnoreCase(perfilUsuario)) {
            scrollPane.setVisible(false);
        }


        // Ações dos botões
        botaoRealizarEmprestimo.addActionListener(e -> realizarEmprestimo());
        botaoDevolverEmprestimo.addActionListener(e -> devolverEmprestimo());
        if ("Estag".equalsIgnoreCase( perfilUsuario)) {
            botaoRealizarEmprestimo.setEnabled(false); // desabilita botão de empréstimo
        }
        if ("admin".equalsIgnoreCase( perfilUsuario)) {
            botaoDevolverEmprestimo.setEnabled(false);
            botaoRealizarEmprestimo.setEnabled(false); // desabilita botão de empréstimo
        }

        carregarEmprestimos();
    }
    
    private void realizarEmprestimo() {
    String matricula = campoMatriculaEmprestimo.getText();
    String codigoObra = campoCodigoObraEmprestimo.getText();

    if (matricula.isEmpty() || codigoObra.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos para realizar o empréstimo.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        if (emprestimoControle.realizarEmprestimo(codigoObra, matricula)) {
            JOptionPane.showMessageDialog(this, "Empréstimo realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarEmprestimos();
            limparCamposEmprestimo();
        } else {
            JOptionPane.showMessageDialog(this, "Não foi possível realizar o empréstimo. Verifique se a obra está disponível e se o usuário não tem multas pendentes.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    } catch (UsuarioNaoEncontradoException e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    } catch (ObraNaoEncontradaException e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}


    private void devolverEmprestimo() {
        String idEmprestimoStr = campoIdEmprestimoDevolucao.getText();
        String dataDevolucaoStr = campoDataDevolucaoReal.getText();
        
        if (idEmprestimoStr.isEmpty() || dataDevolucaoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos para a devolução.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int idEmprestimo = Integer.parseInt(idEmprestimoStr);
            LocalDate dataDevolucaoReal = LocalDate.parse(dataDevolucaoStr);

            Optional<Emprestimo> emprestimoOpt = emprestimoControle.buscarEmprestimoPorId(idEmprestimo);

            if (emprestimoOpt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Empréstimo com o ID '" + idEmprestimo + "' não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (emprestimoControle.devolverEmprestimo(idEmprestimo, dataDevolucaoReal)) {
                double multa = emprestimoOpt.get().getMulta();
                String mensagem = "Devolução realizada com sucesso!";
                if (multa > 0) {
                    mensagem += "\nMulta gerada no valor de R$" + String.format("%.2f", multa) + ".";
                }
                JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarEmprestimos();
                limparCamposDevolucao();
                if (painelMultas != null) {
                painelMultas.carregarMultasPendentes();
            }
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível realizar a devolução. A obra já pode ter sido devolvida.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O ID do empréstimo deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use AAAA-MM-DD.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarEmprestimos() {
        areaEmprestimos.setText("");
        List<Emprestimo> emprestimos = emprestimoControle.buscarTodosEmprestimos();
        emprestimos.forEach(emprestimo -> {
            String status = "Ativo";
            if (emprestimo.getDataDevolucaoReal() != null) {
                status = "Devolvido";
            }
            String multaInfo = (emprestimo.getMulta() > 0) ? " | Multa: R$" + String.format("%.2f", emprestimo.getMulta()) + (emprestimo.isMultaPaga() ? " (Paga)" : " (Pendente)") : "";

            areaEmprestimos.append("ID: " + emprestimo.getId() + " | Utilizador: " + emprestimo.getUsuario().getNome() +
                                 " | Obra: " + emprestimo.getObraEmprestada().getTitulo() +
                                 " | Previsão: " + emprestimo.getDataDevolucaoPrevista() +
                                 " | Estado: " + status + multaInfo + "\n");
        });
    }

    private void limparCamposEmprestimo() {
        campoMatriculaEmprestimo.setText("");
        campoCodigoObraEmprestimo.setText("");
    }
    
    private void limparCamposDevolucao() {
        campoIdEmprestimoDevolucao.setText("");
        campoDataDevolucaoReal.setText("");
    }
}