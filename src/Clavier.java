import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * display the clavier for an other visualization
 * 
 * @author Benedikt
 * @version 1.0
 */
public class Clavier {

  private Theremin parent;
  private int x;
  private int y;

  ArrayList<Key> keys = new ArrayList<>();

  public Clavier(Theremin parent, int posX, int posY) {
    this.parent = parent;
    this.x = posX;
    this.y = posY;

    generateKeys();
  }

  /**
   * draw the key yellow
   * 
   * @param keyNumber
   *          which will be draw yellow
   */
  public void draw(int keyNumber) {
    parent.translate(x, y);

    for (Key key : keys) {
      if (key.key == keyNumber) {
        parent.fill(250, 250, 0);

        if (key.isHalfTone) {
          parent.rect(key.x, 0, 6, key.height);
        } else {
          parent.rect(key.x, 0, 12, key.height);
        }
      }
    }
    parent.noFill();
    parent.translate(-x, -y);
  }

  /**
   * draw all keys of the keyboard
   */
  public void drawBoard() {
    parent.translate(x, y);
    parent.strokeWeight(1);

    for (Key key : keys) {
      if (key.isHalfTone) {
        parent.fill(0);
        parent.rect(key.x, 0, 6, key.height);
      } else {
        parent.noFill();
        if (key.key == 49)
          parent.fill(0, 255, 0);
        parent.rect(key.x, 0, 12, key.height);
      }
      parent.noFill();
    }
    parent.translate(-x, -y);
  }

  /**
   * generate all Keys of the board
   */
  private void generateKeys() {
    LinkedHashMap<Double, Note> map = parent.musicScale.scale;

    for (Note note : map.values()) {
      if (note.getFreqencey() >= 64 && note.getFreqencey() <= 1100) {
        keys.add(new Key(calcPosition(note), (note.IsHalftone() ? 24 : 35), note.getKey(), note.IsHalftone()));
      }

    }
  }

  /**
   * calculate the position of a note at the keyboard
   * 
   * @param note
   *          which will calculated
   * @return xPosition of the note
   */
  private int calcPosition(Note note) {
    int key = note.getKey();
    int pos = 0;

    if (!note.IsHalftone()) {
      key = parent.musicScale.scaleWithoutHalfStep.indexOf(note.getFreqencey()) + 1;
      pos = key * 12;
    } else { // filter HalfStop notes
      Double freq2 = (double) (440d * (Math.pow(2d, (note.getKey() - 48d) / 12d)));
      key = parent.musicScale.scaleWithoutHalfStep.indexOf(freq2) + 1;
      pos = (key * 12) - 3;
    }
    return pos;
  }

}
