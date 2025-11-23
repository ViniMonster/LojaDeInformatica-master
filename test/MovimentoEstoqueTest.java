import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import model.*;
/**
 * @author ViniMonster
 */
public class MovimentoEstoqueTest {

    @Test
    void entradaAumentaEstoque() {
        Produto p = new Produto("P01", "Mouse", 20f, 5, Categoria.perifericos);

        EntradaProduto entrada = new EntradaProduto(
                20f,
                LocalDateTime.now(),
                3,
                p,
                new Fornecedor("Fornecedor X", "Rua Y")
        );

        entrada.aplicarMovimento();

        assertEquals(8, p.getQtdEstoque());
    }

    @Test
    void saidaDiminuiEstoque() {
        Produto p = new Produto("P02", "Memória", 100f, 10, Categoria.hardware);

        SaidaProduto venda = new VendasClientes(
                100f,
                LocalDateTime.now(),
                4,
                p,
                new Cliente("João", "1111")
        );

        venda.aplicarMovimento();

        assertEquals(6, p.getQtdEstoque());
    }

    @Test
    void subclassesDeSaidaDevemRetornarTipoCorreto() {
        Produto p = new Produto("P03", "Cabo", 10f, 5, Categoria.acessorios);

        assertEquals("Venda", new VendasClientes(10f, LocalDateTime.now(), 1, p, null).getTipoSaida());
        assertEquals("Uso Interno", new UsoInterno(10f, LocalDateTime.now(), 1, p, "Setor TI").getTipoSaida());
        assertEquals("Devolução ao Fornecedor",
                new DevolucaoFornecedores(10f, LocalDateTime.now(), 1, p, new Fornecedor("ABC","End")).getTipoSaida());
        assertEquals("Outra Saída",
                new OutrasSaidas(10f, LocalDateTime.now(), 1, p, "Motivo").getTipoSaida());
    }

    @Test
    void movimentoToStringContemCampos() {
        Produto p = new Produto("P04", "Fonte", 80f, 2, Categoria.hardware);

        EntradaProduto ent = new EntradaProduto(80f, LocalDateTime.now(), 1, p, new Fornecedor("F","E"));

        String s = ent.toString();

        assertTrue(s.contains("Fonte"));
        assertTrue(s.contains("1"));
        assertTrue(s.contains("80"));
    }
}
