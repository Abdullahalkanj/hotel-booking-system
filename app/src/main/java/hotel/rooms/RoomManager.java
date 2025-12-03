package hotel.rooms;

import hotel.dbconnection.Coonnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



/**
 * This class is responsible for controlling room objects.
 */
public class RoomManager {
  Coonnection getConn = new Coonnection();
  Connection connection = getConn.getConnection();
  private List<Room> rooms;
  //private ObservableList<Room> roooms;

  public RoomManager() {
    rooms = new ArrayList<>();
    loadRoomsFromDatabase();
  }

  private void loadRoomsFromDatabase() {
    try {
      String selectRoomsQuery = "SELECT * FROM Room";
      PreparedStatement preparedStatement = connection.prepareStatement(selectRoomsQuery);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        int roomId = resultSet.getInt("room_id");
        String size = resultSet.getString("size");
        int bedCount = resultSet.getInt("bed_count");
        int roomNumber = resultSet.getInt("room_number");
        String location = resultSet.getString("location");
        String additionalInfo = resultSet.getString("additional_info");

        Room room = new Room(roomId, size, bedCount, roomNumber, location, additionalInfo);
        rooms.add(room);

      }
    } catch (Exception e) {
      System.out.println("Error executing query: " + e.getMessage());
    }
  }

  public void addRooms(Room room) {
    rooms.add(room);
  }

  /**
 * A method to insert rooms into the database.
 */
  public int insertRoomIntoDatabase(Room room) throws SQLException {
    int generatedId = -1;

    try {
      String insertRoomQuery = "INSERT INTO Room (size, bed_count, room_number, location, "
           + "additional_info) VALUES (?, ?, ?, ?, ?)";
    
      PreparedStatement preparedStatement = connection.prepareStatement(insertRoomQuery, 
          Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, room.getSize());
      preparedStatement.setInt(2, room.getBedCount());
      preparedStatement.setInt(3, room.getRoomNumber());
      preparedStatement.setString(4, room.getLocation());
      preparedStatement.setString(5, room.getAdditionalInfo());

      preparedStatement.executeUpdate();

      ResultSet resultSet = preparedStatement.getGeneratedKeys();
      if (resultSet.next()) {
        generatedId = resultSet.getInt(1);
      }
    } catch (Exception e) {
      System.out.println("Error inserting room into database: " + e.getMessage());
    }

    return generatedId;

  }

  public List<Room> getRooms() {
    return rooms;
  }

  public ObservableList<Room> getRoooms() {
    return FXCollections.observableArrayList(rooms);
  }

  /**
 * This class is responsible for controlling the admin interface.
 */
  public boolean removeRoomByNumber(int roomNumber) {
    boolean isSuccessful = false;

    try {
      // First, check if the room exists
      String checkRoomQuery = "SELECT * FROM Room WHERE room_number = ?";
      PreparedStatement checkStatement = connection.prepareStatement(checkRoomQuery);
      checkStatement.setInt(1, roomNumber);
        
      ResultSet resultSet = checkStatement.executeQuery();

      // If a result exists, the room exists
      if (resultSet.next()) {
        // Room exists, proceed to delete
        String removeRoomQuery = "DELETE FROM Room WHERE room_number = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(removeRoomQuery);
        preparedStatement.setInt(1, roomNumber);

        int rowsAffected = preparedStatement.executeUpdate();

        // If one or more rows were affected, the deletion was successful
        isSuccessful = rowsAffected > 0;
            
        if (isSuccessful) {
          System.out.println("Room with number " + roomNumber + " was successfully removed.");
          // Remove room from array
          rooms.removeIf(room -> room.getRoomNumber() == roomNumber);

        } else {
          System.out.println("There was an error when trying to remove the room.");
        }
      } else {
        // Room does not exist
        System.out.println("Room with number " + roomNumber + " does not exist.");
      }
    } catch (Exception e) {
      System.out.println("Error removing room from the database: " + e.getMessage());
    }

    return isSuccessful;
  }

  /**
 * Updates details of a room in the database.
 */
  public void updateRoomInDatabase(int roomId, 
                                  String newSize, 
                                  String newBedCount,
                                  String newRoomNumber, 
                                  String newLocation, 
                                  String newAdditionalInfo) {
    String updateRoomQuery =   "UPDATE Room SET size = ?, bed_count = ?, "
                              + "room_number = ?, location = ?, additional_info = ? "
                              + "WHERE room_id = ?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(updateRoomQuery)) {
      preparedStatement.setString(1, newSize);
      preparedStatement.setString(2, newBedCount);
      preparedStatement.setString(3, newRoomNumber);
      preparedStatement.setString(4, newLocation);

      if (newAdditionalInfo != null && !newAdditionalInfo.trim().isEmpty()) {
        preparedStatement.setString(5, newAdditionalInfo);
      } else {
        preparedStatement.setNull(5, java.sql.Types.VARCHAR);
      }

      preparedStatement.setInt(6, roomId);
      int rowsAffected = preparedStatement.executeUpdate();

      if (rowsAffected > 0) {
        System.out.println("Room with ID: " + roomId + " was successfully updated.");
      } else {
        System.out.println("There was an error when trying to update the room.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /**
 * This method retrieves a Room object based on the provided room number.
 * It iterates over the list of rooms, comparing each room's number with the provided room number.
 * If a match is found, that room is returned. If no match is found after checking all rooms, 
 * the method returns null.
 * 
 */
  public Room getRoomByNumber(int roomNumber) {
    for (Room room : rooms) {  // Assuming 'rooms' is the ArrayList storing the rooms
      if (room.getRoomNumber() == roomNumber) {
        return room;
      }
    }
    return null;  // If no matching room number is found, return null
  }

  

}
