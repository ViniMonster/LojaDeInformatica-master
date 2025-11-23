package model;

import java.time.LocalDateTime;

/**
 * Classe abstrata que representa uma saída de produto no estoque.
 * <p>
 * Uma saída indica que uma determinada quantidade foi removida do estoque,
 * podendo ocorrer por diversos motivos (venda, devolução, perda, entre outros).
 * </p>
 *
 * <p>As subclasses devem especificar o tipo de saída através do método
 * {@link #getTipoSaida()}.</p>
 *
 * @author ViniMonster
 */
public abstract class SaidaProduto extends MovimentoEstoque {

    /**
     * Constrói uma saída de produto com os dados fornecidos.
     *
     * @param valorUnitario valor unitário do produto no momento da saída
     * @param data          data da operação
     * @param qtd           quantidade removida do estoque
     * @param produto       produto envolvido na saída
     */
    public SaidaProduto(float valorUnitario, LocalDateTime data, int qtd, Produto produto) {
        super(valorUnitario, data, qtd, produto);
    }

    /**
     * Aplica o movimento de saída ao estoque,
     * reduzindo a quantidade do produto correspondente.
     */
    @Override
    public void aplicarMovimento() {
        getProduto().diminuirEstoque(getQtd());
    }

    /**
     * Retorna uma descrição textual do tipo da saída.
     * <p>
     * Exemplo: "Venda", "Devolução ao Fornecedor", "Outra Saída".
     * </p>
     *
     * @return tipo da saída em formato string
     */
    public abstract String getTipoSaida();

    /**
     * Retorna uma representação textual completa da saída,
     * incluindo o tipo específico e informações herdadas.
     *
     * @return string formatada representando a saída
     */
    @Override
    public String toString() {
        return "SAÍDA [" + getTipoSaida() + "] | " + super.toString();
    }
}
