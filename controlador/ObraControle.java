package faculdade.projetoparapoo.controlador;

import faculdade.projetoparapoo.modelo.Obra;
import faculdade.projetoparapoo.modelo.Artigo;
import faculdade.projetoparapoo.modelo.Livro;
import faculdade.projetoparapoo.modelo.Revista;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Controlador para gerenciar obras (livros, artigos, etc.).
 */
public class ObraControle {

    private List<Obra> obras;
    private static final AtomicInteger contadorObras = new AtomicInteger(1);

    public ObraControle() {
        this.obras = new ArrayList<>();
    }

    public void adicionarObra(String tipo, String titulo, String autor, String identificador, int ano, int quantidade) {
    Obra novaObra;
        if ("livro".equalsIgnoreCase(tipo)) {
            novaObra = new Livro(titulo, autor, identificador, ano, quantidade);
        } else if ("artigo".equalsIgnoreCase(tipo)) {
            novaObra = new Artigo(titulo, autor, identificador, ano, quantidade);
        } else if ("revista".equalsIgnoreCase(tipo)) {
            novaObra = new Revista(titulo, autor, identificador, ano, quantidade);
        } else {
            return; // tipo inválido, não adiciona nada
        }
        obras.add(novaObra);
    }

    public List<Obra> buscarTodasObras() {
        return new ArrayList<>(obras);
    }

    public Optional<Obra> buscarObraPorCodigo(String codigo) {
        return obras.stream().filter(o -> o.getCodigo().equals(codigo)).findFirst();
    }

    public boolean removerObraPorCodigo(String codigo) {
        return obras.removeIf(o -> o.getCodigo().equals(codigo));
    }
}