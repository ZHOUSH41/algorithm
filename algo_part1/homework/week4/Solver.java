import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Deque;
import java.util.LinkedList;

public class Solver {
    private SearchNode solutionNode;

    private class SearchNode implements Comparable<SearchNode>{
        public final Board board;
        public final SearchNode prev;
        public final int step;
        public final int priority;

        public SearchNode(Board board, SearchNode prev){
            this.board = board;
            this.prev = prev;
            this.step = prev == null ? 0 : prev.step+1;
            this.priority =  board.manhattan() + step;
        }

        public void insertNeighbors(MinPQ<SearchNode> pq){
            for (Board neighbor : board.neighbors()){
                if(prev != null && neighbor.equals(prev.board)) continue;

                SearchNode node = new SearchNode(neighbor, this);
                pq.insert(node);
            }
        }
        @Override
        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }

    }
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        if (initial == null) throw new IllegalArgumentException();
        MinPQ<SearchNode> solution = new MinPQ<>();
        MinPQ<SearchNode> twinSolution = new MinPQ<>();

        // init
        SearchNode init = new SearchNode(initial,null);
        solution.insert(init);

        SearchNode twinInit = new SearchNode(initial.twin(),null);
        twinSolution.insert(twinInit);


        while (true){
            SearchNode solutionNode = solution.delMin();
            SearchNode twinNode     = twinSolution.delMin();

            if (solutionNode.board.isGoal()){
                this.solutionNode = solutionNode;
                break;
            } else if (twinNode.board.isGoal()){
                this.solutionNode = null;
                break;
            }

            solutionNode.insertNeighbors(solution);
            twinNode.insertNeighbors(twinSolution);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return solutionNode != null;
    }

    // min number of moves to solve initial board
    public int moves(){
        if (isSolvable()){
            return solutionNode.step;
        }else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution(){
        if (isSolvable()){
            Deque<Board> ans = new LinkedList<>();
            SearchNode nextNode = solutionNode;
            while (nextNode != null){
                ans.addFirst(nextNode.board);
                nextNode = nextNode.prev;
            }

            return ans;
        } else return null;

    }

    // test client (see below)
    public static void main(String[] args){
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
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
