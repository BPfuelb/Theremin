import java.awt.event.KeyEvent;

import processing.core.PApplet;
import processing.core.PFont;
import processing.event.MouseEvent;
import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.ugens.Oscil;
import ddf.minim.ugens.Waves;

/**
 * Theremin include the main, setup and draw function. initialize all needed classes:
 * 
 * @see Background
 * @see Clavier
 * @see Metronom
 * @see MusicScale
 * @see NoteDrawer
 * @see NoteRecorder
 * @see Sensor
 * @see Recorder
 * 
 * @author Benedikt
 * @version 1.0
 */
public class Theremin extends PApplet {

  private static final long serialVersionUID = -279550209204634548L;
  public static final int WIDTH_THEREMIN = 900;
  public static final int HEIGHT_THEREMIN = 300;

  public Minim minim;
  public AudioOutput out;

  private Oscil wave;
  private Oscil accord1;
  private Oscil accord2;

  public boolean quantized;
  public boolean mouseHands;
  public boolean onlyHalfTone;

  private Sensor sensor;
  private Metronome metronom;
  private NoteDrawer noteDrawer;
  private Background background;
  private NoteRecorder noteRecorder;
  private Recorder recorder;
  public MusicalScale musicScale;
  public Clavier clavier;

  private PFont font25 = createFont("Arial", 25);

  private int waveForm = 0;
  private Note currentNote = null;
  private Note lastNote = null;
  private float mouseRotation = 0;

  /**
   * setup function initialize all data fields
   */
  public void setup() {

    frameRate(30);
    size(WIDTH_THEREMIN, HEIGHT_THEREMIN);

    minim = new Minim(this);
    out = minim.getLineOut(Minim.STEREO, 512);

    wave = new Oscil(440, 0.0f, Waves.SINE);
    accord1 = new Oscil(440, 0.0f, Waves.SINE);
    accord2 = new Oscil(440, 0.0f, Waves.SINE);

    wave.patch(out);
    accord1.patch(out);
    accord2.patch(out);

    musicScale = new MusicalScale(this);

    sensor = new Sensor(this);

    quantized = true;
    mouseHands = true;
    onlyHalfTone = false;

    background = new Background(this);
    metronom = new Metronome(this, 50, 50, 70);
    noteDrawer = new NoteDrawer(this);
    noteRecorder = new NoteRecorder(this);
    clavier = new Clavier(this, 55, 0);
    recorder = new Recorder(this, 720, 10);
  }

  public void draw() {
    background(255);

    background.draw();

    /* decides hand and mouse interface */
    if (mouseHands) {
      mouseMoved(mouseX, height - mouseY, 0.6f);
    } else
      sensor.getValues();

    // sensor.getGestures();

    metronom.draw();
    noteRecorder.drawAll();
    noteRecorder.noteListUpdate();
    recorder.draw();

    drawType(25, height - 30);
  }

  /**
   * given keyboard calls will be forwarded to the keyPressed() function, so other sources like gesture can also use it.
   */
  public void keyPressed() {
    keyPressed(key);
  }

  /**
   * do diffrent things depend on count value
   * 
   * <br>
   * '1-5' select especially the recorder<br>
   * 'q' toggle if the frequency should quantified<br>
   * 'h' toggle if only full tone are displayed/played<br>
   * '+' only usable if metronome is active. Increase the beat<br>
   * '-' only usable if metronome is active. Decrease the beat<br>
   * 'm' start/mute/stop the metronome<br>
   * 'w' select the next waveform <br>
   * 'i' toggle between input method (mouse/hands)<br>
   * 'r' start/stop the record on the selected looper<br>
   * 'p' start/stop the loop on the selected looper<br>
   * 'd' delete the loop on the selected looper<br>
   * 
   * @param count
   *          for decision what to do
   */
  public void keyPressed(int count) {
    switch (count) {
    case '1':
      recorder.setSelected(4);
      break;
    case '2':
      recorder.setSelected(3);
      break;
    case '3':
      recorder.setSelected(2);
      break;
    case '4':
      recorder.setSelected(1);
      break;
    case '5':
      recorder.setSelected(0);
      break;

    case 'q':
      quantized = !quantized;
      break;
    case 'h':
      onlyHalfTone = !onlyHalfTone;
      break;
    case '+':
      if (metronom()) {
        fill(0);
        metronom.changeBeat(+5);
        noteRecorder.changeBeat(+5);
      }
      break;

    case '-':
      if (metronom()) {
        fill(0);
        metronom.changeBeat(-5);
        noteRecorder.changeBeat(-5);
      }
      break;

    case 'm':
      metronom.onOffMute();
      break;
    case 'w':
      nextWave();
      break;
    case 'i':
      mouseHands = !mouseHands;
      move(0.0f, 0.0f, 0.0f);
      break;
    case 'r':
      recorder.beginEndRecord();
      break;
    case 's':
      break;
    case 'p':
      recorder.startPauseLoop();
      break;
    case 'd':
      recorder.removePlayer();
      break;
    default:
      break;
    }

    /* F1 for help window */
    if (key == CODED) {
      if (keyCode == KeyEvent.VK_F1)
        new HelpWindow();
    }
  }

