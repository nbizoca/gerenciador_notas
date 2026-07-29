package model;

import java.util.ArrayList;
import java.util.List;

// inscrição de um aluno em uma disciplina
// tem a lista de notas colocadas naquela disciplina

public class Inscricao {

    private Disciplina disciplina;
    private List<Nota> notas;

    public Inscricao(Disciplina disciplina) {
        this.disciplina = disciplina;
        this.notas = new ArrayList<>();
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public List<Nota> getNotas() {
        return notas;
    }


    public void adicionarNota(String descricao, double valor, double peso) {
        notas.add(new Nota(descricao, valor, peso));
    }

    // para adicionar nota sem peso
    public void adicionarNota(String descricao, double valor){
        notas.add(new Nota(descricao, valor));
    }


    // calcula a média  das .lnotas lançadas
    public double calcularMedia() {
        double somaValores = 0.0;
        double somaPesos = 0.0;
        for (Nota nota : notas) {
            somaValores += nota.getValor() * nota.getPeso();
            somaPesos += nota.getPeso();
        }
        return somaValores / somaPesos;
    }

    public String getStatus() {
        double media = calcularMedia();
        return media >= 7.0 ? "Aprovado" : (media >= 5.0 ? "Recuperação" : "Reprovado");

    }



}