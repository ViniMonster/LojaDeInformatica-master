package model;

import java.time.LocalDateTime;

/**
 * Classe abstrata que representa um movimento de estoque,
 * podendo ser tanto uma entrada quanto uma saída.
 * <p>
 * Cada movimento possui informações essenciais como valor unitário,
 * data do movimento, quantidade afetada e o produto correspondente.
 * </p>
 *
 * <p>As subclasses devem implementar a lógica específica de
 * {@link #aplicarMovimento()}, definindo como o estoque será alterado.</p>
 *
 * @author ViniMonster
 */
public abstract class MovimentoEstoque implements Movimentavel {

    /** Valor unitário do produto no momento do movimento. */
    protected float valorUnitario;

    /** Data em que o movimento ocorreu. */
    protected LocalDateTime data;

    /** Quantidade de produtos movimentados. */
    protected int qtd;

    /** Produto afetado pelo movimento. */
    protected Produto produto;

    /**
     * Constrói um movimento de estoque com os dados fornecidos.
     *
     * @param valorUnitario valor unitário do produto
     * @param data          data do movimento
     * @param qtd           quantidade movimentada
     * @param produto       produto afetado pela operação
     */
    public MovimentoEstoque(float valorUnitario, LocalDateTime data, int qtd, Produto produto) {
        this.valorUnitario = valorUnitario;
        this.data = data;
        this.qtd = qtd;
        this.produto = produto;
    }

    /**
     * Obtém a quantidade movimentada.
     *
     * @return quantidade afetada
     */
    public int getQtd() {
        return qtd;
    }

    /**
     * Obtém o produto associado ao movimento.
     *
     * @return produto movimentado
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Obtém a data do movimento.
     *
     * @return data da operação
     */
    public LocalDateTime getData() {
        return data;
    }

    /**
     * Obtém o valor unitário do produto no momento do movimento.
     *
     * @return valor unitário
     */
    public float getValorUnitario() {
        return valorUnitario;
    }

    /**
     * Retorna uma representação textual do movimento,
     * contendo data, produto, quantidade e valor unitário.
     *
     * @return string representando o movimento
     */
    @Override
    public String toString() {
        return String.format("%s | Produto: %s | Qtd: %d | ValorUnit: %.2f",
                data, produto.getNome(), qtd, valorUnitario);
    }
}
