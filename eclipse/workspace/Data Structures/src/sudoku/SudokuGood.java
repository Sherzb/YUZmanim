/**
 * Sudoku checker/filler. The checker runs if the puzzle is already filled, while the 
 */

package sudoku;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;

public class SudokuGood
{
    public static int[][] puzzle = new int[9][9];
    public static int filledIn = 0;
    public static ArrayList<Integer> blankBoxes = new ArrayList<Integer>(); 
    public static int currentIndex = 0;

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
		scan.close();

        //Adds all the blank boxes to an ArrayList, and sees if there are any in the first place. 
        //The index is stored as 10*i + j, which can be retrieved via modulo and integer division.
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
            filler(blankBoxes.get(currentIndex));
        }
        else {
            checker();
        }
    }

    /**
     * A general method for testing whether an array contains a
     * duplicate. It creates a set (which cannot have a duplicate), and
     * compares their sizes. Idea from http://javarevisited.blogspot.com/.
     * @param testArray The int[] that is being tested for duplicates
     * @return True if there are NO duplicate, false if there are ANY duplicates.
     */
    public static boolean checkDupl(int[] testArray) {
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
     * The scanner method that's called by the main method before anything
     * else really happens. Doesn't work yet :P.
     * @param fileName
     * @throws FileNotFoundException
     */
    public static void scan(String fileName) throws FileNotFoundException
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
        scanner.close();
    }

    /**
     * Checks all the columns of the puzzle. It takes each column via a for each
     * loop, and then compares the set made by that array. The set cannot have
     * doubles, so if there is a duplicate, the set will be smaller than the
     * array.
     */
    public static boolean checkRows()
    {
        boolean noDuplicate = true;
        // Iterates through all the columns
        for (int i = 0; i < 9; i++) {
            // Takes a single column
            int[] testColumn = puzzle[i];
            // Compares the list and array length
            if(!checkDupl(testColumn)) {
                noDuplicate = false;
            }
        }
        return noDuplicate;
    }

    /**
     * Checks all the rows of the puzzle. It does this by taking every column,
     * then taking the first element of that column, and adding all the first
     * elements of all the columns to an array. It then does the same thing that
     * CheckColumns did. It does this for every row.
     */
    public static boolean checkColumns()
    {
        boolean noDuplicate = true;
        // We take a single column at a time
        for (int i = 0; i < 9; i++) {
            if(!checkSingleColumn(i)) {
                noDuplicate = false;
            }
            /**int[] column = new int[9];
            // Iterate through every row
            for (int j = 0; j < 9; j++) {
            // Adds each element at that row, in that column, to a single
            // row array
            column[j] = puzzle[j][i];
            if (!checkDupl(column)) {
            noDuplicate = false;

            }
            }*/
        }
        return noDuplicate;
    }

    /**
     * Kind of brute force. Creates all the boxes, and figures out what each one has
     * individually, based off its position in the Sudoku game. Return "Every single 
     * box doesn't have a duplicate"
     */
    public static boolean checkBoxes2() {
        //Creates all the boxes
        int[] box1 = new int[9];
        int[] box2 = new int[9];
        int[] box3 = new int[9];
        int[] box4 = new int[9];
        int[] box5 = new int[9];
        int[] box6 = new int[9];
        int[] box7 = new int[9];
        int[] box8 = new int[9];
        int[] box9 = new int[9];

        //Looks at each individual box- The first
        int counter = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                box1[counter] = puzzle[i][j];
                counter++;
            }
        }
        //The second
        counter = 0;
        for(int i = 3; i < 6; i++) {
            for(int j = 0; j < 3; j++) {
                box1[counter] = puzzle[i][j];
                counter++;
            }
        }
        //The third
        counter = 0;
        for(int i = 6; i < 9; i++) {
            for(int j = 0; j < 3; j++) {
                box1[counter] = puzzle[i][j];
                counter++;
            }
        }
        //The Fourth
        counter = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 3; j < 6; j++) {
                box1[counter] = puzzle[i][j];
                counter++;
            }
        }
        //The Fifth
        counter = 0;
        for(int i = 3; i < 6; i++) {
            for(int j = 3; j < 6; j++) {
                box1[counter] = puzzle[i][j];
                counter++;
            }
        }
        //The Sixth
        counter = 0;
        for(int i = 6; i < 9; i++) {
            for(int j = 3; j < 6; j++) {
                box1[counter] = puzzle[i][j];
                counter++;
            }
        }
        //The seventh
        counter = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 6; j < 9; j++) {
                box1[counter] = puzzle[i][j];
                counter++;
            }
        }
        //The Eight
        counter = 0;
        for(int i = 3; i < 6; i++) {
            for(int j = 6; j < 9; j++) {
                box1[counter] = puzzle[i][j];
                counter++;
            }
        }
        //The Nineth. Don't you love copy-paste?
        counter = 0;
        for(int i = 6; i < 9; i++) {
            for(int j = 6; j < 9; j++) {
                box1[counter] = puzzle[i][j];
                counter++;
            }
        }
        //Ready for this?
        return (checkDupl(box1) && checkDupl(box2) && checkDupl(box3) && 
            checkDupl(box4) && checkDupl(box5) && checkDupl(box6) &&
            checkDupl(box7) && checkDupl(box8) && checkDupl(box9));
    }

    /**
     * Err...this loop goes through each of the 9 permutations of what a box is going to consist of.
     * Notice that in each box, the index of both the rows and the columns are either
     * 0-3, 3-6, or 6-9 (exclusive). That gives you 9 possibilities. i and j represent the rows
     * and columns, respectively, and h and k are what are added to them, to make them go from
     * 0-3 to 3-6 to 6-9.
     * @return True if there are no duplicates, false if there are any
     */
    public static boolean checkBoxes() {
        boolean noDupl = true;
        for(int h = 0; h < 9; h += 3) {
            for(int k = 0; k < 9; k += 3) {
                //We now are talking about a specific box
                //Creates a new box array.
                int[] box = new int[9];
                //Create a counter for indexing the elements
                int counter = 0;
                //Adds all the elements from the box into the array
                for(int i = k; i < 3 + k; i++) {
                    for(int j = h; j < 3 + h; j++) {
                        //The index is the length of the box
                        box[counter] = puzzle[i][j];
                        counter++;
                    }
                }
                //This check occurs after each box is created- that is, after
                //each round of the last 2 for-loops.
                if(!checkDupl(box)) {
                    noDupl = false;
                }
            }
        }
        return noDupl;
    }

    /**
     * If the puzzle is full, it will check to see if the puzzle is filled in correctly
     */
    public static void checker() {

        boolean correct = checkColumns() && checkRows() && checkBoxes();
        if (correct) {
            System.out.println("This Sudoko puzzle is correct.");
        }
        else {
            System.out.println("This Sudoko puzzle is incorrect.");
        }
    }

    /**
     * If the puzzle is not full, the filler will be run. The filler is my attempt at a backtracker.
     * It stores every (i,j) for which puzzle[i][j] == 0. It then adds 1 to it's value. If the value
     * is already somewhere else, it adds another 1. If it is 9, and that's already there, it loops to 
     * 0, and the index beforehand is rechecked.
     */
    public static void filler(int indexOfBlank) {
        //If the current index is equal to the size of blankBoxes, meaning that we
        //went through every index of blankBoxes, meaning the puzzle is full and correct.
        if(currentIndex == blankBoxes.size()) {
            System.out.println("The puzzle is full!" + "\n");
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
        //Adds one to the value of that box, and makes it 0 if it hits 10.
        puzzle[row][column] =  (puzzle[row][column] + 1);

        //If the value is 10, meaning it went through all the possible values:
        if(puzzle[row][column] == 10) {
            //Do filler() on the previous box
            puzzle[row][column] = 0;
            currentIndex--;
            filler(currentIndex);
        }
        //If the number is 1-9, and there are no duplicates:
        else if(!(checkSingleRow(row) && checkSingleColumn(column) && checkSingleBox(row, column))) {
            //Do filler() on the next box.
            filler(currentIndex);
        }
        //If the number is 1-9, and there is no duplicate:
        else {
            currentIndex++;
            filler(currentIndex);
        }		
    }

    /**
     * Used to check if a single row has any duplicates or not. This is called by the 
     * filler method.
     * @param row
     * @return
     */
    public static boolean checkSingleRow(int row) {
        return checkDupl(puzzle[row]);
    }

    /**
     * Used to check if a single column has any duplicates or not. This is called by the 
     * filler method, as well as the checkColumns of the checker.
     * @param column
     * @return
     */
    public static boolean checkSingleColumn(int column) {
        int[] singleColumn = new int[9];
        for(int i = 0; i < 9; i++) {
            singleColumn[i] = puzzle[i][column];
        }
        return checkDupl(singleColumn);			
    }

    public static boolean checkSingleBox(int row, int column) {
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
}

