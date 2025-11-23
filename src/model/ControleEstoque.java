package model;

import persistence.DataStore;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Classe responsável por controlar o estoque da aplicação.
 * Gerencia produtos cadastrados, movimentos de entrada e saída,
 * bem como operações de cálculo de saldo e listagens.
 *
 * <p>Ao ser instanciada, a classe carrega automaticamente os
 * produtos e movimentos salvos no {@link DataStore}, aplicando
 * todos os movimentos no estoque atual.</p>
 *
 * @author ViniMonster
 */
public class ControleEstoque {

    /** Lista de produtos cadastrados no estoque. */
    private final List<Produto> produtos;

    /** Lista completa de movimentos de entrada e saída registrados. */
    private final List<MovimentoEstoque> movimentos;

    /**
     * Construtor que inicializa o controle de estoque carregando
     * produtos e movimentos previamente armazenados.
     *
     * <p>Os movimentos são aplicados automaticamente nos produtos
     * correspondentes para reconstruir o estado atual do estoque.</p>
     */
    public ControleEstoque() {
        produtos = DataStore.loadProducts();
        movimentos = DataStore.loadMovements(produtos);

        for (MovimentoEstoque m : movimentos) {
            try {
                m.aplicarMovimento();
            } catch (Exception ignored) {}
        }
    }

    /**
     * Obtém a lista de produtos cadastrados.
     *
     * @return lista de produtos
     */
    public List<Produto> getProdutos() {
        return produtos;
    }

    /**
     * Obtém a lista completa de movimentos registrados.
     *
     * @return lista de movimentos de estoque
     */
    public List<MovimentoEstoque> getMovimentos() {
        return movimentos;
    }

    /**
     * Adiciona um novo produto ao estoque e salva a alteração no {@link DataStore}.
     *
     * @param p produto a ser adicionado
     */
    public void adicionarProduto(Produto p) {
        produtos.add(p);
        DataStore.saveProducts(produtos);
    }

    /**
     * Busca um produto cadastrado usando seu código identificador.
     *
     * @param codigo código do produto
     * @return produto correspondente, ou {@code null} se não encontrado
     */
    public Produto buscarProdutoPorCodigo(String codigo) {
        return produtos.stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    /**
     * Registra um movimento de estoque (entrada ou saída) e persiste
     * automaticamente os dados atualizados.
     *
     * @param mov movimento a ser registrado
     * @throws IllegalArgumentException caso o movimento não seja do tipo permitido
     */
    public void registrarMovimento(Movimentavel mov) {
        mov.aplicarMovimento();

        if (mov instanceof MovimentoEstoque) {
            movimentos.add((MovimentoEstoque) mov);
            DataStore.saveMovements(movimentos);
            DataStore.saveProducts(produtos);
        } else {
            throw new IllegalArgumentException("Movimento inválido.");
        }
    }

    /**
     * Retorna uma lista contendo todos os movimentos de entrada registrados.
     *
     * @return lista de entradas de produto
     */
    public List<EntradaProduto> listarEntradas() {
        List<EntradaProduto> lista = new ArrayList<>();
        for (MovimentoEstoque m : movimentos)
            if (m instanceof EntradaProduto) lista.add((EntradaProduto) m);
        return lista;
    }

    /**
     * Retorna uma lista contendo todos os movimentos de saída registrados.
     *
     * @return lista de saídas de produto
     */
    public List<SaidaProduto> listarSaidas() {
        List<SaidaProduto> lista = new ArrayList<>();
        for (MovimentoEstoque m : movimentos)
            if (m instanceof SaidaProduto) lista.add((SaidaProduto) m);
        return lista;
    }

    /**
     * Retorna a lista completa de movimentos ordenada por data.
     *
     * @return lista ordenada de movimentos
     */
    public List<MovimentoEstoque> listarMovimentosOrdenados() {
        List<MovimentoEstoque> lista = new ArrayList<>(movimentos);
        lista.sort(Comparator.comparing(MovimentoEstoque::getData));
        return lista;
    }

    /**
     * Calcula a quantidade total de itens no estoque somando a quantidade de cada produto.
     *
     * @return saldo total de unidades em estoque
     */
    public int getSaldoAtualQuantidade() {
        int saldo = 0;
        for (Produto p : produtos) saldo += p.getQtdEstoque();
        return saldo;
    }

    /**
     * Calcula o valor total em estoque multiplicando a quantidade
     * pela o valor unitário de cada produto.
     *
     * @return valor total do estoque
     */
    public float getSaldoAtualValor() {
        float total = 0f;
        for (Produto p : produtos)
            total += p.getQtdEstoque() * p.getValorUnitario();
        return total;
    }

    /**
     * Calcula o saldo (positivo ou negativo) de movimentos ocorridos
     * em um intervalo de tempo específico.
     *
     * @param inicio data inicial do período
     * @param fim    data final do período
     * @return saldo financeiro das movimentações no período
     */
    public float getSaldoPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        float total = 0f;

        for (MovimentoEstoque m : movimentos) {
            if (!m.getData().isBefore(inicio) && !m.getData().isAfter(fim)) {
                float valor = m.getValorUnitario();
                if (m instanceof EntradaProduto)
                    total += valor * m.getQtd();
                else
                    total -= valor * m.getQtd();
            }
        }
        return total;
    }
}
