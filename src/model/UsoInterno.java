package model;

import java.time.LocalDateTime;

public class UsoInterno extends SaidaProduto {
    private String destino;

    public UsoInterno(float valorUnitario, LocalDateTime data, int qtd, Produto produto, String destino) {
        super(valorUnitario, data, qtd, produto);
        this.destino = destino;
    }

    @Override
    public String getTipoSaida() { return "Uso Interno"; }

    public String getDestino() { return destino; }

    @Override
    public String toString() {
        return super.toString() + " | Destino: " + destino;
    }
}
