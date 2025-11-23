package model;

/**
 * Interface que representa qualquer entidade capaz de realizar
 * um movimento no estoque, seja de entrada ou de saída.
 * <p>
 * Implementações desta interface devem definir como o movimento
 * afeta o estoque do produto associado.
 * </p>
 *
 * <p>As classes que normalmente implementam esta interface são:
 * {@link MovimentoEstoque}, {@link EntradaProduto},
 * {@link SaidaProduto} e suas subclasses.</p>
 *
 * @author ViniMonster
 */
public interface Movimentavel {

    /**
     * Aplica o movimento ao estoque.
     * <p>
     * A implementação deve realizar a lógica referente ao tipo do movimento,
     * como aumentar ou diminuir a quantidade de um produto.
     * </p>
     */
    void aplicarMovimento();
}
