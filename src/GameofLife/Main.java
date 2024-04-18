package GameofLife;
import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    // Ensuring GUI updates are handled in the Event Dispatch Thread
    SwingUtilities.invokeLater(() -> {
      try {
        // Prompt for the size of the board
        String input = JOptionPane.showInputDialog("Enter the size of the board (N x N):");
        if (input != null && !input.trim().isEmpty()) {
          int size = Integer.parseInt(input.trim());

          // Initialize the game components
          GameBoard model = new GameBoard(size);
          GameView view = new GameView(size);
          GameController controller = new GameController(model, view);

          // Set the controller in the view
          view.setController(controller);

          // Optionally, set an initial configuration or seed
          boolean[][] seed = new boolean[size][size];
          // Example seed configuration (glider)
          if (size >= 3) { // Ensuring the board is large enough for a glider
            seed[0][1] = true;
            seed[1][2] = true;
            seed[2][0] = true;
            seed[2][1] = true;
            seed[2][2] = true;
          }
          controller.setInitialConfiguration(seed);

          // Start the game automatically or via user interface
          // controller.startGame(); // Uncomment if you want to start automatically

        } else {
          System.exit(0); // Exit if no size is provided or input is cancelled
        }
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Please enter a valid integer.");
      }
    });
  }
}
