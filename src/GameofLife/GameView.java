package GameofLife;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameView extends JFrame {
  private GameController controller;
  private JPanel gridPanel;
  private int size; // Variable to store the size of the grid
  private final int cellSize = 10; // Pixel size of each cell in the grid

  public GameView() {
    setTitle("Conway's Game of Life");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    requestBoardSize();
  }

  public void setController(GameController controller) {
    this.controller = controller;
  }

  private void requestBoardSize() {
    String input = JOptionPane.showInputDialog(this, "Enter the size of the board (N x N, max 100):");
    if (input != null && !input.trim().isEmpty()) {
      try {
        int inputSize = Integer.parseInt(input.trim());
        if (inputSize > 0 && inputSize <= 100) {
          size = inputSize;  // Set the size based on user input
          initializeUI();
        } else {
          JOptionPane.showMessageDialog(this, "Please enter a number between 1 and 100.");
          requestBoardSize(); // Re-request if input is out of bounds
        }
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Please enter a valid integer.");
        requestBoardSize(); // Re-request if input is invalid
      }
    } else {
      System.exit(0); // Exit if input is canceled
    }
  }

  private void initializeUI() {
    setSize(size * cellSize + 50, size * cellSize + 100);
    initializeGameBoard();
    initializeControls();
    setLocationRelativeTo(null); // Center the window
    setVisible(true);
  }

  private void initializeGameBoard() {
    gridPanel = new JPanel(new GridLayout(size, size));
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        JPanel cell = new JPanel();
        cell.setPreferredSize(new Dimension(cellSize, cellSize));
        cell.setBorder(BorderFactory.createLineBorder(Color.gray));
        cell.setBackground(Color.white);

        final int finalI = i;  // Effectively final copy of i
        final int finalJ = j;  // Effectively final copy of j

        cell.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            if (controller != null) {
              controller.toggleCellState(finalI, finalJ);
              updateCellColor(cell, finalI, finalJ);
            }
          }
        });
        gridPanel.add(cell);
      }
    }
    add(gridPanel, BorderLayout.CENTER);
  }

  public int getBoardSize() {  // Getter for the grid size
    return size;
  }

  private void updateCellColor(JPanel cell, int i, int j) {
    if (controller != null) {
      cell.setBackground(controller.getCellState(i, j) ? Color.black : Color.white);
    }
  }

  private void initializeControls() {
    JPanel controlPanel = new JPanel();
    JButton startButton = new JButton("Start");
    JButton stopButton = new JButton("Stop");

    startButton.addActionListener(e -> {
      if (controller != null) controller.startGame();
      startButton.setEnabled(false);
      stopButton.setEnabled(true);
    });
    stopButton.addActionListener(e -> {
      if (controller != null) controller.stopGame();
      startButton.setEnabled(true);
      stopButton.setEnabled(false);
    });

    controlPanel.add(startButton);
    controlPanel.add(stopButton);
    add(controlPanel, BorderLayout.SOUTH);
  }

  public void updateView(GameBoard model) {
    Component[] components = gridPanel.getComponents();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        JPanel cell = (JPanel) components[i * size + j];
        cell.setBackground(model.getCell(i, j).isAlive() ? Color.black : Color.white);
      }
    }
  }
}
