package persistence;

import exception.ArquivoException;
import exception.NotaInvalidaException;
import model.Aluno;
import model.Disciplina;
import model.Inscricao;
import model.Nota;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;


// para salvar as inscrições na disciplina e as notas lançadas em cada uma
public class InscricaoRepository {

    private final String caminhoArquivo;


    public InscricaoRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    //percorre todos os alunos e salva as inscrições no arquivo no formato: matricula;codigoDisciplina;nota1,nota2,nota3
    public void salvar(List<Aluno> alunos) throws ArquivoException {
        File arquivo = new File(caminhoArquivo);
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, StandardCharsets.UTF_8))) {
            for (Aluno aluno : alunos) {
                for (Inscricao inscricao : aluno.getInscricoes()) {
                    StringBuilder linha = new StringBuilder(); // vai montando o texto da linha aos poucos
                    linha.append(aluno.getMatricula())
                            .append(";")
                            .append(inscricao.getDisciplina().getCodigo())
                            .append(";");

                    // junta todas as notas separadas por vírgula
                    List<Nota> notas = inscricao.getNotas();
                    for (int i = 0; i < notas.size(); i++) {
                        linha.append(notas.get(i).paraArquivo());
                        if (i < notas.size() - 1) {
                            linha.append(",");  // separa as notas com vírgula
                        }
                    }
                    escritor.write(linha.toString());
                    escritor.newLine();
                }
            }
        } catch (IOException e) {
            throw new ArquivoException("Erro ao salvar os dados de matrículas em '" + caminhoArquivo + "'.", e);
        }
    }

    // reconstrói as inscrições e notas de cada aluno a partir do arquivo
    public void carregar(List<Aluno> alunos, List<Disciplina> disciplinas) throws ArquivoException {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            return;
        }
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo, StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;  // Ignora linhas vazias

                // divide a linha em: [matricula, codigoDisciplina, notas]
                String[] partes = linha.split(";", 3);
                String matriculaAluno = partes[0];
                String codigoDisciplina = partes[1];
                String notasTexto = partes.length > 2 ? partes[2] : "";

                Aluno aluno = buscarAlunoPorMatricula(alunos, matriculaAluno);
                Disciplina disciplina = buscarDisciplinaPorCodigo(disciplinas, codigoDisciplina);
                // ignora se aluno ou disciplina não existem
                if (aluno == null || disciplina == null) {
                    continue;
                }

                Inscricao inscricao = new Inscricao(disciplina);
                // reconstrói as notas a partir da string (converte para Nota)
                if (!notasTexto.isEmpty()) {
                    for (String notaTexto : notasTexto.split(",")) {
                        inscricao.getNotas().add(Nota.deArquivo(notaTexto));
                    }
                }
                aluno.adicionarInscricao(inscricao);
            }
        } catch (IOException e) {
            throw new ArquivoException("Erro ao ler os dados de inscrições de '" + caminhoArquivo + "'.", e);
        } catch (NotaInvalidaException e) {
            throw new ArquivoException("Arquivo de inscrições tem nota em formato inválido.", e);
        }
    }

    // busca um aluno na lista por matrícula e retorna null se não encontrar
    private Aluno buscarAlunoPorMatricula(List<Aluno> alunos, String matricula) {
        for (Aluno a : alunos) {
            if (a.getMatricula().equalsIgnoreCase(matricula)) return a;
        }
        return null;
    }

    // busca uma disciplina na lista por código e retorna null se não encontrar
    private Disciplina buscarDisciplinaPorCodigo(List<Disciplina> disciplinas, String codigo) {
        for (Disciplina d : disciplinas) {
            if (d.getCodigo().equalsIgnoreCase(codigo)) return d;
        }
        return null;
    }
}

