import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked

    private WeightedQuickUnionUF uf;
    private int N;
    private int[] op;
    private int size;
    public Percolation(int n){
        if(n <= 0) throw new IllegalArgumentException();
        uf = new WeightedQuickUnionUF(n*n+2);
        N = n;
        op = new int[N*N];
        for(int i = 1; i < N*N; i++){
            op[i] = 0;
        }
        size = 0;

//        // 把最后一层和最后一个连接
//        for(int i = 1; i <= N; i++){
//            uf.union(N*(N-1)+i,N*N+1);
//        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        throwError(row,col);
        if(isOpen(row,col)) return;

        op[(row-1)*N+col-1] = 1;
        size += 1;

        // union up
        if(row == 1){
            uf.union(0,(row-1)*N+col);
        }else if(isOpen(row-1,col)){
            uf.union((row-2)*N+col,(row-1)*N+col);
        }

        //union down
        if(row != N && isOpen(row+1,col)){
            uf.union(row*N+col,(row-1)*N+col);
        }

        // union right and left
        if(col == 1 && (col+1 <= N) && isOpen(row,col+1)){
            uf.union((row-1)*N+col+1,(row-1)*N+col);
        }
        if(col == N && (col-1 >= 1) && isOpen(row,col-1)){
            uf.union((row-1)*N+col-1,(row-1)*N+col);
        }
        if(col != N && (col+1 <= N) && isOpen(row,col+1)){
            uf.union((row-1)*N+col+1,(row-1)*N+col);
        }
        if(col != 1 && (col-1 >= 1) && isOpen(row,col-1)){
            uf.union((row-1)*N+col-1,(row-1)*N+col);
        }

        // last row
        if(isFull(row,col)){
            for(int i = 1; i <= N; i++){
                if(isFull(N,i)) uf.union(N*N+1,(N-1)*N+i);
            }
        }
//        if(row == N){
//            //uf.union(N*N+1,(row-1)*N+col);
//            if(isFull(row,col)) uf.union(N*N+1,(row-1)*N+col);
//        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        throwError(row,col);
        return op[(row-1)*N+col-1] == 1;
    }

    // is the site (row, col) full?
    /** 根据要求,输入的row和col是1到n的 */
    public boolean isFull(int row, int col){
        throwError(row,col);
        return uf.connected(0,(row-1)*N+col);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return size;
    }

    // does the system percolate?
    public boolean percolates(){
        return uf.connected(0,N*N+1);
    }

//    // convert input row and col to index
//    private int openIndex(int row, int col){
//        return N * (row-1) + col-1;
//    }
    // throw error
    private void throwError(int row, int col){
        if(row > N || row < 1 || col > N || col < 1){
            throw new IllegalArgumentException();
        }
    }

    // test client (optional)
    public static void main(String[] args){
        int n = StdIn.readInt();
        Percolation testUF = new Percolation(n);
//        System.out.println(testUF.isFull(0,0));
//        System.out.println(testUF.isOpen(0,0));
        for(int i = 0;i < 34;i++){
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            testUF.open(p,q);
        }

        System.out.println(testUF.isFull(6,2));
        System.out.println(testUF.isOpen(6,1));
        System.out.println(testUF.percolates());

//        System.out.println(testUF.isOpen(1,1));
//        System.out.println(testUF.isFull(1,1));
//        System.out.println(testUF.numberOfOpenSites());
//        System.out.println(testUF.percolates());
    }
}
