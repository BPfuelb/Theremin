import java.util.concurrent.CopyOnWriteArrayList;

public class NoteRecorder implements OnBeat {

  private CopyOnWriteArrayList<Note> noteDisplay;
  private ClockGenerator collectorBeat;
  private Theremin parent;
  private int beat;
  private NoteDrawer noteDrawer;

  public NoteRecorder(Theremin parent) {
    this.parent = parent;
    noteDrawer = parent.getNoteDrawer();
    noteDisplay = new CopyOnWriteArrayList<>();

    beat = 120;
    collectorBeat = new ClockGenerator(this, 500 / 4);
    collectorBeat.running();

    Thread collectorBeatThread = new Thread(collectorBeat);
    collectorBeatThread.setName("CollectorBeatThread");
    collectorBeatThread.start();
  }

  public void addNote(Note note) {
    if (note != null)
      noteDisplay.add(note.clone());
  }

  public void noteListUpdate() {

    noteDisplay.parallelStream().forEach(f -> f.increasePosYVal(beat / 20));

    for (Note note : noteDisplay) {
      // note.increasePosYVal(beat / 15);

      if (note.getPosX() < 50)
        noteDisplay.remove(note);

    }
  }

  public void changeBeat(int change) {
    if (beat + change >= 60 && beat + change <= 200) {
      beat += change;
      collectorBeat.changeBeat(60000 / (beat));
      System.out.println("Metronom:" + beat);
    }
  }

  @Override
  public void beat(float f) {
    parent.increaseNote();
  }

  public void drawAll() {
    for (Note note : noteDisplay) {
      noteDrawer.draw(note);
    }
  }
}
