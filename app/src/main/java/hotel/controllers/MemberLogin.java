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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This class is responsible for controlling the login functionality in the user interface.
 */
public class MemberLogin {

  Coonnection connec = new Coonnection();
  Connection connection = connec.getConnection();

  @FXML
   private ResourceBundle resources;

  @FXML
   private URL location;

  @FXML
   private Label lableErrorMessage;

  @FXML
   private Button loginButton;

  @FXML
   private PasswordField passwordField;

  @FXML
  private Label errorLabel;


  @FXML
   private TextField usernameField;

  @FXML
   private Button editButton;

  /**
   * Handles the action event when the Login button is clicked.
   *
   * @param e the action event triggered by the Login button
   */
  private void loginButtonOnAction(ActionEvent e) {
    String username = usernameField.getText();
    String password = passwordField.getText();

    if (username.equals("") || password.equals("")) {
      lableErrorMessage.setText("Username or Password is empty");
    } else {
      try {
        String query = "SELECT role FROM User WHERE username = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
          String role = resultSet.getString("role");

          FXMLLoader loader;
          Parent root;
          Stage stage = (Stage) loginButton.getScene().getWindow();
          Scene scene;

          switch (role) {
            case "administrator":
              // Code for when user is an admin
              loader = new FXMLLoader(getClass().getResource("/fxml/AdminInterface.fxml"));
              root = loader.load();
              scene = new Scene(root);
              stage.setScene(scene);
              break;

            case "receptionist":
              // Code for when user is reception staff
              System.out.println("Welcome");
              loader = new FXMLLoader(getClass().getResource("/fxml/receptionistRooms.fxml"));
              root = loader.load();
              scene = new Scene(root);
              stage.setScene(scene);
              break;
            default:
              break;
          }
        } else {
          lableErrorMessage.setText("Username or Password is incorrect");
        }
      } catch (Exception ex) {
        lableErrorMessage.setText(ex.getMessage());
        ex.printStackTrace();
      }
    }
  }

  private void editButtonOnAction(ActionEvent e) {

    try {
      // Load the previous scene
      Parent root = FXMLLoader.load(getClass().getResource("/fxml/updateInfo.fxml"));
        
      // Create a new scene
      Scene scene = new Scene(root);

      // Get the current stage
      Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();

      // Set the new scene to the current stage
      currentStage.setScene(scene);

      // Show the current stage
      currentStage.show();

    } catch (IOException event) {
      System.err.println("Error loading the previous scene: " + event.getMessage());
      event.printStackTrace();
    }
  }

  @FXML
  void initialize() {
    assert lableErrorMessage != null 
        : "fx:id=\"lableErrorMessage\" was not injected: "
        + "check your FXML file 'MemberLogin.fxml'.";

    assert loginButton != null 
        : "fx:id=\"loginButton\" was not injected: "
        + "check your FXML file 'MemberLogin.fxml'.";

    assert passwordField != null 
        : "fx:id=\"PasswordField\" was not injected: "
        + "check your FXML file 'MemberLogin.fxml'.";

    assert usernameField != null 
        : "fx:id=\"usernameField\" was not injected: "
        + "check your FXML file 'MemberLogin.fxml'.";

    loginButton.setOnAction(e -> loginButtonOnAction(e));
    editButton.setOnAction(e -> editButtonOnAction(e));
  }

}
