package persistence;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import model.Disciplina;
import exception.ArquivoException;

// faz leitura e escrita dos dados de Disciplina no arquivo
public class DisciplinaRepository implements Persistivel<Disciplina> {

    private final String caminhoArquivo;

    public DisciplinaRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    // salva todas as disciplinas no arquivo no formato: codigo|nome|cargaHoraria
    @Override
    public void salvar(List<Disciplina> disciplinas) throws ArquivoException {
        File arquivo = new File(caminhoArquivo);
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, StandardCharsets.UTF_8))) {
            // escreve cada disciplina em uma linha
            for (Disciplina d : disciplinas) {
                escritor.write(d.paraArquivo());
                escritor.newLine();
            }
        } catch (IOException e) {
            throw new ArquivoException("Erro ao salvar os dados de disciplinas em '" + caminhoArquivo + "'.", e);
        }
    }

    // reconstrói a lista de disciplinas a partir do arquivo
    @Override
    public List<Disciplina> carregar() throws ArquivoException {
        List<Disciplina> disciplinas = new ArrayList<>();
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            return disciplinas;  // na primeira execução arquivo não existe ainda, então retorna lista vazia
        }
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo, StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    // converte a string em  Disciplina
                    disciplinas.add(Disciplina.deArquivo(linha));
                }
            }
        } catch (IOException e) {
            throw new ArquivoException("Erro ao ler os dados de disciplinas de '" + caminhoArquivo + "'.", e);
        }
        return disciplinas;
    }
}
