package hospital.data;

public class Stay extends Booking {
	private int stayID;
	private int bedID;

	public Stay(int stayID, String patientID, int bedID, String timeStart, String timeEnd, int hasPaid) {
		super(patientID, timeStart, timeEnd, hasPaid);
		this.stayID = stayID;
		this.bedID = bedID;
	}

	public Stay(String patientID, int bedID, String timeStart, String timeEnd, int hasPaid) {
		super(patientID, timeStart, timeEnd, hasPaid);
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
