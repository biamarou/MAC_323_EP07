import java.util.Iterator;
import java.util.LinkedList;

public class Board {
    int[][] board;
    int moves, size, manhattan, hamming, inverted;
    Board previous;

    private boolean validate (int row, int col) {
	if (row < 0 || row > size - 1 || col < 0 || col > size - 1)
	    return false;
        return true;
    }

    public void boardNeighbors (LinkedList<Board> list) {
        int row, col, blank;
	blank = blankSquare();

	row = blank / size;
	col = blank % size;
	
        if (validate(row + 1, col)) {
	    Board neighbors = new Board(board);
	    neighbors.board[row][col] = board[row + 1][col];
	    neighbors.board[row + 1][col] = 0;

	    neighbors.moves = moves + 1;
	    list.add(neighbors);
	}

	if (validate(row - 1, col)) {
	    Board neighbors = new Board(board);
	    neighbors.board[row][col] = board[row - 1][col];
	    neighbors.board[row - 1][col] = 0;

	    neighbors.moves = moves + 1;
	    list.add(neighbors);
	}

	if (validate(row, col + 1)) {
	    Board neighbors = new Board(board);
	    neighbors.board[row][col] = board[row][col + 1];
	    neighbors.board[row][col + 1] = 0;

	    neighbors.moves = moves + 1;
	    list.add(neighbors);
	}

	if (validate(row, col - 1)) {
	    Board neighbors = new Board(board);
	    neighbors.board[row][col] = board[row][col - 1];
	    neighbors.board[row][col - 1] = 0;

	    neighbors.moves = moves + 1;
	    list.add(neighbors);
	}	    
		
    }

    public Board(int[][] tiles) {
	previous = null;
	moves = manhattan = hamming = inverted = 0;
	size = tiles.length;
	board = new int[size][size];

	for (int i = 0; i < size; i++)
	    for (int j = 0; j < size; j++)
	       board[i][j] = tiles[i][j];
	    
    } // construct a board from an N-by-N array of tiles (where tiles[i][j] = tile at row i, column j)

    private int[] creates_positions () {
	int[] positions = new int[size*size];

	for (int i = 0; i < size; i++)
	    for (int j = 0; j < size; j++)
		positions[i * size + j] = board[i][j];

	return positions;
    }

    public int tileAt(int i, int j) {
	return board[i][j];
    } // return tile at row i, column j (or 0 if blank)

    public int size() {
	return size;
    } // board size N

    public int hamming() {
	if (hamming == 0) {   
	    int[] positions = creates_positions(); 
	    for (int i = 0; i < size*size; i++)
		if (positions[i] != 0 && positions[i] != i + 1) hamming++;
	}
	return hamming;
	
    } // number of tiles out of place

    private int modulus (int n) {
	if (n >= 0) return n;
	else return -n;
    }

    public int manhattan() {

	if (manhattan == 0) {
	    int man, row, col, row_goal, col_goal;
	    int[] positions = creates_positions();
	    man = 0;
	    for (int i = 0; i < size*size; i++) {
		if (positions[i] != 0 && positions[i] != i + 1) {
		    row_goal = ((positions[i] - 1) / size);
		    col_goal = ((positions[i] - 1) % size);

		    row = i / size;
		    col = i % size;

		    man += (modulus(row_goal - row) + modulus(col_goal - col));
		}
	    }
	    
            manhattan = man;
	}

	return manhattan;
    } // sum of Manhattan distances between tiles and goal

    public boolean isGoal() {
	int goal = hamming();
	if (goal == 0) return true;
	else return false;
			  
    } // is this board the goal board?

    
    private int inverted () {
	if (inverted == 0) {
	    int[]positions = creates_positions();
	
	    int j, invert;
	    invert = 0;
	    
	    for (int i = 1; i < size*size; i++) {
		if (positions[i] != 0) {
		    j = i - 1;
		    while (j >= 0) {
		        if (positions[j] > positions[i]) {
			    invert++;
		        }
			j--;
		    }
		
		}
	    }

	    inverted = invert;
	}
	return inverted;
    }

    
    private int blankSquare() {
	int blank = 0;
	boolean found_flag = false;
	for (int i = 0; i < size && !found_flag; i++) {
	    for (int j = 0; j < size && !found_flag; j++) {
		if (board[i][j] == 0) {
		    found_flag = true;
		    blank = i*size + j;
		}
	    }
        }
	    
	return blank;
    }

    public boolean isSolvable() {
	//if (isGoal()) return true;

	int solve, blank;
	blank = blankSquare();
	blank /= size;
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
