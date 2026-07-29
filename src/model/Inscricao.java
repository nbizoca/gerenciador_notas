package model;

import exception.NotaInvalidaException;

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


    public void adicionarNota(String descricao, double valor, double peso) throws NotaInvalidaException {
        notas.add(new Nota(descricao, valor, peso));
    }

    // para adicionar nota sem peso
    public void adicionarNota(String descricao, double valor) throws NotaInvalidaException {
        notas.add(new Nota(descricao, valor));
    }


    // calcula a média  das notas lançadas e lança excessão se não tiver nenhuma
    public double calcularMedia() throws NotaInvalidaException {
        if (notas.isEmpty()) {
            throw new NotaInvalidaException(
                    "Não é possível calcular a média: nenhuma nota lançada para " + disciplina.getNome() + ".");
        }
        double somaValores = 0.0;
        double somaPesos = 0.0;
        for (Nota nota : notas) {
            somaValores += nota.getValor() * nota.getPeso();
            somaPesos += nota.getPeso();
        }
        return somaValores / somaPesos;
    }

    public String getStatus() {
        try {
            double media = calcularMedia();
            return media >= 7.0 ? "Aprovado" : (media >= 5.0 ? "Recuperação" : "Reprovado");
        } catch (NotaInvalidaException e) {
            return "Sem notas";
        }
    }

    @Override
    public String toString() {
        return disciplina.toString() + " - " + notas.size() + " nota(s)";
    }

}