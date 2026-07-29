package exception;

/** quando a nota ou peso informado é inválido. */
public class NotaInvalidaException extends SistemaNotasException {
    public NotaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
