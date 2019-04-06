package jp.sw.ku;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

	Connection DBConn = null;

	public void LoadDriver() {

		try {
			// The newInstance() call is a work around for some
			// broken Java implementations
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			log("MySQL JDBC Driver Registered");

		} catch (Exception ex) {
			log("Sorry, couldn't found JDBC driver. Make sure you have added JDBC driver.");
			ex.printStackTrace();
		}

	}

	public void LoadConnection() {
		try {
			DBConn = DriverManager
					.getConnection("jdbc:mysql://1.240.123.168:3306/hospital?" + "user=dev&password=MySQL!=1");

			if (DBConn != null) {
				log("Connection Successful! Now it's time to push data.");
			} else {
				log("Failed to make connection!");
			}
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
		}
	}

	public String addPatient(Patient patient) {
		try {
			PreparedStatement statement = DBConn
					.prepareStatement("INSERT INTO patients (patientID, name) VALUES (?, ?)");
			statement.setString(1, patient.getPatientId());
			statement.setString(2, patient.getName());

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("INSERT failed, no rows affected.");
			} else {

			}
			log("Patient " + patient.getPatientId() + " added successfully.");
			return patient.getPatientId();
		} catch (SQLIntegrityConstraintViolationException ve) {
			log("Duplicate patient ID.");
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}
		return null;
	}

	public int addDoctor(Doctor doctor) {
		try {
			PreparedStatement statement = DBConn.prepareStatement("INSERT INTO doctors (name, price) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, doctor.getName());
			statement.setInt(2, doctor.getPrice());

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("INSERT failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int ID = generatedKeys.getInt(1);
					doctor.setDoctorId(ID);
					log("Doctor " + ID + " added successfully.");
					return ID;
				} else {
					throw new SQLException("INSERT failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}

		return -1;
	}

	public int addTest(Test test) {
		try {
			PreparedStatement statement = DBConn.prepareStatement("INSERT INTO tests (name, price) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, test.getName());
			statement.setInt(2, test.getPrice());

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("INSERT failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int ID = generatedKeys.getInt(1);
					test.setTestID(ID);
					log("Test " + ID + " added successfully.");
					return ID;
				} else {
					throw new SQLException("INSERT failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}

		return -1;
	}

	public int addRooms(Room room) {
		try {
			PreparedStatement statement = DBConn.prepareStatement("INSERT INTO rooms (capacity) VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, room.getCapacity());

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("INSERT failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int ID = generatedKeys.getInt(1);
					room.setRoomID(ID);
					log("Room " + ID + " added successfully.");
					return ID;
				} else {
					throw new SQLException("INSERT failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}

		return -1;
	}

	public int addBeds(Bed bed) {
		try {
			PreparedStatement statement = DBConn.prepareStatement("INSERT INTO beds (roomID, price) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, bed.getRoomID());
			statement.setInt(2, bed.getPrice());

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("INSERT failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int ID = generatedKeys.getInt(1);
					bed.setBedID(ID);
					log("Bed " + ID + " added successfully.");
					return ID;
				} else {
					throw new SQLException("INSERT failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}

		return -1;
	}
	
	public int addAppointment(Appointment appointment) {
		try {
			PreparedStatement statement = DBConn.prepareStatement("INSERT INTO appointments (patientID, doctorID, timeStart, timeEnd) VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, appointment.getPatientID());
			statement.setInt(2, appointment.getDoctorID());
			statement.setString(3, appointment.getTimeStart());
			statement.setString(4, appointment.getTimeEnd());

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("INSERT failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int ID = generatedKeys.getInt(1);
					appointment.setAppointmentID(ID);
					log("Appointment " + ID + " added successfully.");
					return ID;
				} else {
					throw new SQLException("INSERT failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}
		return -1;
	}

	public int addCheckup(Checkup checkup) {
		try {
			PreparedStatement statement = DBConn.prepareStatement("INSERT INTO checkups (patientID, testID, timeStart, timeEnd) VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, checkup.getPatientID());
			statement.setInt(2, checkup.getTestID());
			statement.setString(3, checkup.getTimeStart());
			statement.setString(4, checkup.getTimeEnd());

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("INSERT failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int ID = generatedKeys.getInt(1);
					checkup.setCheckupID(ID);
					log("Checkup " + ID + " added successfully.");
					return ID;
				} else {
					throw new SQLException("INSERT failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}
		return -1;
	}
	
	public int addStay(Stay stay) {
		try {
			PreparedStatement statement = DBConn.prepareStatement("INSERT INTO stays (patientID, bedID, timeStart, timeEnd) VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, stay.getPatientID());
			statement.setInt(2, stay.getBedID());
			statement.setString(3, stay.getTimeStart());
			statement.setString(4, stay.getTimeEnd());

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("INSERT failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int ID = generatedKeys.getInt(1);
					stay.setBedID(ID);
					log("Stay " + ID + " added successfully.");
					return ID;
				} else {
					throw new SQLException("INSERT failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}
		return -1;
	}
	
	public Appointment getAppointment(String patientId) {
		try {
			PreparedStatement statement = DBConn.prepareStatement("SELECT * FROM patients p, appointments a WHERE p.patientID = ? and p.patientID = a.patientID");
			statement.setString(1, patientId);

			try (ResultSet resultSet = statement.executeQuery()) {
				
				Appointment appointment = null;
				while(resultSet.next()) {
					int appointmentId = resultSet.getInt("appointmentId");
					patientId = resultSet.getString("patientId");
					int doctorId = resultSet.getInt("doctorId");
					String timeStart = resultSet.getString("timeStart");
					String timeEnd = resultSet.getString("timeEnd");
					appointment = new Appointment(appointmentId, patientId, doctorId, timeStart, timeEnd, 0);
					return appointment;
				}
			}
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}
		return null;
	}
	
	public Patient getPatient(String patientID) {
		try {
			PreparedStatement statement = DBConn.prepareStatement("SELECT * FROM patients WHERE patientID = ?");
			statement.setString(1, patientID);

			try (ResultSet resultSet = statement.executeQuery()) {
				
				Patient patient = null;
				while(resultSet.next()) {
					patientID = resultSet.getString("patientID");
					String name = resultSet.getString("name");
					patient = new Patient(patientID, name);
					return patient;
				}
			}
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}
		return null;
	}
	public Doctor getDoctor(int doctorID) {
		try {
			PreparedStatement statement = DBConn.prepareStatement("SELECT * FROM doctors WHERE doctorID = ?");
			statement.setInt(1, doctorID);

			try (ResultSet resultSet = statement.executeQuery()) {
				
				Doctor doctor = null;
				while(resultSet.next()) {
					doctorID = resultSet.getInt("doctorID");
					String name = resultSet.getString("name");
					String department = resultSet.getString("department");
					int price = resultSet.getInt("price");
					doctor = new Doctor(doctorID, name, department, price);
					return doctor;
				}
			}
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Booking> getBookings(String patientID) {
		try {
			PreparedStatement statement = DBConn.prepareStatement(
					"SELECT  *, 'appointment' as Source\n" + 
					"FROM appointments\n" + 
					"where hasPaid = 0 and patientID = ?\n" + 
					"union \n" + 
					"SELECT *, 'checkup' as Source\n" + 
					"FROM checkups \n" + 
					"where hasPaid = 0 and patientID = ?\n" + 
					"union\n" + 
					"SELECT *, 'stay' as Source\n" + 
					"FROM stays \n" + 
					"where hasPaid = 0 and patientID = ?;");
			statement.setString(1, patientID);
			statement.setString(2, patientID);
			statement.setString(3, patientID);

			try (ResultSet resultSet = statement.executeQuery()) {
				
				Patient patient = null;
				List<Booking> bookings = new ArrayList<Booking>();
				
				while(resultSet.next()) {
					String bookingType = resultSet.getString("Source");
					
					//getting attributes for all bookings
					int bookingID = resultSet.getInt(1);
					patientID = resultSet.getString("patientID");
					int resourceID = resultSet.getInt(3);
					String timeStart = resultSet.getString("timeStart");
					String timeEnd = resultSet.getString("timeEnd");
					int hasPaid = resultSet.getInt("hasPaid");
					
					if(bookingType.equals("appointment")) { 
						Appointment appointment = new Appointment(bookingID, patientID, resourceID, timeStart, timeEnd, hasPaid);
						bookings.add((Booking)appointment);
					} else if(bookingType.equals("checkup")) { 
						Checkup checkup = new Checkup(bookingID, patientID, resourceID, timeStart, timeEnd, hasPaid);
						bookings.add((Booking)checkup);
					} else if(bookingType.equals("stay")) { 
						Stay stay = new Stay(bookingID, patientID, resourceID, timeStart, timeEnd, hasPaid);
						bookings.add((Booking)stay);
					} 
				}
				return bookings;
			}
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}
		return null;
	}

	boolean setHaspaid(Patient patient, List<Booking> bookings, int lineNo) { // 입력값과, 부킹리스트를 매개변수로 받아
		try { // 시도하자
			PreparedStatement statement;
			// 디비에있는것만 바꿔주자.
			// apo che 아이디만 갖으면 되니깐
			// 스트링체커도 다 하고있음.
			// 입력된 patient의 주민 == 디비의 주민
			if (bookings.get(lineNo - 1) instanceof Appointment) {
				Appointment appointment = (Appointment) bookings.get(lineNo - 1);
				statement = DBConn.prepareStatement("UPDATE appointments SET hasPaid=1 WHERE appointmentID=?;",
						Statement.RETURN_GENERATED_KEYS);
				statement.setInt(1, appointment.getAppointmentID());
			} else if (bookings.get(lineNo - 1) instanceof Checkup) {
				Checkup checkup = (Checkup) bookings.get(lineNo - 1);
				statement = DBConn.prepareStatement("UPDATE checkups SET hasPaid=1 WHERE checkupID=?;",
						Statement.RETURN_GENERATED_KEYS);
				statement.setInt(1, checkup.getCheckupID());
			} else {
				Stay stay = (Stay) bookings.get(lineNo - 1);
				statement = DBConn.prepareStatement("UPDATE stays SET hasPaid=1 WHERE stayID=?;",
						Statement.RETURN_GENERATED_KEYS);
				statement.setInt(1, stay.getStayID());
			}

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("UPDATE haspaid failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					bookings.get(lineNo - 1).setHasPaid(1);
					return true;
				} else {
					throw new SQLException("UPDATE haspaid failed, no ID obtained.");
				}
			}

		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}
		return false;
	}

	public void printBookings(String patientID) {
		List<Booking> bookings = getBookings(patientID);
		for (int i = 0 ; i  < bookings.size(); i++) {
			if(bookings.get(i) instanceof Appointment) {
				Appointment appointment = (Appointment) bookings.get(i);
				System.out.println((i+1) + " Appointment, " + appointment.getDoctorID() + "&"+
						getDoctor(appointment.getDoctorID()).getName()+ ", "+
						appointment.getTimeStart()+", "+appointment.getTimeEnd());
			}
		}
	}
	
	public static void log(String string) {
		System.err.println(string);
	}
}
