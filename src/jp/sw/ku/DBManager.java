package jp.sw.ku;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class DBManager {

	Connection DBConn = null;

	void LoadDriver() {

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

	void LoadConnection() {
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

	/**
	 * @param patient
	 * @return null if duplicate patient ID
	 */
	String addPatient(Patient patient) {
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

	int addDoctor(Doctor doctor) {
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

	int addTest(Test test) {
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

	int addRooms(Room room) {
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

	int addBeds(Bed bed) {
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
	
	int addAppointment(Appointment appointment) {
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

	int addCheckup(Checkup checkup) {
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
	
	int addStay(Stay stay) {
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
	
	Appointment getAppointment(String patientId) {
		try {
			PreparedStatement statement = DBConn.prepareStatement("SELECT * FROM patients p, appointments a WHERE p.patientID = ? and p.patientID = a.patientID");
			statement.setString(1, patientId);

			try (ResultSet resultSet = statement.executeQuery()) {
				
				Appointment appointment = null;
				while(resultSet.next()) {
					int appointmentId = resultSet.getInt("appointmentId");
					patientId = resultSet.getString("patientId");
					int doctorId = resultSet.getInt("doctorId");
					String startTime = resultSet.getString("timeStart");
					String entTime = resultSet.getString("timeEnd");
					appointment = new Appointment(appointmentId, patientId, doctorId, startTime, entTime);
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
	
	Patient getPatient(String patientId) {
		try {
			PreparedStatement statement = DBConn.prepareStatement("SELECT * FROM patients WHERE patientID = ?");
			statement.setString(1, patientId);

			try (ResultSet resultSet = statement.executeQuery()) {
				
				Patient patient = null;
				while(resultSet.next()) {
					patientId = resultSet.getString("patientId");
					String name = resultSet.getString("name");
					patient = new Patient(patientId, name);
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
	
	
	public static void log(String string) {
		System.err.println(string);
	}
}
