package hotel.controllers;

import hotel.dbconnection.Coonnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
/**
 * This class is responsible for controlling the login functionality in the user interface.
 */

public class StartController {

  Coonnection connec = new Coonnection();
  Connection connection = connec.getConnection();
  
  @FXML
  private ResourceBundle resources;
  
  @FXML
  private URL location;
  
  @FXML
  private Label lableErrorMessage;

  @FXML
  private Button startButton;

  private void startButtonOnAction(ActionEvent e) {
    FXMLLoader loader;
    Parent root;
    Stage stage = (Stage) startButton.getScene().getWindow();
    Scene scene;
    loader = new FXMLLoader(getClass().getResource("/fxml/MemberLogin.fxml"));
    try {
      root = loader.load();
      scene = new Scene(root);
      stage.setScene(scene);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @FXML
    void initialize() {
    assert lableErrorMessage != null
    : "fx:id=\"lableErrorMessage\" was not injected: "
    + "check your FXML file 'MemberLogin.fxml'.";
    assert startButton != null
    : "fx:id=\"startButton\" was not injected: "
    + "check your FXML file 'MemberLogin.fxml'.";
    startButton.setOnAction(e -> startButtonOnAction(e));
  }
}
