package persistence;

import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataStore {

    private static final Path DATA_DIR = Paths.get("data");
    private static final Path PRODUCTS_FILE = DATA_DIR.resolve("products.csv");
    private static final Path MOVEMENTS_FILE = DATA_DIR.resolve("movements.csv");
    private static final DateTimeFormatter FMT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    static {
        try {
            if (!Files.exists(DATA_DIR)) Files.createDirectories(DATA_DIR);
            if (!Files.exists(PRODUCTS_FILE)) Files.createFile(PRODUCTS_FILE);
            if (!Files.exists(MOVEMENTS_FILE)) Files.createFile(MOVEMENTS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // products.csv fields: codigo;nome;valorUnitario;qtdEstoque;categoria
    public static List<Produto> loadProducts() {
        List<Produto> list = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(PRODUCTS_FILE, StandardCharsets.UTF_8)) {
            String line;
            while ((line = r.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(";", -1);
                String codigo = parts[0];
                String nome = parts[1];
                float valor = Float.parseFloat(parts[2]);
                int qtd = Integer.parseInt(parts[3]);
                Categoria cat = Categoria.valueOf(parts[4]);
                Produto p = new Produto(codigo, nome, valor, qtd, cat);
                list.add(p);
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }

    public static void saveProducts(Collection<Produto> produtos) {
        try (BufferedWriter w = Files.newBufferedWriter(PRODUCTS_FILE, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Produto p : produtos) {
                String line = String.join(";", p.getCodigo(), p.getNome(), String.valueOf(p.getValorUnitario()), String.valueOf(p.getQtdEstoque()), p.getCategoria().name());
                w.write(line);
                w.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    // movements.csv fields: tipo;valorUnitario;data;qtd;produtoCodigo;extra (cliente/fornecedor/motivo)
    public static List<MovimentoEstoque> loadMovements(List<Produto> produtos) {
        List<MovimentoEstoque> list = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(MOVEMENTS_FILE, StandardCharsets.UTF_8)) {
            String line;
            while ((line = r.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(";", -1);
                String tipo = parts[0];
                float valor = Float.parseFloat(parts[1]);
                LocalDateTime data = LocalDateTime.parse(parts[2], FMT);
                int qtd = Integer.parseInt(parts[3]);
                String produtoCodigo = parts[4];
                String extra = parts.length > 5 ? parts[5] : "";

                Produto p = produtos.stream().filter(pp -> pp.getCodigo().equals(produtoCodigo)).findFirst().orElse(null);
                if (p == null) continue;

                MovimentoEstoque m = null;
                switch (tipo) {
                    case "ENTRADA":
                        Fornecedor f = extra.isEmpty() ? null : new Fornecedor(extra, "");
                        m = new EntradaProduto(valor, data, qtd, p, f);
                        break;
                    case "VENDA":
                        Cliente c = extra.isEmpty() ? null : new Cliente(extra, "");
                        m = new VendasClientes(valor, data, qtd, p, c);
                        break;
                    case "USO":
                        m = new UsoInterno(valor, data, qtd, p, extra);
                        break;
                    case "DEVOLUCAO":
                        Fornecedor f2 = extra.isEmpty() ? null : new Fornecedor(extra, "");
                        m = new DevolucaoFornecedores(valor, data, qtd, p, f2);
                        break;
                    case "OUTRA":
                        m = new OutrasSaidas(valor, data, qtd, p, extra);
                        break;
                }
                if (m != null) list.add(m);
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }

    public static void saveMovements(Collection<MovimentoEstoque> movimentos) {
        try (BufferedWriter w = Files.newBufferedWriter(MOVEMENTS_FILE, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (MovimentoEstoque m : movimentos) {
                String tipo;
                String extra = "";
                if (m instanceof EntradaProduto) {
                    tipo = "ENTRADA";
                    extra = ((EntradaProduto) m).getFornecedor() != null ? ((EntradaProduto) m).getFornecedor().getNome() : "";
                } else if (m instanceof VendasClientes) {
                    tipo = "VENDA";
                    extra = ((VendasClientes) m).getCliente() != null ? ((VendasClientes) m).getCliente().getNome() : "";
                } else if (m instanceof UsoInterno) {
                    tipo = "USO";
                    extra = ((UsoInterno) m).getDestino();
                } else if (m instanceof DevolucaoFornecedores) {
                    tipo = "DEVOLUCAO";
                    extra = ((DevolucaoFornecedores) m).getFornecedor() != null ? ((DevolucaoFornecedores) m).getFornecedor().getNome() : "";
                } else if (m instanceof OutrasSaidas) {
                    tipo = "OUTRA";
                    extra = ((OutrasSaidas) m).toString(); // motivo dentro do toString
                } else {
                    tipo = "OUTRO";
                }
                String line = String.join(";", tipo, String.valueOf(m.getValorUnitario()), m.getData().format(FMT), String.valueOf(m.getQtd()), m.getProduto().getCodigo(), extra);
                w.write(line); w.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}
