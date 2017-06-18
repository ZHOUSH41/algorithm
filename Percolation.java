package FirstWeek;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/**
 * Created by zhou on 2017/6/18.
 */
public class Percolation {
        private int gridLength;
        private boolean[] isOpen;
        private WeightedQuickUnionUF percolation;
        private WeightedQuickUnionUF fullness;
        private int virtualTopIndex;
        private int virtualBottomIndex;

        private int siteIndex(int i, int j){
            checkBound(i,j);
            int x = j;
            int y = i;
            return (y-1) * gridLength + (x);
        }

        private void checkBound(int i, int j) {
            if (i > gridLength || i < 1){
                throw new IndexOutOfBoundsException("row index i out of bounds.");
            }
            if (j > gridLength || j < 1){
                throw new IndexOutOfBoundsException("column index j out of bounds");
            }
        }



        public Percolation(int N)                // create n-by-n grid, with all sites blocked
        {
            if ( N < 1 ){
                throw new IllegalArgumentException();
            }
            gridLength = N;
            int arraySize = N*N + 2;
            isOpen = new boolean[arraySize];
        }
        public    void open(int row, int col)    // open site (row, col) if it is not open already
        public boolean isOpen(int row, int col)  // is site (row, col) open?
        public boolean isFull(int row, int col)  // is site (row, col) full?
        public     int numberOfOpenSites()       // number of open sites
        public boolean percolates()              // does the system percolate?

        public static void main(String[] args)   // test client (optional)
}
