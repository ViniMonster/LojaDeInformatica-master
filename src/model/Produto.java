package model;

public class Produto {

    private static int contador = 0;

    private int id;
    private String codigo;
    private String nome;
    private float valorUnitario;
    private int qtdEstoque;
    private Categoria categoria;

    public Produto(String codigo, String nome, float valorUnitario, int qtdEstoque, Categoria categoria) {
        if (valorUnitario < 0) throw new IllegalArgumentException("Valor não pode ser negativo");
        if (qtdEstoque < 0) throw new IllegalArgumentException("Estoque não pode ser negativo");
        this.id = ++contador;
        this.codigo = codigo;
        this.nome = nome;
        this.valorUnitario = valorUnitario;
        this.qtdEstoque = qtdEstoque;
        this.categoria = categoria;
    }

    public void aumentarEstoque(int qtd) {
        if (qtd > 0) this.qtdEstoque += qtd;
    }

    public void diminuirEstoque(int qtd) {
        if (qtd > qtdEstoque)
            throw new IllegalArgumentException("Estoque insuficiente.");
        this.qtdEstoque -= qtd;
    }

    public int getQtdEstoque() { return qtdEstoque; }
    public String getNome() { return nome; }
    public float getValorUnitario() { return valorUnitario; }
    public Categoria getCategoria() { return categoria; }
    public String getCodigo() { return codigo; }
    public int getId() { return id; }

    @Override
    public String toString() {
        return codigo + " - " + nome + " | Qtd: " + qtdEstoque + " | R$ " + String.format("%.2f", valorUnitario);
    }
}
