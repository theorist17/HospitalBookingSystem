package jp.sw.ku;

class Room {
	int roomID;
	int capacity;
	
	public Room(int capacity) {
		super();
		this.capacity = capacity;
	}
	public int getRoomID() {
		return roomID;
	}
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
}
