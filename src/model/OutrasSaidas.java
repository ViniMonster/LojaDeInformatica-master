package model;

import java.time.LocalDateTime;

public class OutrasSaidas extends SaidaProduto {
    private String outraSaida;

    public OutrasSaidas(float valorUnitario, LocalDateTime data, int qtd, Produto produto, String outraSaida) {
        super(valorUnitario, data, qtd, produto);
        this.outraSaida = outraSaida;
    }

    @Override
    public String getTipoSaida() { return "Outra Saída"; }

    @Override
    public String toString() {
        return super.toString() + " | Motivo: " + outraSaida;
    }
}
