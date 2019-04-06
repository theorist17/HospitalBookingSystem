package jp.sw.ku;

import java.util.Scanner;

import hospital.UI.eText;
import input.*;


public class MainScene {
   DBManager dbManager;
   StringChecker stringCheck=new StringChecker();
   Scanner scan = new Scanner (System.in);
   public void doProcess() {
	  dbManager = new DBManager();
	  dbManager.LoadDriver();
	  dbManager.LoadConnection();
      while(this.goMainMenu()) {};
   }

	private boolean goMainMenu() {
		while (true) {
			UserInterface.getInstance().printMainUI();
			String input = this.scan.nextLine();
			if (StringChecker.checkOneThree(input)) {
				switch (Integer.parseInt(input)) {
				case 1:
					return goMenu1();
				case 2:
					return goMenu2();
				case 3:
					System.out.println("시스템을 종료합니다.");
					return false;
				default:
					return goMainMenu();
				}
			} else {

				UserInterface.getInstance().printMainError();
			}
			// break;
		}
	}

	private boolean goMenu1() {
		UserInterface.getInstance().printPatientUI();
		String input = this.scan.nextLine();
		if (stringCheck.checkOneFour(input)) {
			switch (Integer.parseInt(input)) {
			case 1:
				return goPatientMenu1();
			case 2:
				return goPatientMenu2();
			case 3:
				return goPatientMenu3();
			case 4:
				return goPatientMenu4();
			default:
				UserInterface.getInstance().printPatientError();
				return goMainMenu();
			}
		} else {
			UserInterface.getInstance().printPatientError();
			return goMainMenu();
			
		}

	}
   private boolean goMenu2() {
      UserInterface.getInstance().printDoctorUI();
      String input = this.scan.nextLine();
      if(StringChecker.checkOneFour(input)) {
      switch(Integer.parseInt(input)) {
      case 1:
         return goDoctorMenu1();
      case 2:
         return goDoctorMenu2();
      case 3:
         return goDoctorMenu3();
      case 4:
         return goDoctorMenu4();
         default:
            UserInterface.getInstance().printDoctorError();
            return goMainMenu();
            
      }
      }else {
    	  UserInterface.getInstance().printDoctorError();
          return goMainMenu();
      }
   }