  /**
   * choose the next possible waveform for the main wave and the accords<br>
   * 
   * SINE,TRIANGLE, SAW, SQUARE, QUARTERPULSE
   */
  public void nextWave() {
    waveForm = (waveForm + 1) % 5;

    System.out.println(waveForm);
    switch (waveForm) {
    case 0:
      wave.setWaveform(Waves.SINE);
      accord1.setWaveform(Waves.SINE);
      accord2.setWaveform(Waves.SINE);
      break;
    case 1:
      wave.setWaveform(Waves.TRIANGLE);
      accord1.setWaveform(Waves.TRIANGLE);
      accord2.setWaveform(Waves.TRIANGLE);
      break;
    case 2:
      wave.setWaveform(Waves.SAW);
      accord1.setWaveform(Waves.SAW);
      accord2.setWaveform(Waves.SAW);
      break;
    case 3:
      wave.setWaveform(Waves.SQUARE);
      accord1.setWaveform(Waves.SQUARE);
      accord2.setWaveform(Waves.SQUARE);
      break;
    case 4:
      wave.setWaveform(Waves.QUARTERPULSE);
      accord1.setWaveform(Waves.QUARTERPULSE);
      accord2.setWaveform(Waves.QUARTERPULSE);
      break;
    }
  }

  /**
   * draw the actual waveform on the frame
   * 
   * @param x
   *          position on the window
   * @param y
   *          position on the window
   */
  public void drawType(int x, int y) {
    int height = 20;
    int width = 80;
    text("W", x - 20, y + 18);
    rect(x, y, width, height);
    translate(x, y);

    for (int i = 0; i < width - 1; i++) {
      point(i, height / 2 - (height * 0.4f) * wave.getWaveform().value((float) i / width));
    }
    translate(-x, -y);
  }

  /**
   * draw the current generated frequency on the screen
   * 
   * note used
   */
  @Deprecated
  public void drawFreq() {
    for (int i = 0; i < out.bufferSize() - 1; i++) {
      line(i, 50 - out.left.get(i) * 50, i + 1, 50 - out.left.get(i + 1) * 50);
      line(i, 150 - out.right.get(i) * 50, i + 1, 150 - out.right.get(i + 1) * 50);
    }
  }

  /**
   * generate the sound
   * 
   * @param amp
   *          sound level
   * @param freq
   *          current frequency
   * @param rotate
   *          generate some accord
   */
  private void move(float amp, double freq, float rotate) {
    /* aplitude (sound volume) */
    if (amp < 0)
      amp = 0;
    wave.setAmplitude(amp);

    // freq = 1046; // Test c'''
    // freq = 880; // Test a''
    // freq = 493; // Test h'
    // freq = 440; // Test a'
    // freq = 261; // Test c'
    // freq = 246; // Test h
    // freq = 146; // Test d
    // freq = 130; // Test c
    // freq = 123; // Test H
    // freq = 82; // Test E
    // freq = 73; // Test D
    // freq = 77; // Test DIS/ES
    // freq = 65; // Test C

    /* frequency generation */
    if (quantized) {
      if (onlyHalfTone)
        freq = musicScale.quantifyOnlyHalftone(freq);
      else
        freq = musicScale.quantify(freq);
    }

    /* filter max/min frequency */
    if (freq > 64 && freq < 1100) {
      wave.setFrequency((float) freq);

      fill(0);
      textFont(font25);

      /* quantisizes frequency to get the note to draw */
      double quantisized = musicScale.quantify(freq);
      Note note = musicScale.scale.get(quantisized);

      if (currentNote == null)
        currentNote = note.clone();

      if (note.getKey() != currentNote.getKey())
        currentNote = note.clone();

      rotation(rotate, currentNote);

      fill(0);
      text(note.getDescription(), width / 2 - 50, 200);

      /* draw the note on keyboard and lines */
      noteDrawer.draw(currentNote, 0);
      clavier.draw(currentNote.getKey());

    } else { /* mute the sound if no valid input parameters are given */
      currentNote = null;
      rotation(0, null);
      wave.setAmplitude(0);
    }

  }

