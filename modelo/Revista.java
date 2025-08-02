package faculdade.projetoparapoo.modelo;
public class Revista extends Obra {
    private String editora;
    private String issn;
    private int volume;

    public Revista(String titulo, String editora, String issn, int anoPublicacao, int volume) {
        super(titulo, "", anoPublicacao, 1); // Uma revista tem uma quantidade fixa de 1
        this.editora = editora;
        this.issn = issn;
        this.volume = volume;
    }

    public String getEditora() { return editora; }
    public String getIssn() { return issn; }
    public int getVolume() { return volume; }

    @Override
    public String toString() {
        return super.toString() + " | Editora: " + editora + " | ISSN: " + issn + " | Volume: " + volume;
    }
}