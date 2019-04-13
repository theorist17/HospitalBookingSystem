package hospital.input;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import hospital.data.Appointment;
import hospital.data.Booking;
import hospital.data.Checkup;
import hospital.data.Doctor;
import hospital.data.Stay;

import java.sql.PreparedStatement;
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

	/**
	 * 전달받은 부킹배열과 시간을 대조해 겹치면 참을 반환
	 * @param bookings
	 * @param entryStart
	 * @param entryEnd
	 * @return
	 */
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
	 * 전달받은 부킹배열과 시간을 대조해 시간이 겹치고, "Appointment" "Checkup" "Stay"에 따라 같은 의사, 검사, 침대번호의 자료가 있으면 
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
	
	/**
	 * 전달받은 부킹배열과 시간을 대조해 시간이 겹치고, Appointment 테이블에 patientID랑 겹치는지 확인
	 * @param bookings
	 * @param entryStart
	 * @param entryEnd
	 * @param entryType 타입까지 살펴보기; 시간 겹치는 예약이 있더라도 예약의 유형이 다르면 겹치는 것이 아님
	 * 예를 들어 진료 1~2가 예약되어있는데, 입원 1~2가 예약된 경우 false를 반환 
	 * @return
	 */
	public static boolean isOverlapped(List<Booking> bookings, String entryStart, String entryEnd, String entryID) {
		
		try {
			Date newStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entryStart);
			Date newEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entryEnd);
			for (int i = 0; i < bookings.size(); i++) {
				Appointment appointment = (Appointment) bookings.get(i);
				// 오직 인자로 주어진 자료형의 객체를 대상으로
				Date oldStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bookings.get(i).getTimeStart());
				Date oldEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bookings.get(i).getTimeEnd());

				if ((newStart.compareTo(oldEnd) < 0 && oldStart.compareTo(newEnd) < 0)
						|| (oldStart.compareTo(newStart) < 0 && newEnd.compareTo(oldEnd) < 0)
						|| (oldStart.compareTo(newStart) == 0 && newEnd.compareTo(oldEnd) == 0)) {
						// 이미 입력된 old 시간슬롯과 new 시간슬롯이 겹치는 경우 
						if (entryID.equals(appointment.getPatientID()))
							return true;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
