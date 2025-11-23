import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.*;
import java.util.*;

import org.junit.jupiter.api.*;

import model.*;
import persistence.DataStore;
/**
 * @author ViniMonster
 */
public class DataStoreTest {

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
    void saveAndLoadProductsShouldPersistAndRetrieve() {
        List<Produto> produtos = List.of(
                new Produto("P01", "Mouse", 20f, 5, Categoria.perifericos),
                new Produto("P02", "Teclado", 50f, 3, Categoria.perifericos)
        );

        DataStore.saveProducts(produtos);
        var lidos = DataStore.loadProducts();

        assertEquals(2, lidos.size());
        assertEquals("P01", lidos.get(0).getCodigo());
    }

    @Test
    void saveAndLoadMovementsShouldPreserveMovimentos() {
        Produto p = new Produto("P03", "HD", 200f, 10, Categoria.hardware);

        DataStore.saveProducts(List.of(p));

        EntradaProduto ent = new EntradaProduto(200f,
                java.time.LocalDateTime.now(),
                2, p, null);

        DataStore.saveMovements(List.of(ent));

        var movimentos = DataStore.loadMovements(List.of(p));

        assertEquals(1, movimentos.size());
        assertEquals("P03", movimentos.get(0).getProduto().getCodigo());
        assertEquals(2, movimentos.get(0).getQtd());
    }

    @Test
    void loadMovementsShouldHandleEmptyExtraFields() throws Exception {
        String linha = "ENTRADA;P01;2;100;2024-01-01T10:00;\n";
        Files.write(Paths.get("data/movements.csv"), linha.getBytes());

        Produto p = new Produto("P01", "Mouse", 20f, 5, Categoria.perifericos);

        var lista = DataStore.loadMovements(List.of(p));

        assertEquals(1, lista.size());
        assertEquals(2, lista.get(0).getQtd());
    }
}
