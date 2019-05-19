package hospital.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.*;


import hospital.data.Appointment;
import hospital.data.Booking;
import hospital.data.Checkup;
import hospital.data.Doctor;
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

	private boolean goPatientMenu1() {
		UserInterface.getInstance().printPatientMenu1UI();
		String input = this.scan.nextLine();
		if (StringChecker.checkOneThree(input)) {
			switch (Integer.parseInt(input)) {
			case 1:
				goAppointmentMenu();
				return goMainMenu();
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
	
	private boolean goAppointmentMenu() {
		UserInterface.getInstance().printAppointmentMenu();
		String input = this.scan.nextLine();
		if (StringChecker.checkOneTwo(input)) {
			switch (Integer.parseInt(input)) {
			case 1:
				if(!goChooseDoctor()) {
					UserInterface.getInstance().printAppointmentResultError();		
					return false;
				}
				return true;
			case 2:
				return goChooseDepartment();
			default:
				UserInterface.getInstance().printAppointmentError();
				return goMainMenu();
			}
		} else {
			UserInterface.getInstance().printAppointmentError();
			return goMainMenu();
		}
	}
	private boolean goChooseDoctor() {
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
				dbManager.addPatient(new Patient(patientID, patientName, 0));

			// 예약을 하려는 이가 의사일 경우 
			Doctor doctor = dbManager.getDoctor(patientID);
			if(doctor != null) {
				//String name, int doctorID, String timeStart, String timeEnd, String department, int onDept
				if(!dbManager.executeCheckDoctorAppoint(patientName, doctorID, timeStart, timeEnd, dbManager.getDoctor(doctorID).getDepartment(), 0)) {
					System.out.println("주어진 시간으로 " + doctor.getName()+" 의사 " + doctorID + "번 의사에게 예약 할 수 없음");
					// 5.1 이미 어떤 다른 환자가 자신을 지정해서 진료를 예약한 시간이거나, 진료과만 지정해서 예약했지만 그때 자신의 진료과에 자신 밖에 없는 시간이면, 그 시간 구간에는 자신은 진료받지 못합니. (일 우선)     
					// 5.2 이미 어떤 다른 환자가 진료과만 지정해서 예약했고 자신이 담당하기로 되었더라도, 그 시간에 다른 (같은 진료과의) 의사가 있고 스케쥴이 비어있으면 자신은 진료예약 할 수 있습니다.
					// 5.3 자신이 진료를 받고있는 동안에는, 자신의 원래 진료시간이더라도 환자들은 그 시간 동안 그 의사에게 진료를 예약하지 못합니다. 
					return false;
				} else {
					return true;
				}
			}
			
			// 환자가 진료 예약 하려는 의사가 해당 시간에 근무 중 인지 조사
			if(!dbManager.executeDoctorWorkTime(timeStart, timeEnd, doctorID)) {
				System.out.println(doctorID + "번 의사의 근무시간이 아님");
				return false;
			}
			
			// 환자가 진료 예약 하려는 의사가 해당 시간에 진료가 잡혀있는지 조사
			if(!dbManager.executeDoctorAvailable(timeStart, timeEnd, doctorID)) {
				System.out.println(doctorID + "번 의사가 이미 진료예약 되어 있음");
				return false;
			}
			
			// 환자가 진료 예약 하려는 의사가 검사나 입원을 했는지 조사
			if(!dbManager.executePainDoctor(timeStart, timeEnd, dbManager.getPatient(doctorID).getPatientId())) {
				System.out.println(doctorID + "번 의사가 검사/입원을 할 예정임");
				return false;
			}
			
			// 환자 자신이 (입력한 시간에) 진료/검사를 이미 예약해 둔 경우 배제 (중복예약)
			List<Booking> bookings = dbManager.getBookings(patientID);
			bookings.removeIf(booking -> booking instanceof Stay); // 입원과는 겹쳐도 되므로 제거
			if (ClockManager.isOverlapped(bookings, timeStart, timeEnd)) {
				UserInterface.getInstance().printTimeOverlapError();
				return goMainMenu();
			}

			// 입력된 진료예약정보 등록
			if (dbManager.addAppointment(new Appointment(patientID, doctorID, timeStart, timeEnd, 0, 0)) > 0)
				System.out.println("진료예약이 완료되었습니다.");

			return goMainMenu();
		} else {
			UserInterface.getInstance().printInputError();
			return goMainMenu();
		}
	}
	private boolean goChooseDepartment() {
		UserInterface.getInstance().printDepartmentInformation();;
		String diagnosis = "";
		diagnosis = this.scan.nextLine();

		if (StringChecker.checkDiag2(diagnosis)) {
			String[] word = diagnosis.split("\\/");
			String patientName = word[0];
			String patientID = word[1];
			String deptName = null;
			switch(Integer.parseInt(word[2])) {
			case 1:
				deptName = "안과";
				break;
			case 2:
				deptName = "외과";
				break;
			case 3:
				deptName = "내과";
				break;
			default:
				break;
			}
			String timeStart = ClockManager.clockDiag(word[3]);
			String timeEnd = ClockManager.addHour(timeStart);

			// 환자 확인
			Patient patient = dbManager.getPatient(patientID);
			if (patient == null || !patient.getName().equals(patientName)) // 조회불가능 - 초진, 잘못된 입력, ..
				dbManager.addPatient(new Patient(patientID, patientName, 0));
			
			// 예약을 하려는 이가 의사일 경우 
			Doctor doctor = dbManager.getDoctor(patientID);
			if(doctor != null) {
				//String name, int doctorID, String timeStart, String timeEnd, String department, int onDept
				if(!dbManager.executeCheckDoctorAppoint(patientName, 1, timeStart, timeEnd, deptName, 1)) {
					System.out.println("주어진 시간으로 " + deptName+" 과 의사에게 예약 할 수 없음");
					// 5.1 이미 어떤 다른 환자가 자신을 지정해서 진료를 예약한 시간이거나, 진료과만 지정해서 예약했지만 그때 자신의 진료과에 자신 밖에 없는 시간이면, 그 시간 구간에는 자신은 진료받지 못합니. (일 우선)     
					// 5.2 이미 어떤 다른 환자가 진료과만 지정해서 예약했고 자신이 담당하기로 되었더라도, 그 시간에 다른 (같은 진료과의) 의사가 있고 스케쥴이 비어있으면 자신은 진료예약 할 수 있습니다.
					// 5.3 자신이 진료를 받고있는 동안에는, 자신의 원래 진료시간이더라도 환자들은 그 시간 동안 그 의사에게 진료를 예약하지 못합니다. 
					return false;
				} else {
					return true;
				}
			}
			
			// 해당 시간대에 근무 중인 의사
			List<Doctor> workingDoctors = dbManager.executeCheckTime(timeStart, timeEnd, deptName); // 목표로 하는 진료과 의사들
			
			// 목표 진료과에 모든 가능한 근무시간대의 의사 중에서 없는 한가한 의사 찾기 
			
			List<Doctor> availDoctors = new ArrayList<Doctor>();
			for (int i = 0 ; i < workingDoctors.size(); i++) {
				List<Booking> doctorSchedule = dbManager.getAppointments(workingDoctors.get(i).getDoctorId()); // 각 의사의 일정 
				if(!ClockManager.isOverlapped(doctorSchedule, timeStart, timeEnd)) // 그 의사의 일정과 입력이 겹침	
					availDoctors.add(workingDoctors.get(i));
			}
			

			// 랜덤 의사 선택 & 의사 번호 지정
			Random rand = new Random();
			int doctorID = availDoctors.get(rand.nextInt(availDoctors.size())).getDoctorId();

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
			if (dbManager.addAppointment(new Appointment(patientID, doctorID, timeStart, timeEnd, 0, 1)) > 0)
				System.out.println("진료예약이 완료되었습니다.");

			return goMainMenu();
		} else {
			UserInterface.getInstance().printInputError();
			return goMainMenu();
		}
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

	   // import java.io.*;
	   public static void pause() {
	       try {
	         System.in.read();
	       } catch (IOException e) { }
	   }
	   
	   private boolean goDoctorMenu1() {
	      System.out.println("진료목록을 조회할 의사번호를 입력하세요.");
	      String input = this.scan.nextLine();
	      if (StringChecker.checkOneNine(input)) {
	         // 의사의 의사번호를 통해서 DB에 접근하기
	         dbManager.printBookings_for_doctor(dbManager.getBookings_for_appointment(Integer.parseInt(input)));
	         //pause();
	         return goMainMenu();   
	      } else {
	         UserInterface.getInstance().printMainError1();
	         return goMainMenu();
	      }

	   }
	   
	   private boolean goDoctorMenu2() {
	      System.out.println("조회할 검사번호를 입력하세요.");
	      String input = this.scan.nextLine();
	      System.out.println(Integer.parseInt(input) + "번 검사를 예약한 환자정보 출력");
	      if (StringChecker.checkOneThree(input)) {
	         // 검사번호를 통해서 DB에 접근하기
	         //dbManager.printBookings_for_doctor(dbManager.getBookings_for_checkup(Integer.parseInt(input)));
	         //pause();
	         return goMainMenu();   
	      } else {
	         UserInterface.getInstance().printMainError();
	         return goMainMenu();
	      }
	   }

	   private boolean goDoctorMenu3() {
	      System.out.println("조회할 방번호를 입력하세요.");
	      String input = this.scan.nextLine();
	      if (StringChecker.lookupBed(input)) {
	         // 방 번호를 통해서 DB에 접근하기
	         dbManager.printBookings_for_doctor(dbManager.getBookings_for_stay(Integer.parseInt(input)));
	         //pause();
	         return goMainMenu();   
	      } else {
	         System.out.println("1~6 사이 정수를 입력하세요.");
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
	   }}