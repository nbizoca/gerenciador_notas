package model;

// é a nota do aluno em alguma disciplina
public class Nota {

    private String descricao;
    private double valor;
    private double peso;

    public Nota(String descricao, double valor, double peso)  {
        this.descricao = (descricao == null || descricao.trim().isEmpty()) ? "Avaliação" : descricao;
        setValor(valor);
        setPeso(peso);
    }

    //  quando o peso não é informado, fica igual a 1
    public Nota(String descricao, double valor) {
        this(descricao, valor, 1.0);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso)  {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return String.format("%-15s valor=%.2f  peso=%.1f", descricao, valor, peso);
    }

}