package jp.sw.ku;

public class Checkup extends Booking {
	int checkupID;
	int testID;
	
	public Checkup(int checkupID, String patientID, int testID, String timeStart, String timeEnd, int hasPaid) {
		super(patientID, timeStart, timeEnd, hasPaid);
		this.checkupID = checkupID;
		this.testID = testID;
	}
	public Checkup(String patientID, int testID, String timeStart, String timeEnd, int hasPaid) {
		super(patientID, timeStart, timeEnd, hasPaid);
		this.testID = testID;
	}
	public int getCheckupID() {
		return checkupID;
	}
	public void setCheckupID(int checkupID) {
		this.checkupID = checkupID;
	}
	public int getTestID() {
		return testID;
	}
	public void setTestID(int testID) {
		this.testID = testID;
	}
}
