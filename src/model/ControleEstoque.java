package model;

import persistence.DataStore;
import java.time.LocalDateTime;
import java.util.*;

public class ControleEstoque {
    private List<Produto> produtos = new ArrayList<>();
    private List<MovimentoEstoque> movimentos = new ArrayList<>();

    public ControleEstoque() {
        produtos = DataStore.loadProducts();
        movimentos = DataStore.loadMovements(produtos);

        for (MovimentoEstoque m : movimentos) {
            try { m.aplicarMovimento(); } catch (Exception ignored) {}
        }
    }

    public List<Produto> getProdutos() { return produtos; }
    public List<MovimentoEstoque> getMovimentos() { return movimentos; }

    public void adicionarProduto(Produto p) {
        produtos.add(p);
        DataStore.saveProducts(produtos);
    }

    public Produto buscarProdutoPorCodigo(String codigo) {
        return produtos.stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst().orElse(null);
    }

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

    public List<EntradaProduto> listarEntradas() {
        List<EntradaProduto> lista = new ArrayList<>();
        for (MovimentoEstoque m : movimentos)
            if (m instanceof EntradaProduto) lista.add((EntradaProduto) m);
        return lista;
    }

    public List<SaidaProduto> listarSaidas() {
        List<SaidaProduto> lista = new ArrayList<>();
        for (MovimentoEstoque m : movimentos)
            if (m instanceof SaidaProduto) lista.add((SaidaProduto) m);
        return lista;
    }

    public List<MovimentoEstoque> listarMovimentosOrdenados() {
        List<MovimentoEstoque> lista = new ArrayList<>(movimentos);
        lista.sort(Comparator.comparing(MovimentoEstoque::getData));
        return lista;
    }

    public int getSaldoAtualQuantidade() {
        int saldo = 0;
        for (Produto p : produtos) saldo += p.getQtdEstoque();
        return saldo;
    }

    public float getSaldoAtualValor() {
        float total = 0f;
        for (Produto p : produtos)
            total += p.getQtdEstoque() * p.getValorUnitario();
        return total;
    }

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
