package Database;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.ConnectException;

public class GameClient {
  private static final String BASE_URL = "http://localhost:3000";

  public static void main(String[] args) throws Exception {
    HttpClient client = HttpClient.newHttpClient();
    testDatabaseConnection();

    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI(BASE_URL + "/users"))
        .GET()
        .build();

    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println(response.body());
    } catch (ConnectException e) {
      System.out.println("Failed to connect to the server: " + e.getMessage());
    }

    String json = "{\"name\":\"John Doe\",\"email\":\"john@example.com\",\"password\":\"password\"}";
    HttpRequest postRequest = HttpRequest.newBuilder()
        .uri(new URI(BASE_URL + "/users"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .build();

    try {
      HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
      System.out.println(postResponse.body());
    } catch (ConnectException e) {
      System.out.println("Failed to connect to the server: " + e.getMessage());
    }
  }

  public static void testDatabaseConnection() {
    try {
      HttpClient client = HttpClient.newHttpClient();

      // Test connection by fetching users
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
  }
}