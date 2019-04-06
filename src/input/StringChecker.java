package input;

import jp.sw.ku.UserInterface;

public class StringChecker {
   
   public static boolean checkOneThree(String oneThree) {//1~3 check
      if(!oneThree.matches("^[1-3]{1}$")) {
         //UIManagerError
         return false;
      }
      return true;
   }
   
   public static boolean checkOneFour(String oneFour) {//1~4 check
      if(!oneFour.matches("^[1-4]{1}$")) {
         //UIManagerError
         return false;
      }
      return true;
   }
   
   public static boolean checkDiag(String diag) {//진료체크
      if(!diag.matches("[가-힣]{3}\\/[1-9]{1}[0-9]{12}\\/[1-3]{1}\\/[0-9]{10}")) {
         //UImanagerError
    	
         return false;
      }
      return true;
   }
   
   public static boolean checkCheckUp(String checkUp) {//검사체크
      if(!checkUp.matches("[가-힣]{3}\\/[1-9]{1}[0-9]{12}\\/[1-3]{1}\\/[0-9]{10}")) {
         //UIManagerError
         return false;
      }
      return true;
   }
   
   public static boolean checkAdmission(String admission) {
      if(!admission.matches("[가-힣]{3}\\/[1-9]{1}[0-9]{12}\\/([0-1]{1}[0-9]{1}|20)\\/[0-9]{10}\\/[0-9]{10}")) {
         //UIManagerError
         return false;
      }
      return true;
   }
   
   public static boolean lookupAppoint(String appoint) {
      if(!appoint.matches("^[가-힣]{3}$\\/^[0-9]{13}$")) {
         //UIManagerError
         return false;
      }
      return true;
   }
   public static boolean lookupBed(String bedNum) {
	   if(!bedNum.matches("([0-1]{1}[0-9]{1}|20)")) {
	         //UIManagerError
	         return false;
	      }
	      return true;
   }
   
   
   public static boolean cancelAppoint(String cancel) {
      if(!cancel.matches("[1-9]+[1-9]?")) {
         //UIManagerError
         return false;
      }
      
      return true;
   }
   
   public static boolean payment(String pay) {
      if(pay.matches("[1-9]+[1-9]?"))
    	  return true;
      else
    	  return false;
   }
   
   public static boolean checkPersonalNum(String personalNum) {
      if(!personalNum.matches("[가-힣]{3}\\/[0-9]{13}")) {
         //UIManagerError
         return false;
      }
      return true;
   }
   public static boolean checkOnlyPersonalNum(String personalNum) {
	      if(!personalNum.matches("[0-9]{13}")) {
	         //UIManagerError
	         return false;
	      }
	      return true;
	   }
}