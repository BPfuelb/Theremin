import processing.core.PFont;

public class Recorder {
  Theremin parent;

  public final static int MAX_PLAYER = 5;
  private Record[] playerList;
  private int selected;
  private int x, y;
  private PFont font20;
  public Recorder(Theremin parent, int xPos, int yPos) {
    this.parent = parent;
    this.x = xPos;
    this.y = yPos;
    selected = 4;
    
    font20 = parent.createFont("Arial", 20);

    playerList = new Record[MAX_PLAYER];
    for (int i = 0; i < playerList.length; i++) {
      playerList[i] = new Record(parent.minim, parent.out, "myLoop" + i + ".wav");
    }
  }

  public void beginRecord() {
    if (selected >= 0 && selected <= 5)
      playerList[selected].beginRecord();
  }

  public void endRecord() {
    if (selected >= 0 && selected <= 5)
      playerList[selected].endRecord();
  }

  public boolean startPauseLoop() {
    if (selected >= 0 && selected <= 5) {
      if (playerList[selected].isPlaying())
        playerList[selected].pauseLoop();
      else
        playerList[selected].startLoop();
    }
    return !playerList[selected].isPlaying();
  }

  public void removePlayer() {
    if (selected >= 0 && selected <= 5) {
      playerList[selected].pauseLoop();
      playerList[selected] = new Record(parent.minim, parent.out, "myLoop" + selected + ".wav");
    }
  }

  public void draw() {
    drawTabular();
    drawRecorder();
    drawSelected();
  }

  private void drawTabular() {
    parent.noFill();
    parent.strokeWeight(1);
    parent.translate(x, y);
    for (int i = 0; i < MAX_PLAYER; i++) {
      parent.rect(0, i * 40, 150, 40);
    }
    parent.translate(-x, -y);
  }

  private void drawRecorder() {
    parent.strokeWeight(1);
    parent.translate(x, y);
    for (int i = 0; i < MAX_PLAYER; i++) {
      if (playerList[i].isEmpty())
        drawNOT(30, 20 + i * 40, 20);
      else {
        if (playerList[i].isPlaying()) {
          parent.fill(i * 50, 0, 0, 50);
          parent.rect(0, i * 40, 150 * playerList[i].position(), 40);
          drawPlay(20, 10 + i * 40, 20);
        } else
          drawPause(20, 12 + i * 40, 20);
      }
      parent.fill(0);
      parent.textFont(font20);
      parent.text("" + i, 4, 25 + 40 * i);
    }
    parent.translate(-x, -y);
  }

  private void drawNOT(int x, int y, int scale) {
    parent.translate(x, y);
    parent.noStroke();
    parent.fill(255, 0, 0);
    parent.ellipse(0, 0, scale, scale);
    parent.fill(255);
    parent.ellipse(0, 0, scale * 0.7f, scale * 0.7f);
    parent.fill(255);
    parent.stroke(0);
    parent.line(-5, -5, 5, 5);
    parent.translate(-x, -y);
    parent.fill(255);

  }

  private void drawPause(int x, int y, int scale) {
    parent.fill(0);
    parent.translate(x, y);
    parent.rect(0, 0, 5, 15);
    parent.rect(10, 0, 5, 15);
    parent.translate(-x, -y);
    parent.noFill();
  }

  private void drawPlay(int x, int y, int scale) {
    parent.translate(x, y);
    parent.fill(0);
    parent.triangle(0, 0, 10, 10, 0, 20);
    parent.translate(-x, -y);
    parent.fill(255);
  }

  private void drawSelected() {
    parent.translate(x, y);
    parent.noFill();
    parent.stroke(255, 255, 0,150);
    parent.strokeWeight(2.4f);
    parent.rect(0, 40 * selected, 150, 40);
    parent.translate(-x, -y);
    parent.stroke(0);
    parent.strokeWeight(1);
  }

  public void setSelected(int selected) {
    if (selected >= 0 && selected <= 5)
      this.selected = selected;
  }
}
