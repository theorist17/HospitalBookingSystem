package hospital.input;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hospital.data.Appointment;
import hospital.data.Booking;
import hospital.data.Checkup;
import hospital.data.Stay;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ClockManager {

	public static String clockDiag(String strFormatTime) {

		String pattern = "yyyyMMddHH";
		DateFormat date = new SimpleDateFormat(pattern);
		Date diagDate = null;

		try {
			diagDate = date.parse(strFormatTime);
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(diagDate);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String addHour(String mysqlFormatTime) {
		Calendar calendar = Calendar.getInstance();
		try {
			Date dateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(mysqlFormatTime);
			calendar.setTime(dateStart);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		Date dateEnd = calendar.getTime();
		String timeEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateEnd);
		return timeEnd;
	}

	public static boolean isOverlapped(List<Booking> bookings, String entryStart, String entryEnd) {

		try {
			Date newStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entryStart);
			Date newEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entryEnd);
			for (int i = 0; i < bookings.size(); i++) {
				Date oldStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bookings.get(i).getTimeStart());
				Date oldEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bookings.get(i).getTimeEnd());
				if (newStart.compareTo(oldEnd) < 0 && oldStart.compareTo(newEnd) < 0)
					return true;
				else if (oldStart.compareTo(newStart) < 0 && newEnd.compareTo(oldEnd) < 0)
					return true;
				else if (oldStart.compareTo(newStart) == 0 && newEnd.compareTo(oldEnd) == 0)
					return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param bookings
	 * @param entryStart
	 * @param entryEnd
	 * @param entryType 타입까지 살펴보기; 시간 겹치는 예약이 있더라도 예약의 유형이 다르면 겹치는 것이 아님
	 * 예를 들어 진료 1~2가 예약되어있는데, 입원 1~2가 예약된 경우 false를 반환 
	 * @return
	 */
	public static boolean isOverlapped(List<Booking> bookings, String entryStart, String entryEnd, String entryType, int entryID) {
		
		try {
			Date newStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entryStart);
			Date newEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entryEnd);
			for (int i = 0; i < bookings.size(); i++) {
				Booking booking = bookings.get(i);
				String pacakgeClassName = booking.getClass().getName(); // 예약의 객체의 자료형 조사 ; Appointment/Checkup/Stay 중
																		// 하나 ;
				if (pacakgeClassName.contains(entryType)) {
					// 오직 인자로 주어진 자료형의 객체를 대상으로
					Date oldStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bookings.get(i).getTimeStart());
					Date oldEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bookings.get(i).getTimeEnd());

					if ((newStart.compareTo(oldEnd) < 0 && oldStart.compareTo(newEnd) < 0)
							|| (oldStart.compareTo(newStart) < 0 && newEnd.compareTo(oldEnd) < 0)
							|| (oldStart.compareTo(newStart) == 0 && newEnd.compareTo(oldEnd) == 0)) {
						// 이미 입력된 old 시간슬롯과 new 시간슬롯이 겹치는 경우 
						if (booking instanceof Appointment) { // (의사번호가) 같은 의사에게 예약한 경우는 시간 중복 
							if (entryID == ((Appointment) booking).getDoctorID())
								return true;
						} else if (booking instanceof Checkup) { // (검사번호가) 같은 검사를 예약한 경우는 시간 중복 
							if (entryID == ((Checkup) booking).getTestID())
								return true;
						} else if (booking instanceof Stay) { // (침대번호가) 같은 침대를 예약한 경우는 시간 중복 
							if (entryID == ((Stay) booking).getBedID())
								return true;
						}
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

//
//	public static Date clockCheckUp(String checkUp) {
//
//		String pattern = "yyyy-MM-dd HH:mm:ss";
//		DateFormat date = new SimpleDateFormat(pattern);
//		Date checkUpDate = null;
//
//		try {
//			checkUpDate = date.parse(checkUp);
//			return checkUpDate;
//
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return checkUpDate;
//	}
//
//	public static Date clockStartAdmission(String admission) {
//
//		String pattern = "yyyy-MM-dd HH:mm:ss";
//		DateFormat date = new SimpleDateFormat(pattern);
//		Date startAdmissionDate = null;
//
//		try {
//			startAdmissionDate = date.parse(admission);
//			return startAdmissionDate;
//
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return startAdmissionDate;
//	}
//
//	public static Date clockEndAdmission(String admission) {
//
//		String pattern = "yyyy-MM-dd HH:mm:ss";
//		DateFormat date = new SimpleDateFormat(pattern);
//		Date endAdmissionDate = null;
//
//		try {
//			endAdmissionDate = date.parse(admission);
//			return endAdmissionDate;
//
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return endAdmissionDate;
//	}
}
