package model;

import java.time.LocalDateTime;

public class EntradaProduto extends MovimentoEstoque {
    private Fornecedor fornecedor;

    public EntradaProduto(float valorUnitario, LocalDateTime data, int qtd, Produto produto, Fornecedor fornecedor) {
        super(valorUnitario, data, qtd, produto);
        this.fornecedor = fornecedor;
    }

    @Override
    public void aplicarMovimento() {
        getProduto().aumentarEstoque(getQtd());
    }

    public Fornecedor getFornecedor() { return fornecedor; }

    @Override
    public String toString() {
        return "ENTRADA | " + super.toString() +
                " | Fornecedor: " + (fornecedor != null ? fornecedor.getNome() : "-");
    }
}
