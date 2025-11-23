package view;

import model.ControleEstoque;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TelaConsultarSaldo extends JDialog {

    private ControleEstoque controle;
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

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

        add(lblSaldoAtualQtd); add(lblQtdVal);
        add(lblSaldoAtualValor); add(lblValorVal);
        add(lblPeriodoInicio); add(txtInicio);
        add(lblPeriodoFim); add(txtFim);
        add(btnConsultarPeriodo);
        add(lblPeriodoResultado);

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
