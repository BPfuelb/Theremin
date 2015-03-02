import processing.core.PFont;
import processing.core.PImage;

public class Background {

  private Theremin parent;
  private PImage violin, bass;
  private PImage mouse, hand;
  private PFont font20, font10;

  public static final int VIOLINPOS = 100;
  public static final int BASSPOS = 200;

  public Background(Theremin parent) {
    this.parent = parent;

    violin = parent.loadImage("violin.png");
    bass = parent.loadImage("bass.png");
    mouse = parent.loadImage("mouse.png");
    hand = parent.loadImage("hand.png");

    font20 = parent.createFont("Arial", 20);
    font10 = parent.createFont("Arial", 10);

  }

  private void drawViolin() {
    parent.strokeWeight(1);

    parent.image(violin, 10, VIOLINPOS + 8);

    parent.strokeWeight(1);
    for (int i = 0; i < 5; i++) {
      parent.line(5, i * 10 + VIOLINPOS + 20, parent.width - 5, i * 10 + VIOLINPOS + 20);
    }

  }

  private void drawBass() {
    parent.strokeWeight(1);

    parent.image(bass, 0, BASSPOS + 19);

    parent.strokeWeight(1);
    for (int i = 0; i < 5; i++) {
      parent.line(5, i * 10 + BASSPOS + 20, parent.width - 5, i * 10 + BASSPOS + 20);
    }

  }

  public void draw() {
    drawViolin();
    drawBass();

    parent.line(5, VIOLINPOS + 20, 5, BASSPOS + 60);
    parent.line(parent.width - 5, VIOLINPOS + 20, parent.width - 5, BASSPOS + 60);

    parent.clavier.drawBoard();
    
    parent.textFont(font20);
    if (!parent.metronom()) {
      parent.text("M", 10, 20);
      parent.textFont(font10);
      parent.text("(etronom)", 26, 20);
    }
    else
    {
      parent.text("+", 96, 50);
      parent.text("-", 98, 70);
    }

    parent.textFont(font20);
    parent.text("P", 640, parent.height - 10);

    parent.image(hand, 660, parent.height - 40);
    parent.image(mouse, 600, parent.height - 30);

    if (parent.mouseHands)
      parent.line(660, parent.height - 35, 695, parent.height - 5);
    else
      parent.line(600, parent.height - 35, 635, parent.height - 5);
  }

  public void toggleMouseHand() {

  }

}
