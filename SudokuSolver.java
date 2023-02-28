import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;


public class SudokuSolver {
	
	public static void main(String[] args) {
		Scanner myObj = new Scanner(System.in);  
	    System.out.println("Enter File");

	    String userName = myObj.nextLine();
		int[][] board = populateBoard(userName);
		
		System.out.println("Initial Sudoku Board:");
		printBoard(board);
		
		if (solve(board)) {
			System.out.println("Solution:");
			printBoard(board);
		} else {
			System.out.println("No Solution Found.");
		}
	}
	
	public static void printBoard(int[][] board) {
		for (int a = 0; a < 9; a++) {
			for (int b = 0; b < 9; b++) {
				System.out.print(board[a][b] + " ");
			}
			System.out.println();
		}
	}
	
	public static int[][] populateBoard(String fileName) {
		final int BOARD_WIDTH = 9;
		final int BOARD_HEIGHT = 9;
		int[][] board = null;
		
		try {
			String parser = " ";
			String currentLine = null;
			String[] currentLineValues = null;
			FileReader fileInput = new FileReader(fileName);
			BufferedReader imageReader = new BufferedReader(fileInput);			
			board = new int[BOARD_HEIGHT][BOARD_WIDTH];
			for (int a = 0; a < board.length; a++) {
				currentLine = imageReader.readLine();
				currentLineValues = currentLine.split(parser);

				for (int b = 0; b < board[a].length; b++) {
					board[a][b] = Integer.parseInt(currentLineValues[b]);
				}
			}
			imageReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return board;
	}
	
	public static boolean solve(int[][] board) {
		int[][] progress = new int[board.length][board[0].length];
		
		for (int a = 0; a < 9; a++) {
			for (int b = 0; b < 9; b++) {
				if (board[a][b] > 0) {
					progress[a][b] = 2;
				} else {
					progress[a][b] = 0;
				}
			}
		}
		return solve(board, progress, 0, 0);
	}
	
	public static boolean solve(int[][] board, int[][] progress, int a, int b) {
		if (a == 9) {
			int count = 0;
			
			for (int x = 0; x < 9; x++) {
				for (int y = 0; y < 9; y++) {
					if (progress[x][y] > 0) {
						count++;
					} 
				}
			}
			
			if (count == 81) {
				return true;
			} else {
				return false;
			}
		}
		
		if (progress[a][b] >= 1) {
			int nextA = a;
			int nextB = b + 1;
			if (nextB == 9) {
				nextA = a + 1;
				nextB = 0;
			}
			return solve(board, progress, nextA, nextB);
		}
		
		boolean[] used = new boolean[9];
		for (int i = 0; i < 9; i++) {
			if (progress[a][i] >= 1) {
				used[board[a][i] - 1] = true;
			}
		}
		
		for (int i = 0; i < 9; i++) {
			if (progress[i][b] >= 1) {
				used[board[i][b] - 1] = true;
			}
		}
		
		for (int i = a - (a % 3); i < a - (a % 3) + 3; i++) {
			for (int j = b - (b % 3); j < b - (b % 3) + 3; j++) {
				if (progress[i][j] >= 1) {
					used[board[i][j] - 1] = true;
				}
			}
		}
		
		for (int i = 0; i < used.length; i++) {
			if (!(used[i])) {
				progress[a][b] = 1;
				board[a][b] = i + 1;
				int nextA = a;
				int nextB = b + 1;
				if (nextB == 9) {
					nextA = a + 1;
					nextB = 0;
				}
				if (solve(board, progress, nextA, nextB)) {
					return true;
				}
				
				for (int m = 0; m < 9; m++) {
					for (int n = 0; n < 9; n++) {
						if (m > a || (m == a && n >= b)) {
							if (progress[m][n] == 1) {
								progress[m][n] = 0;
								board[m][n] = 0;
							}
						}
					}
				}
			}
		}
		return false;
	}
}