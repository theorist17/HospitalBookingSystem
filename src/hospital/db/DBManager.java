package hospital.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hospital.data.Appointment;
import hospital.data.Bed;
import hospital.data.Booking;
import hospital.data.Checkup;
import hospital.data.Doctor;
import hospital.data.Patient;
import hospital.data.Room;
import hospital.data.Stay;
import hospital.data.Test;

public class DBManager {

	private Connection conn = null;
	private PreparedStatement statement = null;

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
			conn = DriverManager
					.getConnection("jdbc:mysql://1.240.123.168:3306/hospital?" + "user=dev&password=MySQL!=1");

			if (conn != null) {
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
			 

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {

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
			statement = conn.prepareStatement("INSERT INTO doctors (name, price) VALUES (?, ?)",
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
			statement = conn.prepareStatement("INSERT INTO tests (name, price) VALUES (?, ?)",
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

	public int addRoom(Room room) {
		try {
			statement = conn.prepareStatement("INSERT INTO rooms (capacity) VALUES (?)",
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

	public int addBed(Bed bed) {
		try {
			statement = conn.prepareStatement("INSERT INTO beds (roomID, price) VALUES (?, ?)",
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
			statement = conn.prepareStatement(
					"INSERT INTO appointments (patientID, doctorID, timeStart, timeEnd) VALUES (?, ?, ?, ?)",
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
			statement = conn.prepareStatement(
					"INSERT INTO checkups (patientID, testID, timeStart, timeEnd) VALUES (?, ?, ?, ?)",
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
			statement = conn.prepareStatement(
					"INSERT INTO stays (patientID, bedID, timeStart, timeEnd) VALUES (?, ?, ?, ?)",
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

	public Patient getPatient(String patientID) {
		try {
			statement = conn.prepareStatement("SELECT * FROM patients WHERE patientID = ?");
			statement.setString(1, patientID);

			try (ResultSet resultSet = statement.executeQuery()) {

				Patient patient = null;
				while (resultSet.next()) {
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
			statement = conn.prepareStatement("SELECT * FROM doctors WHERE doctorID = ?");
			statement.setInt(1, doctorID);

			try (ResultSet resultSet = statement.executeQuery()) {

				Doctor doctor = null;
				while (resultSet.next()) {
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

	public Test getTest(int testID) {
		try {
			statement = conn.prepareStatement("SELECT * FROM tests WHERE testID = ?");
			statement.setInt(1, testID);

			try (ResultSet resultSet = statement.executeQuery()) {

				Test test = null;
				while (resultSet.next()) {
					testID = resultSet.getInt("testID");
					String name = resultSet.getString("name");
					int price = resultSet.getInt("price");
					test = new Test(testID, name, price);
					return test;
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

	public Bed getBed(int bedID) {
		try {
			statement = conn.prepareStatement("SELECT * FROM beds WHERE bedID = ?");
			statement.setInt(1, bedID);

			try (ResultSet resultSet = statement.executeQuery()) {

				Bed bed = null;
				while (resultSet.next()) {
					bedID = resultSet.getInt("bedID");
					int roomID = resultSet.getInt("roomID");
					int price = resultSet.getInt("price");
					bed = new Bed(bedID, roomID, price);
					return bed;
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
	
	public List<Booking> getBookings(String startTime, String endTime) {
		try {
			statement = conn.prepareStatement("SELECT  *, '진료' as Source\n" + 
					"FROM appointments\n" + 
					"where hasPaid = 0 and ? <= timeStart and timeEnd <= ?\n" + 
					"UNION\n" + 
					"SELECT *, '검사' as Source\n" + 
					"FROM checkups \n" + 
					"where hasPaid = 0  and ? <= timeStart and timeEnd <= ?\n" + 
					"UNION\n" + 
					"SELECT *, '입원' as Source\n" + 
					"FROM checkups \n" + 
					"where hasPaid = 0  and ? <= timeStart and timeEnd <= ?;");
			statement.setString(1, startTime);
			statement.setString(2, endTime);
			statement.setString(3, startTime);
			statement.setString(4, endTime);
			statement.setString(5, startTime);
			statement.setString(6, endTime);

			try (ResultSet resultSet = statement.executeQuery()) {

				List<Booking> bookings = new ArrayList<Booking>();

				while (resultSet.next()) {
					String bookingType = resultSet.getString("Source");

					// getting attributes for all bookings
					int bookingID = resultSet.getInt(1);
					String patientID = resultSet.getString("patientID");
					int resourceID = resultSet.getInt(3);
					String timeStart = resultSet.getString("timeStart");
					String timeEnd = resultSet.getString("timeEnd");
					int hasPaid = resultSet.getInt("hasPaid");

					if (bookingType.equals("진료")) {
						Appointment appointment = new Appointment(bookingID, patientID, resourceID, timeStart, timeEnd,
								hasPaid);
						bookings.add((Booking) appointment);
					} else if (bookingType.equals("검사")) {
						Checkup checkup = new Checkup(bookingID, patientID, resourceID, timeStart, timeEnd, hasPaid);
						bookings.add((Booking) checkup);
					} else if (bookingType.equals("입원")) {
						Stay stay = new Stay(bookingID, patientID, resourceID, timeStart, timeEnd, hasPaid);
						bookings.add((Booking) stay);
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
	
	public List<Booking> getBookings(String patientID) {
		try {
			statement = conn.prepareStatement("SELECT  *, '진료' as Source\n" + "FROM appointments\n"
					+ "where hasPaid = 0 and patientID = ?\n" + "union \n" + "SELECT *, '검사' as Source\n"
					+ "FROM checkups \n" + "where hasPaid = 0 and patientID = ?\n" + "union\n"
					+ "SELECT *, '입원' as Source\n" + "FROM stays \n" + "where hasPaid = 0 and patientID = ?;");
			statement.setString(1, patientID);
			statement.setString(2, patientID);
			statement.setString(3, patientID);

			try (ResultSet resultSet = statement.executeQuery()) {

				List<Booking> bookings = new ArrayList<Booking>();

				while (resultSet.next()) {
					String bookingType = resultSet.getString("Source");

					// getting attributes for all bookings
					int bookingID = resultSet.getInt(1);
					patientID = resultSet.getString("patientID");
					int resourceID = resultSet.getInt(3);
					String timeStart = resultSet.getString("timeStart");
					String timeEnd = resultSet.getString("timeEnd");
					int hasPaid = resultSet.getInt("hasPaid");

					if (bookingType.equals("진료")) {
						Appointment appointment = new Appointment(bookingID, patientID, resourceID, timeStart, timeEnd,
								hasPaid);
						bookings.add((Booking) appointment);
					} else if (bookingType.equals("검사")) {
						Checkup checkup = new Checkup(bookingID, patientID, resourceID, timeStart, timeEnd, hasPaid);
						bookings.add((Booking) checkup);
					} else if (bookingType.equals("입원")) {
						Stay stay = new Stay(bookingID, patientID, resourceID, timeStart, timeEnd, hasPaid);
						bookings.add((Booking) stay);
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
	

	public List<Booking> printBookings(List<Booking> bookings) {
		for (int i = 0; i < bookings.size(); i++) {
			if (bookings.get(i) instanceof Appointment) {
				Appointment appointment = (Appointment) bookings.get(i);
				System.out.println((i + 1) + ". 진료, " + appointment.getDoctorID() + "&"
						+ getDoctor(appointment.getDoctorID()).getName() + ", " + appointment.getTimeStart() + ", "
						+ appointment.getTimeEnd() + " " + appointment.getAppointmentID());
			} else if (bookings.get(i) instanceof Checkup) {
				Checkup checkup = (Checkup) bookings.get(i);
				System.out.println(
						(i + 1) + ". 검사, " + checkup.getTestID() + "&" + getTest(checkup.getTestID()).getName() + ", "
								+ checkup.getTimeStart() + ", " + checkup.getTimeEnd() + " " + checkup.getCheckupID());
			} else if (bookings.get(i) instanceof Stay) {
				Stay stay = (Stay) bookings.get(i);
				System.out.println((i + 1) + ". 입원, 침대" + stay.getBedID() + "&" + getBed(stay.getBedID()).getRoomID()
						+ "호실, " + stay.getTimeStart() + ", " + stay.getTimeEnd() + " " + stay.getStayID());
			}
		}
		return bookings;
	}

	public boolean setBookingHasPaid(Booking booking) {
		try {
			PreparedStatement statement = null;
			if (booking instanceof Appointment) {
				Appointment appointment = (Appointment) booking;
				statement = conn.prepareStatement("UPDATE appointments SET hasPaid=1 WHERE appointmentID=?;");
				statement.setInt(1, appointment.getAppointmentID());
			} else if (booking instanceof Checkup) {
				Checkup checkup = (Checkup) booking;
				statement = conn.prepareStatement("UPDATE checkups SET hasPaid=1 WHERE checkupID=?;");
				statement.setInt(1, checkup.getCheckupID());
			} else if (booking instanceof Stay) {
				Stay stay = (Stay) booking;
				statement = conn.prepareStatement("UPDATE stays SET hasPaid=1 WHERE stayID=?;");
				statement.setInt(1, stay.getStayID());
			}

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 1) {
				booking.setHasPaid(1);
				return true;
			} else {
				throw new SQLException("UPDATE haspaid failed, no rows affected.");
			}

		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}
		return false;
	}

	boolean deleteBooking(Booking booking) {

		try {
			PreparedStatement statement = null;

			if (booking instanceof Appointment) {
				Appointment appointment = (Appointment) booking;
				statement = conn.prepareStatement("DELETE FROM appointments WHERE appointmentID = ?;");
				statement.setInt(1, appointment.getAppointmentID());
			} else if (booking instanceof Checkup) {
				Checkup checkup = (Checkup) booking;
				statement = conn.prepareStatement("DELETE FROM checkups WHERE checkupID = ?;");
				statement.setInt(1, checkup.getCheckupID());
			} else if (booking instanceof Stay) {
				Stay stay = (Stay) booking;
				statement = conn.prepareStatement("DELETE FROM stays WHERE stayID = ?;");
				statement.setInt(1, stay.getStayID());
			}

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 1)
				return true;
			else
				throw new SQLException("UPDATE haspaid failed, no rows affected.");

		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}
		return false;
	}
	
	public int getRandomDoctor(String timeStart, String timeEnd) {
		try {
			statement = conn.prepareStatement("CALL check_time(?, ?);");
			statement.setString(1, timeStart);
			statement.setString(2, timeEnd);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					int i = resultSet.getInt(1);
					System.out.println(i);
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
	
	public void disconnectDb() {
        try {
            statement.close();
            conn.close();
            log("\n=======Database Connection Closed=======\n");
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
    }
	

	public void log(String string) {
		System.err.println(string);
	}
}
