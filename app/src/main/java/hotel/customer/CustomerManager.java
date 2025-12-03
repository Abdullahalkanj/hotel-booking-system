package hotel.customer;

import hotel.dbconnection.Coonnection;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages customer-related operations with the database.
 *
 * @class CustomerManager
*/
public class CustomerManager { 
  // Class responsible for establishing and managing the database connection
  private Coonnection coonnection;
  private Connection connection;


  /**
   * Constructor for the CustomerManager class. Initializes the database connection.
   */
  public CustomerManager() {
    try {
      coonnection = new Coonnection();
      connection = coonnection.getConnection();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
  /**
   * Inserts a new customer record into the database.
   *
   * @param name The name of the customer.
   * @param address The address of the customer.
   * @param payment The payment method of the customer.
   * @param note Additional information or notes about the customer (can be null or empty).
   */

 public void insertCustomerData(String name, Integer customerNum, String address,
                                String payment, String note) {
    String query = "INSERT INTO customer (Name, customer_number, Address, Payment_Method,"
                    + "Additional_Info) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, name);
        statement.setInt(2, customerNum);
        statement.setString(3, address);
        statement.setString(4, payment);
        // Check if 'note' is provided and not empty
        if (note != null && !note.trim().isEmpty()) {
            statement.setString(5, note);
        } else {
            // If 'note' is not provided or is empty, set it as NULL in the database
            statement.setNull(5, java.sql.Types.VARCHAR);
        }
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            showInformationAlert("Customer created successfully!");
        } else {
            showWarningAlert("No data was inserted. Please check the provided information.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        showErrorAlert("An error occurred while inserting customer data: " + e.getMessage());
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

private void showWarningAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Warning");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
  /**
   * Updates an existing customer record in the database with new information.
   *
   * @param id The unique identifier of the customer record to be updated.
   * @param newName The new name of the customer.
   * @param newAddress The new address of the customer.
   * @param newPayment The new payment method of the customer.
   */

  public void editCustomerData(int id, String newName, Integer customerNum, String newAddress,
       String newPayment, String newNote) {
    String query = "UPDATE customer SET name = ?, customer_number = ?,  address = ?,"
                  + "payment_Method = ?, additional_Info = ? WHERE customer_id = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, newName);
      statement.setInt(2, customerNum);
      statement.setString(3, newAddress);
      statement.setString(4, newPayment);
      //Check if 'newNote' is provided and not empty
      if (newNote != null && !newNote.trim().isEmpty()) {
        statement.setString(5, newNote);
      } else {
        // If 'newNote' is not provided or is empty, set it as NULL in the database
        statement.setNull(5, java.sql.Types.VARCHAR);
      }
      // Set the ID for the WHERE clause
      statement.setInt(6, id);
      statement.executeUpdate();
    } catch (SQLException e) {
      // Show error alert if there's an SQLException
      showErrorAlert("Failed to update customer data: " + e.getMessage());
      e.printStackTrace();
    }
  }

   /**
   * Retrieves all customer records from the database and returns them as alist of Customer objects.
   *
   * @return A list of Customer objects representing all customers in the database.
   *          If there are no customers, an empty list is returned.
   * @precondition The connection to the database must be established.
   * @postcondition A list of Customer objects is returned, each representing
   *                 a customer record in the database. The connection to the database remains open.
   * @throws SQLException If an error occurs while executing the query or processing the result set.
   */

   public List<Customer> getAllCustomers() {
    List<Customer> customers = new ArrayList<>();
    String query = "SELECT * FROM customer";
    try (PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        String name = resultSet.getString("Name");
        String address = resultSet.getString("Address");
        String payment = resultSet.getString("Payment_Method");
        String note = resultSet.getString("Additional_Info");
        Integer id = resultSet.getInt("customer_id");
        Integer customerNum = resultSet.getInt("customer_number");

        // Matching the order to the constructor's order
        Customer customer = new Customer(id, name, address, payment, note, customerNum);
        customers.add(customer);
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return customers;
  }


  /**
   * Closes the connection to the database if it is currently open.
   *
   * @precondition The connection object must be initialized,
   *               either as an open connection or as null.
   * @postcondition If the connection was open, it is closed;
   *           if it was already closed or null, no changes are made.
   * @throws SQLException If an error occurs while attempting to close the connection.
   */

  public void closeConnection() {
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}