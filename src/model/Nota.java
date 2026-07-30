package model;

import exception.NotaInvalidaException;

// é a nota do aluno em alguma disciplina
public class Nota {

    private String descricao;
    private double valor;
    private double peso;

    public Nota(String descricao, double valor, double peso) throws NotaInvalidaException {
        this.descricao = (descricao == null || descricao.trim().isEmpty()) ? "Avaliação" : descricao;
        setValor(valor);
        setPeso(peso);
    }

    //  quando o peso não é informado, fica igual a 1
    public Nota(String descricao, double valor) throws NotaInvalidaException {
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

    public void setValor(double valor) throws NotaInvalidaException {
        if (valor < 0.0 || valor > 10.0) {
            throw new NotaInvalidaException(
                    "Valor de nota inválido: " + valor + ". Deve estar entre 0.0 e 10.0.");
        }
        this.valor = valor;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) throws NotaInvalidaException {
        if (peso <= 0.0) {
            throw new NotaInvalidaException("Peso inválido: " + peso + ". Deve ser maior que zero.");
        }
        this.peso = peso;
    }

    @Override
    public String toString() {
        return String.format("%-15s valor=%.2f  peso=%.1f", descricao, valor, peso);
    }

    // formata a nota para gravar no arquivo
    public String paraArquivo() {
        return descricao + ":" + valor + ":" + peso;
    }

    // reconstrói uma Nota a partir do formato que foi salvo

    public static Nota deArquivo(String texto) throws NotaInvalidaException {
        String[] partes = texto.split(":");
        if (partes.length != 3) {
            throw new NotaInvalidaException("Formato de nota inválido no arquivo: " + texto);
        }
        try {
            String descricao = partes[0];
            double valor = Double.parseDouble(partes[1]);
            double peso = Double.parseDouble(partes[2]);
            return new Nota(descricao, valor, peso);
        } catch (NumberFormatException e) {
            throw new NotaInvalidaException("Não foi possível interpretar a nota: " + texto);
        }
    }
}