package model;

import java.time.LocalDateTime;

/**
 * Representa uma saída de produto do estoque causada por uma venda a um cliente.
 * <p>
 * Esta classe registra a quantidade vendida, o valor unitário no momento da venda
 * e o cliente responsável pela compra.
 * </p>
 *
 * <p>Estende {@link SaidaProduto}, adicionando informações específicas
 * sobre o cliente envolvido na transação.</p>
 *
 * @author ViniMonster
 */
public class VendasClientes extends SaidaProduto {

    /** Cliente que realizou a compra. */
    private final Cliente cliente;

    /**
     * Constrói um movimento de venda para um cliente.
     *
     * @param valorUnitario valor unitário do produto vendido
     * @param data          data da operação de venda
     * @param qtd           quantidade vendida
     * @param produto       produto envolvido na transação
     * @param cliente       cliente que realizou a compra
     */
    public VendasClientes(float valorUnitario, LocalDateTime data, int qtd,
                          Produto produto, Cliente cliente) {
        super(valorUnitario, data, qtd, produto);
        this.cliente = cliente;
    }

    /**
     * Retorna o tipo textual da saída.
     *
     * @return "Venda"
     */
    @Override
    public String getTipoSaida() {
        return "Venda";
    }

    /**
     * Obtém o cliente associado a esta venda.
     *
     * @return cliente da venda
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Retorna uma representação textual da venda,
     * incluindo o nome do cliente envolvido.
     *
     * @return string representando a operação de venda
     */
    @Override
    public String toString() {
        return super.toString() +
                " | Cliente: " + (cliente != null ? cliente.getNome() : "-");
    }
}
