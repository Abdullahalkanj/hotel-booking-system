package hotel.customer;

/**
 * Represents a customer with details such as name, address, payment method, and additional notes.
 * 
 */
public class Customer {
  private Integer id;
  private String name;
  private Integer customerNum;
  private String address;
  private String payment;
  private String note;


  /**
 * Constructs a new Customer object with the specified ID
 * , name, address, payment method, and additional notes.
 *
 * @param id      The unique identifier for the customer.
 * @param name    The name of the customer.
 * @param address The address of the customer.
 * @param payment The payment method used by the customer.
 * @param note    Additional notes related to the customer.
 */
  public Customer(Integer id, String name, String address, String payment,
                 String note, Integer customerNum) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.payment = payment;
    this.note = note;
    this.customerNum = customerNum;
  
  }


  // Getters and setters (you can generate them automatically in most IDEs)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getCustomerNum() {
    return customerNum;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPayment() {
    return payment;
  }

  public void setPayment(String payment) {
    this.payment = payment;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
}