package jp.sw.ku;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBManager {

	static Connection connection = null;
	static PreparedStatement preparedStatement = null;

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
			connection = DriverManager
					.getConnection("jdbc:mysql://1.240.123.168:3306/hospital?" + "user=dev&password=MySQL!=1");

			// Do something with the Connection
			preparedStatement = connection.prepareStatement("INSERT INTO patients (name) values ('hello');");
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	public static void log(String string) {
		System.err.println(string);
	}
}
