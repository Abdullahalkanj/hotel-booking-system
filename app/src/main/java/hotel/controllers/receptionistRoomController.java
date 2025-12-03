package hotel.controllers;

import hotel.rooms.Room;
import hotel.rooms.RoomManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class receptionistRoomController {

  @FXML
    private ResourceBundle resources;

  @FXML
    private URL location;

  @FXML
    private TableColumn<Room, String> additionalInfo;

  @FXML
    private TableColumn<Room, Integer> bedCount;

  @FXML
    private Button customerButton1;

  @FXML
    private TableColumn<Room, String> llocation;

  @FXML
    private Button returnButton;

  @FXML
    private Button bookingButton;

  @FXML
    private TableColumn<Room, Integer> roomNumber;

  @FXML
    private TableView<Room> roomsTable;

  @FXML
    private TableColumn<Room, String> size;

  @FXML
    void handleCustomerButtonClick(ActionEvent event) {
    try {
      // Load the fxml file for the main screen
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
      Parent root = loader.load();
      
      // Get current stage and set the new scene
      Stage stage = (Stage) customerButton1.getScene().getWindow();
      stage.setScene(new Scene(root));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @FXML
    void handleReturnButtonClick(ActionEvent event) {
    try {
      // Load the fxml file for the main screen
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MemberLogin.fxml"));
      Parent root = loader.load();

      // Get current stage and set the new scene
      Stage stage = (Stage) returnButton.getScene().getWindow();
      stage.setScene(new Scene(root));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @FXML
    void handleBookingButtonClick(ActionEvent event) {
    try {
      // Load the fxml file for the main screen
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookingManager.fxml"));
      Parent root = loader.load();
      
      // Get current stage and set the new scene
      Stage stage = (Stage) bookingButton.getScene().getWindow();
      stage.setScene(new Scene(root));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @FXML
  void initialize() {
        assert additionalInfo != null : 
          "fx:id=\"additionalInfo\" was not injected: check your FXML file 'AdminInterFace.fxml'.";
        assert bedCount != null : 
          "fx:id=\"bedCount\" was not injected: check your FXML file 'AdminInterFace.fxml'.";
        assert customerButton1 != null : 
          "fx:id=\"customerButton1\" was not injected: check your FXML file 'AdminInterFace.fxml'.";
        assert location != null : 
           "fx:id=\"location\" was not injected: check your FXML file 'AdminInterFace.fxml'.";
        assert returnButton != null : 
           "fx:id=\"returnButton\" was not injected: check your FXML file 'AdminInterFace.fxml'.";
        assert bookingButton != null : 
            "fx:id=\"roomButton\" was not injected: check your FXML file 'AdminInterFace.fxml'.";
        assert roomNumber != null : 
            "fx:id=\"roomNumber\" was not injected: check your FXML file 'AdminInterFace.fxml'.";
        assert roomsTable != null : 
            "fx:id=\"roomsTable\" was not injected: check your FXML file 'AdminInterFace.fxml'.";
        assert size != null : 
            "fx:id=\"size\" was not injected: check your FXML file 'AdminInterFace.fxml'.";
    roomNumber.setCellValueFactory(cellData ->
                        new SimpleIntegerProperty(cellData.getValue().getRoomNumber()).asObject());
    bedCount.setCellValueFactory(cellData ->
                        new SimpleIntegerProperty(cellData.getValue().getBedCount()).asObject());
    size.setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getSize()));
    llocation.setCellValueFactory(cellData -> 
                        new SimpleStringProperty(cellData.getValue().getLocation()));
    additionalInfo.setCellValueFactory(cellData -> 
                        new SimpleStringProperty(cellData.getValue().getAdditionalInfo()));
                        
    RoomManager roomManager = new RoomManager();
    ObservableList<Room> rooms = roomManager.getRoooms();
    roomsTable.setItems(rooms);

  }

}