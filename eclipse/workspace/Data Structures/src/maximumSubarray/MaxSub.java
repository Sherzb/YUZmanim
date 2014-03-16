package maximumSubarray;

import java.util.Scanner;

/**
 * A class for the Maximum-Subarray Problem, solved in linear time.
 * @author Shmuel
 *
 */
public class MaxSub
{
	public static Scanner s = new Scanner(System.in);
	public static int[] allNumbers;
	static int leftIndex = 0;
	static int rightIndex = 0;
	static int highestArray = 0;
	static int recentArray = 0;
	static int recentArrayLeftIndex = 0;

	/**
	 * Main method, which sets up the input, solves the problem, and prints out the results (via 3 method calls)
	 * @param args
	 */
	public static void main(String[] args) 
	{
		setup();
		evaluate();
		print();
	}

	/**
	 * Creates an array that stores all the integers. Users input the numbers, separated by commas.
	 */
	public static void setup() 
	{
		System.out.println("Input all your numbers, separated by commas");
		String[] allNumString = s.nextLine().split(",");
		allNumbers = new int[allNumString.length];
		for (int i = 0; i < allNumString.length; i++) {
			allNumbers[i] = Integer.parseInt(allNumString[i]);
		}
	}

	/**
	 * Evaluates the leftIndex, rightIndex, and the maximum subarray of a given array. It does this with the help of a variable
	 * "recentArray", which is the maximum subarray of an array A[1..j] that includes A[j]. The method for that is by checking whether
	 * the maximum subarray that included A[j-1], the array A[i...j-1], i <= j, is positive or not. If it is, then the maximum subarray that 
	 * includes A[j] is A[i...j]. If not, the maximum is simply A[j]. It then sees if recentArray is greater than the highest array
	 * accounted for so far. If it is, then it is the highest array. Indexes are managed accordingly.
	 */
	public static void evaluate() 
	{
		for(int i = 0; i < allNumbers.length; i++) {
			int nextNumber = allNumbers[i];

			if(recentArray >= 0) { //The array that included A[j-1] is positive
				recentArray += nextNumber; //The maximum that includes A[j] is added to what we had before
			}
			else { //The array that included A[j-1] is negative
				recentArray = nextNumber; //The maximum that includes A[j] is just A[j]
				recentArrayLeftIndex = i;
			}

			if(recentArray >= highestArray) { //The array that includes A[j] is higher than what we had so far
				highestArray = recentArray; //Modify what the highest is
				rightIndex = i;
				leftIndex = recentArrayLeftIndex;
			}
		}		
	}

	/**
	 * Prints out the left and right indices of the maximum subarray, as well as the total.
	 */
	public static void print() 
	{
		System.out.println("The greatest subarray is between index " + leftIndex + " and " + rightIndex);
		System.out.println("The total is " + highestArray);
	}
}
