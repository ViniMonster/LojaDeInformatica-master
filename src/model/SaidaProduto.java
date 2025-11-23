package model;

import java.time.LocalDateTime;

public abstract class SaidaProduto extends MovimentoEstoque {
    public SaidaProduto(float valorUnitario, LocalDateTime data, int qtd, Produto produto) {
        super(valorUnitario, data, qtd, produto);
    }

    @Override
    public void aplicarMovimento() {
        getProduto().diminuirEstoque(getQtd());
    }

    public abstract String getTipoSaida();

    @Override
    public String toString() {
        return "SAÍDA [" + getTipoSaida() + "] | " + super.toString();
    }
}
