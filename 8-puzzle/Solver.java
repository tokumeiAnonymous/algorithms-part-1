import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
public class Solver {
    private Board board;
    public Solver(Board initial) {
        // update this
        board = initial;
        MinPQ<Board> priorityQueue = new MinPQ<Board>();
        // just to satisfy the error
        initial.manhattan();
    }
    public boolean isSolvable() {
        return false;
    }
    public int moves() {
        return 0;
    }
    public Iterable<Board> solution() {
        return null;
    }
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        // solve the puzzle
        Solver solver = new Solver(initial);
        // print solution to standard output
        if (!solver.isSolvable())
            System.out.println("No solution possible");
        else {
            System.out.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                System.out.println(board);
        }
    }
}
