package hotel.rooms;

public class SharedModel {
  private static SharedModel instance;
  private int roomNumber;

  private SharedModel() {}

  public static SharedModel getInstance() {
    if (instance == null) {
      instance = new SharedModel();
    }
    return instance;
  }

  public void setRoomNumber(int roomNumber) {
    this.roomNumber = roomNumber;
  }

  public int getRoomNumber() {
    return this.roomNumber;
  }
}
