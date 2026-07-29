package exception;

// quando tentar buscar uma disciplina que não existe
public class DisciplinaNaoEncontradaException extends SistemaNotasException {
    public DisciplinaNaoEncontradaException(String codigo) {
        super("Nenhuma disciplina encontrada com o código '" + codigo + "'.");
    }
}
