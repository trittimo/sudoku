package trittimo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Sudoku {
	public static void main(String[] args) throws IOException {
		String filename = args[0];
		Scanner scanner = new Scanner(new File(filename));
		int size = scanner.nextInt();
		int read;
		int[][] board = new int[size][size];
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y] = scanner.nextInt();
			}
		}
		
		if (!solveSudoku(board)) {
			System.out.println("No solution exists");
		}
		printBoard(board);
	}
	
	public static void printBoard(int[][] board) {
		System.out.println("=========================");
		for (int x = 0; x < board.length; x++) {
			String result = "";
			for (int y = 0; y < board.length; y++) {
				result += String.format("%3d", board[x][y]);
			}
			System.out.print(result.trim() + "\n");
		}
		System.out.println("=========================");
	}
	
	public static boolean solveSudoku(int[][] board) {
		ArrayList<Integer[]> bestOrder = getBestSolveOrder(board);
		return solve(bestOrder, 0, board);
	}
	
	
	
	private static ArrayList<Integer[]> getBestSolveOrder(int[][] board) {
		// Temporary solution
		ArrayList<Integer[]> order = new ArrayList<Integer[]>();
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board.length; y++) {
				if (board[x][y] == 0) {
					order.add(new Integer[] {x, y});
				}
			}
		}
		return order;
	}
	
	private static boolean legal(int iX, int iY, int attempt, int[][] board) {
		for (int x = 0; x < board.length; x++) {
			if (attempt == board[x][iY]) {
				return false;
			}
		}
		
		for (int y = 0; y < board.length; y++) {
			if (attempt == board[iX][y]) {
				return false;
			}
		}
		
		int partition = (int) Math.sqrt(board.length);
		
		int xOffset = (iX / partition) * partition;
		int yOffset = (iY / partition) * partition;
		
		for (int x = 0; x < partition; x++) {
			for (int y = 0; y < partition; y++) {
				if (attempt == board[xOffset + x][yOffset + y]) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	private static boolean solve(ArrayList<Integer[]> order, int current, int[][] board) {
		if (order.isEmpty() || current >= order.size()) {
			return true;
		}
		int x, y;
		Integer[] pos = order.get(current);
		x = pos[0];
		y = pos[1];
		
		for (int attempt = 1; attempt <= board.length; attempt++) {
			if (legal(x, y, attempt, board)) {
				board[x][y] = attempt;
				if (solve(order, current + 1, board)) {
					return true;
				}
			}
		}
		board[x][y] = 0;
		return false;
	}
}