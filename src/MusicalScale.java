import java.util.ArrayList;
import java.util.LinkedHashMap;

import processing.core.PApplet;

public class MusicalScale {

	private String[] identifier;

	public LinkedHashMap<Double, Note> scale;
	public ArrayList<Double> scaleWithoutHalfStep;

	public final static double halfStep = (double) (440d * (Math.pow(2d, (-48d) / 12d)));

	public MusicalScale(Theremin parent) {

		scale = new LinkedHashMap<Double, Note>();
		scaleWithoutHalfStep = new ArrayList<>();

		identifier = new String[] { ",,A", ",,Ais/,,B", ",,H", ",C", ",Cis/,Des", ",D", ",Dis/,Es", ",E", ",F", ",Fis/,Ges", ",G", ",Gis/,As", ",A", ",Ais/,B", ",H", "C", "Cis/Des", "D", "Dis/Es",
				"E", "F", "Fis/Ges", "G", "Gis/As", "A", "Ais/B", "H", "c", "cis/des", "d", "dis/es", "e", "f", "fis/ges", "g", "gis/as", "a", "ais/b", "h", "c'", "cis'/des'", "d'", "dis'/es'", "e'",
				"f'", "fis'/ges'", "g'", "gis'/as'", "a'", "ais'/b'", "h'", "c''", "cis''/des''", "d''", "dis''/es''", "e''", "f''", "fis''/ges''", "g''", "gis''/as''", "a''", "ais''/b''", "h''",
				"c'''", "cis'''/des'''", "d'''", "dis'''/es'''", "e'''", "f'''", "fis'''/ges'''", "g'''", "gis'''/as'''", "a'''", "ais'''/b'''", "h'''", "c''''", "cis''''/des''''", "d''''",
				"dis''''/es''''", "e''''", "f''''", "fis''''/ges''''", "g''''", "gis''''/as''''", "a''''", "ais''''/b''''", "h''''", "c'''''" };

		generateNotes(parent);
	}

	private void generateNotes(Theremin parent) {

		for (int i = 0; i < 88; i++) {
			double frequency = (double) (440d * (Math.pow(2d, (i - 48d) / 12d)));
			Note newNote = new Note(frequency, identifier[i]);

			scale.put(frequency, newNote);

			// System.out.println("F: " + frequency + " |Key: " +
			// newNote.getKey());

			if (!newNote.IsHalftone())
				scaleWithoutHalfStep.add(newNote.getFreqencey());
		}

		int k = 0;
		for (Double value : scaleWithoutHalfStep) {
			System.out.println(++k + " Freq: " + value);
		}

		for (Note note : scale.values()) {
			calcPosition(note, parent);
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

	public static int getKeyToFreqency(Double frequency) {
		double key = ((Math.log(frequency / 440d) / Math.log(2)) * 12d + 49d);
		int returnKey = new Double(key).intValue();

		// Workaround: because double key 28
		if (returnKey == 28)
			returnKey = (int) (key + 0.1d);

		return returnKey;
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

	public void calcPosition(Note note, PApplet parent) {

		int key = note.getKey();
		int pos = 0;

		if (!note.IsHalftone()) {
			key = scaleWithoutHalfStep.indexOf(note.getFreqencey()) + 1;
			pos = key * 5;
		} else { // filter HalfStop notes
			Double freq2 = note.getFreqencey() / (Math.pow(2d, 1d / 12d));
			key = scaleWithoutHalfStep.indexOf(freq2) + 1;
			pos = key * 5;
		}

		if (key >= 24) {
			float posY = parent.g.height - (pos + (parent.g.height - (Background.VIOLINPOS + 190)));
			note.setPosY((int) posY);
		} else {
			float posY = parent.g.height - (pos + (parent.g.height - (Background.BASSPOS + 130)));
			note.setPosY((int) posY);
		}
	}

}
