import java.util.ArrayList;
import java.util.LinkedHashMap;

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

  private void generateKeys() {
    LinkedHashMap<Double, Note> map = parent.musicScale.scale;

    for (Note note : map.values()) {
      if (note.getFreqencey() >= 64 && note.getFreqencey() <= 1100) {
        keys.add(new Key(calcPosition(note), (note.IsHalftone() ? 24 : 35), note.getKey(), note.IsHalftone()));
      }

    }
  }

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
