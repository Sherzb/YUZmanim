package sudoku;

import java.util.ArrayList;

public class ManualInput
{

	public static int[][] puzzle = new int[9][9];

	public static int[][] mainMethod() {

		int[] completedNumbers = {1,2,3,4,5,6,7,8,9,
				4,5,6,7,8,9,1,2,3,
				7,8,9,1,2,3,4,5,6,
				2,3,4,5,6,7,8,9,1,
				5,6,7,8,9,1,2,3,4,
				8,9,1,2,3,4,5,6,7,
				3,4,5,6,7,8,9,1,2,
				6,7,8,9,1,2,3,4,5,
				9,1,2,3,4,5,6,7,8};
		ArrayList<Integer> completeArray = new ArrayList<Integer>();
		for(Integer number : completedNumbers) {
			completeArray.add(number);
		}
		int counter = 0;
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				puzzle[i][j] = completeArray.get(counter);
				counter++;
			}
		}
		return puzzle;
	}

	public static int[][] mainMethod2() {
		int[] completedNumbers = {0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,
				3,4,5,6,7,8,9,1,2,
				6,7,8,9,1,2,3,4,5,
				9,1,2,3,4,5,6,7,8};
		ArrayList<Integer> completeArray = new ArrayList<Integer>();
		for(Integer number : completedNumbers) {
			completeArray.add(number);
		}
		int counter = 0;
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				puzzle[i][j] = completeArray.get(counter);
				counter++;
			}
		}
		return puzzle;
	}
}
