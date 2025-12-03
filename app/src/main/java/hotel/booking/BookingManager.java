package hotel.booking;

import hotel.customer.Customer;
import hotel.customer.CustomerManager;
import hotel.dbconnection.Coonnection;
import hotel.rooms.Room;
import hotel.rooms.RoomManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Manages operations related to bookings.
 */
public class BookingManager {
  private Coonnection coonnection;
  private Connection connection;
  private List<Booking> bookings = new ArrayList<>();
  private CustomerManager customerManager = new CustomerManager();

  /**
   * Constructor for the BookingManager class.
   * Initializes the database connection.
   */
  public BookingManager() {
    try {
      coonnection = new Coonnection();
      connection = coonnection.getConnection();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

    
  /**
   * Books a room for a specific date range.
   *
   * @param customerNumber The number of the customer.
   * @param roomNumber The room to be booked.
   * @param checkIn The check-in date.
   * @param checkOut The check-out date.
   * @param paymentStatus The payment status.
   * @return Whether the room was booked successfully.
   */
  public boolean bookRoom(Integer customerNumber, String roomNumber, 
                          java.sql.Date checkIn, java.sql.Date checkOut, boolean paymentStatus) {
    String sql = "INSERT INTO Booking(customer_id, room_id, check_in_date,"
        + " check_out_date, booking_date, paid_status) VALUES (?, ?, ?, ?, NOW(), ?)";

    int customerId = getCustomerIdFromCustomerNumber(customerNumber);
    if (customerId == -1) {
      System.err.println("Customer with number " + customerNumber + " does not exist!");
      return false;
    }
    if (!isRoomAvailable(roomNumber, checkIn, checkOut)) {
      System.err.println("The room " + roomNumber + " is not available from " 
          + checkIn + " to " + checkOut);
      return false;
    }              

    if (!doesCustomerExist(customerId)) {
      System.err.println("Customer with ID " + customerId + " does not exist!");
      return false;
    }

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, customerId);
      statement.setInt(2, getRoomIdFromRoomNumber(roomNumber));
      statement.setDate(3, checkIn);
      statement.setDate(4, checkOut);
      statement.setBoolean(5, paymentStatus);
      statement.executeUpdate();
      return true;

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Gets the customer ID from the customer number.
   *
   * @param customerNumber The customer number.
   * @return The customer ID.
   */
  public int getCustomerIdFromCustomerNumber(Integer customerNumber) {
    List<Customer> allCustomers = customerManager.getAllCustomers();
    for (Customer customer : allCustomers) {
      if (customer.getCustomerNum().equals(customerNumber)) {
        return customer.getId();
      }
    }
    return -1;
  }

  /**
   * Checks if a customer exists.
   *
   * @param customerId The ID of the customer.
   * @return Whether the customer exists or not.
   */
  public boolean doesCustomerExist(int customerId) {
    List<Customer> allCustomers = customerManager.getAllCustomers();
    for (Customer customer : allCustomers) {
      if (customer.getId() == customerId) {
        return true;
      }
    }
    return false;
  }

  /**
   * Retrieves all bookings for a specific room.
   *
   * @param roomNumber The room number.
   * @return The list of bookings.
   */
  public List<Booking> getAllBookingsForRoom(String roomNumber) {
    String sql = "SELECT * FROM Booking WHERE room_id = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, getRoomIdFromRoomNumber(roomNumber));
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        bookings.add(new Booking(rs.getInt("booking_id"),
            rs.getInt("customer_id"),
            rs.getInt("room_id"),
            rs.getString("check_in_date"),
            rs.getString("check_out_date"),
            rs.getString("booking_date"),
            rs.getBoolean("paid_status")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return bookings;
  }

  /**
   * Retrieves all the bookings for a specific date.
   *
   * @param date The date for which bookings are to be retrieved.
   * @return The list of bookings.
   */
  public List<Booking> getAllBookingsForDate(java.sql.Date date) {
    List<Booking> bookings = new ArrayList<>();
    String sql = "SELECT * FROM Booking WHERE check_in_date <= ? AND check_out_date >= ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setDate(1, date);
      statement.setDate(2, date);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        bookings.add(new Booking(rs.getInt("booking_id"),
            rs.getInt("customer_id"),
            rs.getInt("room_id"),
            rs.getString("check_in_date"),
            rs.getString("check_out_date"),
            rs.getString("booking_date"),
            rs.getBoolean("paid_status")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return bookings;
  }

  /**
   * Searches bookings based on the provided criteria.
   *
   * @param phoneNumber The phone number.
   * @param fromDate The starting date.
   * @param toDate The ending date.
   * @return The list of bookings.
   */
  public List<Booking> searchBookings(String phoneNumber, LocalDate fromDate, LocalDate toDate) {
    StringBuilder query = new StringBuilder(
        "SELECT Booking.*, Customer.customer_number FROM Booking INNER JOIN Customer "
        + "ON Booking.customer_id = Customer.customer_id WHERE 1=1");

    List<Object> parameters = new ArrayList<>();

    if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
      query.append(" AND Customer.customer_number LIKE ?");
      parameters.add("%" + phoneNumber + "%");
    }
    if (fromDate != null) {
      query.append(" AND Booking.check_in_date >= ?");
      parameters.add(fromDate);
    }
    if (toDate != null) {
      query.append(" AND Booking.check_out_date <= ?");
      parameters.add(toDate);
    }

    List<Booking> bookings = new ArrayList<>();
    try (PreparedStatement statement = connection.prepareStatement(query.toString())) {

      // Set the parameters
      for (int i = 0; i < parameters.size(); i++) {
        if (parameters.get(i) instanceof LocalDate) {
          statement.setDate(i + 1, java.sql.Date.valueOf((LocalDate) parameters.get(i)));
        } else {
          statement.setString(i + 1, parameters.get(i).toString());
        }
      }

      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        bookings.add(new Booking(rs.getInt("booking_id"),
            rs.getInt("customer_id"),
            rs.getInt("room_id"),
            rs.getString("check_in_date"),
            rs.getString("check_out_date"),
            rs.getString("booking_date"),
            rs.getBoolean("paid_status")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return bookings;
  }

  /**
   * Updates the booking details in the database.
   *
   * @param booking The booking to be updated.
   */
  public void updateBooking(Booking booking) {
    // Include paidStatus in your SQL update statement
    String sql = "UPDATE Booking SET customer_id = ?, room_id = ?, check_in_date = ?,"
            + " check_out_date = ?, paid_status = ? WHERE booking_id = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, booking.getCustomerId());
      statement.setInt(2, booking.getRoomId());
      statement.setString(3, booking.getCheckInDate());
      statement.setString(4, booking.getCheckOutDate());
      statement.setBoolean(5, booking.getPaidStatus());  // Set the paidStatus value
      statement.setInt(6, booking.getBookingId());

      int rowsUpdated = statement.executeUpdate();
      if (rowsUpdated == 0) {
        System.out.println("Warning: No rows were updated.");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Helper function to get RoomId from RoomNumber
  private int getRoomIdFromRoomNumber(String roomNumber) {
    String sql = "SELECT room_id FROM Room WHERE room_number = ?";
    int roomId = -1;

    try (PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, roomNumber);
      ResultSet rs = statement.executeQuery();

      if (rs.next()) {
        roomId = rs.getInt("room_id");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return roomId;
  }

  private java.sql.Date convertStringToDate(String dateString) {
    return java.sql.Date.valueOf(dateString);
  }

  public boolean isRoomAvailable(String roomNumber, java.sql.Date checkInDate, java.sql.Date checkOutDate) {
    Integer roomId = getRoomIdFromRoomNumber(roomNumber);
    List<Booking> bookings = getAllBookings();  // Fetch all bookings within the desired date range
    
    for (Booking booking : bookings) {
      if (booking.getRoomId() == (roomId)) { // Only consider bookings for the room in question
        java.sql.Date bookingCheckIn = convertStringToDate(booking.getCheckInDate());
        java.sql.Date bookingCheckOut = convertStringToDate(booking.getCheckOutDate());

        if (
                // Existing booking starts within the desired date range
                (bookingCheckIn.compareTo(checkInDate) >= 0 
                && bookingCheckIn.compareTo(checkOutDate) <= 0) 
                ||
                // Existing booking ends within the desired date range
                (bookingCheckOut.compareTo(checkInDate) >= 0 
                && bookingCheckOut.compareTo(checkOutDate) <= 0) 
                ||
                // Existing booking encompasses the desired date range
                (bookingCheckIn.compareTo(checkInDate) <= 0 
                && bookingCheckOut.compareTo(checkOutDate) >= 0) 
                ||
                // Desired date range encompasses the existing booking
                (checkInDate.compareTo(bookingCheckIn) <= 0 
                && checkOutDate.compareTo(bookingCheckOut) >= 0)
        ) {
          return false;  // Room is NOT available
        }
      }
    }
    
    return true;  // Room is available
  }




  public List<Room> getAvailableRooms(java.sql.Date checkInDate, java.sql.Date checkOutDate) {
    List<Room> availableRooms = new ArrayList<>();
    // Assuming you have an instance of RoomManager or you can create one
    RoomManager roomManager = new RoomManager();
    // Loop through all rooms to check their availability
    for (Room room : roomManager.getRooms()) {
      if (isRoomAvailable(String.valueOf(room.getRoomNumber()), checkInDate, checkOutDate)) {
        availableRooms.add(room); // If room is available, add it to the list
      }
    }

    return availableRooms;
  }
  
  public ObservableList<Booking> getAllBookings() {
    ObservableList<Booking> bookings = FXCollections.observableArrayList();
    String sql = "SELECT * FROM Booking";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      ResultSet rs = statement.executeQuery();
      
      while (rs.next()) {
        Booking booking = new Booking(
                rs.getInt("booking_id"),
                rs.getInt("customer_id"),
                rs.getInt("room_id"),
                rs.getDate("check_in_date").toString(),
                rs.getDate("check_out_date").toString(),
                rs.getDate("booking_date").toString(),
                rs.getBoolean("paid_status")
            );
        bookings.add(booking);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    return bookings;
  }

  public boolean removeBooking(int bookingId) {
    String sql = "DELETE FROM Booking WHERE booking_id = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, bookingId);
      int rowsDeleted = statement.executeUpdate();
      
      if (rowsDeleted > 0) {
        System.out.println("Successfully deleted booking with ID: " + bookingId);
        return true;
      } else {
        System.out.println("No booking found with ID: " + bookingId);
        return false;
      }

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

}