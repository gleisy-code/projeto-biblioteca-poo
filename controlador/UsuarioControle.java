package faculdade.projetoparapoo.controlador;

import faculdade.projetoparapoo.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para gerenciar usuários.
 */
public class UsuarioControle {

    private List<Usuario> usuarios;

    public UsuarioControle() {
        this.usuarios = new ArrayList<>();
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public Optional<Usuario> buscarUsuarioPorMatricula(String matricula) {
        return usuarios.stream().filter(u -> u.getMatricula().equals(matricula)).findFirst();
    }

    public List<Usuario> buscarTodosUsuarios() {
        return new ArrayList<>(usuarios);
    }

    public boolean removerUsuarioPorMatricula(String matricula) {
        return usuarios.removeIf(u -> u.getMatricula().equals(matricula));
    }

    public Optional<Usuario> login(String matricula, String senha) {
        return usuarios.stream()
                .filter(u -> u.getMatricula().equals(matricula) && u.getSenha().equals(senha))
                .findFirst();
    }
}