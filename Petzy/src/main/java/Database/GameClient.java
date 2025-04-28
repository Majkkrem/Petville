package Database;

import Animals.Animal;
import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

import Animals.Bee;
import Animals.Cat;
import Animals.Dog;
import Animals.Frog;
import org.json.JSONObject;

public class GameClient {
  public static final String SAVE_DIR = "saves";
  private static String currentUser;
  private static Integer currentUserId;

  public static boolean login(String username, String password) {
    try {
      Files.createDirectories(Paths.get(SAVE_DIR));
      currentUser = username;
      currentUserId = username.hashCode();
      return true;
    } catch (IOException e) {
      System.err.println("Error creating saves directory: " + e.getMessage());
      return false;
    }
  }

  public static boolean saveGameData(Animal animal, int coins) {
    if (currentUser == null) return false;

    try {
      JSONObject json = new JSONObject();
      json.put("user_id", currentUserId);
      json.put("username", currentUser);
      json.put("petName", animal.getName());
      json.put("petType", animal.getClass().getSimpleName());
      json.put("petEnergy", animal.getEnergy());
      json.put("petHunger", animal.getHunger());
      json.put("petHealth", animal.getHealth());
      json.put("petMood", animal.getMood());
      json.put("currentGold", coins);

      Path savePath = Paths.get(SAVE_DIR, currentUser + ".json");
      Files.write(savePath, json.toString().getBytes());
      return true;
    } catch (Exception e) {
      System.err.println("Save error: " + e.getMessage());
      return false;
    }
  }

  public static void saveInventoryData(String username, String petName, String petType, Map<String, Integer> inventory) {
    try {
      JSONObject json = new JSONObject();
      json.put("username", username);
      json.put("petName", petName);
      json.put("petType", petType);
      json.put("inventory", inventory);

      Path savePath = Paths.get(SAVE_DIR, username + "_" + petName + "_" + petType + "_inventory.json");
      Files.write(savePath, json.toString().getBytes());
    } catch (Exception e) {
      System.err.println("Save inventory error: " + e.getMessage());
    }
  }
  public static Animal loadGameData(String username, String petName, String petType) {
    try {
      Path savePath = Paths.get(SAVE_DIR, username + "_" + petName + "_" + petType + ".json");
      if (!Files.exists(savePath)) {
        return null;
      }

      String content = new String(Files.readAllBytes(savePath));
      JSONObject json = new JSONObject(content);

      Animal animal;
      switch (petType) {
        case "Cat":
          animal = new Cat(json.optString("petName", "Pet"));
          break;
        case "Frog":
          animal = new Frog(json.optString("petName", "Pet"));
          break;
        case "Bee":
          animal = new Bee(json.optString("petName", "Pet"));
          break;
        default: // Dog
          animal = new Dog(json.optString("petName", "Pet"));
          break;
      }

      animal.setEnergy(json.getInt("petEnergy"));
      animal.setHunger(json.getInt("petHunger"));
      animal.setHealth(json.getInt("petHealth"));
      animal.setMood(json.getInt("petMood"));
      animal.setSleeping(json.optBoolean("isSleeping", false));

      return animal;
    } catch (Exception e) {
      System.err.println("Load error: " + e.getMessage());
      return null;
    }
  }

  public static boolean saveGameData(String username, String petName, String petType, Animal animal, int coins) {
    try {
      JSONObject json = new JSONObject();
      json.put("username", username);
      json.put("petName", petName);
      json.put("petType", petType);
      json.put("petEnergy", animal.getEnergy());
      json.put("petHunger", animal.getHunger());
      json.put("petHealth", animal.getHealth());
      json.put("petMood", animal.getMood());
      json.put("currentGold", coins);

      Path savePath = Paths.get(SAVE_DIR, username + "_" + petName + "_" + petType + ".json");
      Files.write(savePath, json.toString().getBytes());
      return true;
    } catch (Exception e) {
      System.err.println("Save error: " + e.getMessage());
      return false;
    }
  }

  public static int loadCoins() {
    if (currentUser == null) return 100;

    try {
      Path savePath = Paths.get(SAVE_DIR, currentUser + ".json");
      if (!Files.exists(savePath)) {
        return 100;
      }

      String content = new String(Files.readAllBytes(savePath));
      JSONObject json = new JSONObject(content);
      return json.optInt("currentGold", 100);
    } catch (Exception e) {
      System.err.println("Error loading coins: " + e.getMessage());
      return 100;
    }
  }

  public static Map<String, Integer> loadInventoryData() {
    if (currentUser == null) return null;

    try {
      Path savePath = Paths.get(SAVE_DIR, currentUser + "_inventory.json");
      if (!Files.exists(savePath)) {
        return null;
      }

      String content = new String(Files.readAllBytes(savePath));
      JSONObject json = new JSONObject(content);

      Map<String, Object> rawMap = json.getJSONObject("inventory").toMap();
      Map<String, Integer> inventory = new HashMap<>();
      for (Map.Entry<String, Object> entry : rawMap.entrySet()) {
        inventory.put(entry.getKey(), ((Number)entry.getValue()).intValue());
      }
      return inventory;
    } catch (Exception e) {
      System.err.println("Load inventory error: " + e.getMessage());
      return null;
    }
  }

  private static Animal jsonToAnimal(JSONObject json) {
    Animal animal = new Animal(
        json.getString("petName"),
        json.getInt("petEnergy"),
        json.getInt("petHunger"),
        json.getInt("petHealth"),
        json.getInt("petMood")
    );
    animal.setSleeping(json.getBoolean("isSleeping"));
    return animal;
  }

  public static String getCurrentUser() {
    return currentUser;
  }

  public static void logout() {
    currentUser = null;
    currentUserId = null;
  }
}