package exception;

//caso tente cadastrar um aluno com uma matricula que já tem
public class AlunoDuplicadoException extends SistemaNotasException {
    public AlunoDuplicadoException(String matricula) {
        super("Já existe um aluno cadastrado com a matrícula '" + matricula + "'.");
    }
}
