/**
 * Sample Skeleton for 'BookingManager.fxml' Controller Class
 */

package hotel.controllers;

import hotel.booking.Booking;
import hotel.booking.BookingManager;
import hotel.rooms.Room;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BookingController {
  @FXML // ResourceBundle that was given to the FXMLLoader
  private ResourceBundle resources;

  @FXML // URL location of the FXML file that was given to the FXMLLoader
  private URL location;

  @FXML // fx:id="addBookingButton"
  private Button addBookingButton; // Value injected by FXMLLoader

  @FXML
  private Booking selectedBooking;

  @FXML // fx:id="bookingDate"
  private TableColumn<Booking, String> bookingDate; // Value injected by FXMLLoader

  @FXML // fx:id="bookingForm"
  private AnchorPane bookingForm; // Value injected by FXMLLoader

  @FXML // fx:id="bookingsTable"
  private TableView<Booking> bookingsTable; // Value injected by FXMLLoader

  @FXML // fx:id="checkInDate"
  private TableColumn<Booking, String> checkInDate; // Value injected by FXMLLoader

  @FXML // fx:id="checkInDatePicker"
  private DatePicker checkInDatePicker; // Value injected by FXMLLoader

  @FXML // fx:id="checkOutDate"
  private TableColumn<Booking, String> checkOutDate; // Value injected by FXMLLoader

  @FXML // fx:id="checkOutDatePicker"
  private DatePicker checkOutDatePicker; // Value injected by FXMLLoader

  @FXML // fx:id="customerId"
  private TableColumn<Booking, Integer> customerPhone; // Value injected by FXMLLoader

  @FXML // fx:id="customerIdField"
  private TextField customerPhoneField; // Value injected by FXMLLoader

  @FXML // fx:id="customerName"
  private TableColumn<Booking, String> customerName; // Value injected by FXMLLoader

  @FXML // fx:id="editBookingButton1"
  private Button editBookingButton1; // Value injected by FXMLLoader

  @FXML // fx:id="paymentStatus"
  private TableColumn<Booking, Boolean> paymentStatus; // Value injected by FXMLLoader

  @FXML // fx:id="removeBookingButton"
  private Button removeBookingButton; // Value injected by FXMLLoader

  @FXML // fx:id="returnButton"
  private Button returnButton; // Value injected by FXMLLoader

  @FXML // fx:id="roomId"
  private TableColumn<Booking, Integer> roomId; // Value injected by FXMLLoader

  @FXML // fx:id="roomNum"
  private TableColumn<Booking, Integer> roomNum; // Value injected by FXMLLoader

  @FXML // fx:id="roomNumberField"
  private TextField roomNumberField; // Value injected by FXMLLoader

  @FXML // fx:id="searchBarField"
  private TextField searchBarField; // Value injected by FXMLLoader

  @FXML // fx:id="searchButten"
  private Button searchButten; // Value injected by FXMLLoader

  @FXML // fx:id="searchButten"
  private TextField paymentStatusField; // Value injected by FXMLLoader

  @FXML
  private AnchorPane roomPane;

  @FXML
  private TableView<Room> roomTable;

  @FXML
  private TableColumn<Room, Integer> roomNoColumn;

  @FXML
  private TableColumn<Room, Integer> bedCountColumn;

  @FXML
  private TableColumn<Room, String> sizeColumn;

  @FXML
  private TableColumn<Room, String> locationColumn;

  @FXML
  private TableColumn<Room, String> addInfocolumn;

  @FXML
  private Button viewButton;

  @FXML
  private Button bookingsForDay; 

  @FXML 
  private Button bookingsForRoom;


  @FXML
  private Button bookings;

  @FXML
  private MenuItem searchByDate;

  @FXML
  private MenuItem searchByDay;

  @FXML
  private MenuItem searchByNum;

  @FXML
  private MenuItem searchByNumDate;

  @FXML
  private MenuItem searchByRoom;

  @FXML
  private Button update;

  @FXML
  private boolean isEditing = false;
  private boolean isBookingTableVisible = true;
  private BookingManager bookingManager = new BookingManager();

  @FXML
    void handleBookingButtonClicked(ActionEvent event) {
    bookingsTable.setVisible(true);
    roomPane.setVisible(false);
    isBookingTableVisible = true;
    roomTable.setVisible(false);
    return;
  }

  @FXML
    void handleByDateOption(ActionEvent event) {
    bookingsForDay.setVisible(false);
    searchButten.setVisible(true);
    bookingsForRoom.setVisible(false);

  }

  @FXML
    void handleByDay(ActionEvent event) {
    bookingsForDay.setVisible(true);
    searchButten.setVisible(false);
    bookingsForRoom.setVisible(false);
  }

  @FXML
    void handleByNumDateOption(ActionEvent event) {
    bookingsForDay.setVisible(false);
    searchButten.setVisible(true);
    bookingsForRoom.setVisible(false);

  }

  @FXML
  void handleByNumOption(ActionEvent event) {
    bookingsForDay.setVisible(false);
    searchButten.setVisible(true);
    bookingsForRoom.setVisible(false);

  }

  @FXML
  void handleByRoom(ActionEvent event) {
    bookingsForDay.setVisible(false);
    searchButten.setVisible(false);
    bookingsForRoom.setVisible(true);
  }

  @FXML
  void handleRemoveButton(ActionEvent event) {
    selectedBooking = bookingsTable.getSelectionModel().getSelectedItem();
    if (selectedBooking != null) {
      // Call the removeBooking method
      boolean removed = bookingManager.removeBooking(selectedBooking.getBookingId());
      if (removed) {
        // Remove the selected booking from the table
        bookingsTable.getItems().remove(selectedBooking);
        showInformationAlert("Booking removed successfully.");
      } else {
        // Show an error message using the alert dialog
        showErrorAlert("Failed to remove booking!");
      }
    } else {
      // Show a warning message if no booking is selected
      showWarningAlert("Please select a booking to remove.");
    }
  }
 
  @FXML
  void handleBookingsRoomButton(ActionEvent event) {

    BookingManager bookingManager = new BookingManager();
    
    List<Booking> searchedBookings = bookingManager.getAllBookingsForRoom(searchBarField.getText());


    // Set the searched bookings to the bookings table
    ObservableList<Booking> bookings = FXCollections.observableArrayList(searchedBookings);
    bookingsTable.setItems(bookings);

  }

  @FXML
  void handleBookingsDayButton(ActionEvent event) {
    BookingManager bookingManager = new BookingManager();

    if (checkInDatePicker.getValue() == null) {
      // Warn the user if the date picker is empty
      showWarningAlert("Please select a check-in date to search bookings.");
      return;
    }

    try {
      LocalDate checkInLocalDate = checkInDatePicker.getValue();

      // Convert LocalDate to java.sql.Date
      java.sql.Date checkInDate = java.sql.Date.valueOf(checkInLocalDate);

      List<Booking> searchedBookings = bookingManager.getAllBookingsForDate(checkInDate);

      // Set the searched bookings to the bookings table
      ObservableList<Booking> bookings = FXCollections.observableArrayList(searchedBookings);
      bookingsTable.setItems(bookings);

      if (searchedBookings.isEmpty()) {
        // Notify the user if no bookings are found
        showInformationAlert("No bookings found for the selected date.");
      }

    } catch (Exception e) {
      // Show an error alert if any exception occurs during the process
      showErrorAlert("An error occurred while fetching bookings: " + e.getMessage());
    }
  }

  @FXML
  void handleEditBookingButton(ActionEvent event) {
    selectedBooking = bookingsTable.getSelectionModel().getSelectedItem();

    // Assuming the date format is "yyyy-MM-dd"
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    if (selectedBooking != null) {
      // Enable the TextArea for editing
      //customerInputFullInfo.setEditable(true);

      // Store the original customer before editing
      isEditing = true;

      // Parse check-in date
      try {
        LocalDate checkInLocalDate = LocalDate.parse(selectedBooking.getCheckInDate(), formatter);
        checkInDatePicker.setValue(checkInLocalDate);
      } catch (DateTimeParseException e) {
        showErrorAlert("Invalid format for check-in date: " + selectedBooking.getCheckInDate());
      }

      // Parse check-out date
      try {
        LocalDate checkOutLocalDate = LocalDate.parse(selectedBooking.getCheckOutDate(), formatter);
        checkOutDatePicker.setValue(checkOutLocalDate);
      } catch (DateTimeParseException e) {
        showErrorAlert("Invalid format for check-out date: " + selectedBooking.getCheckOutDate());
      }

      paymentStatusField.setText(String.valueOf(selectedBooking.getPaidStatus()));

    } else {
      // Handle the case where no booking is selected (e.g., show an error message)
      showWarningAlert("No Booking selected for editing.");
    }
  }

  @FXML
  void handleUpdateButtonClicked(ActionEvent event) {
    if (selectedBooking != null && isEditing) {
      try {
        // Update the selectedBooking object with values from GUI fields
        selectedBooking.setCheckInDate(checkInDatePicker.getValue().toString());
        selectedBooking.setCheckOutDate(checkOutDatePicker.getValue().toString());
          
        boolean paidStatusValue = Boolean.parseBoolean(paymentStatusField.getText());
        selectedBooking.setPaidStatus(paidStatusValue);

        // Update the booking in the backend system
        bookingManager.updateBooking(selectedBooking);

        // Refresh the bookings table
        bookingsTable.setItems(bookingManager.getAllBookings());
        bookingsTable.refresh();

        checkInDatePicker.setValue(null);
        checkOutDatePicker.setValue(null);
        paymentStatusField.clear();

        // Reset the isEditing flag
        isEditing = false;
          
        // Notify user about successful update
        showInformationAlert("Booking has been successfully updated!");
      } catch (Exception e) {
        // If any unexpected error occurs, show an error alert
        showErrorAlert("An error occurred while updating the booking: " + e.getMessage());
      }
    } else {
      // Show a warning if no booking is selected or not in editing mode
      showWarningAlert("No Booking selected for updating or not in editing mode.");
    }
  }

  @FXML
    void handelReturnButton(ActionEvent event) {
    try {
      // Load the fxml file for the main screen
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/receptionistRooms.fxml"));
      Parent root = loader.load();

      // Get current stage and set the new scene
      Stage stage = (Stage) returnButton.getScene().getWindow();
      stage.setScene(new Scene(root));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
  
  @FXML
  void handleAddBookingButton(ActionEvent event) {
    BookingManager bookingManager = new BookingManager();
    LocalDate checkInLocalDate = checkInDatePicker.getValue();
    LocalDate checkOutLocalDate = checkOutDatePicker.getValue(); 
    // Convert LocalDate to java.sql.Date
    java.sql.Date checkInDate = null;
    java.sql.Date checkOutDate = null;
    if (checkInLocalDate != null) {
      checkInDate = java.sql.Date.valueOf(checkInLocalDate);
    }
    if (checkOutLocalDate != null) {
      checkOutDate = java.sql.Date.valueOf(checkOutLocalDate);
    }
    // Validation checks
    if (checkInLocalDate == null || checkOutLocalDate == null) {
      showWarningAlert("Check-in and check-out dates are required.");
      return;
    }  
    if (checkInDate.after(checkOutDate)) {
      showErrorAlert("Check-in date cannot be after check-out date.");
      return;
    }
    if (roomNumberField.getText().isEmpty() || customerPhoneField.getText().isEmpty()) {
      showErrorAlert("Room number and customer phone are required.");
      return;
    }
      
    java.util.Date currentDate = new java.util.Date();
    if (checkInDate.before(currentDate)) {
      showWarningAlert("Check-in date cannot be before the current date.");
      return;
    }
  
    Boolean payment;
    if ("true".equalsIgnoreCase(paymentStatusField.getText().trim())) {
      payment = true;
    } else if ("false".equalsIgnoreCase(paymentStatusField.getText().trim()) 
                   || paymentStatusField.getText().trim().isEmpty()) {
      payment = false;
    } else {
      showErrorAlert("Invalid input for payment status. Please enter 'true' or 'false'.");
      return;
    }
    String roomNumber = roomNumberField.getText().trim();
    int customerPhone;
    try {
      customerPhone = Integer.parseInt(customerPhoneField.getText());
    } catch (NumberFormatException e) {
      showErrorAlert("Invalid customer phone number.");
      return;
    }
    if (bookingManager.bookRoom(customerPhone, roomNumber, checkInDate, checkOutDate, payment)) {
      showInformationAlert("Booking was successfully made.");
      roomNumberField.clear();
      customerPhoneField.clear();
      checkInDatePicker.setValue(null);
      checkOutDatePicker.setValue(null);
    } else {
      showErrorAlert("Booking failed. Please check the details and try again.");
    }
  
    // Refresh the bookings table
    ObservableList<Booking> bookings = bookingManager.getAllBookings();
    bookingsTable.setItems(bookings);
  }

  @FXML
void handleSearchButton(ActionEvent event) {
    BookingManager bookingManager = new BookingManager();

    LocalDate checkInLocalDate = checkInDatePicker.getValue();
    LocalDate checkOutLocalDate = checkOutDatePicker.getValue();
    String phoneNumber = searchBarField.getText().trim();

    if (checkInLocalDate == null && checkOutLocalDate == null && phoneNumber.isEmpty()) {
      showWarningAlert("Please provide either a date range, phone number, or both.");
      return;
    }

    // If only one of the date fields is filled out, show a warning and exit
    if ((checkInLocalDate == null && checkOutLocalDate != null) 
             || (checkInLocalDate != null && checkOutLocalDate == null)) {
      showWarningAlert("Please provide both check-in and check-out dates.");
      return;
    }

    // If dates are provided, check if the check-in date is after the check-out date
    if (checkInLocalDate != null && checkOutLocalDate != null 
          && checkInLocalDate.isAfter(checkOutLocalDate)) {
      showWarningAlert("Check-in date should be before or on the same day as check-out date.");
      return;
    }

    try {
      List<Booking> searchedBookings = 
          bookingManager.searchBookings(phoneNumber, checkInLocalDate, checkOutLocalDate);

      // Set the searched bookings to the bookings table
      ObservableList<Booking> bookings = FXCollections.observableArrayList(searchedBookings);
      bookingsTable.setItems(bookings);

      if (searchedBookings.isEmpty()) {
        // Notify the user if no bookings match the search criteria
        showInformationAlert("No bookings found for the given search criteria.");
      }

    } catch (Exception e) {
      // Show an error alert if any exception occurs during the process
      showErrorAlert("An error occurred while searching bookings: " + e.getMessage());
    }
  }

  @FXML
  void handleViewButtonClicked(ActionEvent event) {
    try {
      bookingsTable.setVisible(false);
      roomPane.setVisible(true);
      isBookingTableVisible = false;
      roomTable.setVisible(true);
      LocalDate checkInLocalDate = checkInDatePicker.getValue();
      LocalDate checkOutLocalDate = checkOutDatePicker.getValue();

      // If dates are not picked or check-in date is after check-out date, show the booking table.
      if (checkInLocalDate == null || checkOutLocalDate == null 
            || checkInLocalDate.isAfter(checkOutLocalDate)
            || checkInLocalDate != null && checkOutLocalDate != null 
            && checkInLocalDate.isAfter(checkOutLocalDate)) {
        // Display an alert only if dates are picked but invalid
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Date Error");
        alert.setHeaderText(null);
        alert.setContentText(
               "Check-in date cannot be after the check-out date or the fields are empty");
        alert.showAndWait();
      }

      if (checkInDatePicker.getValue() != null && checkOutDatePicker.getValue() != null) {
        java.sql.Date checkInDate = java.sql.Date.valueOf(checkInLocalDate);
        java.sql.Date checkOutDate = java.sql.Date.valueOf(checkOutLocalDate);

        ObservableList<Room> availableRooms = FXCollections.observableArrayList(
                    bookingManager.getAvailableRooms(checkInDate, checkOutDate));
        roomTable.setItems(availableRooms);
        roomTable.refresh();
        checkInDatePicker.setValue(null);
        checkOutDatePicker.setValue(null);

        return;
      } 

    } catch (Exception ex) {
      // Handle exception
      ex.printStackTrace();
    }
  }

  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
  assert addBookingButton != null : 
  "fx:id=\"addBookingButton\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert bookingDate != null : 
  "fx:id=\"bookingDate\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert bookingForm != null : 
  "fx:id=\"bookingForm\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert bookingsTable != null : 
  "fx:id=\"bookingsTable\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert checkInDate != null : 
  "fx:id=\"checkInDate\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert checkInDatePicker != null : 
  "fx:id=\"checkInDatePicker\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert checkOutDate != null : 
  "fx:id=\"checkOutDate\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert checkOutDatePicker != null : 
  "fx:id=\"checkOutDatePicker\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert customerPhone != null : 
  "fx:id=\"customerId\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert customerPhoneField != null : 
  "fx:id=\"customerIdField\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert customerName != null : 
  "fx:id=\"customerName\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert editBookingButton1 != null : 
  "fx:id=\"editBookingButton1\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert paymentStatus != null : 
  "fx:id=\"paymentStatus\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert removeBookingButton != null : 
  "fx:id=\"removeBookingButton\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert returnButton != null : 
  "fx:id=\"returnButton\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert roomId != null : 
  "fx:id=\"roomId\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert roomNum != null : 
  "fx:id=\"roomNum\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert roomNumberField != null : 
  "fx:id=\"roomNumberField\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert searchBarField != null : 
  "fx:id=\"searchBarField\" was not injected: check your FXML file 'BookingManager.fxml'.";
  assert searchButten != null : 
  "fx:id=\"searchButten\" was not injected: check your FXML file 'BookingManager.fxml'.";

    bookingDate.setCellValueFactory(
            cellData -> new SimpleStringProperty(cellData.getValue().getBookingDate()));
    customerPhone.setCellValueFactory(
             cellData -> new SimpleIntegerProperty(
                   cellData.getValue().getCustomerNumber()).asObject());
    roomId.setCellValueFactory(cellData ->
                    new SimpleIntegerProperty(cellData.getValue().getRoomId()).asObject());
    customerName.setCellValueFactory(
           cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
    roomNum.setCellValueFactory(
          cellData -> new SimpleIntegerProperty(
                   cellData.getValue().getRoomNumber()).asObject());
    paymentStatus.setCellValueFactory(
           cellData -> new SimpleBooleanProperty(cellData.getValue().getPaidStatus()).asObject());
    checkInDate.setCellValueFactory(
           cellData -> new SimpleStringProperty(cellData.getValue().getCheckInDate()));
    checkOutDate.setCellValueFactory(
           cellData -> new SimpleStringProperty(cellData.getValue().getCheckOutDate()));

    roomNoColumn.setCellValueFactory(cellData -> 
                         new SimpleIntegerProperty(cellData.getValue().getRoomNumber()).asObject());
    bedCountColumn.setCellValueFactory(cellData ->
                         new SimpleIntegerProperty(cellData.getValue().getBedCount()).asObject());
    sizeColumn.setCellValueFactory(cellData ->
                         new SimpleStringProperty(cellData.getValue().getSize()));
    locationColumn.setCellValueFactory(cellData -> 
                         new SimpleStringProperty(cellData.getValue().getLocation()));
    addInfocolumn.setCellValueFactory(cellData -> 
                         new SimpleStringProperty(cellData.getValue().getAdditionalInfo()));


    // Refresh the bookings table
    BookingManager bookingManager = new BookingManager();
    ObservableList<Booking> bookings = bookingManager.getAllBookings();
    bookingsTable.setItems(bookings);

    

  }

  private void showInformationAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
  
  // Method to show an error alert
  private void showErrorAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
  
  // Method to show a warning alert
  private void showWarningAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Warning");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}