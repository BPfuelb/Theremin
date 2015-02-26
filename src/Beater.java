import processing.core.PApplet;
import processing.core.PConstants;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;

public class Beater {

  private Theremin parent;
  private int posX, posY;
  private int scale;

  private int beat = 0;
  private float fill = 0;
  private boolean onOff;
  private boolean mute;

  private Metronome metronome;
  AudioPlayer player;
  AudioInput input;

  public Beater(Theremin parent, int posX, int posY, int scale) {
    this.parent = parent;
    this.posX = posX;
    this.posY = posY;
    this.scale = scale;

    onOff = false;
    mute = false;
    beat = 120;

    metronome = new Metronome((Theremin) parent, 500);
    player = parent.minim.loadFile("click.wav");
    input = parent.minim.getLineIn();
    new Thread(metronome).start();
  }

  public void draw() {
    if (onOff) {

      parent.g.fill(0);
      parent.g.ellipse(posX, posY, scale, scale);

      parent.g.fill(255);
      parent.g.arc((float) posX, (float) posY, scale / 1.05f, scale / 1.05f, (float) Math.PI / 2f, fill, PConstants.PIE);

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
    }
  }

  public void onOffMute() {
    if (onOff) {
      if (!mute) {
        mute = true;
      } else {
        onOff = false;
      }
    } else {
      onOff = true;
      mute = false;
    }

  }

}
