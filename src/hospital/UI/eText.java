package hospital.UI;

public enum eText{
   //MAIN : Main 화면에서 사용 하기 위한 텍스트
      MAIN_TITLE("병원예약관리 시스템입니다."),
      MAIN_MENU_1("1. 환자"),
      MAIN_MENU_2("2. 의사"),
      MAIN_MENU_3("3. 종료하기"),
      MAIN_ERROR("[1,3]의 정수를 입력하세요."),
      
      MENU1_RESULT_1("1. 예약 등록"),
      MENU1_RESULT_2("2. 예약 조회"),
      MENU1_RESULT_3("3. 예약 취소"),
      MENU1_RESULT_4("4. 수납하기"),
      MENU1_ERROR("[1,4]의 정수를 입력하세요."),
      

      MENU2_RESULT_1("1. 진료 목록 조회"),
      MENU2_RESULT_2("2. 검사 목록 조회"),
      MENU2_RESULT_3("3. 입원 목록 조회"),
      MENU2_RESULT_4("4. 수납 여부 조회"),
      MENU2_ERROR("[1,4]의 정수를 입력하세요."),
      
      Patient_Menu1("1. 진료"),
      Patient_Menu2("2. 검사"),
      Patient_Menu3("3. 입원"),
      
      Doctor_information("의사정보입니다."+"\n"+"1. 안과,안철수,의사번호 : 1"
            +"\n"+"2. 외과,김선경,의사번호 : 2"
            +"\n"+"3. 내과,박찬모,의사번호 : 3"),
      Patient_Menu1_Information("이름,주민번호,의사번호,시작시간 순으로 입력하세요."),
      
      CheckUp_Information("검사번호와 검사이름입니다."+"\n"+"1. 내시경"+"\n"
      +"2. 피검사"+"\n"+"3. 초음파"),
      CheckUp_InputInformation("이름,주민번호,검사번호,시작시간 순으로 입력하세요."),
      Bed_Information("침대위치,침대번호 출력"),
      Bed_InputInformation("이름,주민번호,침대번호,시작시간/종료시간 순으로 입력하세요."),
      Appointment_Patient("예약하신 환자의이름,주민번호를 입력하세요."),
      Input_Error("올바르지 않은 입력입니다."),
         ;      
       final private String text;
      private eText(String text){
         this.text = text;}
      //System.out.println(this.text); unit test         }
      public String getText(){
         return text;
      }
}