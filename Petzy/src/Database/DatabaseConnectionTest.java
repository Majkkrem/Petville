package Database;

import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionTest {
  public static void main(String[] args) {
    try {
      Connection connection = DatabaseConnection.getConnection();
      System.out.println("Connection successful!");

      // Defining the missing classes and variables
      Leaderboard leaderboard = new Leaderboard();
      int userId = 1;
      int score = 100;
      leaderboard.updateScore(userId, score);

      UserAuthentication auth = new UserAuthentication();
      String username = "exampleUser"; // Example username
      String password = "examplePass"; // Example password
      boolean isAuthenticated = auth.authenticate(username, password);

    } catch (SQLException e) {
      System.out.println("Failed to make connection!");
      e.printStackTrace();
    }
  }
}
/*
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionTest {
  public static void main(String[] args) {
    String url = "jdbc:mysql://localhost:3306/petville";
    String username = "root";
    String password = "";
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection connection = DriverManager.getConnection(url, username, password);
      Statement statement = connection.createStatement();
      ResultSet resultSet= statement.executeQuery("select * from users");
      while(resultSet.next()){
        System.out.println(resultSet.getInt(1)+ " "+ resultSet.getString(2)+ " "+ resultSet.getString(3));
      }
      connection.close();
    }
    catch (Exception e){
      System.out.println(e);
    }
    }
}*/
