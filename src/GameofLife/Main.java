package GameofLife;
import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      GameView view = new GameView();

      view.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowOpened(java.awt.event.WindowEvent evt) {
          int size = view.getGridSize();
          GameBoard model = new GameBoard(size);
          GameController controller = new GameController(model, view);

          view.setController(controller);
        }
      });
    });
  }
}
