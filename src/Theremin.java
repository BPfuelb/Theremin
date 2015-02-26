import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.ugens.Oscil;
import ddf.minim.ugens.Waves;

public class Theremin extends PApplet {

  private static final long serialVersionUID = -279550209204634548L;
  private static final int violinPos = 100;
  private static final int bassPos = 300;

  public Minim minim;
  private AudioOutput out;
  private Oscil wave;
  private Oscil accord1;
  private Oscil accord2;
  private Sensor sensor;
  private boolean quantized;

  private Beater beater;

  private Background background;

  private MusicalScale musicScale;
  
  private PImage kreuz;

  private PFont font = createFont("Arial", 32);
  private int waveForm = 0;

  public void setup() {
    
    frameRate(30);
    
    size(700, 500);

    minim = new Minim(this);
    out = minim.getLineOut();

    wave = new Oscil(440, 0.0f, Waves.SINE);
    accord1 = new Oscil(440, 0.0f, Waves.SINE);
    accord2 = new Oscil(440, 0.0f, Waves.SINE);

    wave.patch(out);
    accord1.patch(out);

    musicScale = new MusicalScale();
    sensor = new Sensor(this);

    quantized = true;


    background = new Background(this);
    kreuz = loadImage("kreuz.png");
    beater = new Beater(this, 50, 50, 70);

  }

  public void draw() {
    background(255);
    
    background.draw();

    sensor.getValues();
    // sensor.getGestures();
    beater.draw();

  }

  public void keyPressed() {
    keyPressed(key);
  }

  public void keyPressed(int count) {
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
    case 'q':
      quantized = !quantized;
      break;

    case '+':
      beater.changeBeat(+5);
      break;

    case '-':
      beater.changeBeat(-5);
      break;
      
    case 'm':
      beater.onOffMute();
      break;
      
    default:
      break;
    }
  }

  public void nextWave() {

    waveForm = (waveForm + 6) % 5;
    keyPressed(48 + waveForm);
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

  public void drawFreq() {
    // draw the waveform we are using in the oscillator

    for (int i = 0; i < width - 1; ++i) {
      point(i, height / 2 - (height * 0.49f) * wave.getWaveform().value((float) i / width));
    }
  }

  public void drawType() {
    // draw the waveform of the output
    for (int i = 0; i < out.bufferSize() - 1; i++) {
      line(i, 50 - out.left.get(i) * 50, i + 1, 50 - out.left.get(i + 1) * 50);
      line(i, 150 - out.right.get(i) * 50, i + 1, 150 - out.right.get(i + 1) * 50);
    }
  }

  public void handMoved(float left, float right, float rotate) {

    // aplitude
    float amp = map(left, 100, 500, 0, 1);
    if (amp < 0)
      amp = 0;
    wave.setAmplitude(amp);

    // frequency
    double freq = map(right, 200, 300, 62, 1000); // 2094
    // freq = 440d; // Test
    if (quantized)
      freq = musicScale.quantify(freq);

    wave.setFrequency((float) freq);
    handRotation(rotate, freq);

    if (freq > 50 && freq < 2093) {
      textFont(font);
      double quantisized = musicScale.quantify(freq);
      text(musicScale.getScale().get(quantisized), width / 2, height / 2);
      drawNote(quantisized);
    }
  }

  private void handRotation(float rotation, double freqenz) {

    if (rotation > 0.7) {

      // System.out.print("MOL: ");
      // System.out.println( map(rotation,0.7f,(float)Math.PI,0f,1f));

      float ampAcc = map(rotation, 0.7f, (float) Math.PI, 0f, 1f);
      int ampTrans = (int) map(rotation, 0.7f, (float) Math.PI, 255, 0);

      accord1.setAmplitude(ampAcc);
      accord2.setAmplitude(ampAcc);

      double acc1 = musicScale.getNoteByValue(freqenz, 4);
      double acc2 = musicScale.getNoteByValue(freqenz, 7);

      accord1.setFrequency((float) acc1);
      accord2.setFrequency((float) acc2);

      // drawNote(acc1);
      // drawNote(acc2);

      drawNote(acc1, (int) ampTrans);
      drawNote(acc2, (int) ampTrans);

    } else if (rotation < 0f) {

      // System.out.print("DUR: ");
      // System.out.println( map(rotation,-0.4f,(float)- Math.PI ,0f,1f));

      float ampAcc = map(rotation, 0.0f, (float) -Math.PI + 1.5f, 0f, 1f);
      int ampTrans = (int) map(rotation, 0.0f, (float) -Math.PI + 1.5f, 255, 0);

      accord1.setAmplitude(ampAcc);
      accord2.setAmplitude(ampAcc);

      double acc1 = musicScale.getNoteByValue(freqenz, 3);
      double acc2 = musicScale.getNoteByValue(freqenz, 7);

      accord1.setFrequency((float) acc1);
      accord2.setFrequency((float) (acc2));

      drawNote(acc1, (int) ampTrans);
      drawNote(acc2, (int) ampTrans);

    } else {
      accord1.setAmplitude(0f);
      accord2.setAmplitude(0f);
    }
  }

  public int drawNote(Double freq) {
    return drawNote(freq, 0);
  }

  public int drawNote(Double freq, int transparency) {

    if (transparency >= 0 && transparency <= 255)
      fill(transparency);

    int key = musicScale.getKey(freq);

    // System.out.println("Taste " + key);

    int pos = 0;

    if (!musicScale.isHalfStep(key)) {
      key = musicScale.scaleWithoutHalfStep.indexOf(freq);
      pos = key * 5;
    } else {
      Double freq2 = freq / (Math.pow(2d, 1d / 12d));
      key = musicScale.scaleWithoutHalfStep.indexOf(freq2);
      pos = key * 5;

      if (key >= 24)
        image(kreuz, width / 2 - 15, height - (pos + (height - (violinPos + 182))));
      else
        image(kreuz, width / 2 - 15, height - (pos + (height - (bassPos + 122))));
    }

    if (key >= 24)
      ellipse(width / 2, height - (pos + (height - (violinPos + 190))), 11, 10);
    else
      ellipse(width / 2, height - (pos + (height - (bassPos + 130))), 11, 10);

    return key;

  }

  public void beat(float val) {
    beater.fill(val);
  }

}
