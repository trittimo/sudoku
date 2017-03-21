package trittimo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class SudokuBoard {
	protected ArrayList<Integer> correct;
	protected ArrayList<Integer> goal;
	protected HashMap<Integer[], ArrayList<Integer>> validNumbers;
	protected int[][] board;
	protected int size;
	protected int partition;
	
	public SudokuBoard(int[][] board) {
		this.board = board;
		this.size = board.length;
		this.partition = (int) Math.sqrt(this.size);
		this.correct = new ArrayList<>();
		this.validNumbers = new HashMap<>();
		for (int i = 1; i < this.size + 1; i++) {
			this.correct.add(i);
		}
		this.goal = new ArrayList<>();
		
	}
	
	public SudokuBoard(SudokuBoard board, int x, int y, Integer value) {
		this.board = new int[board.board.length][];
		this.size = board.size;
		this.partition = board.partition;
		this.correct = board.correct;
		this.goal = board.goal;
		this.validNumbers = new HashMap<>();
		for (Integer[] pos : this.validNumbers.keySet()) {
			if (pos[0] == x && pos[1] == y) {
				ArrayList<Integer> changed = new ArrayList<>(this.validNumbers.get(pos));
				changed.remove(value);
				this.validNumbers.put(pos, changed);
			}
		}
		
		for (int i = 0; i < board.board.length; i++) {
			this.board[i] = board.board[i].clone();
		}
		
		this.board[x][y] = value;
	}
	
	public ArrayList<Integer> getValidNumbers(int pX, int pY) {
		Integer[] pos = new Integer[] {pX, pY};
		if (this.validNumbers.containsKey(pos)) {
			return this.validNumbers.get(pos);
		}
		ArrayList<Integer> seen = new ArrayList<>();
		for (int i = 0; i < this.size; i++) {
			if (this.board[pX][i] != 0)
				seen.add(this.board[pX][i]);
			if (this.board[i][pY] != 0)
				seen.add(this.board[i][pY]);
		}
		int startX = (pX / this.partition) * this.partition;
		int startY = (pY / this.partition) * this.partition;
		for (int x = startX; x < startX + this.partition; x++) {
			for (int y = startY; y < startY + this.partition; y++) {
				if (this.board[x][y] != 0)
					seen.add(this.board[x][y]);
			}
		}
		ArrayList<Integer> valid = new ArrayList<Integer>(this.correct);
		valid.removeAll(seen);
		this.validNumbers.put(pos, valid);
		return valid;
	}
	
	public ArrayList<Integer[]> getEmptyPositions() {
		ArrayList<Integer[]> empty = new ArrayList<>();
		for (int x = 0; x < this.size; x++) {
			for (int y = 0; y < this.size; y++) {
				if (this.board[x][y] == 0) {
					empty.add(new Integer[] {x, y});
				}
			}
		}
		return empty;
	}
	
	public PriorityQueue<ComparablePosition> getBestEmptyPositions() {
		PriorityQueue<ComparablePosition> empty = new PriorityQueue<>();
		for (Integer[] pos : getEmptyPositions()) {
			ComparablePosition position = new ComparablePosition(pos, this);
			empty.add(position);
		}
		return empty;
	}
	
	public boolean isGoalState() {
		for (int x = 0; x < this.size; x++) {
			this.goal.clear();
			for (int y = 0; y < this.size; y++) {
				this.goal.add(this.board[x][y]);
			}
			if (!this.goal.containsAll(this.correct)) {
				return false;
			}
		}
		
		for (int y = 0; y < this.size; y++) {
			this.goal.clear();
			for (int x = 0; x < this.size; x++) {
				this.goal.add(this.board[x][y]);
			}
			if (!this.goal.containsAll(this.correct)) {
				return false;
			}
		}
		
		for (int x = 0; x < this.size; x += this.partition) {
			for (int y = 0; y < this.size; y += this.partition) {
				this.goal.clear();
				for (int zX = x; zX < x + this.partition; zX++) {
					for (int zY = y; zY < y + this.partition; zY++) {
						this.goal.add(this.board[zX][zY]);
					}
				}
				if (!this.goal.containsAll(this.correct)) {
					return false;
				}
			}
		}

		return true;
	}
	
	@Override
	public String toString() {
		String result = "";
		for (int x = 0; x < size; x++) {
			String line = "";
			for (int y = 0; y < size; y++) {
				line += String.format("%3d", this.board[x][y]);
			}
			result += line.trim() + "\n";
		}
		return result;
	}
//	
//	protected ArrayList<Integer> possibles;
//	
//	protected ArrayList<ArrayList<Integer>> board;
//	protected HashMap<Integer[], ArrayList<Integer>> changes;
//	protected int size;
//	
//	public SudokuBoard(int[][] inputBoard) {
//		this.possibles = new ArrayList<>();
//		
//		
//		this.board = new ArrayList<>();
//		this.changes = new HashMap<>();
//		this.size = inputBoard.length;
//		
//		for (int i = 1; i < this.size + 1; i++) {
//			this.possibles.add(i);
//		}
//		
//		for (int x = 0; x < this.size; x++) {
//			ArrayList<Integer> current = new ArrayList<>();
//			this.board.add(current);
//			for (int y = 0; y < this.size; y++) {
//				current.add(inputBoard[x][y]);
//			}
//		}
//	}
//	
//	public SudokuBoard(SudokuBoard board, Integer[] pos, Integer value, HashMap<Integer[], ArrayList<Integer>> changes) {
//		this.board = new ArrayList<>(board.board);
//		this.changes = new HashMap<>(changes);
//		this.possibles = board.possibles;
//		
//		this.set(pos[0], pos[1], value);
//	}
//	
//	public int get(int x, int y) {
//		return this.board.get(x).get(y);
//	}
//	
//	public void set(int x, int y, int value) {
//		this.board.get(x).set(y, value);
//	}
//	
//	public int get(Integer[] position) {
//		return this.get(position[0], position[1]);
//	}
//	
//	public int getWithChanges(int x, int y) {
//		int withoutChanges = this.get(x, y);
//		if (withoutChanges != 0) {
//			return withoutChanges;
//		}
//
//		ArrayList<Integer> changes = getChangesForPosition(x, y);
//		if (changes == null || changes.size() == 0) {
//			return 0;
//		}
//		
//		return changes.get(0);
//	}
//	
//	public ArrayList<Integer> getColumn(int y) {
//		ArrayList<Integer> row = new ArrayList<>();
//		for (int x = 0; x < this.size; x++) {
//			row.add(this.get(x, y));
//		}
//		return row;
//	}
//	
//	public ArrayList<Integer> getRow(int x) {
//		ArrayList<Integer> column = new ArrayList<>();
//		for (int y = 0; y < this.size; y++) {
//			column.add(this.get(x, y));
//		}
//		return column;
//	}
//	
//	public ArrayList<Integer> getSquare(int inX, int inY) {
//		ArrayList<Integer> square = new ArrayList<Integer>();
//		int squareSize = (int) Math.sqrt(this.size);
//		for (int x = (inY / squareSize) * squareSize; x < (inY / squareSize) * squareSize + squareSize; x++) {
//			for (int y = (inX / squareSize) * squareSize; y < (inX / squareSize) * squareSize + squareSize; y++) {
//				square.add(this.get(x, y));
//			}
//		}
//		return square;
//	}
//	
//	public ArrayList<Integer> getPossibleOptions(int x, int y) {
//		ArrayList<Integer> row = getRow(y);
//		ArrayList<Integer> column = getColumn(x);
//		ArrayList<Integer> square = getSquare(x, y);
//		ArrayList<Integer> result = new ArrayList<>(this.possibles);
//		result.removeAll(row);
//		result.removeAll(column);
//		result.removeAll(square);
//		
//		return result;
//	}
//	
//	public ArrayList<Integer> getChangesForPosition(int x, int y) {
//		Integer[] position = new Integer[] {x, y};
//		if (!this.changes.containsKey(position)) {
//			this.changes.put(position, new ArrayList<>());
//		}
//		return this.changes.get(position);
//	}
//	
//	public ArrayList<Integer[]> getEmptyPositions() {
//		ArrayList<Integer[]> empty = new ArrayList<>();
//		for (int x = 0; x < this.size; x++) {
//			for (int y = 0; y < this.size; y++) {
//				if (this.get(x, y) == 0) {
//					empty.add(new Integer[] {x, y});
//				}
//			}
//		}
//		return empty;
//	}
//	
//	public void addConstrainedPossibles() {
//		for (Integer[] position : this.getEmptyPositions()) {
//			this.changes.put(position, getPossibleOptions(position[0], position[1]));
//		}
//	}
//	
//	public Integer[] getBestEmptyPosition() {
//		Integer[] smallest = null;
//		int smallVal = Integer.MAX_VALUE;
//		for (Entry<Integer[], ArrayList<Integer>> pair : this.changes.entrySet()) {
//			if (pair.getValue().size() < smallVal) {
//				smallVal = pair.getValue().size();
//				smallest = pair.getKey();
//			}
//		}
//		return smallest;
//	}
//	
//	public void addBoardChange(int x, int y, int value) {
//		Integer[] position = new Integer[] {x, y};
//		if (!this.changes.containsKey(position)) {
//			this.changes.put(position, new ArrayList<>());
//		}
//		this.changes.get(position).add(value);
//	}
//	
//	public boolean isGoalState() {
//		for (int x = 0; x < this.size; x++) {
//			ArrayList<Integer> row = this.getRow(x);
//			Collections.sort(row);
//			if (!row.equals(possibles)) {
//				return false;
//			}
//			
//			ArrayList<Integer> col = this.getColumn(x);
//			Collections.sort(col);
//			if (!col.equals(possibles)) {
//				return false;
//			}
//		}
//		
//		for (int x = 0; x < this.size; x += Math.sqrt(this.size)) {
//			for (int y = 0; y < this.size; y += Math.sqrt(this.size)) {
//				ArrayList<Integer> square = this.getSquare(x, y);
//				Collections.sort(square);
//				if (!square.equals(possibles)) {
//					return false;
//				}
//			}
//		}
//		return true;
//	}
//
}