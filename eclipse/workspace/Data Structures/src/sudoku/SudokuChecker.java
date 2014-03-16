/**
 * Sudoku checker/filler. THIS WILL ONLY WORK IF EVERY ELEMENT OF THE ARRAY 
 * IS FILLED, AND IF "EMPTY" BOXES FILL IN THE ARRAY WITH A 0! 
 * The program automatically detects if the puzzle is filled or not. It fills the puzzle
 * via backtracking.
 * 
 * @author Shmuel Herzberg
 * @version 9/2/2013
 */

package sudoku;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;

public class SudokuChecker
{
	private static int[][] puzzle = new int[9][9];
	private static ArrayList<Integer> blankBoxes = new ArrayList<Integer>(); 
	private static int currentIndex = 0;

	/**
	 * Main method. Scans the file, decides if we need to run a checker or a filler,
	 * and then does that.
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void main(String args[]) throws FileNotFoundException, IOException
	{

		//Scanning stuff
		System.out.println("Type the file name in the line below and press enter:");
		Scanner scan = new Scanner(System.in);
		String fileName = scan.nextLine();
		scan(fileName);

		//Adds all the locations of the blank boxes to an ArrayList, if there are any.
		//The index is stored as 10*i + j, which can be retrieved via modulo and integer division.
		//10 was chosen just as a matter of neatness. 
		boolean containsEmpty = false;
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				if(puzzle[i][j] == 0) {
					blankBoxes.add(10*i + j);
					containsEmpty = true;
				}
			}
		}
		//Runs the checker if it's empty, and the filler if it's not.
		if(containsEmpty) {
			while(!(currentIndex == blankBoxes.size())) {
				//If the current index is less than 0, we had to backtrack earlier than stuff given to us, 
				//meaning that the puzzle is impossible to solve.
				if(currentIndex < 0) {
					System.out.println("The puzzle is impossible to solve.");
					return;
				}
				filler(blankBoxes.get(currentIndex));
				//The reason for the while loop is to avoid a StackOverflow
				//Error if I call the filler() method recursively, even when
				//it fails.
			}
		}
		else {
			checker();
		}
	}

	/**
	 * The scanner method that's called by the main method before anything
	 * else really happens.
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	private static void scan(String fileName) throws FileNotFoundException
	{

		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		for (int j = 0; j < 9; j++) {
			String strline = scanner.nextLine();
			int n = Integer.parseInt(strline);
			for (int i = 8; i >= 0; i--) {
				puzzle[j][i] = (n % 10);
				n = n / 10;
			}
		}
	}


	/**
	 * A general method for testing whether an array contains a
	 * duplicate. Just goes through each element of the array, and
	 * compares it to all the other elements.
	 * @param testArray The int[] that is being tested for duplicates
	 * @return True if there are NO duplicate, false if there are ANY duplicates.
	 */
	private static boolean checkDupl(int[] testArray) {
		for(int i = 0; i < 8; i++) {
			int num = testArray[i];
			for(int j = i + 1; j < 9; j++) {
				if(num == testArray[j] && num != 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Used to check if a single row has any duplicates or not. Kinda straightforward-
	 * as each row is just an array, i can throw it straight into the checkDupl method.
	 * @param The Sudoku row number 
	 * @return True if there are no duplicates, false if there are any
	 */
	private static boolean checkSingleRow(int row) {
		return checkDupl(puzzle[row]);
	}



	/**
	 * Used to check if a single column has any duplicates or not. It adds all the elements
	 * of a single column to a int[], and then runs ths checkDupl method on it.
	 * @param The number of a column.
	 * @return true if there are no duplicates, false if there are any
	 */
	private static boolean checkSingleColumn(int column) {
		int[] singleColumn = new int[9];
		for(int i = 0; i < 9; i++) {
			singleColumn[i] = puzzle[i][column];
		}
		return checkDupl(singleColumn);			
	}


	/**
	 * Given a location of a single square, returns whether or not there are any duplicates
	 * in its box.
	 * @param row The row number that the square is in.
	 * @param column The column number that the square is in.
	 * @return True if there are no duplicates, false if there are any
	 */
	private static boolean checkSingleBox(int row, int column) {
		//Makes row and column be the first row and the first column of the box in which
		//this specific cell appears. So, for example, the box at puzzle[3][7] will iterate
		//through a box from rows 3-6 and columns 6-9 (exclusive).
		row = (row / 3) * 3;
		column = (column / 3) * 3;

		//Iterates through the box
		int[] newBox = new int[9];
		int counter = 0;
		for(int i = row; i < row + 3; i++) {
			for(int j = row; j < row + 3; j++) {
				newBox[counter] = puzzle[i][j];
				counter++;
			}
		}
		return checkDupl(newBox);
	}


	/**
	 * If the puzzle is full, it will check to see if the puzzle is filled in correctly
	 */
	private static void checker() {		
		boolean correct = checkColumns() && checkRows() && checkBoxes();
		if (correct) {
			System.out.println("This Sudoko puzzle is correct.");
		}
		else {
			System.out.println("This Sudoko puzzle is incorrect.");
		}
	}

	/**
	 * Checks all the columns of the puzzle, by just calling the checkSingleColumn method
	 * for all the columns.
	 * @return True if there are no duplicates, false if there are any
	 */
	private static boolean checkColumns()
	{
		boolean noDuplicate = true;
		// We take a single column at a time.
		for (int i = 0; i < 9; i++) {
			if(!checkSingleColumn(i)) {
				noDuplicate = false;
			}
		}
		return noDuplicate;
	}

	/**
	 * Checks all the rows of the puzzle the same way all the columns are checked.
	 * @return True if there are no duplicates, false if there are any
	 */
	private static boolean checkRows()
	{
		boolean noDuplicate = true;
		// We take a single row at a time
		for (int i = 0; i < 9; i++) {
			if(!checkSingleRow(i)) {
				noDuplicate = false;
			}
		}
		return noDuplicate;
	}

	/**
	 * Goes through each of the 9 top-left squares of the 9 boxes, and checks to see
	 * if there are any duplicates in that box. The top-left boxes are permutations of
	 * (i,j), where i and j are either 0, 3, or 6.
	 * @return True if there are no duplicates, false if there are any
	 */
	private static boolean checkBoxes() {
		boolean noDupl = true;
		for(int i = 0; i < 9; i += 3) {
			for(int j = 0; j < 9; j += 3){
				if(!(checkSingleBox(i,j))) {
					noDupl = false;
				}
			}
		}
		return noDupl;
	}

	/**
	 * Fills in the puzzle. The steps are basically explained within it.
	 * @param indexOfBlank
	 */
	private static void filler(int indexOfBlank) {
		//If the current index is equal to the size of blankBoxes, meaning that we
		//went through every index of blankBoxes, meaning the puzzle is full and correct.
		if(currentIndex == blankBoxes.size()) {
			System.out.println("The puzzle is now full!");
			//Prints out the full puzzle
			for(int i = 0; i < 9; i++) {
				System.out.println(); 
				for(int j = 0; j < 9; j++) {
					System.out.print(puzzle[i][j]);					
				}
			}
			return;
		}

		//Assuming the puzzle isn't full, find the row/column of the blankBoxes index.
		int row = blankBoxes.get(currentIndex) / 10;
		int column = blankBoxes.get(currentIndex) % 10;
		//Adds one to the value of that box.
		puzzle[row][column] =  (puzzle[row][column] + 1);

		//If we looped around, meaning there was no possible match:
		if(puzzle[row][column] == 10) {
			//Do filler() on the previous box
			puzzle[row][column] = 0;
			currentIndex--;
			//In the earlier version, I called filler(currentIndex) instead of return and the while-loop
			//in the main method, but I kept getting Stack Overflow Errors because of how high the numbers
			//of recursions was.
			return;
		}
		//If the number is 1-9, but there is a duplicates:
		else if(!(checkSingleRow(row) && checkSingleColumn(column) && checkSingleBox(row, column))) {
			//Do filler() on the next box.
			filler(currentIndex);
		}
		//If the number is 1-9, but there are no duplicates:
		else {
			currentIndex++;
			filler(currentIndex);
		}		
	}
}


