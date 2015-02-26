public class Metronome extends Thread {

  private Theremin parent = null;
  private int timer = 0;

  public Metronome(Theremin parent, int timer) {
    this.parent = parent;
    this.timer = timer;
  }

  @Override
  public synchronized void run() {
    while (true) {
      parent.beat(1f);
      try {
        wait(timer);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void changeBeat(int change) {
    if (timer + change > 0)
      timer = change;
    System.out.println(timer);
  }

  public int getTimer() {
    return timer;
  }

}
