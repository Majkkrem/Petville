package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuthentication {
  public boolean authenticate(String username, String password) {
    String query = "SELECT * FROM users WHERE name = ? AND password = ?";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, username);
      statement.setString(2, password);
      ResultSet resultSet = statement.executeQuery();
      return resultSet.next();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
}
