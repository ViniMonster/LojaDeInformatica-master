import model.ControleEstoque;
import view.TelaLoja;

public class Main {
    public static void main(String[] args) {
        ControleEstoque controle = new ControleEstoque();
        javax.swing.SwingUtilities.invokeLater(() -> new TelaLoja(controle));

    }
}
