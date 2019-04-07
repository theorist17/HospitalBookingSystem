package hospital.data;

public class Doctor {
	private int doctorId;
	private String name;
	private String department;
	private int price;

	public Doctor(int doctorId, String name, String department, int price) {
		super();
		this.doctorId = doctorId;
		this.name = name;
		this.department = department;
		this.price = price;
	}

	public Doctor(String name, int price) {
		super();
		this.name = name;
		this.price = price;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
