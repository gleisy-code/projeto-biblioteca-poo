package faculdade.projetoparapoo.modelo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Classe abstrata que representa uma Obra (Livro, Artigo, etc.).
 */
public abstract class Obra {

    private static final AtomicInteger contadorCodigos = new AtomicInteger(1);
    private final String codigo;
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private int quantidade;

    public Obra(String titulo, String autor, int anoPublicacao, int quantidade) {
        this.codigo = "OBRA-" + contadorCodigos.getAndIncrement();
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnoPublicacao() { return anoPublicacao; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    // Método abstrato que as subclasses devem implementar
    public abstract int getTempoEmprestimo();
    @Override
    public String toString() {
        return "Código: " + codigo + " | Título: " + titulo + " | Autor: " + autor + " | Ano: " + anoPublicacao;
    }
}