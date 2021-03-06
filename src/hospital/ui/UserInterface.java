package hospital.ui;

import hospital.ui.*;

public class UserInterface {

   private static UserInterface instance;

   private UserInterface() {
   }

   public static UserInterface getInstance() {
      if (instance == null) {
         instance = new UserInterface();
      }
      return instance;
   }

   public void printMainUI() {
      this.printTextWithKey(eText.MAIN_TITLE);
      this.printTextWithKey(eText.MAIN_MENU_1);
      this.printTextWithKey(eText.MAIN_MENU_2);
      this.printTextWithKey(eText.MAIN_MENU_3);
   }

   public void printMainError() {
      this.printTextWithKey(eText.MAIN_ERROR);
   }
   public void printMainError1() {
      this.printTextWithKey(eText.MAIN_ERROR1);
   }

   public void printPatientUI() {
      this.printTextWithKey(eText.MENU1_RESULT_1);
      this.printTextWithKey(eText.MENU1_RESULT_2);
      this.printTextWithKey(eText.MENU1_RESULT_3);
      this.printTextWithKey(eText.MENU1_RESULT_4);
   }

   public void printPatientError() {
      this.printTextWithKey(eText.MENU1_ERROR);
   }

   public void printPatientMenu1UI() {
      this.printTextWithKey(eText.Patient_Menu1);
      this.printTextWithKey(eText.Patient_Menu2);
      this.printTextWithKey(eText.Patient_Menu3);
   }

   public void printAppointmentMenu() {
      this.printTextWithKey(eText.Appointment_Menu1);
      this.printTextWithKey(eText.Appointment_Menu2);
   }
   
   
   public void printDoctorInformation() {
      this.printTextWithKey(eText.Doctor_information);
      this.printTextWithKey(eText.Patient_Menu1_Information);
   }

   public void printDepartmentInformation() {
      this.printTextWithKey(eText.Department_information);
      this.printTextWithKey(eText.Department_Menu1_Information);

   }

   public void printCheckUpInformation() {
      this.printTextWithKey(eText.CheckUp_Information);
      this.printTextWithKey(eText.CheckUp_InputInformation);
   }

   public void printBedInformation() {
      //this.printTextWithKey(eText.Bed_Information2);
      this.printTextWithKey(eText.Bed_InputInformation2);
   }

   public void printAppointPatient() {
      this.printTextWithKey(eText.Appointment_Patient);
   }

   public void printInputError() {
      this.printTextWithKey(eText.Input_Error);
   }

   public void printText(String text) {
      System.out.println(text);
   }

   // 의사
   public void printDoctorUI() {
      this.printTextWithKey(eText.MENU2_RESULT_1);
      this.printTextWithKey(eText.MENU2_RESULT_2);
      this.printTextWithKey(eText.MENU2_RESULT_3);
      this.printTextWithKey(eText.MENU2_RESULT_4);
   }

   public void printAppointmentError() {
      this.printTextWithKey(eText.Appointment_ERROR);
   }
   public void printDoctorError() {
      this.printTextWithKey(eText.MENU2_ERROR);
   }
   
   public void printVisitError() {
      this.printTextWithKey(eText.FirstVisit_Error);
   }

   public void printTimeOverlapError() {
      this.printTextWithKey(eText.TimeOverlap_Error);
   }

   public void printDepartmentError() {
      this.printTextWithKey(eText.Department_Error);
   }
   
   public void printDepartmentLastError() {
      this.printTextWithKey(eText.DepartmentLast_Error);
   }
   
   public void printAppointmentResultError() {
      this.printTextWithKey(eText.AppointmentResult_Error);
   }


   /*
    * 입력받은 eText 값에 매핑되어 있는 문자열을 출력하는 함수.
    */
   public void printTextWithKey(eText key) {
      System.out.println(key.getText());
   }
}