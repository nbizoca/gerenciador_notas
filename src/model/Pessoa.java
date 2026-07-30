package model;

// classe abstrata que representa uma pessoa genérica
public abstract class Pessoa {

    protected String nome;
    protected String cpf;

    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("Cpf não pode ser vazio.");
        }
        this.cpf = cpf;
    }

    // metodo para exibir dados
    public abstract String exibirDados();

    @Override
    public String toString() {
        return exibirDados();
    }
}
