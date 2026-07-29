package exception;

// se tentar cadastrar uma disciplina com código que já existe
public class DisciplinaDuplicadaException extends SistemaNotasException {
    public DisciplinaDuplicadaException(String codigo) {
        super("Já existe uma disciplina cadastrada com o código '" + codigo + "'.");
    }
}
