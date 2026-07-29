package exception;

import model.Inscricao;

// pra quando tentar matricular um aluno mais de uma vez na mesma disciplina
public class InscricaoDuplicadaException extends SistemaNotasException {
    public InscricaoDuplicadaException(String matriculaAluno, String codigoDisciplina) {
        super("O aluno '" + matriculaAluno + "' já está matriculado na disciplina '" + codigoDisciplina + "'.");
    }
}
