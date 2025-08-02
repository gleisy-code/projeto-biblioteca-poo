package faculdade.projetoparapoo.modelo;

import java.time.LocalDate;

/**
 * Representa um Empréstimo de uma Obra por um Usuario.
 */
public class Emprestimo {

    private int id;
    private Usuario usuario;
    private Obra obraEmprestada;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
    private double multa;
    private boolean multaPaga;

    public Emprestimo(int id, Usuario usuario, Obra obraEmprestada, LocalDate dataEmprestimo, LocalDate dataDevolucaoPrevista) {
        this.id = id;
        this.usuario = usuario;
        this.obraEmprestada = obraEmprestada;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.dataDevolucaoReal = null;
        this.multa = 0.0;
        this.multaPaga = false;
    }

    // Getters e Setters
    public int getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Obra getObraEmprestada() { return obraEmprestada; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
    public LocalDate getDataDevolucaoReal() { return dataDevolucaoReal; }
    public void setDataDevolucaoReal(LocalDate dataDevolucaoReal) { this.dataDevolucaoReal = dataDevolucaoReal; }
    public double getMulta() { return multa; }
    public void setMulta(double multa) { this.multa = multa; }
    public boolean isMultaPaga() { return multaPaga; }
    public void setMultaPaga(boolean multaPaga) { this.multaPaga = multaPaga; }
}