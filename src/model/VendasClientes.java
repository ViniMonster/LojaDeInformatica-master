package model;

import java.time.LocalDateTime;

public class VendasClientes extends SaidaProduto {
    private Cliente cliente;

    public VendasClientes(float valorUnitario, LocalDateTime data, int qtd, Produto produto, Cliente cliente) {
        super(valorUnitario, data, qtd, produto);
        this.cliente = cliente;
    }

    @Override
    public String getTipoSaida() { return "Venda"; }

    public Cliente getCliente() { return cliente; }

    @Override
    public String toString() {
        return super.toString() +
                " | Cliente: " + (cliente != null ? cliente.getNome() : "-");
    }
}
