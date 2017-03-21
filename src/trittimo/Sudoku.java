package trittimo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Sudoku {
	private static int boardSize = 0;
	private static int partitionSize = 0;
	
	public static void main(String[] args){
		String filename = args[0];
		File inputFile = new File(filename);
		Scanner input = null;
		int[][] board = null;
		int temp = 0;
    	int count = 0;
    	
	    try {
			input = new Scanner(inputFile);
			temp = input.nextInt();
			boardSize = temp;
			partitionSize = (int) Math.sqrt(boardSize);
			System.out.println("Boardsize: " + temp + "x" + temp);
			board = new int[boardSize][boardSize];
			
//			System.out.println("Input:");
	    	int x = 0;
	    	int y = 0;
	    	while (input.hasNext()){
	    		temp = input.nextInt();
	    		count++;
//	    		System.out.print(temp);
				board[x][y] = temp;
				y++;
				if (y == boardSize) {
					y = 0;
					x++;
//					System.out.println();
				}
				if (y == boardSize) {
					break;
				}
	    	}
	    	input.close();
	    }
	    catch (FileNotFoundException exception) {
		    System.out.println("Input file not found: " + filename);
	    } 
	    catch (IOException e) {
	    	System.out.println(e);
		} 
	    if (count != boardSize*boardSize) throw new RuntimeException("Incorrect number of inputs.");

		SudokuBoard theBoard = new SudokuBoard(board);
		System.out.println("Input:");
		System.out.println(theBoard);
		SudokuBoard solved = solveBoard(theBoard, 0);
		
		System.out.println("Ouput:");
		System.out.println(solved);
	}
	
	
	
	public static SudokuBoard solveBoard(SudokuBoard board, int level) {
		System.out.println("Level = " + level + "\n" + board);
		if (board.isGoalState()) {
			return board;
		}
		PriorityQueue<ComparablePosition> empty = board.getBestEmptyPositions();
		
		while (!empty.isEmpty()) {
			ComparablePosition position = empty.poll();
			Integer[] pos = position.position;
			for (int attempt : board.getValidNumbers(pos[0], pos[1])) {
				SudokuBoard result = solveBoard(new SudokuBoard(board, pos[0], pos[1], attempt), level + 1);
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}
}