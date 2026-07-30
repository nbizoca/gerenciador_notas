package service;

import java.util.ArrayList;
import java.util.List;

import model.Aluno;
import model.Disciplina;
import model.Inscricao;
import persistence.AlunoRepository;
import persistence.DisciplinaRepository;
import persistence.InscricaoRepository;
import exception.*;

// classe para todas as regras de negocio
public class GerenciadorAcademico {

    private List<Aluno> alunos;
    private List<Disciplina> disciplinas;

    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final InscricaoRepository inscricaoRepository;

    // cria a pasta dados se não existir, junto com os arquivos txt
    public GerenciadorAcademico(String pastaDados) throws ArquivoException {
        java.io.File pasta = new java.io.File(pastaDados);
        if (!pasta.exists() && !pasta.mkdirs()) {
            throw new ArquivoException("Não foi possível criar a pasta de dados '" + pastaDados + "'.", null);
        }

        this.alunoRepository = new AlunoRepository(pastaDados + "/alunos.txt");
        this.disciplinaRepository = new DisciplinaRepository(pastaDados + "/disciplinas.txt");
        this.inscricaoRepository = new InscricaoRepository(pastaDados + "/inscricoes.txt");

        this.alunos = new ArrayList<>();
        this.disciplinas = new ArrayList<>();
        carregarDados();
    }

    // para persistir os dados

    public final void carregarDados() throws ArquivoException {
        alunos = alunoRepository.carregar();
        disciplinas = disciplinaRepository.carregar();
        inscricaoRepository.carregar(alunos, disciplinas);
    }

    public void salvarDados() throws ArquivoException {
        alunoRepository.salvar(alunos);
        disciplinaRepository.salvar(disciplinas);
        inscricaoRepository.salvar(alunos);
    }

    // cadastro de aluno

    public Aluno cadastrarAluno(String nome, String cpf, String matricula) throws AlunoDuplicadoException {
        if (buscarAlunoOpcional(matricula) != null) {
            throw new AlunoDuplicadoException(matricula);
        }
        Aluno aluno = new Aluno(nome, cpf, matricula);
        alunos.add(aluno);
        return aluno;
    }

    // busca aluno por matricula
    public Aluno buscarAluno(String matricula) throws AlunoNaoEncontradoException {
        Aluno aluno = buscarAlunoOpcional(matricula);
        if (aluno == null) {
            throw new AlunoNaoEncontradoException(matricula);
        }
        return aluno;
    }

    // metodo para buscar o aluno internamente
    private Aluno buscarAlunoOpcional(String matricula) {
        for (Aluno a : alunos) {
            if (a.getMatricula().equalsIgnoreCase(matricula)) return a;
        }
        return null;
    }

    // deleta aluno
    public void removerAluno(String matricula) throws AlunoNaoEncontradoException {
        Aluno aluno = buscarAluno(matricula);
        alunos.remove(aluno);
    }

    //lista alunos
    public List<Aluno> listarAlunos() {
        return alunos;
    }

    // cadastra disciplina

    public Disciplina cadastrarDisciplina(String codigo, String nome, int cargaHoraria)
            throws DisciplinaDuplicadaException {
        if (buscarDisciplinaOpcional(codigo) != null) {
            throw new DisciplinaDuplicadaException(codigo);
        }
        Disciplina disciplina = new Disciplina(codigo, nome, cargaHoraria);
        disciplinas.add(disciplina);
        return disciplina;
    }

    //busca disciplina
    public Disciplina buscarDisciplina(String codigo) throws DisciplinaNaoEncontradaException {
        Disciplina disciplina = buscarDisciplinaOpcional(codigo);
        if (disciplina == null) {
            throw new DisciplinaNaoEncontradaException(codigo);
        }
        return disciplina;
    }

    // faz verficação se a disciplina buscada existe
    private Disciplina buscarDisciplinaOpcional(String codigo) {
        for (Disciplina d : disciplinas) {
            if (d.getCodigo().equalsIgnoreCase(codigo)) return d;
        }
        return null;
    }

    //lista disciplinas
    public List<Disciplina> listarDisciplinas() {
        return disciplinas;
    }

    // inscreve o aluno em uma disciplina
    public Inscricao matricularAluno(String matriculaAluno, String codigoDisciplina)
            throws AlunoNaoEncontradoException, DisciplinaNaoEncontradaException, InscricaoDuplicadaException {
        Aluno aluno = buscarAluno(matriculaAluno);
        Disciplina disciplina = buscarDisciplina(codigoDisciplina);

        if (aluno.estaMatriculado(codigoDisciplina)) {
            throw new InscricaoDuplicadaException(matriculaAluno, codigoDisciplina);
        }
        Inscricao inscricao = new Inscricao(disciplina);
        aluno.adicionarInscricao(inscricao);
        return inscricao;
    }

    // lançar nota informando peso
    public void lancarNota(String matriculaAluno, String codigoDisciplina, String descricao,
                           double valor, double peso)
            throws AlunoNaoEncontradoException, NotaInvalidaException {
        Inscricao inscricao = obterMatriculaDoAluno(matriculaAluno, codigoDisciplina);
        inscricao.adicionarNota(descricao, valor, peso);
    }

    // lançar nota sem peso (fica sendo 1)
    public void lancarNota(String matriculaAluno, String codigoDisciplina, String descricao, double valor)
            throws AlunoNaoEncontradoException, NotaInvalidaException {
        Inscricao inscricao = obterMatriculaDoAluno(matriculaAluno, codigoDisciplina);
        inscricao.adicionarNota(descricao, valor);
    }

    // calcula media do aluno
    public double calcularMedia(String matriculaAluno, String codigoDisciplina)
            throws AlunoNaoEncontradoException, NotaInvalidaException {
        Inscricao inscricao = obterMatriculaDoAluno(matriculaAluno, codigoDisciplina);
        return inscricao.calcularMedia();
    }

    // busca pra saber se o aluno tá matriculado em uma disciplina
    private Inscricao obterMatriculaDoAluno(String matriculaAluno, String codigoDisciplina)
            throws AlunoNaoEncontradoException, NotaInvalidaException {
        Aluno aluno = buscarAluno(matriculaAluno);
        Inscricao inscricao = aluno.buscarInscricao(codigoDisciplina);
        if (inscricao == null) {
            throw new NotaInvalidaException(
                    "O aluno '" + matriculaAluno + "' não está matriculado na disciplina '" + codigoDisciplina + "'.");
        }
        return inscricao;
    }
}
