import java.util.concurrent.CopyOnWriteArrayList;

public class NoteRecorder implements OnBeat {

  private CopyOnWriteArrayList<Note> noteDisplay;
  private CopyOnWriteArrayList<Note> barLines;
  private ClockGenerator collectorBeat;
  private Theremin parent;
  private int beat;
  private NoteDrawer noteDrawer;

  public NoteRecorder(Theremin parent) {
    this.parent = parent;
    noteDrawer = parent.getNoteDrawer();
    noteDisplay = new CopyOnWriteArrayList<>();
    barLines = new CopyOnWriteArrayList<>();

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

  public void addBar(Integer x) {
    if (x != null)
    {
      Note bar = new Note(440, "Bar");
      bar.setPosX(x);
      barLines.add(bar);
    } 
  }

  public void noteListUpdate() {
    // noteDisplay.parallelStream().forEach(f -> f.increasePosYVal(beat / 20));

    for (Note bar : barLines) {
      bar.increasePosYVal(beat / 20);

      if (bar.getPosX() < 70)
        barLines.remove(bar);
    }

    for (Note note : noteDisplay) {
      // System.out.println(note);
      note.increasePosYVal(beat / 20);
      if (note.getPosX() < 70)
        noteDisplay.remove(note);
    }
  }

  public void changeBeat(int change) {
    if (beat + change >= 60 && beat + change <= 200) {
      beat += change;
      collectorBeat.changeBeat(60000 / beat / 4);
    }
  }

  @Override
  public void beat(float f) {
    parent.increaseNote();
  }

  public void drawAll() {
    for (Note note : noteDisplay)
      noteDrawer.draw(note);

    parent.strokeWeight(1);
    for (Note bar : barLines) {
      parent.line(bar.getPosX(), Background.VIOLINPOS + 21, bar.getPosX(), Background.VIOLINPOS + 60);
      parent.line(bar.getPosX(), Background.BASSPOS + 21, bar.getPosX(), Background.BASSPOS + 60);
    }
  }
}
