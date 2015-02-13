import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MusicalScale {

  private String[] identifier;
  private LinkedHashMap<Double, String> scale;
  public ArrayList<Double> scaleWithoutHalfStep;

  public MusicalScale() {
    scale = new LinkedHashMap<Double, String>();
    scaleWithoutHalfStep = new ArrayList<>();

    identifier = new String[] { ",,A", ",,Ais/,,B", ",,H", ",C", ",Cis/,Des", ",D", ",Dis/,Es", ",E", ",F", ",Fis/,Ges", ",G", ",Gis/,As", ",A", ",Ais/,B", ",H", "C", "Cis/Des", "D", "Dis/Es", "E",
        "F", "Fis/Ges", "G", "Gis/As", "A", "Ais/B", "H", "c", "cis/des", "d", "dis/es", "e", "f", "fis/ges", "g", "gis/as", "a", "ais/b", "h", "c'", "cis'/des'", "d'", "dis'/es'", "e'", "f'",
        "fis'/ges'", "g'", "gis'/as'", "a'", "ais'/b'", "h'", "c''", "cis''/des''", "d''", "dis''/es''", "e''", "f''", "fis''/ges''", "g''", "gis''/as''", "a''", "ais''/b''", "h''", "c'''",
        "cis'''/des'''", "d'''", "dis'''/es'''", "e'''", "f'''", "fis'''/ges'''", "g'''", "gis'''/as'''", "a'''", "ais'''/b'''", "h'''", "c''''", "cis''''/des''''", "d''''", "dis''''/es''''",
        "e''''", "f''''", "fis''''/ges''''", "g''''", "gis''''/as''''", "a''''", "ais''''/b''''", "h''''", "c'''''" };

    generateNotes();
  }

  private void generateNotes() {

    for (int i = 0; i < 88; i++) {
      // double frequency = (double) (440d * Math.pow(Math.pow(2d, 1d / 12d), i - 48));
      double frequency = (double) (440d * (Math.pow(2d, (i - 48d) / 12d)));

      scale.put(frequency, identifier[i]);

      if (!isHalfStep(getKey(frequency))) {
        scaleWithoutHalfStep.add(frequency);
      }
    }
  }

  public LinkedHashMap<Double, String> getScale() {
    return scale;
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
    // System.out.println("inputFreq " + freq + " key " + key);
    return key;
  }

  public boolean isHalfStep(int key) {
    
    // System.out.print("inputKey " + key);
    
    key = ((key - 1) % 12);
    
    // System.out.print(" case " + key);
    
    switch (key) {
    case 1:
    case 4:
    case 6:
    case 9:
    case 11:
      // System.out.println(" -> Halbton (schwarz)");
      return true;
    default:
      //System.out.println(" -> Vollton (weiﬂ)");
      return false;
    }
  }
}
