package faculdade.projetoparapoo.modelo;

/**
 * Representa um Usuário do sistema da biblioteca.
 */
public class Usuario {

    private String matricula;
    private String senha;
    private String nome;
    private boolean multasPendentes;

    public Usuario(String matricula, String senha, String nome) {
        this.matricula = matricula;
        this.senha = senha;
        this.nome = nome;
        this.multasPendentes = false;
    }

    // Getters e Setters
    public String getMatricula() { return matricula; }
    public String getSenha() { return senha; }
    public String getNome() { return nome; }
    public boolean temMultasPendentes() { return multasPendentes; }
    public void setMultasPendentes(boolean multasPendentes) { this.multasPendentes = multasPendentes; }

    @Override
    public String toString() {
        return "Matrícula: " + matricula + " | Nome: " + nome;
    }
}