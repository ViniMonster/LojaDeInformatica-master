import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;

import org.junit.jupiter.api.*;

import model.*;
import persistence.DataStore;

/**
 * @author ViniMonster
 */
public class ControleEstoqueTest {

    @BeforeEach
    void limparData() throws Exception {
        Path dataDir = Paths.get("data");
        if (Files.exists(dataDir)) {
            Files.walk(dataDir)
                    .sorted((a,b) -> b.compareTo(a))
                    .map(Path::toFile)
                    .forEach(f -> f.delete());
        }
        Files.createDirectories(dataDir);
    }

    @Test
    void adicionarProdutoDeveSalvarNoDataStore() {
        ControleEstoque ce = new ControleEstoque();

        Produto p = new Produto("P01", "Mouse", 20f, 5, Categoria.perifericos);
        ce.adicionarProduto(p);

        assertNotNull(ce.buscarProdutoPorCodigo("P01"));

        assertTrue(Files.exists(Paths.get("data/products.csv")));
    }

    @Test
    void buscarProdutoPorCodigoDeveFuncionar() {
        ControleEstoque ce = new ControleEstoque();

        Produto p = new Produto("P02", "Teclado", 50f, 3, Categoria.perifericos);
        ce.adicionarProduto(p);

        assertEquals("P02", ce.buscarProdutoPorCodigo("P02").getCodigo());
        assertNull(ce.buscarProdutoPorCodigo("NAOEXISTE"));
    }

    @Test
    void registrarMovimentoAplicaEGrava() {
        ControleEstoque ce = new ControleEstoque();

        Produto p = new Produto("P03", "Monitor", 900f, 2, Categoria.hardware);
        ce.adicionarProduto(p);

        EntradaProduto ent = new EntradaProduto(900f, LocalDateTime.now(), 3, p, new Fornecedor("A","B"));
        ce.registrarMovimento(ent);

        assertEquals(5, p.getQtdEstoque());
        assertTrue(Files.exists(Paths.get("data/movements.csv")));
    }

    @Test
    void listarMovimentosDeveClassificar() {
        ControleEstoque ce = new ControleEstoque();

        Produto p = new Produto("P04", "HD", 200f, 10, Categoria.hardware);
        ce.adicionarProduto(p);

        ce.registrarMovimento(new EntradaProduto(200f, LocalDateTime.of(2024,1,1,10,0), 2, p, null));
        ce.registrarMovimento(new EntradaProduto(200f, LocalDateTime.of(2024,1,1,9,0), 1, p, null));

        var lista = ce.listarMovimentosOrdenados();

        assertTrue(lista.get(0).getData().isBefore(lista.get(1).getData()));
    }

    @Test
    void saldoAtualQuantidadeEValorDevemSerCalculados() {
        ControleEstoque ce = new ControleEstoque();

        Produto p1 = new Produto("P10", "Cabo", 10f, 2, Categoria.acessorios);
        Produto p2 = new Produto("P11", "Mouse", 20f, 3, Categoria.perifericos);

        ce.adicionarProduto(p1);
        ce.adicionarProduto(p2);

        assertEquals(5, ce.getSaldoAtualQuantidade());
        assertEquals(2 * 10 + 3 * 20, ce.getSaldoAtualValor());
    }
}
