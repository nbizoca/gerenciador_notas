package exception;

/** classe base para as excessões. todas as exceções específicas herdam dela */
public class SistemaNotasException extends Exception {

    public SistemaNotasException(String mensagem) {
        super(mensagem);
    }

    public SistemaNotasException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
