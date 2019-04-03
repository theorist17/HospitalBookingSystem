package jp.sw.ku;

public class Booking {
	String patientID;
	String timeStart;
	String timeEnd;
	
	public Booking(String patientID, String timeStart, String timeEnd) {
		super();
		this.patientID = patientID;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
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
}
