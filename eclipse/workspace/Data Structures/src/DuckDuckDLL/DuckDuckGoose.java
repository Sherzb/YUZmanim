package DuckDuckDLL;

import java.util.Scanner;
import DuckDuckDLL.CircularSentinelDLL.CSDLLIterator;

/**
 * Ever wanted to play Duck Duck Goose, but didn't know Java?! Well now you can! Simply follow the directions
 * given, and you will be returned a list of the ducks hit, in their order. Which isn't really how Duck Duck Goose
 * is played. But whatever.
 * @author Shmuel
 *
 */

public class DuckDuckGoose {

	/**
	 * Takes as input the names of the ducks, the number of ducks (not used), and the skip number. It then returns
	 * the names of the ducks, in the order that they'll be hit.
	 * Note that the skip number is actually the number skipped- if 1 is given, every other duck will be hit, not every duck.
	 * @param args (Doesn't do anything with the arguments)
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("This is a simulation of the game Duck-Duck-Goose.");
		System.out.println("How many ducks are there?");
		String strm = scan.nextLine();
		System.out.println("What is the skip count?");
		String strn = scan.nextLine();
		System.out.println("What are the names of the ducks (separated by commas)?");
		String[] names = scan.nextLine().split(",");
		int m = Integer.parseInt(strm);
		int n = Integer.parseInt(strn);
		DuckDuckGoose d = new DuckDuckGoose();
		d.play(m, n, names);
		scan.close();
	}

	/**
	 * Creates a CSDLLIterator, and iterates through the ducks, based off the skip count. It then prints out the
	 * info of the next duck (the one after the skips are counted), and deletes it. It does this until the list is empty.
	 * The DLL adds elements from the front, so to reverse that, the ducks are first put in a Stack, and then emptied out
	 * of it.
	 * @param numDucks The number of ducks given
	 * @param skip The distance skipped between each duck.
	 * @param duckNames The names of the ducks
	 */
	private void play(int numDucks, int skip, String[] duckNames) {
		CircularSentinelDLL dLL = new CircularSentinelDLL();

		//Puts the ducks in the DLL in the order they were inputted, by reversing it, via a stack
		for(String name : duckNames) {
			dLL.insertAtTail(name.trim());
		}

		CSDLLIterator it = (CSDLLIterator)dLL.iterator();
		while(it.hasNext()) { //While the DLL isn't empty
			//Skips the skip number, so that the next duck is the one counted
			for(int i = 1; i <= skip; i++) {
				it.next();
			}
			//Gets the duck, prints out its info, and removes it
			String temp = (String)it.next();
			System.out.println(temp);
			it.remove();
		}
	}

}
