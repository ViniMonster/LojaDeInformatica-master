package model;

import java.time.LocalDateTime;

/**
 * Representa uma saída de produto destinada ao uso interno da empresa.
 * <p>
 * Esse tipo de saída ocorre quando um item é retirado do estoque
 * para consumo próprio da organização, como uso em manutenção,
 * testes ou atividades internas.
 * </p>
 *
 * <p>Estende {@link SaidaProduto}, adicionando a informação sobre o
 * destino interno do produto.</p>
 *
 * @author ViniMonster
 */
public class UsoInterno extends SaidaProduto {

    /** Local, setor ou finalidade interna para onde o produto foi destinado. */
    private final String destino;

    /**
     * Constrói uma saída de produto para uso interno.
     *
     * @param valorUnitario valor unitário do produto no momento da saída
     * @param data          data da operação
     * @param qtd           quantidade removida do estoque
     * @param produto       produto envolvido na saída
     * @param destino       destino ou finalidade interna do uso
     */
    public UsoInterno(float valorUnitario, LocalDateTime data, int qtd,
                      Produto produto, String destino) {
        super(valorUnitario, data, qtd, produto);
        this.destino = destino;
    }

    /**
     * Retorna o tipo textual da saída.
     *
     * @return "Uso Interno"
     */
    @Override
    public String getTipoSaida() {
        return "Uso Interno";
    }

    /**
     * Retorna o destino interno do produto.
     *
     * @return destino da utilização
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Retorna uma representação textual da saída,
     * incluindo destino interno.
     *
     * @return string formatada representando a operação
     */
    @Override
    public String toString() {
        return super.toString() + " | Destino: " + destino;
    }
}
