package faculdade.projetoparapoo.controlador;

import faculdade.projetoparapoo.exceptions.ObraNaoEncontradaException;
import faculdade.projetoparapoo.exceptions.UsuarioNaoEncontradoException;
import faculdade.projetoparapoo.modelo.Emprestavel;
import faculdade.projetoparapoo.modelo.Emprestimo;
import faculdade.projetoparapoo.modelo.Obra;
import faculdade.projetoparapoo.modelo.Usuario;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para gerenciar empréstimos.
 */
public class EmprestimoControle implements Emprestavel{

    private List<Emprestimo> emprestimos;
    private final UsuarioControle usuarioControle;
    private final ObraControle obraControle;
    private int proximoIdEmprestimo = 1;

    public EmprestimoControle(UsuarioControle usuarioControle, ObraControle obraControle) {
        this.emprestimos = new ArrayList<>();
        this.usuarioControle = usuarioControle;
        this.obraControle = obraControle;
    }

    /**
     * Realiza um novo empréstimo.
     * @param codigoObra Código da obra a ser emprestada.
     * @param matriculaUsuario Matrícula do usuário.
     * @return true se o empréstimo for realizado com sucesso, false caso contrário.
     */
    @Override
public boolean realizarEmprestimo(String codigoObra, String matriculaUsuario) throws ObraNaoEncontradaException, UsuarioNaoEncontradoException {
    Optional<Usuario> usuarioOpt = usuarioControle.buscarUsuarioPorMatricula(matriculaUsuario);
    Optional<Obra> obraOpt = obraControle.buscarObraPorCodigo(codigoObra);

    // Lança exceção se usuário não encontrado
    if (usuarioOpt.isEmpty()) {
        throw new UsuarioNaoEncontradoException("Usuário com matrícula " + matriculaUsuario + " não encontrado.");
    }
    // Lança exceção se obra não encontrada
    if (obraOpt.isEmpty()) {
        throw new ObraNaoEncontradaException("Obra com código " + codigoObra + " não encontrada.");
    }

    Usuario usuario = usuarioOpt.get();
    Obra obra = obraOpt.get();

    // Verifica disponibilidade e multas
    if (obra.getQuantidade() > 0 && !usuario.temMultasPendentes()) {
        obra.setQuantidade(obra.getQuantidade() - 1);
        LocalDate dataEmprestimo = LocalDate.now();
        LocalDate dataDevolucaoPrevista = dataEmprestimo.plusDays(obra.getTempoEmprestimo());

        Emprestimo emprestimo = new Emprestimo(proximoIdEmprestimo++, usuario, obra, dataEmprestimo, dataDevolucaoPrevista);
        emprestimos.add(emprestimo);
        return true;
    }

    return false;
}


    /**
     * Devolve uma obra emprestada.
     * @param idEmprestimo ID do empréstimo a ser devolvido.
     * @param dataDevolucaoReal Data real da devolução.
     * @return true se a devolução for realizada com sucesso, false caso contrário.
     */
    @Override
    public boolean devolverEmprestimo(int idEmprestimo, LocalDate dataDevolucaoReal) {
        Optional<Emprestimo> emprestimoOpt = buscarEmprestimoPorId(idEmprestimo);

        if (emprestimoOpt.isPresent()) {
            Emprestimo emprestimo = emprestimoOpt.get();
            if (emprestimo.getDataDevolucaoReal() == null) {
                emprestimo.setDataDevolucaoReal(dataDevolucaoReal);
                emprestimo.getObraEmprestada().setQuantidade(emprestimo.getObraEmprestada().getQuantidade() + 1);

                // Calcula a multa se houver atraso
                long diasAtraso = ChronoUnit.DAYS.between(emprestimo.getDataDevolucaoPrevista(), dataDevolucaoReal);
                if (diasAtraso > 0) {
                    double multa = diasAtraso * 1.50; // Multa de R$1.50 por dia de atraso
                    emprestimo.setMulta(multa);
                    emprestimo.getUsuario().setMultasPendentes(true);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Paga a multa de um empréstimo.
     * @param idEmprestimo ID do empréstimo.
     * @return true se o pagamento for bem-sucedido, false caso contrário.
     */
    public boolean pagarMulta(int idEmprestimo) {
        Optional<Emprestimo> emprestimoOpt = buscarEmprestimoPorId(idEmprestimo);
        if (emprestimoOpt.isPresent()) {
            Emprestimo emprestimo = emprestimoOpt.get();
            if (emprestimo.getMulta() > 0 && !emprestimo.isMultaPaga()) {
                emprestimo.setMultaPaga(true);
                // Verifica se o usuário tem outras multas pendentes
                boolean temOutrasMultas = emprestimos.stream()
                        .filter(e -> e.getUsuario().equals(emprestimo.getUsuario()) && e.getMulta() > 0 && !e.isMultaPaga())
                        .count() > 0;
                emprestimo.getUsuario().setMultasPendentes(temOutrasMultas);
                return true;
            }
        }
        return false;
    }

    public List<Emprestimo> buscarTodosEmprestimos() {
        return new ArrayList<>(emprestimos);
    }

    public Optional<Emprestimo> buscarEmprestimoPorId(int id) {
        return emprestimos.stream().filter(e -> e.getId() == id).findFirst();
    }

    
}