  /**
   * normalize the input parameters for mouse interface.
   * 
   * @param left
   *          current x position of the mouse (amplitude)
   * @param right
   *          current y position of the mouse (frequency)
   * @param rotate
   *          generate an accord ({@link #mouseRotation})
   */
  private void mouseMoved(float left, float right, float rotate) {
    // aplitude
    float amp = map(left, 100, 600, 0, 1);

    // frequency
    double freq = map(right, 50, 250, 200, 1000); // 2094

    move(amp, freq, mouseRotation);
  }

  /**
   * determine the mousewheel position for the accord
   */
  public void mouseWheel(MouseEvent event) {
    if (event.getCount() > 0)
      mouseRotation += 0.1;
    else
      mouseRotation -= 0.1;

    System.out.println(mouseRotation);
  }

  /**
   * normalize the input parameter for hannd interface
   * 
   * @param left
   *          current x position of the left hand (amplitude)
   * @param right
   *          current y position of the right hand (frequency)
   * @param rotate
   *          current rotation of the left hand (accord)
   */
  public void handMoved(float left, float right, float rotate) {
    // aplitude
    float amp = map(left, 100, 500, 0, 1);

    // frequency
    double freq = map(right, 150, 400, 200, 1000); // 2094

    move(amp, freq, rotate);
  }

  /**
   * generate the accord
   * 
   * @param rotation
   *          sound level for the accord
   * @param note
   *          reference note for the accord
   */
  private void rotation(float rotation, Note note) {

    /* error handling */
    if (rotation == 0 || note == null) {
      accord1.setAmplitude(0f);
      accord2.setAmplitude(0f);
      return;
    }

    Note acc1 = null;
    Note acc2 = null;
    int ampTrans = 0;

    /* mol accord */
    if (rotation > 0.7) { // Mol
      float ampAcc = map(rotation, 0.7f, (float) Math.PI - 1.5f, 0f, 1f);
      ampTrans = (int) map(rotation, 0.7f, (float) Math.PI - 1.5f, 255, 0);

      accord1.setAmplitude(ampAcc);
      accord2.setAmplitude(ampAcc);

      acc1 = musicScale.NoteByDistance(note, 4).clone();
      acc2 = musicScale.NoteByDistance(note, 7).clone();

    } else if (rotation < 0f) { /* dur accord */
      // Dur
      float ampAcc = map(rotation, 0.0f, (float) -Math.PI + 2f, 0f, 1f);
      ampTrans = (int) map(rotation, 0.0f, (float) -Math.PI + 2f, 255, 0);

      accord1.setAmplitude(ampAcc);
      accord2.setAmplitude(ampAcc);

      acc1 = musicScale.NoteByDistance(note, 3).clone();
      acc2 = musicScale.NoteByDistance(note, 7).clone();

    } else { /* if value is between (especially for hand important) no accord */
      accord1.setAmplitude(0f);
      accord2.setAmplitude(0f);
      return;
    }

    accord1.setFrequency(new Float(acc1.getFreqencey()));
    accord2.setFrequency(new Float(acc2.getFreqencey()));

    /* no period for accord notes */
    acc1.setPeriod(Period.quarterNote);
    acc2.setPeriod(Period.quarterNote);

    /* draw additional notes */
    noteDrawer.draw(acc1, (int) ampTrans);
    noteDrawer.draw(acc2, (int) ampTrans);
  }

  /**
   * return the noteDrawer for the noterecorder
   * 
   * @return noteDrawer draw the note
   */
  public NoteDrawer getNoteDrawer() {
    return noteDrawer;
  }

  /**
   * increase the current note period (flag)
   */
  public void increaseNote() {
    if (currentNote != null)
      currentNote.increase();
  }

  /**
   * return the status of the metronom (on/off)
   * 
   * @return status of the metronom
   */
  public boolean metronom() {
    return metronom.onOff;
  }

  /**
   * recordNote is called from the NoteRecorder thread to collect the current played note
   * 
   * TODO not every note is collected
   */
  public void recordNote() {
    if (lastNote == null)
      lastNote = currentNote;
    if (currentNote != null) {
      if (currentNote.getKey() != lastNote.getKey()) {
        noteRecorder.addNote(currentNote);
        lastNote = currentNote;
      } else if (currentNote.getPeriod() == Period.wholeNote) {
        noteRecorder.addNote(currentNote);
        lastNote = null;
        currentNote = null;
      }
    }
  }

  /**
   * add a bar line to the recorder
   */
  public void addBar() {
    noteRecorder.addBar(640);
  }

  /**
   * Main to start
   * 
   * @param passedArgs
   */
  static public void main(String[] passedArgs) {
    System.out.println("main wird aufgerufen");
    String[] appletArgs = new String[] { "Theremin" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
