
public class PassParamTest
{
	
	private static String string = "hello";
	public static void main(String[] args) {
		change(string);
		System.out.println(string);
	}
	public static void change(String input) {
		input = "Goodbye";
	}
}
