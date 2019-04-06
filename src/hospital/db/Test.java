package jp.sw.ku;

public class Test {
	int testID;
	String name;
	int price;
	public Test(String name, int price) {
		super();
		this.name = name;
		this.price = price;
	}
	public int getTestID() {
		return testID;
	}
	public void setTestID(int testID) {
		this.testID = testID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
}
