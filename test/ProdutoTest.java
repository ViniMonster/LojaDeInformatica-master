import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Categoria;
import model.Produto;
/**
 * @author ViniMonster
 */
public class ProdutoTest {

    @Test
    void produtoDeveSerCriadoComDadosValidos() {
        Produto p = new Produto("P001", "Teclado", 50f, 10, Categoria.perifericos);

        assertEquals("P001", p.getCodigo());
        assertEquals("Teclado", p.getNome());
        assertEquals(50f, p.getValorUnitario());
        assertEquals(10, p.getQtdEstoque());
    }

    @Test
    void construtorDeveLancarParaValorNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Produto("P002", "Mouse", -1f, 5, Categoria.perifericos);
        });
    }

    @Test
    void construtorDeveLancarParaQuantidadeNegativa() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Produto("P003", "Monitor", 100f, -2, Categoria.perifericos);
        });
    }

    @Test
    void toStringDeveConterCamposEsperados() {
        Produto p = new Produto("P004", "SSD", 200f, 3, Categoria.hardware);
        String s = p.toString();

        assertTrue(s.contains("P004"));
        assertTrue(s.contains("SSD"));
        assertTrue(s.contains("200"));
        assertTrue(s.contains("3"));
    }
}
