package GameofLife;

import javax.swing.*;

public class GameController {
  private GameBoard model;
  private GameView view;
  private boolean running = false;
  private final int delay = 5000; // Delay between generations in milliseconds

  public GameController(GameBoard model, GameView view) {
    this.model = model;
    this.view = view;
    view.setController(this);
  }

  public void startGame() {
    if (!running) {
      running = true;
      Thread gameThread = new Thread(this::runGame);
      gameThread.start();
    }
  }

  public void stopGame() {
    running = false;
  }

  private void runGame() {
    while (running) {
      model.nextGeneration();
      SwingUtilities.invokeLater(() -> view.updateView(model));
      try {
        Thread.sleep(delay); // Control timing between generations
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  public void toggleCellState(int x, int y) {
    model.getCell(x, y).toggleState();
    view.updateView(model);
  }

  public boolean getCellState(int x, int y) {
    return model.getCell(x, y).isAlive();
  }

}

