package faculdade.projetoparapoo.exceptions;

/**
 * Exceção lançada quando um usuário não é encontrado.
 */
public class UsuarioNaoEncontradoException extends Exception {
    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}

