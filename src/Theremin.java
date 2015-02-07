import processing.core.PApplet;
import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.ugens.Oscil;
import ddf.minim.ugens.Waves;

public class Theremin extends PApplet {

	private static final long serialVersionUID = -279550209204634548L;

	Minim minim;
	AudioOutput out;
	Oscil wave;
	Sensor sensor;

	int waveForm;

	public void setup() {
		size(512, 200, P3D);

		waveForm = 0;

		minim = new Minim(this);
		out = minim.getLineOut();
		wave = new Oscil(440, 0.5f, Waves.SINE);
		wave.patch(out);

		sensor = new Sensor(this);
	}

	public void draw() {

		sensor.getValues();
		// sensor.getGestures();

		background(255);
		stroke(0);
		strokeWeight(1);

		// draw the waveform of the output
		for (int i = 0; i < out.bufferSize() - 1; i++) {
			line(i, 50 - out.left.get(i) * 50, i + 1,
					50 - out.left.get(i + 1) * 50);
			line(i, 150 - out.right.get(i) * 50, i + 1,
					150 - out.right.get(i + 1) * 50);
		}

		// draw the waveform we are using in the oscillator
		stroke(0);
		strokeWeight(4);

		for (int i = 0; i < width - 1; ++i) {
			point(i,
					height / 2 - (height * 0.49f)
							* wave.getWaveform().value((float) i / width));
		}
	}

	public void handMoved(float left, float right) {
		// System.out.println("handMoved (" + left + "," + right + ")");

		// aplitude
		float amp = map(left, 100, 300, 0, 1);
		if (amp < 0)
			amp = 0;
		// System.out.println("amp: " + amp);
		wave.setAmplitude(amp);

		// frequency
		float freq = map(right, 0, 350, 0, 2000);
		// System.out.println("freq" + freq);
		wave.setFrequency(freq);
	}

	public void keyPressed() {
		chooseWave(key);
	}

	public void chooseWave(int count) {
		System.out.println("choose Wave: " + count);
		switch (count) {
		case '0':
			wave.setWaveform(Waves.SINE);
			break;
		case '1':
			wave.setWaveform(Waves.TRIANGLE);
			break;

		case '2':
			wave.setWaveform(Waves.SAW);
			break;

		case '3':
			wave.setWaveform(Waves.SQUARE);
			break;

		case '4':
			wave.setWaveform(Waves.QUARTERPULSE);
			break;

		default:
			break;
		}
	}

	public void nextWave() {

		waveForm = (waveForm + 6) % 5;
		chooseWave(48 + waveForm);
		System.out.println("Next wave: " + waveForm);

	}

	static public void main(String[] passedArgs) {
		String[] appletArgs = new String[] { "Theremin" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}

}
