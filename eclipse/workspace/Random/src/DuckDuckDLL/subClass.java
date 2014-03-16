package DuckDuckDLL;

public class subClass extends SuperClass{
	private int number = 9;

	public static void main(String[] args) {
		subClass thing = new subClass();
		thing.print();
	}

	/**
	public int getNumber() {
		return number;
	}
	 */


	public void print() {
		System.out.println(getNumber());
		System.out.println(number);
	}
}