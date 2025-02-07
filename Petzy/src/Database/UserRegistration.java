package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRegistration {
  public boolean registerUser(String username, String password) {
    String query = "INSERT INTO users (name, password) VALUES (?, ?)";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, username);
      statement.setString(2, password);
      int rowsInserted = statement.executeUpdate();
      return rowsInserted > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
}
