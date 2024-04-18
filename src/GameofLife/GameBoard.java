package GameofLife;

public class GameBoard {
  private Cell[][] cells;
  private int size; // This should already be part of your class

  public GameBoard(int size) {
    this.size = size;
    cells = new Cell[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        cells[i][j] = new Cell(false);  // Initially, all cells are dead.
      }
    }
  }

  public Cell getCell(int x, int y) {
    return cells[x][y];
  }

  public void initialize(boolean[][] seed) {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        cells[i][j].setAlive(seed[i][j]);
      }
    }
  }

  public void nextGeneration() {
    Cell[][] nextCells = new Cell[size][size];

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        int liveNeighbors = countLiveNeighbors(i, j);
        nextCells[i][j] = new Cell(cells[i][j].isAlive());

        if (cells[i][j].isAlive()) {
          if (liveNeighbors < 2 || liveNeighbors > 3) {
            nextCells[i][j].setAlive(false);
          }
        } else {
          if (liveNeighbors == 3) {
            nextCells[i][j].setAlive(true);
          }
        }
      }
    }

    // Copy nextCells to cells
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        cells[i][j].setAlive(nextCells[i][j].isAlive());
      }
    }
  }

  public int getSize() {
    return size; // Getter method for the size of the board
  }

  private int countLiveNeighbors(int x, int y) {
    int count = 0;
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        if (i == 0 && j == 0) continue; // skip the cell itself
        int dx = x + i;
        int dy = y + j;
        if (dx >= 0 && dx < size && dy >= 0 && dy < size && cells[dx][dy].isAlive()) {
          count++;
        }
      }
    }
    return count;
  }
}

