package model;

/**
 * Representa um cliente da loja, contendo nome e endereço.
 *
 * <p>Esta classe é usada para armazenar informações básicas
 * do cliente que podem ser utilizadas em pedidos, cadastros
 * ou registros internos.</p>
 *
 * @author ViniMonster
 *
 */
public class Cliente {

    /** Nome completo do cliente. */
    private String nome;

    /** Endereço residencial ou comercial do cliente. */
    private String endereco;

    /**
     * Constrói um novo cliente com nome e endereço informados.
     *
     * @param nome     o nome completo do cliente
     * @param endereco o endereço do cliente
     */
    public Cliente(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
    }

    /**
     * Obtém o nome do cliente.
     *
     * @return o nome do cliente
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define um novo nome para o cliente.
     *
     * @param nome o novo nome do cliente
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o endereço do cliente.
     *
     * @return o endereço do cliente
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Define um novo endereço para o cliente.
     *
     * @param endereco o novo endereço do cliente
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
