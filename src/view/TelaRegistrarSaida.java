package view;

import model.*;
import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TelaRegistrarSaida extends JDialog {

    private ControleEstoque controle;
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public TelaRegistrarSaida(JFrame parent, ControleEstoque controle) {
        super(parent, "Registrar Saída", true);
        this.controle = controle;

        setSize(520, 380);
        setLayout(null);
        setLocationRelativeTo(parent);

        JLabel lblProduto = new JLabel("Código do Produto:");
        JLabel lblQtd = new JLabel("Quantidade:");
        JLabel lblValor = new JLabel("Valor unitário:");
        JLabel lblData = new JLabel("Data:");
        JLabel lblTipo = new JLabel("Tipo:");

        String[] tipos = {"VENDA", "USO", "DEVOLUCAO", "OUTRA"};
        JComboBox<String> tipoBox = new JComboBox<>(tipos);

        JLabel lblExtra = new JLabel("Detalhe:");
        JTextField txtExtra = new JTextField();

        JTextField txtProduto = new JTextField();
        JTextField txtQtd = new JTextField();
        JTextField txtValor = new JTextField();
        JTextField txtData = new JTextField(LocalDateTime.now().format(fmt));

        JButton btnRegistrar = new JButton("Registrar");

        lblProduto.setBounds(20, 20, 180, 25);
        txtProduto.setBounds(200, 20, 290, 25);

        lblQtd.setBounds(20, 60, 180, 25);
        txtQtd.setBounds(200, 60, 290, 25);

        lblValor.setBounds(20, 100, 180, 25);
        txtValor.setBounds(200, 100, 290, 25);

        lblData.setBounds(20, 140, 180, 25);
        txtData.setBounds(200, 140, 290, 25);

        lblTipo.setBounds(20, 180, 180, 25);
        tipoBox.setBounds(200, 180, 290, 25);

        lblExtra.setBounds(20, 220, 180, 25);
        txtExtra.setBounds(200, 220, 290, 25);

        btnRegistrar.setBounds(200, 270, 120, 30);

        add(lblProduto); add(txtProduto);
        add(lblQtd); add(txtQtd);
        add(lblValor); add(txtValor);
        add(lblData); add(txtData);
        add(lblTipo); add(tipoBox);
        add(lblExtra); add(txtExtra);
        add(btnRegistrar);

        btnRegistrar.addActionListener(e -> {
            try {
                String codigo = txtProduto.getText().trim();
                int qtd = Integer.parseInt(txtQtd.getText().trim());
                float valor = Float.parseFloat(txtValor.getText().trim().replace(",", "."));
                LocalDateTime data = LocalDateTime.parse(txtData.getText().trim(), fmt);
                String tipo = tipoBox.getSelectedItem().toString();
                String extra = txtExtra.getText().trim();

                Produto p = controle.buscarProdutoPorCodigo(codigo);
                if (p == null) {
                    JOptionPane.showMessageDialog(this, "Produto não encontrado.");
                    return;
                }

                SaidaProduto saida = null;

                switch (tipo) {
                    case "VENDA":
                        Cliente c = extra.isEmpty() ? null : new Cliente(extra, "");
                        saida = new VendasClientes(valor, data, qtd, p, c);
                        break;

                    case "USO":
                        saida = new UsoInterno(valor, data, qtd, p, extra);
                        break;

                    case "DEVOLUCAO":
                        Fornecedor f = extra.isEmpty() ? null : new Fornecedor(extra, "");
                        saida = new DevolucaoFornecedores(valor, data, qtd, p, f);
                        break;

                    case "OUTRA":
                        saida = new OutrasSaidas(valor, data, qtd, p, extra);
                        break;
                }

                controle.registrarMovimento(saida);

                JOptionPane.showMessageDialog(this, "Saída registrada.");
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
