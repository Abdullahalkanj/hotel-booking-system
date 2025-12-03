package hotel.controllers;

import hotel.dbconnection.Coonnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
 * This class is responsible for controlling the remove user functionality in the user interface.
 */
public class RemoveUserController {

  @FXML
    private ResourceBundle resources;

  @FXML
    private URL location;

  @FXML
    private Label errorField;

  @FXML
    private Label label;

  @FXML
    private Label label1;

  @FXML
    private Button returnButton;

  @FXML
    private Button removeButtton;

  @FXML
    private TextField textField;

  @FXML
    private TextField typeField;

  @FXML
private void removeButtonsetOnAction(ActionEvent event) {
  
    Coonnection connec = new Coonnection();
    Connection connection = connec.getConnection();

    if (textField.getText().isEmpty() 
        || typeField.getText().isEmpty()) {
      showErrorAlert("Error: One or more fields are empty.");
      return;
    } 

    String username = textField.getText();
    String role = typeField.getText();

    if (!role.equalsIgnoreCase("administrator") && !role.equalsIgnoreCase("receptionist")) {
      showErrorAlert(
                "Error: Invalid role entered. Please enter 'administrator' or 'receptionist'.");
      return;
    }

    try {
      String query = "DELETE FROM User WHERE username = ? AND role = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, username);
      statement.setString(2, role);

      int rowsDeleted = statement.executeUpdate();
      if (rowsDeleted > 0) {
        showInformationAlert("A user was deleted successfully!");
      } else {
        showErrorAlert("Error: User not found or could not be deleted.");
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
      showErrorAlert("Error: An error occurred while removing the user.");
    }
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


  @FXML
private void handleReturnButtonAction(ActionEvent event) {
    try {
      // Load the previous scene
      Parent root = FXMLLoader.load(getClass().getResource("/fxml/AdminInterface.fxml"));
        
      // Create a new scene
      Scene scene = new Scene(root);

      // Get the current stage
      Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

      // Set the new scene to the current stage
      currentStage.setScene(scene);

      // Show the current stage
      currentStage.show();

    } catch (IOException e) {
      System.err.println("Error loading the previous scene: " + e.getMessage());
      e.printStackTrace();
    }
  }



  @FXML
    void initialize() {
       assert errorField != null 
    : "fx:id=\"errorField\" was not injected: "
    + "check your FXML file 'RemoveUser.fxml'.";

      assert label != null 
    : "fx:id=\"label\" was not injected: "
    + "check your FXML file 'RemoveUser.fxml'.";

      assert label1 != null 
    : "fx:id=\"label1\" was not injected: "
    + "check your FXML file 'RemoveUser.fxml'.";

      assert removeButtton != null 
    : "fx:id=\"removeButtton\" was not injected: "
    + "check your FXML file 'RemoveUser.fxml'.";

      assert textField != null 
    : "fx:id=\"textField\" was not injected: "
    + "check your FXML file 'RemoveUser.fxml'.";

      assert typeField != null 
    : "fx:id=\"typeField\" was not injected: "
    + "check your FXML file 'RemoveUser.fxml'.";

    removeButtton.setOnAction(this:: removeButtonsetOnAction);
    returnButton.setOnAction(this::handleReturnButtonAction);

  }

}
