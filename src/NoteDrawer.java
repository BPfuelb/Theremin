import processing.core.PApplet;
import processing.core.PImage;

public class NoteDrawer {
  
  private PImage cross;
  private PApplet parent;
  
  public NoteDrawer(PApplet parent)
  {
    this.parent = parent;
    cross = parent.loadImage("kreuz.png");
  }

  public int draw(Note note, int transparency) {

    if (transparency >= 0 && transparency <= 255)
      parent.fill(transparency);

    int key = MusicalScale.singleton.getKey(note.getFreqencey());

    int pos = 0;

    if (!MusicalScale.isNoteHalfStep(key)) {
      key = MusicalScale.singleton.scaleWithoutHalfStep.indexOf(note.getFreqencey());
      pos = key * 5;
    } else { // filter HalfStop notes
      Double freq2 = note.getFreqencey() / (Math.pow(2d, 1d / 12d));
      key = MusicalScale.singleton.scaleWithoutHalfStep.indexOf(freq2);
      pos = key * 5;

      // draw cross
      if (key >= 24) {
        float posYimage = parent.g.height - (pos + (parent.g.height - (Background.VIOLINPOS + 182)));
        parent.image(cross, note.getPosX() - 15, posYimage);
      } else {
        float posYimage = parent.g.height - (pos + (parent.g.height - (Background.BASSPOS + 122)));
        parent.image(cross, note.getPosX() - 15, posYimage);
      }
    }

    // draw note
    if (key >= 24)
      parent.ellipse(note.getPosX(), parent.g.height - (pos + (parent.g.height - (Background.VIOLINPOS + 190))), 11, 10);
    else
      parent.ellipse(note.getPosX(), parent.g.height - (pos + (parent.g.height - (Background.BASSPOS + 130))), 11, 10);

    return key;

  }
}
