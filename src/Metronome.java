import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;

/**
 * Generate a cyclic click sound and visualize with a filled circle. Use a sperated thread for the beat signal
 * 
 * @author Benedikt
 * @version 1.0
 */
public class Metronome implements OnBeat {

  private final static int BLACK = 0;
  private final static int WHITE = 255;

  private Theremin parent;
  private int posX, posY;
  private int scale;

  private int beat;
  private float fill;
  private int beatCounter;

  private boolean toggleColor = true;

  public boolean onOff;
  private boolean mute;
  private PFont font40, font20, font10;

  private ClockGenerator metronome;
  AudioPlayer player;
  AudioInput input;

  private Thread metronomThread;

  public Metronome(Theremin parent, int posX, int posY, int scale) {
    this.parent = parent;
    this.posX = posX;
    this.posY = posY;
    this.scale = scale;

    onOff = false;
    mute = false;
    beat = 120;
    fill = 0;
    beatCounter = 0;

    metronome = new ClockGenerator(this, 500);
    player = parent.minim.loadFile("click.wav");
    input = parent.minim.getLineIn();

    metronomThread = new Thread(metronome);
    metronomThread.setName("MetronomThread");
    metronomThread.start();

    font40 = parent.createFont("Arial", 40);
    font20 = parent.createFont("Arial", 20);
    font10 = parent.createFont("Arial", 10);
  }

  /**
   * draw the circle dependent on the beat
   */
  public void draw() {
    if (onOff) {
      if (toggleColor)
        parent.g.fill(BLACK);
      else
        parent.g.fill(WHITE);

      parent.g.ellipse(posX, posY, scale, scale);

      if (toggleColor)
        parent.g.fill(WHITE);
      else
        parent.g.fill(BLACK);

      /* radial fill of circle */
      parent.g.arc((float) posX, (float) posY, scale - 1, scale - 1, (float) Math.PI / 2f, fill, PConstants.PIE);

      parent.g.fill(BLACK);
      parent.g.ellipse(posX, posY, scale / 2, scale / 2);

      parent.g.fill(WHITE);
      parent.g.textFont(parent.createFont("Arial", scale / 5));

      int beatsPerMinute = (int) (60000 / metronome.getTimer());

      /* display the beats per minute */
      parent.g.text(beatsPerMinute, posX - scale / 7, posY + scale / 12);

      /* emptying per step of the circle */
      fill -= (2 * Math.PI) / ((60000 / beat) / (1000 / 30));

      parent.g.fill(BLACK);
      parent.text("+", 96, 50);
      parent.text("-", 98, 70);

    } else {
      parent.g.fill(BLACK);
      parent.textFont(font20);
      parent.text("M", 10, 20);
      parent.textFont(font10);
      parent.text("(etronom)", 26, 20);
    }
  }

  /**
   * refill the circle on beat
   * 
   * @param value
   *          of filling [0,1] (float)
   */
  public void fill(float value) {
    toggleColor = !toggleColor;
    if (onOff) {
      if (!mute)
        player.play();
      value = PApplet.map(value, 0f, 1f, 0f, (float) Math.PI * 2.5f);
      // System.out.println("Wert: " + value);
      this.fill = value;
      player.rewind();
    }
  }

  /**
   * change the beat, change the timing of the thread which is used for the beat generation
   * 
   * @param change
   *          value (bpm)
   */
  public void changeBeat(int change) {
    if (beat + change >= 60 && beat + change <= 200) {
      beat += change;
      metronome.changeBeat(60000 / beat);
      parent.textFont(font40);

      if (change < 0)
        parent.text("-", 95, 77);
      else
        parent.text("+", 90, 59);
    }
  }

  /**
   * allows to turn the metronom on, mute and off
   */
  public void onOffMute() {
    if (onOff) { // if on
      if (!mute) { // and note mute
        mute = true;
      } else { // if on and mute
        metronome.pause();
        onOff = false;
      }
    } else { // if off
      metronome.running();
      onOff = true;
      mute = false;
    }

  }

  /**
   * method will called by beat generator thread to refill the circle and record a note
   */
  @Override
  public void beat(float val) {
    fill(val);
//    parent.recordNote();

    beatCounter = (beatCounter + 1) % 4;
    if (beatCounter == 0) {
      parent.addBar();
    }
  }

}
