package persistence;

import java.util.List;
import exception.ArquivoException;

// interface para garantir os métodos de persistencia dos arquivos nos repositorios

public interface Persistivel<T> {
    void salvar(List<T> itens) throws ArquivoException;
    List<T> carregar() throws ArquivoException;
}
