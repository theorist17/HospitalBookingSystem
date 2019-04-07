package hospital.UI;

import hospital.UI.*;

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

	public void printDoctorInformation() {
		this.printTextWithKey(eText.Doctor_information);
		this.printTextWithKey(eText.Patient_Menu1_Information);
	}

	public void printCheckUpInformation() {
		this.printTextWithKey(eText.CheckUp_Information);
		this.printTextWithKey(eText.CheckUp_InputInformation);
	}

	public void printBedInformation() {
		this.printTextWithKey(eText.Bed_Information);
		this.printTextWithKey(eText.Bed_InputInformation);
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

	public void printDoctorError() {
		this.printTextWithKey(eText.MENU2_ERROR);
	}
	
	public void printVisitError() {
		this.printTextWithKey(eText.First_Visit);
	}

	/*
	 * 입력받은 eText 값에 매핑되어 있는 문자열을 출력하는 함수.
	 */
	public void printTextWithKey(eText key) {
		System.out.println(key.getText());
	}
}