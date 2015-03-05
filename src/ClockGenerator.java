/**
 * Thread generate the beat
 * 
 * @author Benedikt
 * @version 1.0
 */
public class ClockGenerator extends Thread {

  private OnBeat parent = null;
  private int timer = 0;
  private boolean running = false;

  public ClockGenerator(OnBeat parent, int timer) {
    this.parent = parent;
    this.timer = timer;

  }

  @Override
  public synchronized void run() {

    while (true) {
      if (!running) { /* pause the thread */
        try {
          wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      /* beat function */
      parent.beat(1f);

      try {
        wait(timer); /* sleep timer */
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * change the sleep timer
   * 
   * @param change
   *          value in ms
   */
  public void changeBeat(int change) {
    if (timer + change > 0)
      timer = change;
    // System.out.println(timer);
  }

  /**
   * get the sleep timer value
   * 
   * @return sleep timer value
   */
  public int getTimer() {
    return timer;
  }

  /**
   * unpause thread
   */
  public synchronized void running() {
    running = true;
    notifyAll();
  }

  /**
   * pausethread
   */
  public synchronized void pause() {
    running = false;
  }

}
