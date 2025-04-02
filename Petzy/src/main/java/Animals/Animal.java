package Animals;

public class Animal {
    private String name;
    private int energy;
    private int food;
    private int water;
    private int health;
    private int mood;
    private boolean isSleeping;

  public Animal(String name, int energy, int food, int water, int health, int mood) {
    this.name = name;
    this.energy = energy;
    this.food = food;
    this.water = water;
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
        "%s - Energy: %d, Food: %d, Water: %d, Health: %d, Mood: %d",
        name, energy, food, water, health, mood
    );
  }

  public int getEnergy() {
    return energy;
  }

  public void setEnergy(int energy) {
    this.energy = Math.max(0, Math.min(100, energy));
  }

  public int getFood() {
    return food;
  }

  public void setFood(int food) {
    this.food = Math.max(0, Math.min(100, food));
  }

  public int getWater() {
    return water;
  }

  public void setWater(int water) {
    this.water = Math.max(0, Math.min(100, water));
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

  public boolean isSleeping() {
    return isSleeping;
  }

  public void setSleeping(boolean sleeping) {
    isSleeping = sleeping;
  }

  public void reduceStatsOverTime() {
    if (!isSleeping) {
      setEnergy(getEnergy() - 5);
    }
    setFood(getFood() - 5);
    setWater(getWater() - 5);
    setMood(getMood() - 5);

    if (getFood() <= 0 || getWater() <= 0) {
      setHealth(getHealth() - 10);
    }
  }

  public void feed() {
    setFood(getFood() + 20);
  }

  public void giveWater() {
    setWater(getWater() + 20);
  }

  public void play() {
    setMood(getMood() + 20);
    setEnergy(getEnergy() - 10);
  }

  public void sleep() {
    setEnergy(getEnergy() + 30);
  }

  public void heal() {
    setHealth(getHealth() + 20);
  }

  public boolean isAlive() {
    return getHealth() > 0;
  }

  public void displayStats() {
    System.out.println("Energy: " + getEnergy());
    System.out.println("Food: " + getFood());
    System.out.println("Water: " + getWater());
    System.out.println("Health: " + getHealth());
    System.out.println("Mood: " + getMood());
  }
}