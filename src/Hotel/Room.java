package Hotel;

public class Room {
	int roomId;
	int roomNumber;
	String roomType; 
	int price;
	boolean status;
	int capacity;
	String features;
	
	
	public Room() {
		
	}
	
	public Room(int roomId, int roomNumber, String roomType, int price, boolean status, int capacity, String features) {
		this.roomId = roomId;
		this.roomNumber = roomNumber;
		this.roomType = roomType;
		this.price = price;
		this.status = status;
		this.capacity = capacity;
		this.features = features;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}
	
	
	
	
	
}