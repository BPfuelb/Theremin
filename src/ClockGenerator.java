public class ClockGenerator extends Thread {

  private Metronome parent = null;
  private int timer = 0;

  public ClockGenerator(Metronome parent, int timer) {
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
