package model;

/**
 * Representa um produto cadastrado no estoque da loja.
 * <p>
 * Cada produto possui código único, nome, valor unitário, quantidade em estoque
 * e uma categoria associada. Também possui um identificador interno gerado
 * automaticamente.
 * </p>
 *
 * <p>Esta classe fornece operações para aumentar ou diminuir o estoque,
 * garantindo validação básica para evitar valores inválidos.</p>
 *
 * @author ViniMonster
 */
public class Produto {

    /** Contador estático para geração automática de IDs. */
    private static int contador = 0;

    /** Identificador único do produto. */
    private final int id;

    /** Código único atribuído ao produto. */
    private final String codigo;

    /** Nome descritivo do produto. */
    private final String nome;

    /** Valor unitário no momento do cadastro. */
    private final float valorUnitario;

    /** Quantidade atual disponível em estoque. */
    private int qtdEstoque;

    /** Categoria à qual o produto pertence. */
    private final Categoria categoria;

    /**
     * Constrói um novo produto com os dados fornecidos.
     *
     * @param codigo        código identificador do produto
     * @param nome          nome do produto
     * @param valorUnitario valor unitário (deve ser ≥ 0)
     * @param qtdEstoque    quantidade inicial em estoque (deve ser ≥ 0)
     * @param categoria     categoria do produto
     *
     * @throws IllegalArgumentException se valorUnitario &lt; 0 ou qtdEstoque &lt; 0
     */
    public Produto(String codigo, String nome, float valorUnitario, int qtdEstoque, Categoria categoria) {
        if (valorUnitario < 0)
            throw new IllegalArgumentException("Valor não pode ser negativo");
        if (qtdEstoque < 0)
            throw new IllegalArgumentException("Estoque não pode ser negativo");

        this.id = ++contador;
        this.codigo = codigo;
        this.nome = nome;
        this.valorUnitario = valorUnitario;
        this.qtdEstoque = qtdEstoque;
        this.categoria = categoria;
    }

    /**
     * Aumenta a quantidade de estoque do produto.
     *
     * @param qtd quantidade a ser adicionada (deve ser positiva)
     */
    public void aumentarEstoque(int qtd) {
        if (qtd > 0)
            this.qtdEstoque += qtd;
    }

    /**
     * Diminui o estoque do produto, garantindo que
     * a operação não gere estoque negativo.
     *
     * @param qtd quantidade a ser removida
     *
     * @throws IllegalArgumentException se a quantidade removida
     *                                  exceder o estoque disponível
     */
    public void diminuirEstoque(int qtd) {
        if (qtd > qtdEstoque)
            throw new IllegalArgumentException("Estoque insuficiente.");
        this.qtdEstoque -= qtd;
    }

    /** @return quantidade atual em estoque */
    public int getQtdEstoque() { return qtdEstoque; }

    /** @return nome do produto */
    public String getNome() { return nome; }

    /** @return valor unitário do produto */
    public float getValorUnitario() { return valorUnitario; }

    /** @return categoria do produto */
    public Categoria getCategoria() { return categoria; }

    /** @return código identificador do produto */
    public String getCodigo() { return codigo; }

    /** @return ID interno do produto */
    public int getId() { return id; }

    /**
     * Retorna uma representação textual contendo código, nome,
     * quantidade em estoque e valor unitário.
     *
     * @return string formatada representando o produto
     */
    @Override
    public String toString() {
        return codigo + " - " + nome +
                " | Qtd: " + qtdEstoque +
                " | R$ " + String.format("%.2f", valorUnitario);
    }
}
