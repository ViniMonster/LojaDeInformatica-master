package model;

import java.time.LocalDateTime;

/**
 * Representa um movimento de entrada de produto no estoque.
 * <p>
 * Esta operação indica que determinada quantidade de um produto
 * foi adicionada ao estoque, normalmente proveniente de um fornecedor.
 * </p>
 *
 * <p>Estende {@link MovimentoEstoque}, adicionando a informação
 * do fornecedor relacionado à entrada.</p>
 *
 * @author ViniMonster
 */
public class EntradaProduto extends MovimentoEstoque {

    /** Fornecedor associado à entrada do produto. */
    private final Fornecedor fornecedor;

    /**
     * Constrói um movimento de entrada de produto no estoque.
     *
     * @param valorUnitario valor unitário do produto no momento da entrada
     * @param data          data da entrada no estoque
     * @param qtd           quantidade adicionada
     * @param produto       produto que está sendo estocado
     * @param fornecedor    fornecedor responsável pelo envio
     */
    public EntradaProduto(float valorUnitario, LocalDateTime data, int qtd,
                          Produto produto, Fornecedor fornecedor) {
        super(valorUnitario, data, qtd, produto);
        this.fornecedor = fornecedor;
    }

    /**
     * Aplica o movimento de entrada ao estoque,
     * aumentando a quantidade do produto envolvido.
     */
    @Override
    public void aplicarMovimento() {
        getProduto().aumentarEstoque(getQtd());
    }

    /**
     * Obtém o fornecedor relacionado à entrada do produto.
     *
     * @return fornecedor da entrada
     */
    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    /**
     * Retorna uma representação textual da entrada de produto,
     * incluindo detalhes do fornecedor.
     *
     * @return string formatada representando a entrada
     */
    @Override
    public String toString() {
        return "ENTRADA | " + super.toString() +
                " | Fornecedor: " + (fornecedor != null ? fornecedor.getNome() : "-");
    }
}
