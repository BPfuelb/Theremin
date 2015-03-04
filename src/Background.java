import processing.core.PFont;
import processing.core.PImage;

/**
 * generate the background (only static draws)
 * 
 * @author Benedikt
 * @version 1.0
 */
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

  public void draw() {
    drawViolin();
    drawBass();

    parent.line(5, VIOLINPOS + 20, 5, BASSPOS + 60);
    parent.line(695, VIOLINPOS + 20, 695, BASSPOS + 60);

    parent.clavier.drawBoard();

    drawMouseHand();
    drawQuant();
    drawNoHalf();
  }

  /**
   * draw the violin and lines
   */
  private void drawViolin() {
    parent.strokeWeight(1);

    parent.image(violin, 10, VIOLINPOS + 8);

    parent.strokeWeight(1);
    for (int i = 0; i < 5; i++) {
      parent.line(5, i * 10 + VIOLINPOS + 20, 695, i * 10 + VIOLINPOS + 20);
    }

  }

  /**
   * draw the bass and lines
   */
  private void drawBass() {
    parent.strokeWeight(1);

    parent.image(bass, 0, BASSPOS + 19);

    parent.strokeWeight(1);
    for (int i = 0; i < 5; i++) {
      parent.line(5, i * 10 + BASSPOS + 20, 695, i * 10 + BASSPOS + 20);
    }

  }

  /**
   * draw the mouse/hand image (what is active)
   */
  private void drawMouseHand() {
    parent.textFont(font20);
    parent.text("I", 640, parent.height - 10);

    parent.image(hand, 660, parent.height - 40);
    parent.image(mouse, 600, parent.height - 30);

    if (parent.mouseHands)
      parent.line(660, parent.height - 35, 695, parent.height - 5);
    else
      parent.line(600, parent.height - 35, 635, parent.height - 5);
  }

  /**
   * show the user if the frequence get quantised
   */
  private void drawQuant() {
    parent.textFont(font20);
    parent.text("Q", 630, 20);

    parent.textFont(font10);
    parent.text("(uantised)", 645, 20);

    if (!parent.quantized) {
      parent.line(630, 5, 645, 20);
    }
  }

  /**
   * show the user if only full note will be played/displayed
   */
  private void drawNoHalf() {
    parent.textFont(font20);
    parent.text("H", 630, 50);

    parent.textFont(font10);
    parent.text("(alfstep)", 645, 50);

    if (!parent.onlyHalfTone) {
      parent.line(630, 35, 645, 50);
    }
  }

}
