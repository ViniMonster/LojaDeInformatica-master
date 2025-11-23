package model;

import java.time.LocalDateTime;

public abstract class MovimentoEstoque implements Movimentavel {
    protected float valorUnitario;
    protected LocalDateTime data;
    protected int qtd;
    protected Produto produto;

    public MovimentoEstoque(float valorUnitario, LocalDateTime data, int qtd, Produto produto) {
        this.valorUnitario = valorUnitario;
        this.data = data;
        this.qtd = qtd;
        this.produto = produto;
    }

    public int getQtd() { return qtd; }
    public Produto getProduto() { return produto; }
    public LocalDateTime getData() { return data; }
    public float getValorUnitario() { return valorUnitario; }

    @Override
    public String toString() {
        return String.format("%s | Produto: %s | Qtd: %d | ValorUnit: %.2f",
                data, produto.getNome(), qtd, valorUnitario);
    }
}
