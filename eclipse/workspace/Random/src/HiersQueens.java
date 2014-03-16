
public class HiersQueens
{
	final static long startTime = System.currentTimeMillis();
	
	public static void findQueens() {
		int [][] board = new int[8][8];
		board = findSolution(board, 0);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				String str;
				if (board[i][j] == 1) {
					str = "Q|";
				} else {
					str = " |";
				}
				System.out.print(str);
			}
			System.out.println();
		}
	}

	private static int[][] findSolution(int[][] cand, int spaces) {
		if (isSolution(cand)) {
			return cand;
		}
		if (isInvalid(cand)) {
			return null;
		}
		// make the next queen one space ahead of the last queen
		int times = 1;
		int[][] solution = first(cand, spaces + (times++));
		while (solution != null) {
			int[][] sol = findSolution(solution, spaces + times++);
			if (sol != null && isSolution(sol)) {
				return sol;
			}
			solution = next(cand, spaces + times);
		}
		return solution;
	}

	private static boolean isInvalid(int[][] cand) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (cand[i][j] == 1 &&
						!(validRow(cand, i) && validColumn(cand, j) && validDiagonals(cand, i, j))) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean validRow(int[][] cand, int i) {
		int total = 0;
		for (int j = 0; j < 8; j++) {
			total += cand[i][j];
		}
		return total == 1;
	}

	private static boolean validColumn(int [][] cand, int j) {
		int total = 0;
		for (int i = 0; i < 8; i++) {
			total += cand[i][j];
		}
		return total == 1;
	}

	private static boolean validDiagonals(int[][] cand, int i, int j) {
		int total = 1;
		int row = i + 1;
		int column = j + 1;
		while (row >= 0 && row < 8 && column >= 0 && column < 8) {
			total += cand[row++][column++];
		}
		row = i - 1;
		column = j - 1;
		while (row >= 0 && row < 8 && column >= 0 && column < 8) {
			total += cand[row--][column--];
		}
		row = i + 1;
		column = j - 1;
		while (row >= 0 && row < 8 && column >= 0 && column < 8) {
			total += cand[row++][column--];
		}
		row = i - 1;
		column = j + 1;
		while (row >= 0 && row < 8 && column >= 0 && column < 8) {
			total += cand[row--][column++];
		}
		return total == 1;
	}

	private static boolean isSolution(int[][] cand) {
		int total = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				total += cand[i][j];
			}
		}
		return (!isInvalid(cand) && total == 8);
	}

	private static int[][] first(int[][] cand, int spaces) {
		int[][] newCand = copy(cand);
		int i = spaces / 8;
		int j = spaces % 8;
		if (i >= 8) {
			return null;
		}
		newCand[i][j] = 1;
		return newCand;
	}

	private static int[][] next(int[][] cand, int spaces) {
		int i = spaces / 8;
		int j = spaces % 8;
		if (i >= 8) {
			return null;
		}
		int[][] newCand = copy(cand);
		newCand[i][j] = 1;
		return newCand;
	}

	private static int[][] copy(int[][] cand) {
		int[][] copied = new int[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				copied[i][j] = cand[i][j];
			}
		}
		return copied;
	}

	public static void main(String [] args) {
		findQueens();
		final long endTime = System.currentTimeMillis();
		System.out.println();
		System.out.println("Total execution time: " + (endTime - startTime) );
	}	
}

