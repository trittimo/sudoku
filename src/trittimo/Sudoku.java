package trittimo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

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
		SudokuBoard solved = solveBoard(theBoard);
		
		System.out.println("Ouput:");
		System.out.println(solved);
	}
	
	
	
	public static SudokuBoard solveBoard(SudokuBoard board) {
		board.addConstrainedPossibles();
		Stack<SudokuBoard> boards = new Stack<>();
		boards.push(board);
		while (!boards.isEmpty()) {
			SudokuBoard current = boards.pop();
			if (current.isGoalState()) {
				return current;
			}
			
			Integer[] pos = current.getBestEmptyPosition();
			int attempt = current.changes.get(pos).get(0);
			HashMap<Integer[], ArrayList<Integer>> changes = new HashMap<>(current.changes);
			changes.get(pos).remove(0);
			SudokuBoard attemptBoard = new SudokuBoard(current, pos, attempt, changes);
			boards.push(attemptBoard);
		}
		return null;
	}
	
//	
//	
//	public static Integer[] getNextPosition(Integer[][] board) {
//		return getEmpty(board);
//	}
//	
//	public static void addOptions(Stack<Integer[]> moves, Stack<Integer[]> undo, Integer[][] board) {
//		Integer[] next = getNextPosition(board);
//		if (next.length == 0) {
//			moves.push(next);
//			return;
//		}
//		for (int possible : getAllPossibleValues(next[0], next[1], board)) {
//			Integer[] clone = new Integer[] {next[0], next[1], possible};
//			moves.push(clone);
//			undo.push(clone);
//		}
//	}
//	
//	public static void solveBoard(Integer[][] board) {
//		// Idea: instead of get first empty, get the best empty
//		Stack<Integer[]> moves = new Stack<Integer[]>();
//		Stack<Integer[]> undo = new Stack<Integer[]>();
//		
//		addOptions(moves, undo, board);
//		
//		while (!moves.isEmpty()) {
//			Integer[] current = moves.pop();
//			
//			if (current.length == 0) {
//				Integer[] reset = undo.pop();
//				board[reset[1]][reset[0]] = 0;
//				continue;
//			}
//			
//			board[current[1]][current[0]] = current[2];
//			if (isGoalState(board)) {
//				break;
//			}
//			
//			
////			for (int possible : getAllPossibleValues(empty[0], empty[1], current)) {
////				Integer[][] attempt = current.clone();
////				attempt[empty[1]][empty[0]] = possible;
////				boards.push(attempt);
////				printBoard(current);
////			}
//		}
//		printBoard(board);
//	}
}