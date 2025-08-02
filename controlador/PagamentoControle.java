package faculdade.projetoparapoo.controlador;

import faculdade.projetoparapoo.modelo.Emprestimo;

import java.util.Optional;

/**
 * Controlador para gerenciar pagamentos de multas.
 */
public class PagamentoControle {

    private final EmprestimoControle emprestimoControle;

    public PagamentoControle(EmprestimoControle emprestimoControle) {
        this.emprestimoControle = emprestimoControle;
    }

    /**
     * Registra o pagamento de uma multa de um empréstimo.
     * @param idEmprestimo ID do empréstimo.
     * @return true se o pagamento for registrado com sucesso, false caso contrário.
     */
    public boolean registrarPagamento(int idEmprestimo) {
        return emprestimoControle.pagarMulta(idEmprestimo);
    }
}
