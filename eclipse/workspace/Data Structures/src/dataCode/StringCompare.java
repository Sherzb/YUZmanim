package dataCode;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Compares the second letters of a String
 * @author Shmuel
 *
 */
public class StringCompare
{
	
	private static String[] stringArray = {"Hello", "Goodbye", "word", "Word", "sfjsdufn"};
	
	public static void main(String[] args) {
		//StringCompare2 c = new StringCompare2();
		//Arrays.sort(stringArray, c);
		Arrays.sort(stringArray, new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.substring(1,2).compareTo(s2.substring(1,2));
			}
		});
		print();
		
	}
	
	public static void print() {
		System.out.println();
		for(String string: stringArray) {
			System.out.print(string + ",");
		}
	}
	
	public static class StringCompare2 implements Comparator<String>{
		public int compare(String s1, String s2) {
			String s1Letter = s1.substring(1,2);
			String s2Letter = s2.substring(1,2);
			return s1Letter.compareTo(s2Letter);
		}
	}
}
