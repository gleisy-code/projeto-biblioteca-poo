package faculdade.projetoparapoo.modelo;

/**
 * Representa um Livro, que é uma Obra.
 */
public class Livro extends Obra {
    private String isbn;

    public Livro(String titulo, String autor, String isbn, int anoPublicacao, int quantidade) {
        super(titulo, autor, anoPublicacao, quantidade);
        this.isbn = isbn;
    }

    public String getIsbn() { return isbn; }

    @Override
    public String toString() {
        return super.toString() + " | ISBN: " + isbn;
    }
}