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
      if (!running) {
        try {
          wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      
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
//    System.out.println(timer);
  }

  public int getTimer() {
    return timer;
  }

  public synchronized void running() {
    running = true;
    notifyAll();
  }

  public synchronized void pause() {
    running = false;
  }

}
