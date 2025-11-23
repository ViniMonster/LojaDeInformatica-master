package view;

import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaLoja extends JFrame {

    private ControleEstoque controle;
    private DefaultTableModel modeloTabela;

    public TelaLoja(ControleEstoque controle) {
        this.controle = controle;

        setTitle("Sistema de Controle de Estoque - Loja de Informática");
        setSize(950, 650);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(235, 235, 235));

        JPanel menu = new JPanel();
        menu.setLayout(null);
        menu.setBounds(30, 30, 320, 550);
        menu.setBackground(new Color(210, 210, 210));
        add(menu);

        JButton btnProdutos   = new JButton("Produtos");
        JButton btnEntrada    = new JButton("Registrar Entrada");
        JButton btnSaida      = new JButton("Registrar Saída");
        JButton btnSaldo      = new JButton("Consultar Saldo");
        JButton btnMovimentos = new JButton("Movimentações");

        btnProdutos.setBounds(80, 40, 160, 45);
        btnEntrada.setBounds(80, 110, 160, 45);
        btnSaida.setBounds(80, 180, 160, 45);
        btnSaldo.setBounds(80, 250, 160, 45);
        btnMovimentos.setBounds(80, 320, 160, 45);

        menu.add(btnProdutos);
        menu.add(btnEntrada);
        menu.add(btnSaida);
        menu.add(btnSaldo);
        menu.add(btnMovimentos);

        JPanel cadastro = new JPanel();
        cadastro.setLayout(null);
        cadastro.setBounds(380, 30, 520, 280);
        cadastro.setBackground(new Color(210, 210, 210));
        add(cadastro);

        JLabel titulo = new JLabel("Cadastro de Produtos", SwingConstants.CENTER);
        titulo.setBounds(60, 10, 400, 40);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        cadastro.add(titulo);

        JLabel lblCodigo = new JLabel("Código:");
        JLabel lblNome = new JLabel("Nome:");
        JLabel lblCategoria = new JLabel("Categoria:");
        JLabel lblPreco = new JLabel("Preço (R$):");
        JLabel lblQtd = new JLabel("Quantidade:");

        lblCodigo.setBounds(20, 60, 120, 30);
        lblNome.setBounds(20, 95, 120, 30);
        lblCategoria.setBounds(20, 130, 120, 30);
        lblPreco.setBounds(20, 165, 120, 30);
        lblQtd.setBounds(20, 200, 120, 30);

        cadastro.add(lblCodigo); cadastro.add(lblNome);
        cadastro.add(lblCategoria); cadastro.add(lblPreco);
        cadastro.add(lblQtd);

        JTextField txtCodigo = new JTextField();
        JTextField txtNome = new JTextField();
        JTextField txtPreco = new JTextField();
        JTextField txtQtd = new JTextField();

        txtCodigo.setBounds(140, 60, 340, 30);
        txtNome.setBounds(140, 95, 340, 30);
        txtPreco.setBounds(140, 165, 340, 30);
        txtQtd.setBounds(140, 200, 340, 30);

        cadastro.add(txtCodigo); cadastro.add(txtNome);
        cadastro.add(txtPreco); cadastro.add(txtQtd);

        JComboBox<String> categoriaBox = new JComboBox<>(
                new String[]{"hardware", "perifericos", "acessorios", "outrosProdutos"}
        );

        categoriaBox.setBounds(140, 130, 340, 30);
        cadastro.add(categoriaBox);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnNovo = new JButton("Novo");
        JButton btnExcluir = new JButton("Excluir");

        btnSalvar.setBounds(40, 240, 120, 30);
        btnNovo.setBounds(200, 240, 120, 30);
        btnExcluir.setBounds(360, 240, 120, 30);

        cadastro.add(btnSalvar);
        cadastro.add(btnNovo);
        cadastro.add(btnExcluir);

        String[] colunas = {"Código", "Nome", "Categoria", "Preço", "Estoque"};
        modeloTabela = new DefaultTableModel(colunas, 0);

        JTable tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(380, 330, 520, 250);
        add(scroll);

        carregarProdutosNaTabela();

        btnSalvar.addActionListener(e -> {
            try {
                String codigo = txtCodigo.getText().trim();
                String nome = txtNome.getText().trim();
                String preco = txtPreco.getText().trim();
                String qtd = txtQtd.getText().trim();

                if (codigo.isEmpty() || nome.isEmpty() || preco.isEmpty() || qtd.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                    return;
                }

                float precoF = Float.parseFloat(preco.replace(",", "."));
                int qtdI = Integer.parseInt(qtd);

                Categoria cat = Categoria.valueOf(categoriaBox.getSelectedItem().toString());

                if (controle.buscarProdutoPorCodigo(codigo) != null) {
                    JOptionPane.showMessageDialog(null, "Código já cadastrado.");
                    return;
                }

                Produto p = new Produto(codigo, nome, precoF, qtdI, cat);
                controle.adicionarProduto(p);

                modeloTabela.addRow(new Object[]{
                        codigo, nome, cat.name(),
                        String.format("%.2f", precoF), qtdI
                });

                txtCodigo.setText("");
                txtNome.setText("");
                txtPreco.setText("");
                txtQtd.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro nos valores numéricos.");
            }
        });

        btnNovo.addActionListener(e -> {
            txtCodigo.setText("");
            txtNome.setText("");
            txtPreco.setText("");
            txtQtd.setText("");
            categoriaBox.setSelectedIndex(0);
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um produto para excluir.");
                return;
            }

            String codigo = (String) modeloTabela.getValueAt(linha, 0);
            Produto p = controle.buscarProdutoPorCodigo(codigo);

            if (p != null) {
                controle.getProdutos().remove(p);
                persistence.DataStore.saveProducts(controle.getProdutos());
            }

            modeloTabela.removeRow(linha);
        });

        btnEntrada.addActionListener(e -> new TelaRegistrarEntrada(this, controle));
        btnSaida.addActionListener(e -> new TelaRegistrarSaida(this, controle));
        btnSaldo.addActionListener(e -> new TelaConsultarSaldo(this, controle));
        btnMovimentos.addActionListener(e -> new TelaListarMovimentos(this, controle));

        setVisible(true);
    }

    public void carregarProdutosNaTabela() {
        modeloTabela.setRowCount(0);

        for (Produto p : controle.getProdutos()) {
            modeloTabela.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNome(),
                    p.getCategoria().name(),
                    String.format("%.2f", p.getValorUnitario()),
                    p.getQtdEstoque()
            });
        }
    }
}
