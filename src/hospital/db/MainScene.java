package hospital.db;

import java.util.List;
import java.util.Scanner;

import hospital.data.Appointment;
import hospital.data.Booking;
import hospital.data.Checkup;
import hospital.data.Patient;
import hospital.data.Stay;
import hospital.input.ClockManager;
import hospital.input.StringChecker;
import hospital.ui.UserInterface;

public class MainScene {
	DBManager dbManager;
	Scanner scan = new Scanner(System.in);
	ClockManager clockManager;

	public void doProcess() {
		dbManager = new DBManager();
		clockManager = new ClockManager();
		dbManager.LoadDriver();
		dbManager.LoadConnection();
		while (this.goMainMenu()) {
		}
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
		if (StringChecker.checkOneFour(input)) {
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
		if (StringChecker.checkOneFour(input)) {
			switch (Integer.parseInt(input)) {
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
		} else {
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

				if (StringChecker.checkDiag(diagnosis)) {
					String[] word = diagnosis.split("\\/");
					String patientName = word[0];
					String patientID = word[1];
					int doctorID = Integer.parseInt(word[2]);
					String timeStart = ClockManager.clockDiag(word[3]);
					String timeEnd = ClockManager.addHour(timeStart);

					// 환자 확인
					Patient patient = dbManager.getPatient(patientID);
					if (patient == null || !patient.getName().equals(patientName)) // 조회불가능 - 초진, 잘못된 입력, ..
						dbManager.addPatient(new Patient(patientID, patientName));

					// 환자 자신이 (입력한 시간에) 진료/검사를 이미 예약해 둔 경우 배제 (중복예약)
					List<Booking> bookings = dbManager.getBookings(patientID);
					bookings.removeIf(booking -> booking instanceof Stay); // 입원과는 겹쳐도 되므로 제거
					if (ClockManager.isOverlapped(bookings, timeStart, timeEnd)) {
						UserInterface.getInstance().printTimeOverlapError();
						return goMainMenu();
					}

					// 모든 환자가 (입력한 시간에) (입력한 의사번호로) 진료/검사를 이미 예약해 둔 경우 배제 (선점된 예약)
					bookings = dbManager.getBookings(timeStart, timeEnd);
					if (ClockManager.isOverlapped(bookings, timeStart, timeEnd, "Appointment", doctorID)) {
						UserInterface.getInstance().printTimeOverlapError();
						return goMainMenu();
					}

					// 입력된 진료예약정보 등록
					if (dbManager.addAppointment(new Appointment(patientID, doctorID, timeStart, timeEnd, 0)) > 0)
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

				if (StringChecker.checkCheckUp(checkup)) {
					String[] word = checkup.split("\\/");
					String patientName = word[0];
					String patientID = word[1];
					int testID = Integer.parseInt(word[2]);
					String timeStart = ClockManager.clockDiag(word[3]);
					String timeEnd = ClockManager.addHour(timeStart);

					// 환자 확인
					Patient patient = dbManager.getPatient(patientID);
					if (patient == null) {
						UserInterface.getInstance().printVisitError();
						return goMainMenu();
					} else if (!patient.getName().equals(patientName)) {
						UserInterface.getInstance().printInputError();
						return goMainMenu();
					}

					// 환자 자신이 입력한 시간에 진료/검사를 이미 예약해 둔 경우 배제 (중복예약)
					List<Booking> bookings = dbManager.getBookings(patientID); // 한 환자의 예약
					bookings.removeIf(booking -> booking instanceof Stay); // 입원과 겹쳐도 되므로 제거
					if (ClockManager.isOverlapped(bookings, timeStart, timeEnd)) {
						UserInterface.getInstance().printTimeOverlapError();
						return goMainMenu();
					}

					// 모든 환자가 (입력한 시간에) (입력한 검사번호로) 진료/검사를 이미 예약해 둔 경우 배제 (선점된 예약)
					bookings = dbManager.getBookings(timeStart, timeEnd); // 모든 환자의 예약
					if (ClockManager.isOverlapped(bookings, timeStart, timeEnd, "Checkup", testID)) { // Checkup 중에 매칭
																										// 찾기
						UserInterface.getInstance().printTimeOverlapError();
						return goMainMenu();
					}

					if (dbManager.addCheckup(new Checkup(patientID, testID, timeStart, timeEnd, 0)) > 0)
						System.out.println("검사예약이 완료되었습니다.");

					return goMainMenu();
				} else {
					UserInterface.getInstance().printInputError();
					return goMainMenu();
				}

			case 3:
				UserInterface.getInstance().printBedInformation();
				String stay = this.scan.nextLine();

				if (StringChecker.checkAdmission(stay)) {
					String[] word = stay.split("\\/");
					String patientName = word[0];
					String patientID = word[1];
					int bedID = Integer.parseInt(word[2]);
					String timeStart = ClockManager.clockDiag(word[3]);
					String timeEnd = ClockManager.clockDiag(word[4]);
					if (timeStart.compareTo(timeEnd) > 0) {
						UserInterface.getInstance().printInputError(); // 시간 뒤바뀜
						return goMainMenu();
					}

					// 환자 확인
					Patient patient = dbManager.getPatient(patientID);
					if (patient == null) {
						UserInterface.getInstance().printVisitError();
						return goMainMenu();
					} else if (!patient.getName().equals(patientName)) {
						UserInterface.getInstance().printInputError();
						return goMainMenu();
					}

					// 환자 자신이 입력한 시간에 진료/검사를 이미 예약해 둔 경우 배제 (중복예약)
					List<Booking> bookings = dbManager.getBookings(patientID); // 한 환자의 예약
					bookings.removeIf(booking -> booking instanceof Appointment | booking instanceof Checkup); // 진료/검사
																												// 겹쳐도
																												// 되므로
																												// 제거
					if (ClockManager.isOverlapped(bookings, timeStart, timeEnd)) {
						UserInterface.getInstance().printTimeOverlapError();
						return goMainMenu();
					}

					// 모든 환자가 (입력한 시간에) (입력한 침대번호로) 진료/검사를 이미 예약해 둔 경우 배제 (선점된 예약)
					bookings = dbManager.getBookings(timeStart, timeEnd); // 모든 환자의 예약
					if (ClockManager.isOverlapped(bookings, timeStart, timeEnd, "Stay", bedID)) {
						UserInterface.getInstance().printTimeOverlapError();
						return goMainMenu();
					}

					if (dbManager.addStay(new Stay(patientID, bedID, timeStart, timeEnd, 0)) > 0)
						System.out.println("입원예약이 완료되었습니다.");

					return goMainMenu();
				} else {
					UserInterface.getInstance().printInputError();
					return goMainMenu();
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
		if (StringChecker.checkPersonalNum(Input)) {
			String[] word = Input.split("\\/");
			String patientName = word[0];
			String patientID = word[1];
			// 환자의 이름과 주민번호 DB에서 찾아보기
			if (dbManager.getPatient(patientID).getName().equals(patientName)) {
				dbManager.printBookings(dbManager.getBookings(patientID));
			} else {
				UserInterface.getInstance().printInputError();
			}
		}
		return goMainMenu();
	}

	private boolean goPatientMenu3() {
		UserInterface.getInstance().printAppointPatient();
		String input = this.scan.nextLine();
		if (StringChecker.checkPersonalNum(input)) {
			String[] word = input.split("\\/");
			String patientName = word[0];
			String patientID = word[1];
			// 환자의 이름과 주민번호 DB에서 찾아보기
			List<Booking> bookings = null;
			if (dbManager.getPatient(patientID).getName().equals(patientName)) {
				bookings = dbManager.printBookings(dbManager.getBookings(patientID));
			} else {
				UserInterface.getInstance().printInputError();
			}
			System.out.println("삭제하실 예약의 번호를 입력하세요.");
			String lineNumber = this.scan.nextLine();
			if (StringChecker.cancelAppoint(lineNumber)) {
				if (dbManager.deleteBooking(bookings.get(Integer.parseInt(lineNumber) - 1))) {
					System.out.println("삭제완료되었습니다.");
				} else {
					// UserInterface.getInstance().printInputError(); //DB의 삭제연산 오류
				}
				return goMainMenu();
			} else {
				UserInterface.getInstance().printInputError(); // 입력 오류
				return goMainMenu();
			}
		} else {
			UserInterface.getInstance().printInputError();
		}
		return goMainMenu();
	}

	private boolean goPatientMenu4() {
		UserInterface.getInstance().printAppointPatient();
		String patientLine = this.scan.nextLine();
		if (StringChecker.checkPersonalNum(patientLine)) {
			String[] word = patientLine.split("\\/");
			String patientName = word[0];
			String patientID = word[1];
			// 환자의 이름과 주민번호 DB에서 찾아보기
			List<Booking> bookings = null;
			if (dbManager.getPatient(patientID).getName().equals(patientName)) {
				bookings = dbManager.printBookings(dbManager.getBookings(patientID));
			} else {
				UserInterface.getInstance().printInputError();
			}
			System.out.println("수납하실 예약의 번호를 입력하세요.");
			String lineNumber = this.scan.nextLine();
			if (StringChecker.payment(lineNumber)) {
				if(dbManager.setBookingHasPaid(bookings.get(Integer.parseInt(lineNumber)-1)))
					System.out.println("수납완료되었습니다.");
//				else 
//					// DB오류
				return goMainMenu();
			}
		} // stringcheck
			// 올바르지 않을경우 UserInterface.getInstance().printInputError();
		else
			UserInterface.getInstance().printInputError();
		return goMainMenu();
	}

	private boolean goDoctorMenu1() {
		System.out.println("진료목록을 조회할 의사번호를 입력하세요.");
		String input = this.scan.nextLine();
		if (StringChecker.checkOneThree(input)) {
			switch (Integer.parseInt(input)) {
			case 1:
				// Doctor Doctor=new Doctor();
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
			}
		} else {
			UserInterface.getInstance().printMainError();
			return goMainMenu();
		}

	}

	private boolean goDoctorMenu2() {
		System.out.println("조회할 검사번호를 입력하세요.");
		String input = this.scan.nextLine();
		if (StringChecker.checkOneThree(input)) {
			switch (Integer.parseInt(input)) {
			case 1:
				// CheckUp CheckUp=new CheckUp();
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
			}
		} else {
			UserInterface.getInstance().printMainError();
			return goMainMenu();
		}
	}

	private boolean goDoctorMenu3() {
		System.out.println("조회할 방번호를 입력하세요.");
		String input = this.scan.nextLine();
		if (StringChecker.lookupBed(input)) {
			for(int i=1;i<=20;i++) {
				if(i==Integer.parseInt(input)) {
					//room
					return goMainMenu();
				}
			}return goMainMenu();
			/*
			
			switch (Integer.parseInt(input)) {
			case 1:
				// Room Room=new Room();
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
			}*/
		} else {
			System.out.println("1~20 사이 정수를 입력하세요.");
			return goMainMenu();
		}
	}

	private boolean goDoctorMenu4() {
		System.out.println("수납여부를 조회할 환자의 주민번호를 입력하세요.");
		String input = this.scan.nextLine();
		if (StringChecker.checkOnlyPersonalNum(input)) {
			System.out.println("수납여부 목록 출력");
			return goMainMenu();
		} // stringcheck
		else {
			System.out.println("13자리 정수의 주민번호를 입력하세요.");
			return goMainMenu();
		}
	}
}