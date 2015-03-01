import processing.core.PApplet;
import processing.core.PConstants;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;

public class Metronome implements OnBeat {

  private final static int black = 0;
  private final static int white = 255;

  private Theremin parent;
  private int posX, posY;
  private int scale;

  private int beat;
  private float fill = 0;

  private boolean toggleColor = true;

  private boolean onOff;
  private boolean mute;

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

    metronome = new ClockGenerator(this, 500);
    player = parent.minim.loadFile("click.wav");
    input = parent.minim.getLineIn();

    metronomThread = new Thread(metronome);
    metronomThread.setName("MetronomThread");
    metronomThread.start();
  }

  public void draw() {
    if (onOff) {

      if (toggleColor)
        parent.g.fill(black);
      else
        parent.g.fill(white);

      parent.g.ellipse(posX, posY, scale, scale);

      if (toggleColor)
        parent.g.fill(white);
      else
        parent.g.fill(black);

      parent.g.arc((float) posX, (float) posY, scale - 1, scale - 1, (float) Math.PI / 2f, fill, PConstants.PIE);

      parent.g.fill(0);
      parent.g.ellipse(posX, posY, scale / 2, scale / 2);

      parent.g.fill(255);
      parent.g.textFont(parent.createFont("Arial", scale / 5));

      int beatsPerMinute = (int) (60000 / metronome.getTimer());

      parent.g.text(beatsPerMinute, posX - scale / 7, posY + scale / 12);

      // empty the bar
      fill -= (2 * Math.PI) / ((60000 / beat) / (1000 / 30));
    }
  }

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

  public void changeBeat(int change) {
    if (beat + change >= 60 && beat + change <= 200) {
      beat += change;
      metronome.changeBeat(60000 / beat);
      System.out.println("Metronom:" + beat);
    }
  }

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

  @Override
  public void beat(float val) {
    fill(val);
    parent.recordNote();
  }

}
