package hotel.controllers;

import hotel.rooms.RoomManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This class is responsible for controlling the remove room interface.
 */
public class RemoveRoomController {

  @FXML
    private ResourceBundle resources;

  @FXML
    private URL location;

  @FXML
    private Label errorLabel;

  @FXML
    private Button removeButton;

  @FXML
    private Button returnButton;

  @FXML
    private TextField roomNumber;

  private RoomManager roomManager;




  private void removeButtonClicked(ActionEvent e) {
    if (roomNumber.getText().isEmpty()) {
      showWarningAlert("Please enter room number");
    } else {
      try {
        int number = Integer.parseInt(roomNumber.getText());
        boolean result = roomManager.removeRoomByNumber(number);
        if (result) {
          showInformationAlert("Room successfully removed!");
        } else {
          showErrorAlert("Error while removing room!");
        }
      } catch (NumberFormatException ee) {
        showErrorAlert("Invalid room number!");
      }
    }
  }

  @FXML
  private void handleReturnButtonAction(ActionEvent event) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/fxml/AdminInterface.fxml"));
      Scene scene = new Scene(root);
      Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      currentStage.setScene(scene);
      currentStage.show();
    } catch (IOException e) {
      showErrorAlert("Error loading the previous scene: " + e.getMessage());
    }
  }






  @FXML
    void initialize() {
        assert errorLabel != null 
        : "fx:id=\"errorLabel\" was not injected: check your FXML file 'RemoveRoom.fxml'.";
        assert removeButton != null
        : "fx:id=\"removeButton\" was not injected: check your FXML file 'RemoveRoom.fxml'.";
        assert returnButton != null 
        : "fx:id=\"returnButton\" was not injected: check your FXML file 'RemoveRoom.fxml'.";
        assert roomNumber != null 
        : "fx:id=\"roomNumber\" was not injected: check your FXML file 'RemoveRoom.fxml'.";

    errorLabel.setVisible(false);
    removeButton.setOnAction(e -> removeButtonClicked(e));
    returnButton.setOnAction(this::handleReturnButtonAction);
    roomManager = new RoomManager(); // Instantiate roomManager


  }

  private void showInformationAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  private void showErrorAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  private void showWarningAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Warning");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
