package input;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;

public class ClockManager {
   
	public static String addHour(String timeStart) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new SimpleDateFormat("yyyyMMddhh").parse(timeStart));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		String timeEnd = new SimpleDateFormat("yyyyMMddhh").format(calendar.getTime());
		return clockDiag(timeEnd);
	}
	public static String clockDiag(String diag) {

		String pattern = "yyyyMMddhh";
		DateFormat date = new SimpleDateFormat(pattern);
		Date diagDate = null;

		try {
			diagDate = date.parse(diag);
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(diagDate);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
   
   public static Date clockCheckUp(String checkUp) {
      
      String pattern = "yyyy-MM-dd HH:mm:ss";
      DateFormat date = new SimpleDateFormat(pattern);
      Date checkUpDate = null;
      
      try {
      checkUpDate = date.parse(checkUp);
      return checkUpDate;

      }catch(ParseException e) {
         e.printStackTrace();
      }
      return checkUpDate;
   }
   
   public static Date clockStartAdmission(String admission) {
      
      String pattern = "yyyy-MM-dd HH:mm:ss";
      DateFormat date = new SimpleDateFormat(pattern);
      Date startAdmissionDate = null;
      
      try {   
      startAdmissionDate = date.parse(admission);
      return startAdmissionDate;

      }catch(ParseException e) {
         e.printStackTrace();
      }
      return startAdmissionDate;
   }
   
   public static Date clockEndAdmission(String admission) {
      
      String pattern = "yyyy-MM-dd HH:mm:ss";
      DateFormat date = new SimpleDateFormat(pattern);
      Date endAdmissionDate = null;
      
      try {
      endAdmissionDate = date.parse(admission);
      return endAdmissionDate;

      }catch(ParseException e) {
         e.printStackTrace();
      }
      return endAdmissionDate;
   }
}
   
