package sudoku;

import java.io.PrintWriter;
import java.util.Scanner;

public class Sudoku {
	// Data fields
	private char[][] board;
	private static final int SIZE = 16;
	private static final char BLANK = '.';
	private char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };		// Array of legal digits
	private int count;			// Stores the number of solutions found
	private int numOfBlanks; 	// Stores the number of blank cells on the board
	
	
	// Constructor
	public Sudoku() { 
		board = new char[SIZE][SIZE];
		numOfBlanks = numOfBlanks();
	}
	
	// Methods	
	/** 
	 * Reads in the cell values from an input file 
	 * @param scanner: scanner that reads in the file
	 * */
	public void loadData(Scanner scanner) {
		for (int row = 0; row < SIZE; row++) {
			String currentRow = scanner.nextLine();
			for (int col = 0; col < SIZE; col++) {
				board[row][col] = currentRow.charAt(col);
			}
		}
	}
	
	/** 
	 * Find the row index of the next cell
	 *  @param row: row of current cell
	 *  @param col: column of current cell
	 *  return: the row index of the next cell
	 * */
	private int nextRowIndex(int row, int col) {
		if (row == SIZE - 1 && col == SIZE - 1) {
			return -1; // no next cell
		}
		if (col == SIZE - 1) {
			return row + 1;
		} else {
			return row;
		}		
	}
	
	/** 
	 * Finds the column index of the next cell
	 * @param row: row of current cell
	 * @param col: column of current cell
	 * @return: the column index of the next cell
	 */
	private int nextColIndex(int row, int col) {
		if (row == SIZE - 1 && col == SIZE - 1) {
			return -1; // no next cell
		}
		if (col == SIZE - 1) {
			return 0;
		} else {
			return col + 1;
		}
	}
	
	/** 
	 * Determines if char is in the same row as cell
	 * @param row: the row to check
	 * @param digit: the char to check for
	 * @return: returns true if digits is in the row; otherwise returns false
	 */
	private boolean inSameRow(int row, char digit) {
		for (int col = 0; col < SIZE; col++) {
			if (board[row][col] == digit) {
				return true;
			}
		}
		return false;
	}
 	
	/**
	 * Determines if char is in the same row as cell
	 * @param col: the column to check
	 * @param digit: the char to check for
	 * @return: returns true if digit is in the row; otherwise returns false
	 */
	private boolean inSameCol(int col, char digit) {
		for (int row = 0; row < SIZE; row++) {
			if (board[row][col] == digit) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Determines if the char is in the same 4x4 grid that board[row][col] is in
	 * @param row: the row for board[row][col]
	 * @param col: the column for board[row][col]
	 * @param digit: the char to check for
	 * @return: returns true if digit is in the row; otherwise returns false
	 */
	private boolean inSameGrid(int row, int col, char digit) {
		int gridStartRow = row / 4 * 4, gridStartCol = col / 4 * 4;
		for (int i = gridStartRow; i < gridStartRow + 4; i++) {
			for (int j = gridStartCol; j < gridStartCol + 4; j++) {
				if (board[i][j] == digit) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Determines the number of blank cell on the board
	 * @return: the number of blank cells
	 */
	private int numOfBlanks() {
		int blanks = 256;
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				if (board[row][col] == BLANK) {
					blanks -= 1;
				}
			}
		}
		return blanks;
	}
	
	/**
	 * Prints the entire board to an output file when a solution is found
	 * @param writer: writer that writes to output file
	 */
	private void printSolution(PrintWriter writer) {
		writer.println("Solution " + count);
		writer.println();
		for (int row = 0; row < SIZE; row++) {
				for (int col = 0; col < SIZE; col++) {
					writer.print(board[row][col]);
				}
				writer.println();
			}
			writer.println();		
	}
	
	/**
	 * Solves the sudoku puzzle and outputs all possible solutions to file
	 * @param row: the row of the cell you are filling
	 * @param col: the column of the cell you are filling
	 * @param writer: writer that writes to output file
	 */
	private void solve(int row, int col, PrintWriter writer) {
		if (row == -1 || col == -1) { 	// The entire board is completed
			count += 1;
			printSolution(writer);
			return;
		}		
		if (board[row][col] != BLANK) { 	// Move to the next cell if board[row][col] is not blank
			solve(nextRowIndex(row, col), nextColIndex(row, col), writer);
		} else { 	// The current cell is blank
			for (int i = 0; i < digits.length; i++) {		// Find a legal digit for empty cell
				if (inSameRow(row, digits[i])) { continue; }
				if (inSameCol(col, digits[i])) { continue; }
				if (inSameGrid(row, col, digits[i])) { continue; }
				
				// Fill the blank cell with digit
				board[row][col] = digits[i];
				
				// Subtract from blanks because a blank cell was filled
				numOfBlanks -= 1;
				
				// Move to next cell
				solve(nextRowIndex(row, col), nextColIndex(row, col), writer);
				
				// If no solution is found then change cell back to blank and try the next digit
				if (numOfBlanks != 0) {
					board[row][col] = BLANK;
					numOfBlanks += 1;		// Add to blanks because the cell was changed back to blank
				}
			}
		}
	}
	
	 /** Wrapper method
	  * Solves the Sudoku puzzle outputting all possible solutions
	  * @param writer: writer that writes to output file
	  */
	public void solve(PrintWriter writer) {
		solve(0, 0, writer);	// Start puzzle at board[0][0]
	}	

}