	private boolean goPatientMenu1() {
		UserInterface.getInstance().printPatientMenu1UI();
		String input = this.scan.nextLine();
		if (StringChecker.checkOneThree(input)) {
			switch (Integer.parseInt(input)) {
			case 1:
				UserInterface.getInstance().printDoctorInformation();
				String diagnosis = "";
				diagnosis = this.scan.nextLine();
				
				
				// 스트링체크
				// DBManager.addPatient(diagnosis);
				// DBManager.addAppointment(appointment);
				if (stringCheck.checkDiag(diagnosis)) {
					System.out.println("진료예약이 완료되었습니다.");
					return goMainMenu();
				} else {
					UserInterface.getInstance().printInputError();
					return goMainMenu();
				}
			case 2:
				UserInterface.getInstance().printCheckUpInformation();
				String checkup = "";
				checkup = this.scan.nextLine();
				// stringcheck
				
				// DBManager.addCheckup(checkup);
				if (StringChecker.checkCheckUp(checkup)) {
					System.out.println("검사예약이 완료되었습니다.");
					return goMainMenu();
				} else {
					System.out.println("올바르지 않은 입력입니다.or 초진입니다.");
					return goMainMenu();
				}
					
				
			case 3:
				UserInterface.getInstance().printBedInformation();
				String Bed = this.scan.nextLine();
				// stringcheck
				// DBManager.addBeds(bed);
				
				if (StringChecker.checkAdmission(Bed)) {
					System.out.println("입원예약이 완료되었습니다.");
					return goMainMenu();
				} else {
					UserInterface.getInstance().printInputError();
				}
			}
		} else {
			UserInterface.getInstance().printMainError();
			return goMainMenu();
		}

		return false;
   }
   private boolean goPatientMenu2() {
      UserInterface.getInstance().printAppointPatient();
      String Input = this.scan.nextLine();
      if(StringChecker.checkPersonalNum(Input)) {
    	 dbManager.printBookings(Input.split("\\/")[1]);
      }//stringcheck
      //만약 예약한 환자이름과 주민번호가 일치하다면
      //일치하지않다면 UserInterface.getInstance().printInputError();
      return goMainMenu();
   }
   private boolean goPatientMenu3() {
      UserInterface.getInstance().printAppointPatient();
      String Patient=this.scan.nextLine();
      if(StringChecker.checkPersonalNum(Patient)) {
    	  System.out.println("예약정보 출력");
    	  System.out.println("삭제하실 예약의 번호를 입력하세요.");    	  
    	  String num=this.scan.nextLine();
    	  if(StringChecker.cancelAppoint(num)) {
    		  System.out.println("삭제완료되었습니다.");
    		  return goMainMenu();
    	  }else {
    		  UserInterface.getInstance().printInputError();
    		  return goMainMenu();
    	  }
      } //stringcheck
     
      //올바르지않을 경우  UserInterface.getInstance().printInputError();
      else
         UserInterface.getInstance().printInputError();
      return goMainMenu();
   }
   private boolean goPatientMenu4() {
      UserInterface.getInstance().printAppointPatient();
      String patientLine=this.scan.nextLine();
      if(StringChecker.checkPersonalNum(patientLine)) {
    	  Patient patient = dbManager.getPatient(patientLine.split("\\/")[1]);
    	  dbManager.printBookings(patient.getPatientId());
    	  System.out.println("수납하실 예약의 번호를 입력하세요.");
    	  String num=this.scan.nextLine();
    	  if(StringChecker.payment(num)) {
    		  dbManager.setHaspaid(patient, dbManager.getBookings(patient.getPatientId()), Integer.parseInt(num));
    		  System.out.println("수납완료되었습니다.");
    		 return goMainMenu();
    	  }    	  
      }//stringcheck
      // 올바르지 않을경우 UserInterface.getInstance().printInputError();
      else
         UserInterface.getInstance().printInputError();
      return goMainMenu();
   }
   private boolean goDoctorMenu1() {
      System.out.println("진료목록을 조회할 의사번호를 입력하세요.");
      String input=this.scan.nextLine();
      if(StringChecker.checkOneThree(input)) {
      switch(Integer.parseInt(input)) {
      case 1:
         //Doctor Doctor=new Doctor();
         System.out.println("1번 의사의 환자정보 출력");
         return goMainMenu();
      case 2:
         System.out.println("2번 의사의 환자정보 출력");
         return goMainMenu();
      case 3:
         System.out.println("3번 의사의 환자정보 출력");
         return goMainMenu();
         default:
            return goMainMenu();
      }}else {
    	  UserInterface.getInstance().printMainError();
    	  return goMainMenu();
      }
      
   }
   private boolean goDoctorMenu2() {
      System.out.println("조회할 검사번호를 입력하세요.");
      String input=this.scan.nextLine();
      if(StringChecker.checkOneThree(input)) {
      switch(Integer.parseInt(input)) {
      case 1:
         //CheckUp CheckUp=new CheckUp();
         System.out.println("1번 검사를 예약한 환자정보 출력");
         return goMainMenu();
      case 2:
         System.out.println("2번 검사를 예약한 환자정보 출력");
         return goMainMenu();
      case 3:
         System.out.println("3번 검사를 예약한 환자정보 출력");
         return goMainMenu();
         default:
            return goMainMenu();
      }}else {
    	  UserInterface.getInstance().printMainError();
    	  return goMainMenu();
      }
   }
   private boolean goDoctorMenu3() {
      System.out.println("조회할 방번호를 입력하세요.");
      String input=this.scan.nextLine();
      if(StringChecker.lookupBed(input)) {
      switch(Integer.parseInt(input)) {
      case 1:
         //Room Room=new Room();
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 2:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 3:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 4:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 5:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 6:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 7:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 8:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 9:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 10:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 11:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 12:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 13:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 14:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 15:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 16:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 17:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 18:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 19:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
      case 20:
         System.out.println("1~4번 침대정보 출력");
         return goMainMenu();
         default:
            return goMainMenu();
      }}else {
    	  System.out.println("1~20 사이 정수를 입력하세요.");
    	  return goMainMenu();
      }
   }
   private boolean goDoctorMenu4() {
      System.out.println("수납여부를 조회할 환자의 주민번호를 입력하세요.");
      String input=this.scan.nextLine();
      if(StringChecker.checkOnlyPersonalNum(input)) {
    	  System.out.println("수납여부 목록 출력");    	  
    	  return goMainMenu();
      }//stringcheck
      else {
    	  System.out.println("13자리 정수의 주민번호를 입력하세요.");
    	  return goMainMenu();
      }
   }
}