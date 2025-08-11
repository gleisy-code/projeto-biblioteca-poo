/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package faculdade.projetoparapoo.modelo;

import faculdade.projetoparapoo.exceptions.ObraNaoEncontradaException;
import faculdade.projetoparapoo.exceptions.UsuarioNaoEncontradoException;
import java.time.LocalDate;

public interface Emprestavel {

    // A interface pode ser usada para definir métodos comuns de emprestar e devolver.
    boolean realizarEmprestimo(String codigoObra, String matriculaUsuario)throws ObraNaoEncontradaException, UsuarioNaoEncontradoException ;
    boolean devolverEmprestimo(int idEmprestimo, LocalDate dataDevolucaoReal);
}
