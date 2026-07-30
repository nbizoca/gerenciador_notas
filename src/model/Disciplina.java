package model;

public class Disciplina {

    private String codigo;
    private String nome;
    private int cargaHoraria;

    public Disciplina(String codigo, String nome, int cargaHoraria) {
        this.codigo = codigo;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
    }

    // para criar disciplina sem dizer a carga horária (fica 60h)
    public Disciplina(String codigo, String nome) {
        this(codigo, nome, 60);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        if (cargaHoraria <= 0) {
            throw new IllegalArgumentException("Carga horária deve ser maior que zero.");
        }
        this.cargaHoraria = cargaHoraria;
    }


    @Override
    public String toString() {
        return String.format("[%s] %s (%dh)", codigo, nome, cargaHoraria);
    }

    // formata para salvar no arquivo
    public String paraArquivo() {
        return codigo + ";" + nome + ";" + cargaHoraria;
    }

    public static Disciplina deArquivo(String linha) {
        String[] partes = linha.split(";");
        return new Disciplina(partes[0], partes[1], Integer.parseInt(partes[2]));
    }

}