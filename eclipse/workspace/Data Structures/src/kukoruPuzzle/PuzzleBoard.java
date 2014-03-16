package kukoruPuzzle;

import java.util.Random;
import java.util.ArrayList;

public class PuzzleBoard
{
	private static Box[][] puzzle = new Box[10][10];
	private static Random randomizer = new Random();
	private static int[] allPossibilitiesArray= {1,2,3,4,5,6,7,8,9};
	
	public static void main(String[] args) {
		//Sets each box to a ClueBox or EmptyBox
		setBoxType();
		//Fills in each EmptyBox with a number
		fillInStuff();
		//Defines the vertical clues for the ClueBoxes
		setUpClues();
		//Defines the horizontal clues for the ClueBoxes
		setLeftClues();
		//Prints out the puzzle
		printPuzzle();
		//Prints out the answer to the puzzle
		printAnswer();
	}
	
	/**
	 * Starts off by defining the boxes on the left and top borders and ClueBoxes.
	 * Sets whether the other boxes will be empty ones, or clue ones. From what I've seen, there seems
	 * to be somewhere upwards of 50% of the puzzle being EmptyBoxes. This method chooses a random
	 * difficulty (from 0-4), adds a multiplier of that to 50, and comes up with a percentage of the
	 * boxes that (about that) should be EmptyBoxes. It then makes about that percentage of boxes
	 * EmotyBoxes.
	 */
	private static void setBoxType() {
		
		//Makes the top and left be clueBoxes
		for(int i = 0; i < 10; i++) {
			puzzle[0][i] = new ClueBox();
			puzzle[i][0] = new ClueBox();
		}
		
		//Chooses a difficulty between 0 and 4.
		int difficulty = randomizer.nextInt(5);
		//Chooses a percentage based off of that difficulty.
		int emptyPercent = 8 * difficulty + 50;
		System.out.println("Difficulty (from 1-5): " + (1 + difficulty));
		for(int i = 1; i < 10; i++) { //Starting from index 1, not 0.
			for(int j = 1; j < 10; j++) { //Starting from index 1, not 0.s
				//Choose a random number from 0 to 100
				int randomNumber = randomizer.nextInt(100) + 1;
				if(randomNumber < emptyPercent) {//Within the EmptyBox percent
					puzzle[i][j] = new EmptyBox();
				}
				else {//Outside of the EmptyBox percent
					puzzle[i][j] = new ClueBox();
				}
			}
		}	
	}
	
	/**
	 * Will go through the whole puzzle. If a box is empty, it will run a method that assigns that 
	 * box a number. Also, today I learned (TIL) that you can remove items in a list while iterating
	 * with a for-loop. 
	 */
	private static void fillInStuff() {
		for(int i = 1; i < 10; i++) {	
			for(int j = 1; j < 10; j++) {
				if(puzzle[i][j] instanceof EmptyBox) {
					fillInNumber(i,j);
				}	
			}
		}
	}
	
	/**
	 * First makes an ArrayList (called possibilities) containing the numbers 1-9. It then searches all
	 * the boxes above it and to the left of it (because the puzzle is filled in that way). If it hits a clueBox 
	 * (which means the numbers past that box don't matter), the loop breaks. If it hits an emptyBox, and
	 * that emptyBox has a value that's in the possibilities ArrayList, that possibility is removed (via
	 * a new Integer, as just saying remove(x) will remove the item at index x, not the integer x).
	 * Finally, it chooses a random number index among the possibilities, and fills it in. If there are no
	 * possibilities, it changes that box to a ClueBox. 
	 * @param row
	 * @param column
	 */
	private static void fillInNumber(int row, int column) {
		//First makes an ArrayList of the possible numbers the box
		//can contain.
		ArrayList<Integer> possibilities = new ArrayList<Integer>();
		//Add the numbers 1-9 to this ArrayList
		for(Integer number: allPossibilitiesArray) {
				possibilities.add(number);
		}
			
		//Removing numbers above it
		for(int x = row; x >= 0; x--) {
			if(puzzle[x][column] instanceof ClueBox) {//So we don't care about any more numbers above it
				break;
			}
			if(possibilities.contains(puzzle[x][column].getValue())) {
				possibilities.remove(new Integer(puzzle[x][column].getValue()));//Removes that value
			}			
		}
		//Removing numbers to the left
		for(int x = column; x >= 0; x--) {
			if(puzzle[row][x] instanceof ClueBox) {//So we don't care about any more numbers to the left of it
				break;
			}
			if(possibilities.contains(puzzle[row][x].getValue())) {
				possibilities.remove(new Integer (puzzle[row][x].getValue()));//Removes that value
			}			
		}
		//Now chooses a value for that EmptyBox
		
		//If there are no possibilities, make it a ClueBox
		if(possibilities.size() == 0) {
			puzzle[row][column] = new ClueBox();
		}
		//If there is only one possibility, use that.
		else if(possibilities.size() == 1) {
			puzzle[row][column].setValue(possibilities.get(0));
		}
		//If there are more than one possibility, choose one randomly.
		else {
		puzzle[row][column].setValue(possibilities.get(randomizer.nextInt(possibilities.size() - 1)));
		}
	}
	
