import processing.core.PApplet;
import processing.core.PFont;
import processing.event.MouseEvent;
import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.ugens.Oscil;
import ddf.minim.ugens.Waves;

public class Theremin extends PApplet {

  private static final long serialVersionUID = -279550209204634548L;
  public static final int WIDTH_THEREMIN = 700;

  public Minim minim;
  private AudioOutput out;

  private Oscil wave;
  private Oscil accord1;
  private Oscil accord2;

  private Sensor sensor;
  private boolean quantized;
  public boolean mouseHands;
  private boolean halfTone;
  // TODO No HalfTone
  // Why no whole and half Notes

  private Metronome metronom;
  private NoteDrawer noteDrawer;
  private Background background;
  private NoteRecorder noteRecorder;
  public MusicalScale musicScale;
  public Clavier clavier;

  private PFont font = createFont("Arial", 25);
  private int waveForm = 0;
  private Note currentNote = null;
  private Note lastNote = null;
  private float mouseRotation = 0;

  public void setup() {

    frameRate(30);
    size(WIDTH_THEREMIN, 300);

    minim = new Minim(this);
    out = minim.getLineOut();

    wave = new Oscil(440, 0.0f, Waves.SINE);
    accord1 = new Oscil(440, 0.0f, Waves.SINE);
    accord2 = new Oscil(440, 0.0f, Waves.SINE);

    wave.patch(out);
    accord1.patch(out);

    musicScale = new MusicalScale(this);

    sensor = new Sensor(this);

    quantized = true;
    mouseHands = true;
    halfTone = false;

    background = new Background(this);
    metronom = new Metronome(this, 50, 50, 70);
    noteDrawer = new NoteDrawer(this);
    noteRecorder = new NoteRecorder(this);
    clavier = new Clavier(this, 55, 0);

  }

  public void draw() {
    background(255);

    background.draw();

    if (mouseHands) {
      mouseMoved(mouseX, height - mouseY, 0.6f);
    } else
      sensor.getValues();
    // sensor.getGestures();

    metronom.draw();

    noteRecorder.drawAll();
    noteRecorder.noteListUpdate();

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
      metronom.changeBeat(+5);
      noteRecorder.changeBeat(+5);
      break;

    case '-':
      metronom.changeBeat(-5);
      noteRecorder.changeBeat(-5);
      break;

    case 'm':
      metronom.onOffMute();
      break;
    case 't':
      test();
      break;
    case 'p':
      mouseHands = !mouseHands;
      move(0.0f, 0.0f, 0.0f);
    default:
      break;
    }
  }

  private void test() {

    // handMoved(400, 245, 0.6f);
  }

  public void nextWave() {
    waveForm = (waveForm + 6) % 5;
    keyPressed(48 + waveForm);
    System.out.println("Next wave: " + waveForm);

  }

  public void drawFreq() {
    for (int i = 0; i < width - 1; ++i) {
      point(i, height / 2 - (height * 0.49f) * wave.getWaveform().value((float) i / width));
    }
  }

  public void drawType() {
    for (int i = 0; i < out.bufferSize() - 1; i++) {
      line(i, 50 - out.left.get(i) * 50, i + 1, 50 - out.left.get(i + 1) * 50);
      line(i, 150 - out.right.get(i) * 50, i + 1, 150 - out.right.get(i + 1) * 50);
    }
  }

  private void move(float amp, double freq, float rotate) {
    // aplitude
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

    // frequency
    if (quantized)
      freq = musicScale.quantify(freq);

    if (freq > 64 && freq < 1100) {

      wave.setFrequency((float) freq);

      fill(0);
      textFont(font);

      double quantisized = musicScale.quantify(freq);
      Note note = musicScale.scale.get(quantisized);

      if (currentNote == null)
        currentNote = note.clone();

      if (note.getKey() != currentNote.getKey())
        currentNote = note.clone();

      rotation(rotate, currentNote);

      fill(0);
      text(note.getDescription(), width / 2 - 50, 200);
      noteDrawer.draw(currentNote, 0);
      clavier.draw(currentNote.getKey());

    } else {
      currentNote = null;
      rotation(0, null);
      wave.setAmplitude(0);
    }

  }

  private void mouseMoved(float left, float right, float rotate) {
    // aplitude
    float amp = map(left, 100, 600, 0, 1);

    // frequency
    double freq = map(right, 50, 250, 200, 1000); // 2094

    move(amp, freq, mouseRotation);
  }

  public void mouseWheel(MouseEvent event) {
    if (event.getCount() > 0)
      mouseRotation += 0.1;
    else
      mouseRotation -= 0.1;

    System.out.println(mouseRotation);
  }

  public void handMoved(float left, float right, float rotate) {
    // aplitude
    float amp = map(left, 100, 500, 0, 1);

    // frequency
    double freq = map(right, 150, 400, 200, 1000); // 2094

    move(amp, freq, rotate);
  }

  private void rotation(float rotation, Note note) {

    if (rotation == 0 || note == null) {
      accord1.setAmplitude(0f);
      accord2.setAmplitude(0f);
      return;
    }

    Note acc1 = null;
    Note acc2 = null;
    int ampTrans = 0;

    if (rotation > 0.7) { // Mol
      float ampAcc = map(rotation, 0.7f, (float) Math.PI - 1.5f, 0f, 1f);
      ampTrans = (int) map(rotation, 0.7f, (float) Math.PI - 1.5f, 255, 0);

      accord1.setAmplitude(ampAcc);
      accord2.setAmplitude(ampAcc);

      acc1 = musicScale.NoteByDistance(note, 4);
      acc2 = musicScale.NoteByDistance(note, 7);

    } else if (rotation < 0f) { // Dur
      float ampAcc = map(rotation, 0.0f, (float) -Math.PI + 2f, 0f, 1f);
      ampTrans = (int) map(rotation, 0.0f, (float) -Math.PI + 2f, 255, 0);

      accord1.setAmplitude(ampAcc);
      accord2.setAmplitude(ampAcc);

      acc1 = musicScale.NoteByDistance(note, 3);
      acc2 = musicScale.NoteByDistance(note, 7);

    } else {
      accord1.setAmplitude(0f);
      accord2.setAmplitude(0f);
      return;
    }

    accord1.setFrequency(new Float(acc1.getFreqencey()));
    accord2.setFrequency(new Float(acc2.getFreqencey()));

    noteDrawer.draw(acc1, (int) ampTrans);
    noteDrawer.draw(acc2, (int) ampTrans);

  }

  public NoteDrawer getNoteDrawer() {
    return noteDrawer;
  }

  public void increaseNote() {
    if (currentNote != null)
      currentNote.increase();
  }

  public boolean metronom() {

    return metronom.onOff;
  }

  public void recordNote() {
    if (lastNote == null)
      lastNote = currentNote;
    if (currentNote != null) {

      if (currentNote.getKey() != lastNote.getKey()) {
        noteRecorder.addNote(currentNote);
        lastNote = currentNote;
        // currentNote = null;
      } else if (currentNote.getPeriod() == Period.wholeNote) {
        noteRecorder.addNote(currentNote);
        lastNote = null;
        // currentNote = null;
      }
    }

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
