package hotel.controllers;

import hotel.dbconnection.Coonnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * This class is responsible for controlling the add user functionality in the user interface.
 */
public class UpdateInfoController {

  @FXML
  private ResourceBundle resources;

  @FXML
  private Button returnButton;

  @FXML
  private URL location;

  @FXML
  private Button updateInfoButton;

  @FXML
  private Label instructionLabel;

  @FXML
  private Label errorLabel;
 
  @FXML
  private TextField passwordField;

  @FXML
  private TextField newPasswordField;

  @FXML
  private TextField confirmNewPasswordField;

  @FXML
  private TextField userNameField;

  @FXML
  private TextField newUserNameField;

  @FXML
  private void updateInfoButtonClicked(ActionEvent e) {
    Coonnection connec = new Coonnection();
    Connection connection = connec.getConnection();

    String username = userNameField.getText().trim();
    String newUserName = newUserNameField.getText().trim();
    String password = passwordField.getText().trim();
    String newPassword = newPasswordField.getText().trim();
    String confirmNewPassword = confirmNewPasswordField.getText().trim();

    if (null == username || "".equals(username)) {
      errorLabel.setText("Please input old username.");
      errorLabel.setVisible(true);
    } else if (null == password || "".equals(password)) {
      errorLabel.setText("Please input old password.");
      errorLabel.setVisible(true);
    } else if (null == newUserName || "".equals(newUserName)) {
      errorLabel.setText("Please input new userName.");
      errorLabel.setVisible(true);
    } else if (null == newPassword || "".equals(newPassword)) {
      errorLabel.setText("Please input new password.");
      errorLabel.setVisible(true);
    } else if (null == confirmNewPassword || "".equals(confirmNewPassword)) {
      errorLabel.setText("Please confirm new password.");
      errorLabel.setVisible(true);
    } else if (!confirmNewPassword.equals(newPassword)) {
      errorLabel.setText("The new passwords entered are inconsistent.");
      errorLabel.setVisible(true);
    } else {
      String query = "SELECT * FROM User WHERE username = ? AND password = ?";
      try {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
          query = "update User set username=?, password=? where username=? and password=? ";
          PreparedStatement pstmt = connection.prepareStatement(query);
          errorLabel.setVisible(false);
          pstmt.setString(1, newUserName);
          pstmt.setString(2, newPassword);
          pstmt.setString(3, username);
          pstmt.setString(4, password);
          errorLabel.setText(" Update info successfully!");
          errorLabel.setVisible(true);
          pstmt.executeUpdate();

        } else {
          errorLabel.setText("Username or Password is incorrect");
        }

      } catch (Exception ex) {
        ex.printStackTrace();
        errorLabel.setText("Error: " + ex.getMessage());
        errorLabel.setVisible(true);
      }

    }
  }

  @FXML
  private void handleReturnButtonAction(ActionEvent event) {
    try {
      // Load the previous scene
      Parent root = FXMLLoader.load(getClass().getResource("/fxml/MemberLogin.fxml"));

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
    assert updateInfoButton != null
    : "fx:id=\"updateInfoButton\" was not injected: check your FXML file 'addUserInterface.fxml'.";

    assert instructionLabel != null
    : "fx:id=\"instructionLabel\" was not injected: "
    + "check your FXML file 'addUserInterface.fxml'.";

    assert userNameField != null
    : "fx:id=\"userNameField\" was not injected: "
    + "check your FXML file 'addUserInterface.fxml'.";

    assert passwordField != null
    : "fx:id=\"passwordField\" was not injected: "
    + "check your FXML file 'addUserInterface.fxml'.";
    assert newPasswordField != null
    : "fx:id=\"newPasswordField\" was not injected: "
    + "check your FXML file 'addUserInterface.fxml'.";
    assert confirmNewPasswordField != null
    : "fx:id=\"confirmNewPasswordField\" was not injected: "
    + "check your FXML file 'addUserInterface.fxml'.";

    updateInfoButton.setOnAction(this::updateInfoButtonClicked);
    returnButton.setOnAction(this::handleReturnButtonAction);

  }

}
