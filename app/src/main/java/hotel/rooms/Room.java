package hotel.rooms;

/**
 * Represents a room with its properties and methods.
 */

public class Room {
  private int roomId;
  private String size;
  private int bedCount;
  private int roomNumber;
  private String location;
  private String additionalInfo;
  /**
   * Constructs a room object.
   *
   * @param roomId         the ID of the room
   * @param size           the size of the room
   * @param bedCount       the number of beds in the room
   * @param roomNumber     the room number
   * @param location       the location of the room
   * @param additionalInfo additional information about the room
   */

  public Room(int roomId, String size, int bedCount, int roomNumber,
               String location, String additionalInfo) {
    
    setRoomId(roomId);
    setSize(size);
    setBedCount(bedCount);
    setRoomNumber(roomNumber);
    setLocation(location);
    setAdditionalInfo(additionalInfo);

  }
  // getters..

  public int getRoomId() {
    return roomId;
  }

  public String getSize() {
    return size;
  }

  public int getBedCount() {
    return bedCount;
  }

  public int getRoomNumber() {
    return roomNumber;
  }

  public String getLocation() {
    return location;
  }

  public String getAdditionalInfo() {
    return additionalInfo;
  }
  // setters..

  public void setRoomId(int roomId) {
    this.roomId = roomId;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public void setBedCount(int bedCount) {
    if (bedCount > 0) {
      this.bedCount = bedCount;
    } else {
      throw new IllegalArgumentException("Bed count should be greater than 0.");
    }
  }

  public void setRoomNumber(int roomNumber) {
    this.roomNumber = roomNumber;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setAdditionalInfo(String additionalInfo) {
    this.additionalInfo = additionalInfo;
  }
}

