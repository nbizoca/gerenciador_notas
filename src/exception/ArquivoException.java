package exception;

// para erros na leitura ou escrita dos arquivos de dados
public class ArquivoException extends SistemaNotasException {
    public ArquivoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
