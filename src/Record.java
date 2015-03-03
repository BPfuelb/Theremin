import ddf.minim.AudioOutput;
import ddf.minim.AudioPlayer;
import ddf.minim.AudioRecorder;
import ddf.minim.Minim;

public class Record {

  private String file;
  private Minim minim;
  private AudioRecorder recorder;
  private AudioPlayer player;

  public Record(Minim minim, AudioOutput in, String file) {
    this.minim = minim;
    this.file = file;

    recorder = minim.createRecorder(in, file);
  }

  public void beginRecord() {
    System.out.println("beginRecord(" + file + ")");
    if (!recorder.isRecording())
      recorder.beginRecord();
  }

  public void endRecord() {
    System.out.println("endRecord(" + file + ")");
    if (recorder.isRecording()) {
      recorder.endRecord();
      recorder.save();
      player = minim.loadFile(file);
    }
  }

  public void startLoop() {
    System.out.println("startLoop(" + file + ")");
    if (player != null && !player.isPlaying()) {
      player.loop();
    }
  }

  public void pauseLoop() {
    System.out.println("stopLoop(" + file + ")");
    if (player != null && player.isPlaying()) {
      player.pause();
    }
  }

  public float position() {
    if (player == null) {
      return 0.5f;
    }
    return ((float)player.position() / (float)player.length());
  }

  public boolean isPlaying() {
    if (player == null) {
      return false;
    }
    return player.isPlaying();
  }

  public boolean isEmpty() {
    return (player == null);
  }
}
