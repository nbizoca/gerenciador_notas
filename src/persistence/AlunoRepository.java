package persistence;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import model.Aluno;
import exception.ArquivoException;

// faz leitura e escrita dos dados de Aluno no arquivo

public class AlunoRepository implements Persistivel<Aluno> {

    private final String caminhoArquivo;

    public AlunoRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    // salva todos os alunos em arquivo no formato: nome|cpf|matricula
    @Override
    public void salvar(List<Aluno> alunos) throws ArquivoException {
        File arquivo = new File(caminhoArquivo);
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, StandardCharsets.UTF_8))) {
            // escreve cada aluno em uma linha
            for (Aluno aluno : alunos) {
                escritor.write(aluno.paraArquivo());
                escritor.newLine();
            }
        } catch (IOException e) {
            throw new ArquivoException("Erro ao salvar os dados de alunos em '" + caminhoArquivo + "'.", e);
        }
    }

    // reconstrói a lista de alunos a partir do arquivo
    @Override
    public List<Aluno> carregar() throws ArquivoException {
        List<Aluno> alunos = new ArrayList<>();
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            return alunos;
        }
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo, StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    // converte a string em objeto Aluno
                    alunos.add(Aluno.deArquivo(linha));
                }
            }
        } catch (IOException e) {
            throw new ArquivoException("Erro ao ler os dados de alunos de '" + caminhoArquivo + "'.", e);
        }
        return alunos;
    }
}
