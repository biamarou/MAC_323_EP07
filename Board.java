import java.util.Iterator;
import java.util.LinkedList;

public class Board {
    int[][] board;
    int[] positions;
    int moves;
    int size;
    Board previous;

    private boolean validate (int row, int col) {
	if (row < 0 || row > size - 1 || col < 0 || col > size - 1)
	    return false;
        return true;
    }
	
    public void boardNeighbors (LinkedList<Board> list) {
	int i, row, col;
	for (i = 0; positions[i] != 0; i++);      
	
	row = i/size; col = i % size;
        	
	if (validate(row + 1, col)) {
	    Board neighbors = new Board(board);
	    neighbors.board[row][col] = board[row + 1][col];
	    neighbors.board[row + 1][col] = 0;

	    neighbors.positions[i] = positions[size*(row + 1) + col];
	    neighbors.positions[size*(row + 1) + col] = 0;

	    neighbors.moves = moves + 1;
	    list.add(neighbors);
	}

	if (validate(row - 1, col)) {
	    Board neighbors = new Board(board);
	    neighbors.board[row][col] = board[row - 1][col];
	    neighbors.board[row - 1][col] = 0;

	    neighbors.positions[i] = positions[size*(row - 1) + col];
	    neighbors.positions[size*(row - 1) + col] = 0;

	    neighbors.moves = moves + 1;
	    list.add(neighbors);
	}

	if (validate(row, col + 1)) {
	    Board neighbors = new Board(board);
	    neighbors.board[row][col] = board[row][col + 1];
	    neighbors.board[row][col + 1] = 0;

	    neighbors.positions[i] = positions[size*row + col + 1];
	    neighbors.positions[size*row + col + 1] = 0;

	    neighbors.moves = moves + 1;
	    list.add(neighbors);
	}

	if (validate(row, col - 1)) {
	    Board neighbors = new Board(board);
	    neighbors.board[row][col] = board[row][col - 1];
	    neighbors.board[row][col - 1] = 0;

	    neighbors.positions[i] = positions[size*row + col - 1];
	    neighbors.positions[size*row + col - 1] = 0;

	    neighbors.moves = moves + 1;
	    list.add(neighbors);
	}	    
		
    }
	

    public Board(int[][] tiles) {
	previous = null;
	moves = 0;
	size = tiles.length;
	board = new int[size][size];
	positions = new int[size*size];

	for (int i = 0; i < size; i++)
	    for (int j = 0; j < size; j++)
		positions[i * size + j] = board[i][j] = tiles[i][j];

	    
    } // construct a board from an N-by-N array of tiles (where tiles[i][j] = tile at row i, column j)

    public int tileAt(int i, int j) {
	return board[i][j];
    } // return tile at row i, column j (or 0 if blank)

    public int size() {
	return size;
    } // board size N

    public int hamming() {
	int ham = 0;
	for (int i = 0; i < size*size; i++)
	    if (positions[i] != 0 && positions[i] != i + 1) ham++;

	return ham;
    } // number of tiles out of place

    private int modulus (int n) {
	if (n >= 0) return n;
	else return -n;
    }

    public int manhattan() {
	int man, row, col, row_goal, col_goal;
	man = 0;
	for (int i = 0; i < size*size; i++) {
	    if (positions[i] != 0 && positions[i] != i + 1) {
		row_goal = ((positions[i] - 1) / size);
		col_goal = ((positions[i] - 1) % size);

	        row = i / size;
		col = i % size;

		man += ((row_goal - row) + (col_goal - col));
	    }
	}

        return modulus(man);
    } // sum of Manhattan distances between tiles and goal

    public boolean isGoal() {
	int goal = hamming();
	if (goal == 0) return true;
	else return false;
			  
    } // is this board the goal board?

    private int inverted() {
	int j, invert;
	invert = 0;
	for (int i = 1; i < size*size; i++) {
	    if (positions[i - 1] > positions[i]) {
		j = i - 1;
		while (j >= 0 && positions[j] > positions[i]) {
		    invert++;
		    j--;
		}
		
	    }
	}

	return invert;
    }

    private int blankSquare() {
	int blank = 0;
	for (int i = 0; i < size*size; i++)
	    if (positions[i] == 0)
		blank = i/size;
	return blank;
    }

    public boolean isSolvable() {
	int solve, blank;
	blank = blankSquare();
	solve = inverted();

	if (size % 2 != 0) {
	    if (solve % 2 != 0)
		return false;
	    else
		return true;
	}

	else {
	    if ((solve + blank) % 2 == 0)
		return false;
	    else
		return true;
	}
    } // is this board solvable?

    public boolean equals(Object y) {
	return (y.equals(board));
	
    } // does this board equal y?


    public Iterable<Board> neighbors() {
	LinkedList<Board> neighbors = new LinkedList<Board>();
	boardNeighbors(neighbors);
	return neighbors;
    } // all neighboring boards

    public String toString() {
	String board_rep = "";
	for (int i = 0; i < size; i++) {
	    for (int j = 0; j < size; j++)
		board_rep += (Integer.toString(board[i][j]) + " ");
	    board_rep += '\n';
	}

	return board_rep;
    } // string representation of this board (in the output format specified below)

    public static void main(String[] args) {

    } // unit testing (required)
}
