import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.lang.Iterable;
import java.util.Comparator;
import java.util.Stack;

public class Solver {
    MinPQ<Board> solver = null;

    public static class BoardOrder implements Comparator<Board> {
	public int compare (Board b1, Board b2) {
	    if (b1.manhattan() + b1.moves > b2.manhattan() + b2.moves) return +1;
	    if (b1.manhattan() + b1.moves < b2.manhattan() + b2.moves) return -1;
	    return 0;
	}
    }
    
    public Solver(Board initial) {
	if (!initial.isSolvable())
	    return;
	Iterable<Board> paths;
	Board tmp, prev;

	solver = new MinPQ<Board>(new BoardOrder());
	tmp = prev = null;
	solver.insert(initial);

	while(!((solver.min()).isGoal())) {
	    tmp = solver.delMin();
	    paths = tmp.neighbors();
	    
            
	    for(Board b : paths) {
		if (prev != null && !b.equals(prev)) {
                    
		    b.previous = tmp;
		    solver.insert(b);
		}

		else if (prev == null) {
		    b.previous = tmp;
		    solver.insert(b);
		    
		}	
	    }
	    prev = tmp;
	}

	
    } // find a solution to the initial board (using the A* algorithm)

    public int moves() {
	return solver.min().moves;
    } // min number of moves to solve initial board

    public Iterable<Board> solution() {
	return solver;
    } // sequence of boards in a shortest solution

    public static void main(String[] args) {
        In file = new In(args[0]);
        int num = file.readInt();
	int[][]game = new int[num][num];
	Stack<Board> solution = new Stack<Board>();
	Board print;
	
	for(int i = 0; i < num; i++)
	    for (int j = 0; j < num; j++)
		game[i][j] = file.readInt();

	Board initial = new Board(game);

	Solver search = new Solver(initial);

	if(search.solver == null) StdOut.println("no solution");
	else {
	    print = search.solver.delMin();

	    while (print != null) {
	        solution.push(print);
		print = print.previous;
	    }

	    while (!solution.isEmpty()) {
		print = solution.pop();
		StdOut.println(print.toString());

	    }
	}
    } // unit testing 
}
