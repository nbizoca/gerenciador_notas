package exception;

// pra quando for buscar ou remover um aluno que a matricula não existe
public class AlunoNaoEncontradoException extends SistemaNotasException {
    public AlunoNaoEncontradoException(String matricula) {
        super("Nenhum aluno encontrado com a matrícula '" + matricula + "'.");
    }
}
