package jp.sw.ku;

public class Stay extends Booking {
	int stayID;
	int bedID;
	
	public Stay(String patientID, int bedID, String timeStart, String timeEnd) {
		super(patientID, timeStart, timeEnd);
		this.bedID = bedID;
	}
	public int getStayID() {
		return stayID;
	}
	public void setStayID(int stayID) {
		this.stayID = stayID;
	}
	public int getBedID() {
		return bedID;
	}
	public void setBedID(int bedID) {
		this.bedID = bedID;
	}
}
