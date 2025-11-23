package model;

public class Fornecedor {
    private String nome;
    private String endereco;

    public Fornecedor(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
    }

    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
}
