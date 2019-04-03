package jp.sw.ku;

class Doctor {
	int doctorId;
	String name;
	int price;
	
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
}
