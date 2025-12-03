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
 * This class is responsible for controlling the add user functionality in the user interface.
 */
public class AddUserInterfaceController {

  @FXML
    private ResourceBundle resources;

  @FXML
    private Button returnButton;

  @FXML
    private URL location;

  @FXML
    private Button addButton;

  @FXML
    private Label instructionLabel;

  @FXML
  private Label errorLabel;

  @FXML
    private TextField passwordField;

  @FXML
    private TextField typeField;

  @FXML
    private TextField userNameField;

  @FXML
  private void addButtonClicked(ActionEvent e) {
    Coonnection connec = new Coonnection();
    Connection connection = connec.getConnection();

    String role = typeField.getText().trim().toLowerCase();
    String username = userNameField.getText().trim();
    String password = passwordField.getText().trim();
    if (role.equals("administrator") || role.equals("receptionist")) {
      try {
        String query = "INSERT INTO User (username, password, role) VALUES (?, ?, ?);";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        pstmt.setString(3, role);
        pstmt.executeUpdate();
          
        showInformationAlert("User added successfully!");
      } catch (SQLException sqle) {
        showErrorAlert("Error: " + sqle.getMessage());
      }
    } else {
      // If the type isn't "admin" or "reception staff", clear the type field
      typeField.clear();
      showWarningAlert("Invalid user type entered.");
    }
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
        assert addButton != null 
        : "fx:id=\"addButton\" was not injected: check your FXML file 'addUserInterface.fxml'.";

        assert instructionLabel != null 
        : "fx:id=\"instructionLabel\" was not injected: "
        + "check your FXML file 'addUserInterface.fxml'.";

        assert passwordField != null 
        : "fx:id=\"passwordField\" was not injected: "
        + "check your FXML file 'addUserInterface.fxml'.";

        assert typeField != null 
        : "fx:id=\"typeField\" was not injected: "
        + "check your FXML file 'addUserInterface.fxml'.";

        assert userNameField != null 
        : "fx:id=\"userNameField\" was not injected: "
        + "check your FXML file 'addUserInterface.fxml'.";
        
    addButton.setOnAction(this::addButtonClicked);
    returnButton.setOnAction(this::handleReturnButtonAction);


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
