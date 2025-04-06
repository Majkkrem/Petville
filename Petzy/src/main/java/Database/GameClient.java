package Database;

import Animals.Animal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class GameClient {
  private static final String BASE_URL = "http://localhost:3000";
  private static String authToken;
  private static String currentUser;
  private static Integer currentUserId;

  public static boolean login(String username, String password) {
    try {
      HttpClient client = HttpClient.newHttpClient();
      JSONObject json = new JSONObject();
      json.put("name", username);
      json.put("password", password);

      HttpRequest request = HttpRequest.newBuilder()
          .uri(new URI(BASE_URL + "/auth/login"))
          .header("Content-Type", "application/json")
          .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
          .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 201) {
        JSONObject responseJson = new JSONObject(response.body());
        //authToken = responseJson.getString("token");
        currentUserId = responseJson.getInt("id");
        currentUser = username;
        return true;
      }
    } catch (Exception e) {
      System.out.println("Login error: " + e.getMessage());
    }
    return false;
  }

  public static boolean saveGameData(Animal animal) {

    try {
      HttpClient client = HttpClient.newHttpClient();
      JSONObject json = new JSONObject();
      json.put("user_id", currentUserId);
      json.put("petName", animal.getName());
      json.put("petType", animal.getClass().getSimpleName());
      json.put("petEnergy", animal.getEnergy());
      json.put("petHunger", animal.getHunger());
      json.put("petHealth", animal.getHealth());
      json.put("petMood", animal.getMood());
      json.put("hoursPlayer", 0);
      json.put("goldEarned", 0);
      json.put("currentGold", 0);


      HttpRequest request = HttpRequest.newBuilder()
          .uri(new URI(BASE_URL + "/save-files"))
          .header("Content-Type", "application/json")
          //.header("Authorization", "Bearer " + authToken)
          .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
          .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return response.statusCode() == 201;
    } catch (Exception e) {
      System.out.println("Save error: " + e.getMessage());
      return false;
    }
  }

  public static Animal loadGameData() {
    if (authToken == null || currentUser == null) return null;

    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(new URI(BASE_URL + "/load?username=" + currentUser))
          .header("Authorization", "Bearer " + authToken)
          .GET()
          .build();
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        JSONObject json = new JSONObject(response.body());
        return jsonToAnimal(json.getJSONObject("animalData"));
      }
    } catch (Exception e) {
      System.out.println("Load error: " + e.getMessage());
    }
    return null;
  }

  private static Animal jsonToAnimal(JSONObject json) {
    Animal animal = new Animal(
        json.getString("name"),
        json.getInt("energy"),
        json.getInt("hunger"),
        json.getInt("health"),
        json.getInt("mood")
    );
    animal.setSleeping(json.getBoolean("isSleeping"));
    return animal;
  }

  /*public static void testDatabaseConnection() {
    try {
      HttpClient client = HttpClient.newHttpClient();

      HttpRequest request = HttpRequest.newBuilder()
          .uri(new URI(BASE_URL + "/users"))
          .GET()
          .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        System.out.println("Database connection successful!");
      } else {
        System.out.println("Failed to connect to the database. Status code: " + response.statusCode());
      }
    } catch (ConnectException e) {
      System.out.println("Failed to connect to the server: " + e.getMessage());
    } catch (Exception e) {
      System.out.println("An error occurred while testing the database connection: " + e.getMessage());
    }
  }*/

  public static String getCurrentUser() {
    return currentUser;
  }

  public static void logout() {
    authToken = null;
    currentUser = null;
  }

}
