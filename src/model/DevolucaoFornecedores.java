package model;

import java.time.LocalDateTime;

public class DevolucaoFornecedores extends SaidaProduto {
    private Fornecedor fornecedor;

    public DevolucaoFornecedores(float valorUnitario, LocalDateTime data, int qtd, Produto produto, Fornecedor fornecedor) {
        super(valorUnitario, data, qtd, produto);
        this.fornecedor = fornecedor;
    }

    @Override
    public String getTipoSaida() { return "Devolução ao Fornecedor"; }

    public Fornecedor getFornecedor() { return fornecedor; }

    @Override
    public String toString() {
        return super.toString() +
                " | Fornecedor: " + (fornecedor != null ? fornecedor.getNome() : "-");
    }
}
