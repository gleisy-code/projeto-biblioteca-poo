package faculdade.projetoparapoo.modelo;

/**
 * Representa um Artigo, que é uma Obra.
 */
public class Artigo extends Obra {
    private String doi;

    public Artigo(String titulo, String autor, String doi, int anoPublicacao, int quantidade) {
        super(titulo, autor, anoPublicacao, quantidade);
        this.doi = doi;
    }

    public String getDoi() { return doi; }

    @Override
    public String toString() {
        return super.toString() + " | DOI: " + doi;
    }

    @Override
    public int getTempoEmprestimo() {
        return 2;
    }

}
