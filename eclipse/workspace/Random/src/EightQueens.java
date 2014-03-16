public class EightQueens
{
	private static boolean finished = false;
	private static int[][] board = new int[8][8];
	private static int currentColumn = 0;
	final static long startTime = System.currentTimeMillis();

	public static void main(String[] args) {
		//Places the Queens
		eightQueens();
		//Prints it out
		for(int i = 0; i < 8; i++) {
			System.out.println();
			System.out.println();
			for(int j = 0; j < 8; j++) {
				System.out.print(board[j][i] + " ");
			}
		}
		final long endTime = System.currentTimeMillis();
		System.out.println();
		System.out.println("Total execution time: " + (endTime - startTime) );
	}

	public static void eightQueens() {
		while(!finished) {
			addQueen(currentColumn);
		}
	}

	/**
	 * Here's the possibilities of this method:
	 * 1) We are trying to place a Queen on the 9th row, meaning we already placed 8 Queens succesfully. So 
	 * finished = true, and end the method.
	 * 2) We are trying to place a Queen on the -1th row, meaning we backtracked to before the first row. That 
	 * means something is wrong with the checker(), so print an error message, and end the program.
	 * 3) There isn't yet a Queen in this column. If so, add a Queen to this column, in the first row, and check if
	 * that works. If it does, increment currentColumn, and return. If not, just return (keeping currentColumn the same).
	 * 4) If a Queen is already in this column, but in the last row, we already went through all the possible rows it could 
	 * be in. That means that we previously placed a Queen wrongly. So we BACKTRACK!!! by subtracting 1 from currentColumn, and
	 * then returning.
	 * 5) If a Queen is already in the column, and not in the last row, move it up a row, and check. If it does work, increment
	 * currentColumn, and return. If not, just return.
	 * 
	 * One important note: This code will not work well if, instead of changing currentColumn and returning, you just have
	 * addQueen calling itself on different columns. If you do, since check() fails very often, addQueen() will be calling itself
	 * many, many times, and you'll have a huge method stack, which will result in a StackOverflow error. Instead, we increment 
	 * currentColumn, and break out of the method call, going back to the while loop.
	 * @param column
	 */
	public static void addQueen(int column) {
		boolean hasQueen = false;
		int row = 0;

		//1)
		if(column > 7) {
			finished = true;
			return;
		}
		
		//2)
		else if(column < 0) {
			System.out.println("Error: Bad Code Somewhere...");
			finished = true;
			return;
		}

		//Finds the Queen, if there is one.
		for(int i = 0; i < 8; i++) {
			if(board[column][i] == 1) {
				hasQueen = true;
				row = i;
			}
		}

		//3)
		if (!hasQueen) {
			board[column][0] = 1;
			if(!check(column, row)) {
				return;
			}
			else {
				currentColumn++;
				return;
			}
		}

		//4)
		if (row == 7) {
			board[column][7] = 0;
			currentColumn--;
			return;
		}

		//5)
		board[column][row] = 0;
		row++;
		board[column][row ] = 1;
		if(!check(column, row)) {
			return;
		}
		else {
			currentColumn++;
			return;
		}
	}

	/**
	 * Ya, this is ugly code, sorry :P. The other Queens cannot be to the right of this Queen, as we go from the 
	 * left to the right.
	 * @param column
	 * @param row
	 * @return
	 */
	public static boolean check(int column, int row) {
		int origRow = row;
		int origColumn = column;
		//Check for duplicates in the same row
		for(int i = 1; i <= column; i++) {
			if(board[column - i][row] == 1) {
				return false;
			}
		}
		
		//Check for duplicates going diagonal left up. We keep checking until we hit the side of the board, or the top.
		while(row > 0 && column > 0) {
			column--;
			row--;
			if(board[column][row] == 1) {
				return false;
			}
		}
		//Reset the values of row and column. Good code wouldn't need this...
		row = origRow;
		column = origColumn;
		
		//Check for duplicates going diagonal left down. We keep checking until we hit the side of the board, or the top.
		while(row < 7 && column > 0) {
			column--;
			row++;
			if(board[column][row] == 1) {
				return false;
			}
		}
		//If nothing returned false...
		return true;
	}
}