	/**
	 * Defines the vertical clues. It goes from the bottom right, moving up, then left. It adds up 
	 * the values of the EmptyBoxes until it hits a ClueBox, and then it assigns the ClueBox that
	 * value as the UpValue of that ClueBox. If there are two ClueBoxes in a row (or more), the 
	 * later ones will have a 0 value. 
	 */
	private static void setUpClues() {
		for(int i = 9; i >= 0; i--) {
			int tempSum = 0;
			for(int j = 9; j >= 0; j--) {
				if(puzzle[j][i] instanceof ClueBox) { 
					puzzle[j][i].setUpValue(tempSum);
					tempSum = 0;
				}
				else {    //It's an EmptyBox with a value.
					tempSum += puzzle[j][i].getValue();
				}
			}
		}
	}
	
	/**
	 * Same as setUpClues, except moving left, then up (that's why the i and j are switched).
	 */
	private static void setLeftClues() {
		for(int i = 9; i >= 0; i--) {
			int tempSum = 0;
			for(int j = 9; j >= 0; j--) {
				if(puzzle[i][j] instanceof ClueBox) {
					puzzle[i][j].setLeftValue(tempSum);
					tempSum = 0;
				}
				else {
					tempSum += puzzle[i][j].getValue();
				}
			}
		}
	}
	
	/**
	 * The fun printing method! Each row is two characters tall. The methods are overridden so that EmptyBoxes
	 * print spaces, while ClueBoxes print their clue values and "\" characters. 
	 */
	private static void printPuzzle() {
		System.out.println();
		System.out.println("------------------------------------------");
		for(int i = 0; i < 10; i++) {//For each row
			for(int j = 0; j < 10; j++) {//Top half of each row				
				System.out.print("|");
				puzzle[i][j].printSpaceChar(); // "\" for ClueBoxes, " " for EmptyBoxes 
				puzzle[i][j].printLeftData();  // Data for ClueBoxes, " " for EmptyBoxes
			}
			System.out.print("|");
			//Now the lower half of each row
			System.out.println();
			for(int k = 0; k < 10; k++) {//Bottom half of each row
				System.out.print("|");
				puzzle[i][k].printUpData();    // Data for ClueBoxes, " " for EmptyBoxes
				puzzle[i][k].printSpaceChar(); // "\" for ClueBoxes, " " for EmptyBoxes
			}
			System.out.print("|");
			//The bottom of each row
			System.out.println();
			System.out.println("------------------------------------------");
		}
		System.out.println();
	}
	
	/**
	 * Prints out the answer to the puzzle. Basically the same thing as above, except for the one
	 * change that makes the EmptyBoxes print out answers instead of blanks.
	 */
	private static void printAnswer() {
		System.out.println();
		System.out.println("Answer");
		System.out.println("------------------------------------------");
		for(int i = 0; i < 10; i++) {//For each row
			for(int j = 0; j < 10; j++) {//Top half of each row				
				System.out.print("|");
				puzzle[i][j].printSpaceChar(); // "\" for ClueBoxes, " " for EmptyBoxes 
				puzzle[i][j].printLeftDataOrAnswer();  // Data for ClueBoxes, Data for EmptyBoxes <-----DIFFERENT!
			}
			System.out.print("|");
			//Now the lower half of each row
			System.out.println();
			for(int k = 0; k < 10; k++) {//Bottom half of each row
				System.out.print("|");
				puzzle[i][k].printUpData();    // Data for ClueBoxes, " " for EmptyBoxes
				puzzle[i][k].printSpaceChar(); // "\" for ClueBoxes, " " for EmptyBoxes
			}
			System.out.print("|");
			//The bottom of each row
			System.out.println();
			System.out.println("------------------------------------------");
		}
		System.out.println();
	}
}

