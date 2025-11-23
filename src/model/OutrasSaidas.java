package model;

import java.time.LocalDateTime;

/**
 * Representa uma saída de produto cujo motivo não se enquadra
 * nas categorias padrão (como venda ou devolução).
 * <p>
 * Esta classe permite registrar saídas diversas, informando
 * um motivo personalizado para o movimento.
 * </p>
 *
 * <p>Estende {@link SaidaProduto}, herdando a estrutura base
 * e comportamento comum das saídas de estoque.</p>
 *
 * @author ViniMonster
 */
public class OutrasSaidas extends SaidaProduto {

    /** Descrição do motivo da saída. */
    private final String outraSaida;

    /**
     * Constrói uma saída de estoque de tipo genérico.
     *
     * @param valorUnitario valor unitário do produto no momento da saída
     * @param data          data da operação
     * @param qtd           quantidade retirada
     * @param produto       produto envolvido no movimento
     * @param outraSaida    motivo/descritivo da saída
     */
    public OutrasSaidas(float valorUnitario, LocalDateTime data, int qtd,
                        Produto produto, String outraSaida) {
        super(valorUnitario, data, qtd, produto);
        this.outraSaida = outraSaida;
    }

    /**
     * Retorna o tipo textual da saída.
     *
     * @return "Outra Saída"
     */
    @Override
    public String getTipoSaida() {
        return "Outra Saída";
    }

    /**
     * Retorna uma representação textual da saída,
     * incluindo o motivo informado.
     *
     * @return string formatada com os detalhes da operação
     */
    @Override
    public String toString() {
        return super.toString() + " | Motivo: " + outraSaida;
    }
}
