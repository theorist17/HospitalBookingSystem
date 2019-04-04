package jp.sw.ku;

import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.List;


public class UserInterface {
	public static void main(String[] args) {
		DBManager DBManager = new DBManager();
		DBManager.LoadDriver();
		DBManager.LoadConnection();
//		DBManager.addPatient(new Patient("9203221234567", "이홍인"));
//		DBManager.addDoctor(new Doctor("안철수", 10000));
//		DBManager.addTest(new Test("초음파", 300000));
//		DBManager.addRooms(new Room(4));
//		DBManager.addBeds(new Bed(1, 12000));
		
		String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
//		DBManager.addAppointment(new Appointment("9203221234567", 3, startTime, startTime));
//		DBManager.addCheckup(new Checkup("9203221234567", 3, startTime, startTime));
//		DBManager.addStay(new Stay("9203221234567", 3, startTime, startTime));
//		System.out.println(DBManager.getPatient("9203221234567").getName());
//		System.out.println(DBManager.getAppointment("9203221234567").timeStart);
		List<Booking> bookings = DBManager.getBookings("9203221234567");
	}
}
