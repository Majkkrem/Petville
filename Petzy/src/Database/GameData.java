package Database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GameData {
  public void saveGame(int userId, Object gameData) {
    String query = "INSERT INTO save_files (user_id, save_data) VALUES (?, ?)";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(bos);
      oos.writeObject(gameData);
      byte[] saveData = bos.toByteArray();
      statement.setInt(1, userId);
      statement.setBytes(2, saveData);
      statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Object loadGame(int userId) {
    String query = "SELECT save_data FROM save_files WHERE user_id = ? ORDER BY id DESC LIMIT 1";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, userId);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        byte[] saveData = resultSet.getBytes("save_data");
        ByteArrayInputStream bis = new ByteArrayInputStream(saveData);
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
