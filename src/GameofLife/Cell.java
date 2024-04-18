package GameofLife;
public class Cell {
  private boolean isAlive;

  public Cell(boolean isAlive) {
    this.isAlive = isAlive;
  }

  public boolean isAlive() {
    return isAlive;
  }

  public void setAlive(boolean alive) {
    isAlive = alive;
  }

  public void toggleState() {
    isAlive = !isAlive;
  }
}
