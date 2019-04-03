package jp.sw.ku;

public class UserInterface {
	public static void main(String[] args) {
		DBManager dbManager = new DBManager();
		dbManager.LoadDriver();
		dbManager.LoadConnection();
	}
}
