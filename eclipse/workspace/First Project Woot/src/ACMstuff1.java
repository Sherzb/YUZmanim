import java.util.ArrayList;
import java.util.Scanner;

public class ACMstuff1 {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String numOfProbs = s.nextLine();
		for(int i = 1; i <= Integer.parseInt(numOfProbs); i++) {
			String[] allArgs = s.nextLine().split(" ");
			String probNum = allArgs[0] ;
			int digits = Integer.parseInt(allArgs[1]);
			int answer = doIt(digits);
			System.out.println(probNum + " " + answer);
		}
	}

	public static int doIt(int digits) {
		if(digits == 1) {
			return 10;
		}
		ArrayList<Integer> array1 = new ArrayList<Integer>();
		for(int i = 0; i <= 9; i++) {
			array1.add(1);
		}
		ArrayList<Integer> finalArray = recursive(array1, digits);
		int total = 0;
		for(int i = 0; i <= 9; i++) {
			total += finalArray.get(i);
		}
		return total;
	}

	public static ArrayList<Integer> recursive(ArrayList<Integer> array, int digits) {
		ArrayList<Integer> array1 = new ArrayList<Integer>();
		if(digits == 1) {
			return array;
		}
		ArrayList<Integer> nextArray = new ArrayList<Integer>();
		int counter = 0;
		for(int i = 0; i <= 9; i++) {
			int newNum = 0;
			for(int j = 0; j <= counter; j++) {
				newNum = newNum + array.get(j);
			}
			nextArray.add(newNum);
			counter++;
		}
		return recursive(nextArray, digits - 1);
	}
}

