
public class First_Class_Woot {
	
	private static int num = 7;
	private static String cheeseString;
	
	public First_Class_Woot(String newString) {
		cheeseString = newString;
	}

	public static void main(String[] args) {
	      int whatever = cheese(6);
		System.out.println("Hello, world!" + num + cheeseString + whatever);
	   }
	
	public static int cheese(int number) {
		return number;
	}
}
