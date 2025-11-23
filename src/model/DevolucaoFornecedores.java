package model;

import java.time.LocalDateTime;

/**
 * Representa uma devolução de produtos realizada ao fornecedor.
 * <p>
 * Esta classe é um tipo específico de {@link SaidaProduto}, indicando
 * que a saída do item do estoque ocorreu devido a uma devolução.
 * Inclui a referência ao fornecedor responsável pela operação.
 * </p>
 *
 * @author ViniMonster
 */
public class DevolucaoFornecedores extends SaidaProduto {

    /** Fornecedor ao qual o produto está sendo devolvido. */
    private final Fornecedor fornecedor;

    /**
     * Cria uma devolução para fornecedores, registrando os dados necessários
     * para a operação de saída no estoque.
     *
     * @param valorUnitario valor unitário do produto devolvido
     * @param data          data da devolução
     * @param qtd           quantidade devolvida
     * @param produto       produto envolvido na devolução
     * @param fornecedor    fornecedor responsável pela operação
     */
    public DevolucaoFornecedores(float valorUnitario, LocalDateTime data, int qtd,
                                 Produto produto, Fornecedor fornecedor) {
        super(valorUnitario, data, qtd, produto);
        this.fornecedor = fornecedor;
    }

    /**
     * Retorna o tipo textual da saída.
     *
     * @return descrição da saída: "Devolução ao Fornecedor"
     */
    @Override
    public String getTipoSaida() {
        return "Devolução ao Fornecedor";
    }

    /**
     * Obtém o fornecedor ao qual o produto foi devolvido.
     *
     * @return o fornecedor relacionado à devolução
     */
    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    /**
     * Retorna uma representação textual da devolução,
     * incluindo informações do fornecedor.
     *
     * @return string formatada com detalhes da devolução
     */
    @Override
    public String toString() {
        return super.toString() +
                " | Fornecedor: " + (fornecedor != null ? fornecedor.getNome() : "-");
    }
}
