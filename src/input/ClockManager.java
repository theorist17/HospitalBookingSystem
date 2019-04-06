package input;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;

public class ClockManager {
   
   public static Date clockDiag(String diag) {
      
      String startClock = diag.substring(17);
      String pattern = "yyyyMMddHHmm";
      DateFormat date = new SimpleDateFormat(pattern);
      Date diagDate = null;
      
      try {
      diagDate = date.parse(startClock);
      return diagDate;

      }catch(ParseException e) {
         e.printStackTrace();
      }
      return diagDate;
   }
   
   public static Date clockCheckUp(String checkUp) {
      
      String startClock = checkUp.substring(17);
      String pattern = "yyyyMMddHHmm";
      DateFormat date = new SimpleDateFormat(pattern);
      Date checkUpDate = null;
      
      try {
      checkUpDate = date.parse(startClock);
      return checkUpDate;

      }catch(ParseException e) {
         e.printStackTrace();
      }
      return checkUpDate;
   }
   
   public static Date clockStartAdmission(String admission) {
      
      String startClock = admission.substring(18,10);
      String pattern = "yyyyMMddHHmm";
      DateFormat date = new SimpleDateFormat(pattern);
      Date startAdmissionDate = null;
      
      try {   
      startAdmissionDate = date.parse(startClock);
      return startAdmissionDate;

      }catch(ParseException e) {
         e.printStackTrace();
      }
      return startAdmissionDate;
   }
   
   public static Date clockEndAdmission(String admission) {
      
      String endClock = admission.substring(28,10);
      String pattern = "yyyyMMddHHmm";
      DateFormat date = new SimpleDateFormat(pattern);
      Date endAdmissionDate = null;
      
      try {
      endAdmissionDate = date.parse(endClock);
      return endAdmissionDate;

      }catch(ParseException e) {
         e.printStackTrace();
      }
      return endAdmissionDate;
   }
}
   
