package view;

import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Janela responsável por exibir, em forma de tabela, todos os movimentos
 * registrados no estoque. A listagem inclui entradas, vendas, usos internos,
 * devoluções e outras saídas.
 * <p>
 * A tabela apresenta informações importantes como:
 * data, tipo de movimento, produto, quantidade movimentada,
 * valor unitário, impacto no estoque e impacto financeiro.
 * </p>
 *
 * <p>
 * O último registro exibido na tabela corresponde ao saldo final
 * acumulado de quantidade e valor.
 * </p>
 *
 * @author GustavoVirges
 */
public class TelaListarMovimentos extends JDialog {

    /** Controle que fornece os movimentos do estoque. */
    private final ControleEstoque controle;

    /** Formatação padrão usada para exibir datas na tabela. */
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Construtor da janela de listagem de movimentos.
     *
     * @param parent    a janela principal que abriu este diálogo
     * @param controle  instância que gerencia operações de estoque
     */
    public TelaListarMovimentos(JFrame parent, ControleEstoque controle) {
        super(parent, "Movimentos do Estoque", true);
        this.controle = controle;

        setSize(900, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        String[] colunas = {
                "Data", "Tipo", "Produto", "Qtd",
                "Valor Unit", "Impacto Qtd", "Impacto Valor"
        };

        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        carregarMovimentos(modelo);

        setVisible(true);
    }

    /**
     * Preenche a tabela com todos os movimentos registrados,
     * aplicando ordenação por data e cálculo dos impactos.
     *
     * @param modelo modelo da tabela onde os dados serão inseridos
     */
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

        // Linha final exibindo o saldo total acumulado
        modelo.addRow(new Object[]{
                "", "", "SALDO ATUAL", saldoQtd, "", "", String.format("%.2f", saldoValor)
        });
    }
}
