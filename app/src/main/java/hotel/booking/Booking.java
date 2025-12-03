package hotel.booking;

import hotel.customer.Customer;
import hotel.customer.CustomerManager;
import hotel.rooms.Room;
import hotel.rooms.RoomManager;
import java.util.List;


/**
 * Represents a customer with details such as name, address, payment method, and additional notes.
 */
public class Booking {
  private int bookingId;
  private int customerId;
  private int roomId;
  private String checkInDate;
  private String checkOutDate;
  private String bookingDate;
  private boolean paidStatus;


  public Booking(int bookingId, int customerId, int roomId,
             String checkInDate, String checkOutDate, String bookingDate, boolean paidStatus) {
    this.bookingId = bookingId;
    this.customerId = customerId;
    this.roomId = roomId;
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
    this.bookingDate = bookingDate;
    this.paidStatus = paidStatus;
  }
  
  public int getBookingId() {
    return bookingId;
  }

  public void setBookingId(int bookingId) {
    this.bookingId = bookingId;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public int getRoomId() {
    return roomId;
  }

  public void setRoomId(int roomId) {
    this.roomId = roomId;
  }

  public String getCheckInDate() {
    return checkInDate;
  }

  public void setCheckInDate(String checkInDate) {
    this.checkInDate = checkInDate;
  }

  public String getCheckOutDate() {
    return checkOutDate;
  }

  public void setCheckOutDate(String checkOutDate) {
    this.checkOutDate = checkOutDate;
  }

  public String getBookingDate() {
    return bookingDate;
  }

  public void setBookingDate(String bookingDate) {
    this.bookingDate = bookingDate;
  }

  public boolean getPaidStatus() {
    return paidStatus;
  }

  public void setPaidStatus(boolean paidStatus) {
    this.paidStatus = paidStatus;
  }





  public Integer getRoomNumberFromList(int roomId, List<Room> roomsList) {
    for (Room room : roomsList) {
      if (room.getRoomId() == roomId) {
        return room.getRoomNumber();
      }
    }
    return null; // or some default value if the room id doesn't match any room
  }



  public Integer getRoomNumber() {

    RoomManager RoomManager = new RoomManager();
    List<Room> roomList = RoomManager.getRooms();
    return getRoomNumberFromList(this.roomId, roomList);
}

  public String getCustomerNameFromList(int customerId, List<Customer> customerList) {

    for (Customer customer : customerList) {
      if (customer.getId() == customerId) {
        return customer.getName();
      }
    }
    return ""; // return an empty string if the customer id doesn't match any customer
  }


  public String getCustomerName() {

    CustomerManager customerManager = new CustomerManager();
    List<Customer> customerList = customerManager.getAllCustomers();
    return getCustomerNameFromList(this.customerId, customerList);
  }

  public Integer getCustomerNumberFromList(int customerId, List<Customer> customerList) {

    for (Customer customer : customerList) {
      if (customer.getId() == customerId) {
        return customer.getCustomerNum();
      }
    }
    return 0; // return an empty string if the customer id doesn't match any customer
  }

  public Integer getCustomerNumber() {

    CustomerManager customerManager = new CustomerManager();
    List<Customer> customerList = customerManager.getAllCustomers();
    return getCustomerNumberFromList(this.customerId, customerList);
  }

}