package model;

/**
 * Representa um fornecedor de produtos para a loja.
 * <p>
 * Um fornecedor possui informações básicas como nome e endereço,
 * sendo utilizado para registrar entradas e devoluções no estoque.
 * </p>
 *
 * @author ViniMonster
 */
public class Fornecedor {

    /** Nome do fornecedor. */
    private final String nome;

    /** Endereço físico ou comercial do fornecedor. */
    private final String endereco;

    /**
     * Cria uma nova instância de fornecedor com nome e endereço.
     *
     * @param nome     nome do fornecedor
     * @param endereco endereço do fornecedor
     */
    public Fornecedor(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
    }

    /**
     * Obtém o nome do fornecedor.
     *
     * @return nome do fornecedor
     */
    public String getNome() {
        return nome;
    }

    /**
     * Obtém o endereço do fornecedor.
     *
     * @return endereço do fornecedor
     */
    public String getEndereco() {
        return endereco;
    }
}
