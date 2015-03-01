import processing.core.PApplet;
import processing.core.PImage;

public class NoteDrawer {

  public final static int BLACK = 0;
  public final static int WHITE = 255;

  private PImage cross, flag, doubleFlag;
  private PApplet parent;

  public NoteDrawer(PApplet parent) {
    this.parent = parent;
    cross = parent.loadImage("kreuz.png");
    flag = parent.loadImage("flag.png");
    doubleFlag = parent.loadImage("doubleFlag.png");
  }

  public void draw(Note note) {
    draw(note, BLACK);
  }

  public void draw(Note note, int transparency) {

    if (transparency >= 0 && transparency <= 255)
      parent.fill(transparency);

    if (note == null) {
      return;
    }
    
    cross(note);
    extendLine(note);
    noteHead(note);
    noteSteam(note);
    notePeriod(note);
  }

  private void cross(Note note) {
    if (note != null) {
      if (note.IsHalftone())
        parent.image(cross, note.getPosX() - 15, note.getPosY() - 8);
    }
  }

  private void noteHead(Note note) {

    switch (note.getPeriod()) {
    case wholeNote:
      parent.translate(note.getPosX(), note.getPosY());
      parent.pushMatrix();
      parent.rotate(-0.1f);
      parent.fill(0);
      parent.ellipse(0, 0, 13, 10);
      parent.rotate(1.1f);
      parent.fill(255);
      parent.ellipse(0, 0, 8, 6);
      parent.popMatrix();
      parent.translate(-note.getPosX(), -note.getPosY());
      break;
    case halfNote:
      parent.translate(note.getPosX(), note.getPosY());
      parent.pushMatrix();
      parent.rotate(-0.4f);
      parent.fill(0);
      parent.ellipse(0, 0, 13, 8);
      parent.fill(255);
      parent.ellipse(0, 0, 12, 5);
      parent.popMatrix();
      parent.translate(-note.getPosX(), -note.getPosY());
      break;
    case quarterNote:
    case eighthNote:
    case sixteenthNote:
      parent.translate(note.getPosX(), note.getPosY());
      parent.pushMatrix();
      parent.rotate(-0.4f);
      parent.ellipse(0, 0, 13, 8);
      parent.popMatrix();
      parent.translate(-note.getPosX(), -note.getPosY());
      break;
    default:
      break;
    }

  }

  private void noteSteam(Note note) {

    int x = note.getPosX();
    int y = note.getPosY();
    parent.strokeWeight(2f);

    switch (note.getSteam()) {
    case upSteam:
      parent.line(x + 5, y - 2, x + 5, y - 30);
      break;
    case downSteam:
      parent.line(x - 6, y + 2, x - 6, y + 30);
      break;
    case noSteam:
      break;
    default:
      break;
    }

  }

  private void notePeriod(Note note) {

    int x = note.getPosX();
    int y = note.getPosY();
    parent.noFill();

    if (note.getSteam() == Steam.upSteam) {
      switch (note.getPeriod()) {
      case wholeNote:
      case halfNote:
      case quarterNote:
        // nothing to do here
        break;
      case eighthNote:
        parent.image(flag, x + 5, y - 31);
        break;
      case sixteenthNote:
        parent.image(doubleFlag, x + 5, y - 31);
        break;
      default:
        break;
      }
    } else if (note.getSteam() == Steam.downSteam) {
      switch (note.getPeriod()) {
      case wholeNote:
      case halfNote:
      case quarterNote:
        // nothing to do here
        break;
      case eighthNote:
        parent.translate(x - 5, y + 31);
        parent.pushMatrix();
        parent.rotate((float) Math.PI);
        parent.image(flag, 0, 0);
        // parent.ellipse(0, 0, 13, 8);
        parent.popMatrix();
        parent.translate(-x + 5, -y - 31);

        break;
      case sixteenthNote:
        parent.translate(x - 5, y + 31);
        parent.pushMatrix();
        parent.rotate((float) Math.PI);
        parent.image(doubleFlag, 0, 0);
        parent.popMatrix();
        parent.translate(-x + 5, -y - 31);

        break;
      default:
        break;
      }
    }

  }

  private void extendLine(Note note) {
    if (note.getKey() >= 40) {
      switch (note.getLine()) {
      case twoLineAbove:
        parent.line(note.getPosX() - 10, Background.VIOLINPOS, note.getPosX() + 9, Background.VIOLINPOS);
      case oneLineAbove:
        parent.line(note.getPosX() - 10, 10 + Background.VIOLINPOS, note.getPosX() + 9, 10 + Background.VIOLINPOS);
        break;
      case twoLineBelow:
        parent.line(note.getPosX() - 10, 10 + Background.VIOLINPOS + 20 + 50, note.getPosX() + 9, 10 + Background.VIOLINPOS + 20 + 50);
      case oneLineBelow:
        parent.line(note.getPosX() - 10, Background.VIOLINPOS + 20 + 50, note.getPosX() + 9, Background.VIOLINPOS + 20 + 50);
        break;
      case noLine:
        // nothing to do here
      default:
        break;
      }
    } else {
      switch (note.getLine()) {
      case twoLineAbove:
        parent.line(note.getPosX() - 10, Background.BASSPOS, note.getPosX() + 9, Background.BASSPOS);
      case oneLineAbove:
        parent.line(note.getPosX() - 10, 10 + Background.BASSPOS, note.getPosX() + 9, 10 + Background.BASSPOS);
        break;
      case twoLineBelow:
        parent.line(note.getPosX() - 10, 10 + Background.BASSPOS + 20 + 50, note.getPosX() + 9, 10 + Background.BASSPOS + 20 + 50);
      case oneLineBelow:
        parent.line(note.getPosX() - 10, Background.BASSPOS + 20 + 50, note.getPosX() + 9, Background.BASSPOS + 20 + 50);
        break;
      case noLine:
        // nothing to do here
      default:
        break;
      }
    }

  }

}
