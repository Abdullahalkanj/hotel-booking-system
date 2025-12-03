package hotel.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
A class to establish and manage a connection to a MySQL database.
**/
public class Coonnection {
  private Connection connection;
    
  public Coonnection() {
    connectToDatabase();
  }
    
  private void connectToDatabase() {
    try {
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel",
       "root", "jack123");
       
    } catch (Exception e) {
      System.out.println("Error connecting to database: " + e.getMessage());
    }
  }
  
  public Connection getConnection() {
    return connection;
  }
}