package jp.sw.ku;

class Bed {
	int bedID;
	int roomID;
	int price;
	
	public Bed(int roomID, int price) {
		super();
		this.roomID = roomID;
		this.price = price;
	}
	public int getBedID() {
		return bedID;
	}
	public void setBedID(int bedID) {
		this.bedID = bedID;
	}
	public int getRoomID() {
		return roomID;
	}
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}

