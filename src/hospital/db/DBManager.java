package hospital.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.mysql.cj.jdbc.CallableStatement;

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
		//

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

	public void LoadConnection()  {
		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://175.113.152.102:3306/hospital?" + "user=dev&password=MySQL!=1");

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
			
			statement = conn.prepareStatement("INSERT INTO patients (patientID, name) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
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
					int doctorID = resultSet.getInt("doctorID");
					patient = new Patient(patientID, name, doctorID);
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
	public Patient getPatient(int doctorID) {
		try {
			statement = conn.prepareStatement("SELECT * FROM patients WHERE doctorID = ?");
			statement.setInt(1, doctorID);

			try (ResultSet resultSet = statement.executeQuery()) {

				Patient patient = null;
				while (resultSet.next()) {
					String patientID = resultSet.getString("patientID");
					String name = resultSet.getString("name");
					doctorID = resultSet.getInt("doctorID");
					patient = new Patient(patientID, name, doctorID);
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

	public Doctor getDoctor(String patientID) {
		try {
			statement = conn.prepareStatement("SELECT d.* FROM patients p, doctors d WHERE p.doctorID = d.doctorID and p.patientID = ?;");
			statement.setString(1, patientID);

			try (ResultSet resultSet = statement.executeQuery()) {

				Doctor doctor = null;
				while (resultSet.next()) {
					int doctorID = resultSet.getInt("doctorID");
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
						Appointment appointment = new Appointment(bookingID, patientID, resourceID, timeStart, timeEnd,hasPaid, 0);
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
								hasPaid, 0);
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
	
	public List<Booking> getBookings_for_appointment(int doctorID) {
	      try {
	         statement = conn.prepareStatement("SELECT  *, '진료' as Source\n" + "FROM appointments\n" + "where doctorID = ?\n;");
	         statement.setInt(1, doctorID);

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

	               // 이름, 환자주민, 시작, 종료
	               if (bookingType.equals("진료")) {
	                  Appointment appointment = new Appointment(bookingID, patientID, resourceID, timeStart, timeEnd,
	                        hasPaid, 0);
	                  bookings.add((Booking) appointment);
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
	   
	   public List<Booking> getBookings_for_checkup(int testID) {
	      try {
	         statement = conn.prepareStatement("SELECT *, '검사' as Source\n" + "FROM checkups \n" + "where testID = ?\n;");
	         statement.setInt(1, testID);

	         try (ResultSet resultSet = statement.executeQuery()) {

	            List<Booking> bookings = new ArrayList<Booking>();

	            while (resultSet.next()) {
	               String bookingType = resultSet.getString("Source");

	               // getting attributes for all bookings
//	               int bookingID = resultSet.getInt(1);
//	               String patientID = resultSet.getString("patientID");
//	               int resourceID = resultSet.getInt(3);
//	               String timeStart = resultSet.getString("timeStart");
//	               String timeEnd = resultSet.getString("timeEnd");
//	               int hasPaid = resultSet.getInt("hasPaid");
	               
	               // 이름, 환자주민, 시작, 종료
//	               if (bookingType.equals("검사")) {
//	                  Checkup checkup = new Checkup(bookingID, patientID, resourceID, timeStart, timeEnd, hasPaid);
//	                  bookings.add((Booking) checkup);
//	               }
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
	   
	   public List<Booking> getBookings_for_stay(int roomID) {
	      try {
	         statement = conn.prepareStatement("SELECT s.*, '입원' as Source\n" + "FROM beds b, stays s\n" + "where b.roomID = ? and b.bedID = s.bedID order by b.bedID asc\n;");
	         statement.setInt(1, roomID);

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
	               
	               // 침대번호, 시작시간, 종료시간
	               if (bookingType.equals("입원")) {
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

	public List<Booking> getAppointments() {
		try {
			statement = conn.prepareStatement("SELECT  * " + 
					"FROM appointments " + 
					"where hasPaid = 0;");
			
			try (ResultSet resultSet = statement.executeQuery()) {

				List<Booking> bookings = new ArrayList<Booking>();

				while (resultSet.next()) {
					// getting attributes for all bookings
					int bookingID = resultSet.getInt(1);
					String patientID = resultSet.getString("patientID");
					int resourceID = resultSet.getInt(3);
					String timeStart = resultSet.getString("timeStart");
					String timeEnd = resultSet.getString("timeEnd");
					int hasPaid = resultSet.getInt("hasPaid");

					Appointment appointment = new Appointment(bookingID, patientID, resourceID, timeStart, timeEnd,
								hasPaid, 0);
					bookings.add((Booking) appointment);
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
	public List<Booking> getAppointments(int doctorID) {
		try {
			statement = conn.prepareStatement("SELECT  * " + 
					"FROM appointments " + 
					"where hasPaid = 0 and doctorID = ?;");
			statement.setInt(1, doctorID);
			
			try (ResultSet resultSet = statement.executeQuery()) {

				List<Booking> bookings = new ArrayList<Booking>();

				while (resultSet.next()) {
					// getting attributes for all bookings
					int bookingID = resultSet.getInt(1);
					String patientID = resultSet.getString("patientID");
					int resourceID = resultSet.getInt(3);
					String timeStart = resultSet.getString("timeStart");
					String timeEnd = resultSet.getString("timeEnd");
					int hasPaid = resultSet.getInt("hasPaid");
					int onDept = resultSet.getInt("onDept");

					Appointment appointment = new Appointment(bookingID, patientID, resourceID, timeStart, timeEnd,
								hasPaid, onDept);
					bookings.add((Booking) appointment);
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
						+ appointment.getTimeEnd());
			} else if (bookings.get(i) instanceof Checkup) {
				Checkup checkup = (Checkup) bookings.get(i);
				System.out.println(
						(i + 1) + ". 검사, " + checkup.getTestID() + "&" + getTest(checkup.getTestID()).getName() + ", "
								+ checkup.getTimeStart() + ", " + checkup.getTimeEnd());
			} else if (bookings.get(i) instanceof Stay) {
				Stay stay = (Stay) bookings.get(i);
				System.out.println((i + 1) + ". 입원, 침대" + stay.getBedID() + "&" + getBed(stay.getBedID()).getRoomID()
						+ "호실, " + stay.getTimeStart() + ", " + stay.getTimeEnd());
			}
		}
		return bookings;
	}

	public List<Booking> printBookings_for_doctor(List<Booking> bookings) {
	      for (int i = 0; i < bookings.size(); i++) {
	         if (bookings.get(i) instanceof Appointment) {
	            Appointment appointment = (Appointment) bookings.get(i);
	            if(i==0) {
	               System.out.println(getDoctor(appointment.getDoctorID()).getName() + " 의사가 진료했던 과거의 환자정보 출력");
	               System.out.println("구분\t환자이름\t주민번호\t\t시작시간\t\t\t종료시간");
	            }
	            System.out.println((i + 1) + "\t" +
	            getPatient(appointment.getPatientID()).getName() + "\t" +
	            getPatient(appointment.getPatientID()).getPatientId() + "\t" +
	            appointment.getTimeStart() + "\t" +
	            appointment.getTimeEnd());
	         }
	         
	         else if (bookings.get(i) instanceof Checkup) {
	            Checkup checkup = (Checkup) bookings.get(i);
	            if(i==0) {
	               System.out.println("구분\t환자이름\t주민번호\t\t시작시간\t\t\t종료시간");
	            }
	            System.out.println(
	                  (i + 1) + ". 검사, " + checkup.getTestID() + "&" + getTest(checkup.getTestID()).getName() + ", "
	                        + checkup.getTimeStart() + ", " + checkup.getTimeEnd() + " " + checkup.getCheckupID());
	         }
	         
	         else if (bookings.get(i) instanceof Stay) {
	            Stay stay = (Stay) bookings.get(i);
	            if(i==0) {
	               System.out.println("구분\t방번호\t침대번호\t\t시작시간\t\t\t종료시간");
	            }
	            
	            
	            
	            System.out.println((i + 1) + "\t" + getBed(stay.getBedID()).getRoomID() + "호실" + "\t" + stay.getBedID() + 
	                  "\t\t" + stay.getTimeStart() + ", " + stay.getTimeEnd());
	            
	            
//	            Stay appointment = (Stay) bookings.get(i);
//	            
//	            System.out.println((i + 1) + "\t" +
//	            getPatient(appointment.getPatientID()).getName() + "\t" +
//	            getPatient(appointment.getPatientID()).getPatientId() + "\t" +
//	            appointment.getTimeStart() + "\t" +
//	            appointment.getTimeEnd());
	         }
	      }
	      return bookings;
	   }
	
	/**
	 * 의사가 아파서 진료과지정으로 배정받은 환자진료예약을 다른 의사에 넘기는 쿼리를 준비하는 함(update)
	 * @param bookings
	 * @param entryStart
	 * @param entryEnd
	 * @param candiDoctors
	 * @return
	 */
	public List<PreparedStatement> getSubDocQueries(List<Booking> bookings, String entryStart, String entryEnd, String deptName, Doctor lookingForSub) {
		
		try {
			Date newStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entryStart);
			Date newEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entryEnd);
			
			List<Doctor> subDoctors = new ArrayList<Doctor>();
			Random random = new Random();
			List<PreparedStatement> updateQueries = new ArrayList<PreparedStatement>();

			//의사 자신의 대체의사를 찾아보는 반복문.
			for (int i = 0; i < bookings.size(); i++) {
				Appointment appointment = (Appointment) bookings.get(i);
				// 오직 인자로 주어진 자료형의 객체를 대상으로
				Date oldStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bookings.get(i).getTimeStart());
				Date oldEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bookings.get(i).getTimeEnd());

				if ((newStart.compareTo(oldEnd) < 0 && oldStart.compareTo(newEnd) < 0)
						|| (oldStart.compareTo(newStart) < 0 && newEnd.compareTo(oldEnd) < 0)
						|| (oldStart.compareTo(newStart) == 0 && newEnd.compareTo(oldEnd) == 0)) {
						// 입력과 의사의 예약중 시간 겹치는 것 가져오기
						if( appointment.getOnDept() == 0) {
							// 의사에 대한 진료예약이 의사지정이면 의사는 일해야 하므로 거짓 반환
							return null;
							
						} else {
							// 진료예약이 진료과지정이면 대체의사를 찾아본다.
							List<Doctor> candiDoctors = executeCheckTime(appointment.getTimeStart(), appointment.getTimeEnd(), deptName);
							candiDoctors.remove(lookingForSub);
							if (candiDoctors.isEmpty())
								return null;
							Doctor subDoctor = candiDoctors.get(random.nextInt(candiDoctors.size()));
							candiDoctors.remove(subDoctor);
							subDoctors.add(subDoctor);
							
							// 쿼리를 모아둔다.
							PreparedStatement updateQuery = conn.prepareStatement("update appointments set doctorID = ? where appointmentID = ?;");
							updateQuery.setInt(1, subDoctor.getDoctorId());
							updateQuery.setInt(2, appointment.getAppointmentID());
							updateQueries.add(updateQuery);
						}
				}
			}
			
			//자신의 환자를 다 넘겼으므로 참 반환
			return updateQueries;
			
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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
	
	public List<Doctor> executeCheckTime(String timeStart, String timeEnd, String deptName) {
		try {
			statement = conn.prepareStatement("call check_time(?, ?, ?);");
			statement.setString(1, timeStart);
			statement.setString(2, timeEnd);
			statement.setString(3, deptName);

			try (ResultSet resultSet = statement.executeQuery()) {
				List<Doctor> doctors = new ArrayList<Doctor>();
				while (resultSet.next()) {
					int doctorID = resultSet.getInt("doctorID");
					String name = resultSet.getString("name");
					String department = resultSet.getString("department");
					int price = resultSet.getInt("price");
					
					doctors.add(new Doctor(doctorID, name, department, price));
				}
				return doctors;
			}
			
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}
		return null;
	}

	public boolean executeCheckDoctorAppoint(String patientID, int doctorID, String timeStart, String timeEnd, String department, int onDept) {
		try {
			CallableStatement csmt=(CallableStatement) conn.prepareCall("CALL check_doctor_appoint(?, ?, ?, ?, ?, ?, ?);");
			csmt.setString(1, patientID);
			csmt.setInt(2, doctorID);
			csmt.setString(3, timeStart);
			csmt.setString(4, timeEnd);
			csmt.setString(5, department);
			csmt.setInt(6, onDept);
			csmt.registerOutParameter(7, Types.BOOLEAN);
			csmt.execute();
			return csmt.getBoolean(7);
			
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}
		return false;	
	}
	public boolean executeDoctorWorkTime(String timeStart, String timeEnd, int DoctorID) {
		try {
			CallableStatement csmt=(CallableStatement) conn.prepareCall("CALL doctor_workTime(?, ?, ?, ?);");
			csmt.setString(1, timeStart);
			csmt.setString(2, timeEnd);
			csmt.setInt(3,DoctorID);
			csmt.registerOutParameter(4, Types.BOOLEAN);
			csmt.execute();
			return csmt.getBoolean(4);
			
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean executeDoctorAvailable(String timeStart, String timeEnd, int DoctorID) {
		try {
			
			CallableStatement csmt=(CallableStatement) conn.prepareCall("CALL doctor_available(?, ?, ?, ?);");
			csmt.setString(1, timeStart);
			csmt.setString(2, timeEnd);
			csmt.setInt(3,DoctorID);
			csmt.registerOutParameter(4, Types.BOOLEAN);
			csmt.execute();
			return csmt.getBoolean(4);
			
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean executePainDoctor(String timeStart, String timeEnd, String patientID) {
		try {
			
			CallableStatement csmt=(CallableStatement) conn.prepareCall("CALL pain_doctor(?, ?, ?,?);");
			csmt.setString(1, timeStart);
			csmt.setString(2, timeEnd);
			csmt.setString(3,patientID);
			csmt.registerOutParameter(4, Types.BOOLEAN);
			csmt.execute();
			return csmt.getBoolean(4);
			
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Doctor> executeCheckFree_id(String timeStart, String timeEnd,int DoctorID, String deptName) {
		try {
			
			System.out.println("executeCheckFree_id");
			CallableStatement csmt=(CallableStatement) conn.prepareCall("CALL free_id(?, ?, ?, ?, ?);");
//			statement = conn.prepareStatement("CALL doctor_available(?, ?, ?,?);");
//			statement.setString(1, timeStart);
//			statement.setString(2, timeEnd);
//			statement.setInt(3,DoctorID);
			csmt.setString(1, timeStart);
			csmt.setString(2, timeEnd);
			csmt.setInt(3,DoctorID);
			csmt.setString(4, deptName);
			csmt.registerOutParameter(5, Types.INTEGER);
			csmt.execute();
			System.out.println("getint:"+csmt.getInt(5));
			
		

			try (ResultSet resultSet = statement.executeQuery()) {
				List<Doctor> doctors = new ArrayList<Doctor>();
				while (resultSet.next()) {
					int doctorID = resultSet.getInt("doctorID");
					String name = resultSet.getString("name");
					String department = resultSet.getString("department");
					int price = resultSet.getInt("price");
					
					doctors.add(new Doctor(doctorID, name, department, price));
				}
				return doctors;
			}
			
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			e.printStackTrace();
		}
		return null;
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
		//System.err.println(string);
	}
}
