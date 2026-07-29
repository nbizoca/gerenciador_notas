package model;

import java.util.ArrayList;
import java.util.List;


public class Aluno extends Pessoa {

    private String matricula;
    private List<Inscricao> inscricoes; /** lista de todas as disciplinas que o aluno tá inscrito */

    public Aluno(String nome, String cpf, String matricula) {
        super(nome, cpf);
        this.matricula = matricula;
        this.inscricoes = new ArrayList<>();
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public List<Inscricao> getInscricoes() {
        return inscricoes;
    }

    public Inscricao buscarInscricao(String codigoDisciplina) {
        for (Inscricao m : inscricoes) {
            if (m.getDisciplina().getCodigo().equalsIgnoreCase(codigoDisciplina)) {
                return m;
            }
        }
        return null;
    }

    public boolean estaMatriculado(String codigoDisciplina) {
        return buscarInscricao(codigoDisciplina) != null;
    }

    public void adicionarInscricao(Inscricao inscricao) {
        this.inscricoes.add(inscricao);
    }

    @Override
    public String exibirDados() {
        return String.format("Matrícula: %-10s Nome: %-25s CPF: %s", matricula, nome, cpf);
    }

}