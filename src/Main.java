import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import model.Aluno;
import model.Disciplina;
import service.GerenciadorAcademico;
import exception.*;


 // toda a lógica de negócio fica na classe GerenciadorAcademico, aqui é a parte de iteração com o usuário

public class Main {

    private static final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    private static final String PASTA_DADOS = "dados";

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.out.println("\nSistema de Gerenciamento de Notas de Alunos\n");

        GerenciadorAcademico gerenciador;
        try {
            gerenciador = new GerenciadorAcademico(PASTA_DADOS);
            System.out.println("\nDados carregados com sucesso.");
        } catch (ArquivoException e) {
            System.out.println("\nErro ao carregar dados: " + e.getMessage());
            System.out.println("\nIniciando sistema sem dados.");
            gerenciador = criarGerenciadorVazio();
        }

        boolean executando = true;
        while (executando) { //para exibir menu e processar as operações
            exibirMenu();
            int opcao = lerInteiro("\nEscolha uma opção: ");

            try {
                switch (opcao) {
                    case 1 -> cadastrarAluno(gerenciador);
                    case 2 -> cadastrarDisciplina(gerenciador);
                    case 3 -> matricularAluno(gerenciador);
                    case 4 -> lancarNota(gerenciador);
                    case 5 -> consultarMedia(gerenciador);
                    case 6 -> listarAlunos(gerenciador);
                    case 7 -> listarDisciplinas(gerenciador);
                    case 8 -> removerAluno(gerenciador);
                    case 9 -> salvarDados(gerenciador);
                    case 0 -> {
                        salvarDados(gerenciador);
                        executando = false;
                        System.out.println("\nEncerrando o sistema. Até logo!");
                    }
                    default -> System.out.println("\nOpção inválida. Tente novamente.");
                }
            } catch (SistemaNotasException e) {
                System.out.println("\nErro: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\nErro inesperado " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static GerenciadorAcademico criarGerenciadorVazio() {
        try {
            new java.io.File(PASTA_DADOS).mkdirs();
            return new GerenciadorAcademico(PASTA_DADOS);
        } catch (ArquivoException e) {
            throw new RuntimeException("\nFalha ao iniciar o sistema.\n", e);
        }
    }

    private static void exibirMenu() {
        System.out.println("\n1 - Cadastrar aluno");
        System.out.println("2 - Cadastrar disciplina");
        System.out.println("3 - Matricular aluno em disciplina");
        System.out.println("4 - Lançar nota");
        System.out.println("5 - Consultar média de um aluno em uma disciplina");
        System.out.println("6 - Listar alunos");
        System.out.println("7 - Listar disciplinas");
        System.out.println("8 - Remover aluno");
        System.out.println("9 - Salvar dados em arquivo");
        System.out.println("0 - Salvar e sair\n");
    }

    // menu

    //cadastra um novo aluno
    private static void cadastrarAluno(GerenciadorAcademico g) throws AlunoDuplicadoException {
        String nome = lerTexto("Nome: ");
        String cpf = lerTexto("CPF: ");
        String matricula = lerTexto("Matrícula: ");
        Aluno aluno = g.cadastrarAluno(nome, cpf, matricula);
        System.out.println("Aluno cadastrado: " + aluno);
    }

    // cadastra uma nova disciplina
    private static void cadastrarDisciplina(GerenciadorAcademico g) throws DisciplinaDuplicadaException {
        String codigo = lerTexto("Código da disciplina: ");
        String nome = lerTexto("Nome da disciplina: ");
        int cargaHoraria = lerInteiro("Carga horária (h): ");
        Disciplina disciplina = g.cadastrarDisciplina(codigo, nome, cargaHoraria);
        System.out.println("Disciplina cadastrada: " + disciplina);
    }

    // inscreve o aluno em uma disciplina específica
    private static void matricularAluno(GerenciadorAcademico g)
            throws AlunoNaoEncontradoException, DisciplinaNaoEncontradaException, InscricaoDuplicadaException {
        String matriculaAluno = lerTexto("Matrícula do aluno: ");
        String codigoDisciplina = lerTexto("Código da disciplina: ");
        g.matricularAluno(matriculaAluno, codigoDisciplina);
        System.out.println("Aluno matriculado com sucesso.");
    }

    // cadastra nota do aluno, com descrição e peso se tiver
    private static void lancarNota(GerenciadorAcademico g)
            throws AlunoNaoEncontradoException, NotaInvalidaException {
        String matriculaAluno = lerTexto("Matrícula do aluno: ");
        String codigoDisciplina = lerTexto("Código da disciplina: ");
        String descricao = lerTexto("Descrição da avaliação (ex.: Prova 1): ");
        double valor = lerDouble("Valor da nota (0 a 10): ");

        String informarPeso = lerTexto("Informar peso? (s/n): ");
        if (informarPeso.equalsIgnoreCase("s")) {
            double peso = lerDouble("Peso: ");
            g.lancarNota(matriculaAluno, codigoDisciplina, descricao, valor, peso);
        } else {
            g.lancarNota(matriculaAluno, codigoDisciplina, descricao, valor);
        }
        System.out.println("Nota lançada com sucesso.");
    }

    // para ver a média do aluno
    private static void consultarMedia(GerenciadorAcademico g)
            throws AlunoNaoEncontradoException, NotaInvalidaException {
        String matriculaAluno = lerTexto("Matrícula do aluno: ");
        String codigoDisciplina = lerTexto("Código da disciplina: ");
        double media = g.calcularMedia(matriculaAluno, codigoDisciplina);
        System.out.printf("Média do aluno: %.2f%n", media);
    }

    // para ver todos os alunos
    private static void listarAlunos(GerenciadorAcademico g) {
        List<Aluno> alunos = g.listarAlunos();
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }
        for (Aluno aluno : alunos) {
            System.out.println(aluno);
            for (var matricula : aluno.getInscricoes()) {
                System.out.println("   -> " + matricula + " | status: " + matricula.getStatus());
            }
        }
    }

    // para ver todas as disciplinas
    private static void listarDisciplinas(GerenciadorAcademico g) {
        List<Disciplina> disciplinas = g.listarDisciplinas();
        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
            return;
        }
        disciplinas.forEach(System.out::println);
    }

    // para remover um aluno
    private static void removerAluno(GerenciadorAcademico g) throws AlunoNaoEncontradoException {
        String matricula = lerTexto("Matrícula do aluno a remover: ");
        g.removerAluno(matricula);
        System.out.println("Aluno removido com sucesso.");
    }

    // para salvar os dados no arquivo txt
    private static void salvarDados(GerenciadorAcademico g) throws ArquivoException {
        g.salvarDados();
        System.out.println("Dados salvos em arquivo.");
    }

    // métodos para fazer a leitura dos dados

    private static String lerTexto(String rotulo) {
        System.out.print(rotulo);
        return scanner.nextLine().trim();
    }

    private static int lerInteiro(String rotulo) {
        while (true) {
            System.out.print(rotulo);
            try {
                int valor = Integer.parseInt(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    private static double lerDouble(String rotulo) {
        while (true) {
            System.out.print(rotulo);
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número (ex.: 8.5).");
            }
        }
    }
}
