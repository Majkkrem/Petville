package Animals;

public class Animal {
    private String name;
    private int energy;
    private int hunger;
    private int health;
    private int mood;
    private boolean isSleeping;

  public Animal(String name, int energy, int hunger, int health, int mood) {
    this.name = name;
    this.energy = energy;
    this.hunger = hunger;
    this.health = health;
    this.mood = mood;
    this.isSleeping = false;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return String.format(
        "%s - Energy: %d, Hunger:, Health: %d, Mood: %d",
        name, energy, hunger, health, mood
    );
  }

  public int getEnergy() {
    return energy;
  }

  public void setEnergy(int energy) {
    this.energy = Math.max(0, Math.min(100, energy));
  }

  public int getHunger() {
    return hunger;
  }

  public void setHunger(int hunger) {
    this.hunger = Math.max(0, Math.min(100, hunger));
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = Math.max(0, Math.min(100, health));
  }

  public int getMood() {
    return mood;
  }

  public void setMood(int mood) {
    this.mood = Math.max(0, Math.min(100, mood));
  }

  public void setSleeping(boolean sleeping) {
    isSleeping = sleeping;
  }

  public void reduceStatsOverTime() {
    if (!isSleeping) {
      setEnergy(getEnergy() - 2);
    }
    setHunger(getHunger() + 5);
    setMood(getMood() - 3);

    if (getHunger() >= 100) {
      setHealth(getHealth() - 10);
    }
  }

  public void feed() {
    setHunger(getHunger() - 30);
    if (getHunger() < 0) setHunger(0);
  }

  public void play() {
    setMood(getMood() + 20);
    setEnergy(getEnergy() - 10);
  }

  public void heal() {
    setHealth(getHealth() + 20);
  }

  public boolean isAlive() {
    return getHealth() > 0;
  }
}