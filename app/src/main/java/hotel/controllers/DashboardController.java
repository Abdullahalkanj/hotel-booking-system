package hotel.controllers;

import hotel.customer.Customer;
import hotel.customer.CustomerManager;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DashboardController {

  @FXML
    private ResourceBundle resources;

  @FXML
    private URL location;

  @FXML
    private TableColumn<Customer, String> address;

  @FXML
  private boolean isEditing = false;

  @FXML
  private Customer selectedCustomer;

  @FXML
    private Button createButton1;

  @FXML
    private ButtonBar customerButtonBar1;

  @FXML
    private Button customerButtonCancel1;

  @FXML
    private Button customerButtonEdit1;

  @FXML
    private Button customerButtonOK1;

  @FXML
    private TextField customerInputAddress1;

  @FXML
    private TextField customerInputName1;

  @FXML
    private TextField customerInputNote1;

  @FXML
    private TextField customerInputPayment1;

  @FXML
    private TableColumn<Customer, Integer> customerNum;

  @FXML
    private TextField customerNumberField;

  @FXML
    private TableView<Customer> customerResult;

  @FXML
    private Label errorLabael;

  @FXML
    private Label errorLabel1;

  @FXML
    private Button homeButton;

  @FXML
    private TableColumn<Customer, String> name;

  @FXML
    private TableColumn<Customer, String> note;

  @FXML
    private TableColumn<Customer, String> payment1;

  @FXML
    private AnchorPane statusBar;

  @FXML
    private Button removeButton;


  @FXML
  void handleCustomerButtonCancel(ActionEvent event) {
    try {
      // Clear the input fields when "Cancel" button is clicked
      customerInputName1.clear();
      customerInputAddress1.clear();
      customerInputPayment1.clear();
      customerInputNote1.clear();
      customerNumberField.clear();
        
      // If the clearing is successful, show an information alert
      showInformationAlert("All fields have been cleared successfully!");

    } catch (Exception e) {
      // If there's an exception while clearing the fields, show an error alert
      showErrorAlert("An error occurred while clearing the fields. Please try again.");
    }
  }

  @FXML
  void handleCustomerButtonCreate(ActionEvent event) {
    String name = customerInputName1.getText();
    String address = customerInputAddress1.getText();
    String payment = customerInputPayment1.getText();
    String note = customerInputNote1.getText();
    String customerNumStr = customerNumberField.getText();

    // Check if any of the fields are empty
    if (name.trim().isEmpty() || address.trim().isEmpty()
        || payment.trim().isEmpty() || note.trim().isEmpty() || customerNumStr.trim().isEmpty()) {
      showWarningAlert("Please fill in all the fields before creating a customer.");
      return; // Exit the method without performing further actions
    }

    // Assuming you want to continue with the rest of the method after the check:
    try {
      Integer customerNum = Integer.parseInt(customerNumStr);

      // Call the insertCustomerData method from DatabaseManager to add the data to the database
      CustomerManager csManager = new CustomerManager();
      csManager.insertCustomerData(name, customerNum, address, payment, note);

      // Clear the input fields after adding the data to the database
      customerInputName1.clear();
      customerInputAddress1.clear();
      customerInputPayment1.clear();
      customerInputNote1.clear();
      customerNumberField.clear();
      customerResult.refresh();
      List<Customer> customers = csManager.getAllCustomers();
      // Update the observable list
      ObservableList<Customer> customerList = FXCollections.observableArrayList(customers);
      // Set the updated data to the table view
      customerResult.setItems(customerList);

      // Refresh the table view
      customerResult.refresh();

    } catch (NumberFormatException e) {
      // Handle the case where the customer number is not a valid integer
      showErrorAlert("Please enter a valid customer number.");
    } catch (Exception e) {
      // If there's any other exception, show an error alert
      showErrorAlert("An error occurred while creating the customer. Please try again.");
    }
  }


  @FXML
  void handleCustomerButtonEdit(ActionEvent event) {
    // Get the selected customer from the table view
    selectedCustomer = customerResult.getSelectionModel().getSelectedItem();

    // Check if a customer is selected
    if (selectedCustomer != null) {
      // Enable the TextArea for editing
      //customerInputFullInfo.setEditable(true);

      // Store the original customer before editing
      isEditing = true;

      // Populate the text fields with the selected customer's data
      customerInputName1.setText(selectedCustomer.getName());
      customerInputAddress1.setText(selectedCustomer.getAddress());
      customerInputPayment1.setText(selectedCustomer.getPayment());
      customerInputNote1.setText(selectedCustomer.getNote());
      customerNumberField.setText(String.valueOf(selectedCustomer.getCustomerNum()));

    } else {
      // Handle the case where no customer is selected (e.g., show an error message)
      showErrorAlert("No customer selected for editing.");
    }
  }


  @FXML
  void handleCustomerButtonoK(ActionEvent event) {
    if (isEditing && selectedCustomer != null) {
      try {
        // Get the updated values from the text fields
        String newName = customerInputName1.getText();
        String newAddress = customerInputAddress1.getText();
        String newPayment = customerInputPayment1.getText();
        String newNote = customerInputNote1.getText();
        Integer customerNum = Integer.parseInt(customerNumberField.getText());
        // Get the selected customer's ID
        int customerId = selectedCustomer.getId();
        // Create an instance of CustomerManager
        CustomerManager csManager = new CustomerManager();
        // Call the editCustomerData method to update the customer in the database
        csManager.editCustomerData(
              customerId, newName, customerNum, newAddress, newPayment, newNote);

        // Refresh the table view to reflect the changes
        ObservableList<Customer> customerList = 
            FXCollections.observableArrayList(csManager.getAllCustomers());
        customerResult.setItems(customerList);

        // Reset the editing state
        isEditing = false;
        selectedCustomer = null;
        // Clear the input fields
        customerInputName1.clear();
        customerInputAddress1.clear();
        customerInputPayment1.clear();
        customerInputNote1.clear();
        customerNumberField.clear();

        // Show an informational alert to confirm the successful update
        showInformationAlert("Customer data updated successfully!");

      } catch (NumberFormatException e) {
        // Handle potential parsing errors
        showErrorAlert("Invalid customer number entered!");
      } catch (Exception e) {
        // Generic error handling for unexpected exceptions
        showErrorAlert("An error occurred: " + e.getMessage());
      }

    } else {
      // Handle the case where the user tries to click the OK button without selecting a customer
      showWarningAlert("Please select a customer before attempting to save changes.");
    }
  }

  @FXML
    void handleHomeButtonClick(ActionEvent event) {
    try {
      // Load the fxml file for the main screen
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/receptionistRooms.fxml"));
      Parent root = loader.load();

      // Get current stage and set the new scene
      Stage stage = (Stage) homeButton.getScene().getWindow();
      stage.setScene(new Scene(root));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @FXML
    void initialize() {
        assert address != null : "fx:id=\"address\" was not injected: " 
                        + "check your FXML file 'dashboard.fxml'.";
        assert createButton1 != null : "fx:id=\"createButton1\" was not injected: " 
                            +   "check your FXML file 'dashboard.fxml'.";
        assert customerButtonBar1 != null : "fx:id=\"customerButtonBar1\" was not injected: " 
                                +   "check your FXML file 'dashboard.fxml'.";
        assert customerButtonCancel1 != null : "fx:id=\"customerButtonCancel1\" was not injected: " 
                                    +   "check your FXML file 'dashboard.fxml'.";
        assert customerButtonEdit1 != null : "fx:id=\"customerButtonEdit1\" was not injected: " 
                                    + "check your FXML file 'dashboard.fxml'.";
        assert customerButtonOK1 != null : "fx:id=\"customerButtonOK1\" was not injected: " 
                                 +  "check your FXML file 'dashboard.fxml'.";
        assert customerInputAddress1 != null : "fx:id=\"customerInputAddress1\" was not injected: " 
                                     +  "check your FXML file 'dashboard.fxml'.";
        assert customerInputName1 != null : "fx:id=\"customerInputName1\" was not injected: " 
                                 +   "check your FXML file 'dashboard.fxml'.";
        assert customerInputNote1 != null : "fx:id=\"customerInputNote1\" was not injected: " 
                                +    "check your FXML file 'dashboard.fxml'.";
        assert customerInputPayment1 != null : "fx:id=\"customerInputPayment1\" was not injected: " 
                                    +   "check your FXML file 'dashboard.fxml'.";
        assert customerNum != null : "fx:id=\"customerNum\" was not injected: " 
                           +  "check your FXML file 'dashboard.fxml'.";
        assert customerNumberField != null : "fx:id=\"customerNumberField\" was not injected: " 
                                   +  "check your FXML file 'dashboard.fxml'.";
        assert customerResult != null : "fx:id=\"customerResult\" was not injected: " 
                               + "check your FXML file 'dashboard.fxml'.";
        assert errorLabael != null : "fx:id=\"errorLabael\" was not injected: " 
                            + "check your FXML file 'dashboard.fxml'.";
        assert errorLabel1 != null : "fx:id=\"errorLabel1\" was not injected: " 
                            + "check your FXML file 'dashboard.fxml'.";
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: " 
                         +   "check your FXML file 'dashboard.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: " 
                    +  "check your FXML file 'dashboard.fxml'.";
        assert note != null : "fx:id=\"note\" was not injected: " 
                    +  "check your FXML file 'dashboard.fxml'.";
        assert payment1 != null : "fx:id=\"payment1\" was not injected: " 
                        +  "check your FXML file 'dashboard.fxml'.";
        assert statusBar != null : "fx:id=\"statusBar\" was not injected: " 
                         +  "check your FXML file 'dashboard.fxml'.";

        assert removeButton != null : "fx:id=\"removeButton\" was not injected: check "
                         + "your FXML file 'dashboard.fxml'.";
    // Initialize the TableView and columns
    name.setCellValueFactory(
           cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    address.setCellValueFactory(
          cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
    payment1.setCellValueFactory(
         cellData -> new SimpleStringProperty(cellData.getValue().getPayment()));
    note.setCellValueFactory(
         cellData -> new SimpleStringProperty(cellData.getValue().getNote()));
    customerNum.setCellValueFactory(
         cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCustomerNum()));
    // Fetch data from MySQL
    CustomerManager csManager = new CustomerManager();
    List<Customer> customers = csManager.getAllCustomers();
    // Set the data to the TableView
    ObservableList<Customer> customerList = 
         FXCollections.observableArrayList(customers); // Initialize the customerList
    customerResult.setItems(customerList);
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