import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MusicalScale {

  public static MusicalScale singleton;

  private String[] identifier;

  public LinkedHashMap<Double, Note> scale;

  public ArrayList<Note> scale2;
  public ArrayList<Double> scaleWithoutHalfStep;

  public final static double halfStep = (double) (440d * (Math.pow(2d, (-48d) / 12d)));

  public MusicalScale(Theremin parent) {
    scale2 = new ArrayList<>();
    scale = new LinkedHashMap<Double, Note>();

    scaleWithoutHalfStep = new ArrayList<>();

    identifier = new String[] { ",,A", ",,Ais/,,B", ",,H", ",C", ",Cis/,Des", ",D", ",Dis/,Es", ",E", ",F", ",Fis/,Ges", ",G", ",Gis/,As", ",A", ",Ais/,B", ",H", "C", "Cis/Des", "D", "Dis/Es", "E",
        "F", "Fis/Ges", "G", "Gis/As", "A", "Ais/B", "H", "c", "cis/des", "d", "dis/es", "e", "f", "fis/ges", "g", "gis/as", "a", "ais/b", "h", "c'", "cis'/des'", "d'", "dis'/es'", "e'", "f'",
        "fis'/ges'", "g'", "gis'/as'", "a'", "ais'/b'", "h'", "c''", "cis''/des''", "d''", "dis''/es''", "e''", "f''", "fis''/ges''", "g''", "gis''/as''", "a''", "ais''/b''", "h''", "c'''",
        "cis'''/des'''", "d'''", "dis'''/es'''", "e'''", "f'''", "fis'''/ges'''", "g'''", "gis'''/as'''", "a'''", "ais'''/b'''", "h'''", "c''''", "cis''''/des''''", "d''''", "dis''''/es''''",
        "e''''", "f''''", "fis''''/ges''''", "g''''", "gis''''/as''''", "a''''", "ais''''/b''''", "h''''", "c'''''" };

    generateNotes(parent);
  }

  private void generateNotes(Theremin parent) {

    for (int i = 0; i < 88; i++) {
      double frequency = (double) (440d * (Math.pow(2d, (i - 48d) / 12d)));

      scale.put(frequency, new Note(frequency, identifier[i]));

      if (!isNoteHalfStep(getKey(frequency))) {
        scaleWithoutHalfStep.add(frequency);
      }
    }
  }

  public double quantify(double currentFreqency) {
    for (Double frequency : scale.keySet()) {
      if (frequency >= currentFreqency) {
        return frequency;
      }
    }
    return 0;
  }

  public int getKey(Double freq) {
    int key = (int) ((Math.log(freq / 440d) / Math.log(2)) * 12d + 49d);
    return key;
  }

  public static int getKeyToFreqency(Double frequency) {
    int key = (int) ((Math.log(frequency / 440d) / Math.log(2)) * 12d + 49d);
    return key;
  }

  public static boolean isNoteHalfStep(int key) {
    key = ((key - 1) % 12);

    switch (key) {
    case 1:
    case 4:
    case 6:
    case 9:
    case 11:
      return true;
    default:
      return false;
    }
  }

  public Note NoteByDistance(Note note, int distance) {
    int key = note.getKey();
    if (key + distance < scale.values().size()) {
      Object[] returnFreq = scale.values().toArray();

      return (Note) returnFreq[key + distance];
    }
    return null;
  }

}
