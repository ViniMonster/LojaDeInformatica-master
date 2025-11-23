package view;

import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaListarMovimentos extends JDialog {

    private ControleEstoque controle;
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public TelaListarMovimentos(JFrame parent, ControleEstoque controle) {
        super(parent, "Movimentos do Estoque", true);
        this.controle = controle;

        setSize(900, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        String[] colunas = {"Data", "Tipo", "Produto", "Qtd", "Valor Unit", "Impacto Qtd", "Impacto Valor"};

        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        carregarMovimentos(modelo);

        setVisible(true);
    }

    private void carregarMovimentos(DefaultTableModel modelo) {
        modelo.setRowCount(0);

        List<MovimentoEstoque> movs = controle.listarMovimentosOrdenados();

        int saldoQtd = 0;
        float saldoValor = 0f;

        for (MovimentoEstoque m : movs) {

            String tipo = m instanceof EntradaProduto ? "ENTRADA" : "SAÍDA";

            if (m instanceof VendasClientes) tipo = "VENDA";
            if (m instanceof UsoInterno) tipo = "USO INTERNO";
            if (m instanceof DevolucaoFornecedores) tipo = "DEVOLUÇÃO FORNECEDOR";
            if (m instanceof OutrasSaidas) tipo = "OUTRA SAÍDA";

            int impactoQtd = m instanceof EntradaProduto ? m.getQtd() : -m.getQtd();
            float impactoValor = impactoQtd * m.getValorUnitario();

            saldoQtd += impactoQtd;
            saldoValor += impactoValor;

            modelo.addRow(new Object[]{
                    m.getData().format(fmt),
                    tipo,
                    m.getProduto().getNome() + " (" + m.getProduto().getCodigo() + ")",
                    m.getQtd(),
                    String.format("%.2f", m.getValorUnitario()),
                    impactoQtd,
                    String.format("%.2f", impactoValor)
            });
        }

        modelo.addRow(new Object[]{"", "", "SALDO ATUAL", saldoQtd, "", "", String.format("%.2f", saldoValor)});
    }
}
