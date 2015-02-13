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

  private Minim minim;
  private AudioOutput out;
  private Oscil wave;
  private Sensor sensor;

  private PImage violin, bass, kreuz;
  private PFont font = createFont("Arial", 32);

  private MusicalScale musicScale;
  private int waveForm = 0;

  public void setup() {
    size(700, 500);

    minim = new Minim(this);
    out = minim.getLineOut();
    wave = new Oscil(440, 0.5f, Waves.SINE);
    wave.patch(out);
    musicScale = new MusicalScale();
    sensor = new Sensor(this);

    violin = loadImage("violin.png");
    bass = loadImage("bass.png");
    kreuz = loadImage("kreuz.png");

  }

  public void draw() {
    background(255);

    // sensor.getGestures();

    drawViolin();
    drawBass();

    sensor.getValues();
  }

  public void keyPressed() {
    chooseWave(key);
  }

  public void chooseWave(int count) {
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

  public void drawViolin() {
    strokeWeight(1);

    image(violin, 10, violinPos + 8);

    // Oben
    strokeWeight(0.1f);
    for (int i = 0; i < 2; i++) {
      line(0, i * 10 + violinPos, width, i * 10 + violinPos);
    }

    // Mitte
    strokeWeight(1);
    for (int i = 0; i < 5; i++) {
      line(0, i * 10 + violinPos + 20, width, i * 10 + violinPos + 20);
    }

    // Unten
    strokeWeight(0.1f);
    for (int i = 0; i < 2; i++) {
      line(0, i * 10 + violinPos + 20 + 50, width, i * 10 + violinPos + 20 + 50);
    }
  }

  public void drawBass() {
    strokeWeight(1);

    image(bass, 0, bassPos + 19);

    // Oben
    strokeWeight(0.1f);
    for (int i = 0; i < 2; i++) {
      line(0, i * 10 + bassPos, width, i * 10 + bassPos);
    }

    // Mitte
    strokeWeight(1);
    for (int i = 0; i < 5; i++) {
      line(0, i * 10 + bassPos + 20, width, i * 10 + bassPos + 20);
    }

    // Unten
    strokeWeight(0.1f);
    for (int i = 0; i < 2; i++) {
      line(0, i * 10 + bassPos + 20 + 50, width, i * 10 + bassPos + 20 + 50);
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

  public void handMoved(float left, float right) {

    // aplitude
    float amp = map(left, 100, 300, 0, 1);
    if (amp < 0)
      amp = 0;
    // System.out.println("amp: " + amp);
    wave.setAmplitude(amp);

    // frequency
    double freq = map(right, 100, 300, 62, 2094);
    // freq = 440d;
    freq = musicScale.quantify(freq);
    // System.out.println("freq" + freq);
    wave.setFrequency((float) freq);

    
    

    if (freq > 28)
    {
      textFont(font);
      text(musicScale.getScale().get(freq) , width / 2, height / 2);
      drawNote(freq);
    }
  }

  public void drawNote(Double freq) {
    fill(0);

    int key = musicScale.getKey(freq);

    // System.out.println("Taste "+key);

    int pos = 0;

    if(!musicScale.isHalfStep(key))
    {
      key = musicScale.scaleWithoutHalfStep.indexOf(freq);
      pos = key * 5;
    }
    else
    {
      // System.out.println("Freq davor " + freq);
      Double freq2 = freq /  (Math.pow(2d, 1d / 12d));
      //System.out.println("Freq danach " + freq2);
      key = musicScale.scaleWithoutHalfStep.indexOf(freq2);
      pos = key * 5;
      
      if (key >= 24)
      image(kreuz, width/2 -15, height - (pos + (height - (violinPos + 182))));
      else
        image(kreuz, width/2 -15, height - (pos + (height - (bassPos + 122))));
      // System.out.println("Key " + key);
    }

    if (key >= 24)
      ellipse(width / 2, height - (pos + (height - (violinPos + 190))), 11, 10);
    else
      ellipse(width / 2, height - (pos + (height - (bassPos + 130))), 11, 10);

    // text("Key: " + key + " pos: " + (height - (pos + (height - (bassPos + 130)))), width / 2, height - 50);

  }

}
