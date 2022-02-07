package sudoku;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SudokuSolver {
	public static void main(String[] args) throws IOException {
		// Open input and output files and streams
		 FileInputStream inputFile = new FileInputStream("puzzle_1.txt");
		Scanner scanner = new Scanner(inputFile);
		FileOutputStream outputFile = new FileOutputStream("solutions.txt");
		PrintWriter writer = new PrintWriter(outputFile);
		
		Sudoku puzzle = new Sudoku(); 	// Create new Sudoku game
		puzzle.loadData(scanner);		// load the game board from input file
		puzzle.solve(writer);			// solve game and output all possible solutions
		
		// Close files and scanners
		writer.close();
		outputFile.close();
		scanner.close();
		inputFile.close();
	}
}
