import Animals.Animal;
import java.util.Timer;
import java.util.TimerTask;

public class StatManager {
  private Animal animal;

  public StatManager(Animal animal) {
    this.animal = animal;
  }

  public void startStatReductionTimer() {
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        animal.reduceStatsOverTime();
        // Logic to update the UI
      }
    }, 0, 2000);
  }
}