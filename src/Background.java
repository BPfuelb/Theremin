import processing.core.PImage;

public class Background {

  private Theremin parent;
  private PImage violin, bass;

  public static final int VIOLINPOS = 100;
  public static final int BASSPOS = 200;

  public Background(Theremin parent) {
    this.parent = parent;

    violin = parent.loadImage("violin.png");
    bass = parent.loadImage("bass.png");

  }

  private void drawViolin() {
    parent.strokeWeight(1);

    parent.image(violin, 10, VIOLINPOS + 8);

    // Oben
    // parent.strokeWeight(0.1f);
    // for (int i = 0; i < 2; i++) {
    // parent.line(0, i * 10 + violinPos, parent.width, i * 10 + violinPos);
    // }

    // Mitte
    parent.strokeWeight(1);
    for (int i = 0; i < 5; i++) {
      parent.line(0, i * 10 + VIOLINPOS + 20, parent.width, i * 10 + VIOLINPOS + 20);
    }

    // Unten
    // parent.strokeWeight(0.1f);
    // for (int i = 0; i < 2; i++) {
    // parent.line(0, i * 10 + violinPos + 20 + 50, parent.width, i * 10 + violinPos + 20 + 50);
    // }
  }

  private void drawBass() {
    parent.strokeWeight(1);

    parent.image(bass, 0, BASSPOS + 19);

    // Oben
    // parent.strokeWeight(0.1f);
    // for (int i = 0; i < 2; i++) {
    // parent.line(0, i * 10 + bassPos, parent.width, i * 10 + bassPos);
    // }

    // Mitte
    parent.strokeWeight(1);
    for (int i = 0; i < 5; i++) {
      parent.line(0, i * 10 + BASSPOS + 20, parent.width, i * 10 + BASSPOS + 20);
    }

    // Unten
    // parent.strokeWeight(0.1f);
    // for (int i = 0; i < 2; i++) {
    // parent.line(0, i * 10 + bassPos + 20 + 50, parent.width, i * 10 + bassPos + 20 + 50);
    // }
  }

  public void draw() {
    drawViolin();
    drawBass();
  }

}
