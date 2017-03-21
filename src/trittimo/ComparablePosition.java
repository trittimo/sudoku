package trittimo;

public class ComparablePosition implements Comparable<ComparablePosition> {
	public Integer[] position;
	public SudokuBoard board;
	public ComparablePosition(Integer[] position, SudokuBoard board) {
		this.position = position;
		this.board = board;
	}
	
	@Override
	public int compareTo(ComparablePosition other) {
		int mine = board.getValidNumbers(position[0], position[1]).size();
		int theirs = board.getValidNumbers(other.position[0], other.position[1]).size();
		if (mine < theirs) {
			return -1;
		} else if (mine == theirs) {
			return 0;
		}
		return 1;
	}
	
}