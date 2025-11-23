package view;

import model.*;
import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TelaRegistrarEntrada extends JDialog {

    private ControleEstoque controle;
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public TelaRegistrarEntrada(JFrame parent, ControleEstoque controle) {
        super(parent, "Registrar Entrada", true);
        this.controle = controle;

        setSize(500, 330);
        setLayout(null);
        setLocationRelativeTo(parent);

        JLabel lblProduto = new JLabel("Código do Produto:");
        JLabel lblQtd = new JLabel("Quantidade:");
        JLabel lblValor = new JLabel("Valor unitário:");
        JLabel lblData = new JLabel("Data:");

        JTextField txtProduto = new JTextField();
        JTextField txtQtd = new JTextField();
        JTextField txtValor = new JTextField();
        JTextField txtData = new JTextField(LocalDateTime.now().format(fmt));

        JButton btnRegistrar = new JButton("Registrar");

        lblProduto.setBounds(20, 20, 150, 25);
        txtProduto.setBounds(170, 20, 300, 25);

        lblQtd.setBounds(20, 60, 150, 25);
        txtQtd.setBounds(170, 60, 300, 25);

        lblValor.setBounds(20, 100, 150, 25);
        txtValor.setBounds(170, 100, 300, 25);

        lblData.setBounds(20, 140, 150, 25);
        txtData.setBounds(170, 140, 300, 25);

        btnRegistrar.setBounds(170, 190, 120, 30);

        add(lblProduto); add(txtProduto);
        add(lblQtd); add(txtQtd);
        add(lblValor); add(txtValor);
        add(lblData); add(txtData);
        add(btnRegistrar);

        btnRegistrar.addActionListener(e -> {
            try {
                String codigo = txtProduto.getText().trim();
                int qtd = Integer.parseInt(txtQtd.getText().trim());
                float valor = Float.parseFloat(txtValor.getText().trim().replace(",", "."));
                LocalDateTime data = LocalDateTime.parse(txtData.getText().trim(), fmt);

                Produto p = controle.buscarProdutoPorCodigo(codigo);
                if (p == null) {
                    JOptionPane.showMessageDialog(this, "Produto não encontrado.");
                    return;
                }

                EntradaProduto entrada = new EntradaProduto(valor, data, qtd, p, null);
                controle.registrarMovimento(entrada);

                JOptionPane.showMessageDialog(this, "Entrada registrada.");
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
