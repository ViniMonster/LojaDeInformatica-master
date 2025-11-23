package view;

import model.ControleEstoque;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Janela responsável por exibir ao usuário o saldo atual do estoque,
 * tanto em quantidade quanto em valor financeiro, além de permitir
 * a consulta do saldo dentro de um período específico.
 * <p>
 * Esta tela é modal e bloqueia a janela principal enquanto está aberta.
 * </p>
 *
 * <p>O usuário pode visualizar:</p>
 * <ul>
 *     <li>Saldo total em quantidade;</li>
 *     <li>Saldo total em valor;</li>
 *     <li>Consultar o valor movimentado em um período desejado.</li>
 * </ul>
 *
 * @author GustavoVirges
 */
public class TelaConsultarSaldo extends JDialog {

    /** Controle principal responsável pelos cálculos de saldo. */
    private final ControleEstoque controle;

    /** Formatação padrão para exibir e interpretar datas. */
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Constrói a janela de consulta de saldo.
     *
     * @param parent    janela principal que abriu este diálogo
     * @param controle  instância de controle responsável pelos cálculos de estoque
     */
    public TelaConsultarSaldo(JFrame parent, ControleEstoque controle) {
        super(parent, "Consultar Saldo", true);
        this.controle = controle;

        setSize(520, 300);
        setLayout(null);
        setLocationRelativeTo(parent);

        JLabel lblSaldoAtualQtd = new JLabel("Saldo total (Quantidade):");
        JLabel lblSaldoAtualValor = new JLabel("Saldo total (Valor):");

        JLabel lblQtdVal = new JLabel(String.valueOf(controle.getSaldoAtualQuantidade()));
        JLabel lblValorVal = new JLabel(String.format("R$ %.2f", controle.getSaldoAtualValor()));

        JLabel lblPeriodoInicio = new JLabel("Início:");
        JLabel lblPeriodoFim = new JLabel("Fim:");

        JTextField txtInicio = new JTextField(LocalDateTime.now().minusDays(30).format(fmt));
        JTextField txtFim = new JTextField(LocalDateTime.now().format(fmt));

        JButton btnConsultarPeriodo = new JButton("Consultar período");
        JLabel lblPeriodoResultado = new JLabel("Valor no período: R$ 0.00");

        // Posicionamento dos componentes
        lblSaldoAtualQtd.setBounds(20, 20, 220, 25);
        lblQtdVal.setBounds(260, 20, 200, 25);

        lblSaldoAtualValor.setBounds(20, 55, 220, 25);
        lblValorVal.setBounds(260, 55, 200, 25);

        lblPeriodoInicio.setBounds(20, 100, 220, 25);
        txtInicio.setBounds(260, 100, 240, 25);

        lblPeriodoFim.setBounds(20, 140, 220, 25);
        txtFim.setBounds(260, 140, 240, 25);

        btnConsultarPeriodo.setBounds(180, 190, 160, 30);
        lblPeriodoResultado.setBounds(20, 230, 480, 25);

        // Adicionando componentes à tela
        add(lblSaldoAtualQtd); add(lblQtdVal);
        add(lblSaldoAtualValor); add(lblValorVal);
        add(lblPeriodoInicio); add(txtInicio);
        add(lblPeriodoFim); add(txtFim);
        add(btnConsultarPeriodo);
        add(lblPeriodoResultado);

        /**
         * Ação do botão que calcula o saldo no período fornecido.
         */
        btnConsultarPeriodo.addActionListener(e -> {
            try {
                LocalDateTime inicio = LocalDateTime.parse(txtInicio.getText().trim(), fmt);
                LocalDateTime fim = LocalDateTime.parse(txtFim.getText().trim(), fmt);
                float valor = controle.getSaldoPorPeriodo(inicio, fim);
                lblPeriodoResultado.setText(String.format("Valor no período: R$ %.2f", valor));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao consultar: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
