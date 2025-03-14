package Animals;

import java.util.Timer;
import java.util.TimerTask;

public class GameLogic {
  private Animal pet;
  private Timer timer;

  public GameLogic(Animal pet) {
    this.pet = pet;
    this.timer = new Timer();
  }

  public void startGame() {
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        pet.reduceStatsOverTime();
        System.out.println(pet);
        if (!pet.isAlive()) {
          System.out.println("You neglected your pet! Take better care of it next time!");
          stopGame();
        }
      }
    }, 0, 2000); 
  }

  public void stopGame() {
    if (timer != null) {
      timer.cancel();
      timer = null;
    }
  }

  public void feedPet() {
    pet.feed();
  }

  public void giveWaterToPet() {
    pet.giveWater();
  }

  public void playWithPet() {
    pet.play();
  }

  public void putPetToSleep() {
    pet.sleep();
  }

  public void healPet() {
    pet.heal();
  }
}