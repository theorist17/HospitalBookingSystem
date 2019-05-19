package hospital.data;

public class Appointment extends Booking {
	private int appointmentID;
	private int doctorID;
	private int onDept;

	public Appointment(int appointmentID, String patientID, int doctorID, String timeStart, String timeEnd,
			int hasPaid, int onDept) {
		super(patientID, timeStart, timeEnd, hasPaid);
		this.appointmentID = appointmentID;
		this.doctorID = doctorID;
		this.onDept = onDept;
	}

	public Appointment(String patientID, int doctorID, String timeStart, String timeEnd, int hasPaid, int onDept) {
		super(patientID, timeStart, timeEnd, hasPaid);
		this.doctorID = doctorID;
		this.onDept = onDept;
	}

	public int getAppointmentID() {
		return appointmentID;
	}

	public void setAppointmentID(int appointmentID) {
		this.appointmentID = appointmentID;
	}

	public int getDoctorID() {
		return doctorID;
	}

	public void setDoctorID(int doctorID) {
		this.doctorID = doctorID;
	}
	

	public int getOnDept() {
		return onDept;
	}

	public void setOnDept(int onDept) {
		this.onDept = onDept;
	}


}
