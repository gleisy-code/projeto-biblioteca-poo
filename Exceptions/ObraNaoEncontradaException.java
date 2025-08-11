package faculdade.projetoparapoo.exceptions;

/**
 * Exceção lançada quando uma obra não é encontrada.
 */
public class ObraNaoEncontradaException extends Exception {
    public ObraNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}