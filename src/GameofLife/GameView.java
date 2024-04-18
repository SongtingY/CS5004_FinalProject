package GameofLife;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameView extends JFrame {
  private GameController controller;
  private JPanel gridPanel;
  private int size;
  private int cellSize = 10; // Pixel size of each cell in the grid

  public GameView(int size) {
    this.size = size;
    setTitle("Conway's Game of Life");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(size * cellSize + 50, size * cellSize + 100);
    initializeGridPanel();
    initializeControls();
    setLocationRelativeTo(null); // Center the window
    setVisible(true);
  }

  public void setController(GameController controller) {
    this.controller = controller;
  }

  private void initializeGridPanel() {
    gridPanel = new JPanel();
    gridPanel.setLayout(new GridLayout(size, size));
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        JPanel cell = new JPanel();
        cell.setPreferredSize(new Dimension(cellSize, cellSize));
        cell.setBorder(BorderFactory.createLineBorder(Color.gray));
        cell.setBackground(Color.white);
        int finalI = i;
        int finalJ = j;
        cell.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            controller.toggleCellState(finalI, finalJ);
            cell.setBackground(controller.getCellState(finalI, finalJ) ? Color.black : Color.white);
          }
        });
        gridPanel.add(cell);
      }
    }
    add(gridPanel, BorderLayout.CENTER);
  }

  private void initializeControls() {
    JPanel controlPanel = new JPanel();
    JButton startButton = new JButton("Start");
    JButton stopButton = new JButton("Stop");

    startButton.addActionListener(e -> controller.startGame());
    stopButton.addActionListener(e -> controller.stopGame());

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
