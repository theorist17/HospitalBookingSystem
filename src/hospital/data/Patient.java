package hospital.data;

public class Patient {
	private String patientId;
	private String name;
	private int doctorID;
	
	public Patient(String patientId, String name, int doctorID) {
		super();
		this.patientId = patientId;
		this.name = name;
		this.doctorID = doctorID;
	}
	
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getDoctorID() {
		return doctorID;
	}

	public void setDoctorID(int doctorID) {
		this.doctorID = doctorID;
	}
	
}
