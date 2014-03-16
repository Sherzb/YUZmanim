import java.util.Scanner;


public class hexConverter
{
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("Type in 0 to go from Hex to Dec, or 1 to go from Dec to Hex");
		String choice = s.nextLine();
		System.out.println("Type what you want to convert");
		String input = s.nextLine();
		if (choice.equals("0")) {
				System.out.println(hexToDec(input));
		}
		else {
			System.out.println(decToHex(input));
		}
		s.close();
	}
	
	public static Integer hexToDec(String input) {
		return Integer.parseInt(input, 16);
	}
	
	public static String decToHex(String input) {
		return Integer.toHexString(Integer.parseInt(input));
	}
}

