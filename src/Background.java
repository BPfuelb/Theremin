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
	}

}
