package hospital.data;

public class Booking {
	private String patientID;
	private String timeStart;
	private String timeEnd;
	private int hasPaid;

	public Booking(String patientID, String timeStart, String timeEnd, int hasPaid) {
		super();
		this.patientID = patientID;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.hasPaid = hasPaid;
	}

	public String getPatientID() {
		return patientID;
	}

	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public int getHasPaid() {
		return hasPaid;
	}

	public void setHasPaid(int hasPaid) {
		this.hasPaid = hasPaid;
	}

}
