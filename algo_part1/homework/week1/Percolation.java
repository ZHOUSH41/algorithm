import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked

    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufTop;
    private int N;
    private int[] op;
    private int size;
    public Percolation(int n){
        if(n <= 0) throw new IllegalArgumentException();
        uf = new WeightedQuickUnionUF(n*n+2);
        ufTop = new WeightedQuickUnionUF(n*n+1);
        N = n;
        op = new int[N*N+1];
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

        int index = (row - 1) * N + col;
        op[index] = 1;
        size += 1;

        // union up
        if (row == 1) {
            uf.union(0, col);
            ufTop.union(0, col);
        }
        if (row == N) {
            uf.union(N * N + 1, index);
        }

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        for (int i = 0; i < 4; i++) {
            int posX = row + dx[i];
            int posY = col + dy[i];
            if (posX <= N && posX >= 1
                    && posY <= N && posY >= 1
                    && isOpen(posX, posY)) {
                uf.union(index, (posX - 1) * N + posY);
                ufTop.union(index, (posX - 1) * N + posY);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        throwError(row,col);
        return op[(row-1)*N+col] == 1;
    }

    // is the site (row, col) full?
    /** 根据要求,输入的row和col是1到n的 */
    public boolean isFull(int row, int col){
        throwError(row,col);
        return ufTop.connected(0,(row-1)*N+col);
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
        for(int i = 0;i < 18;i++){
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            testUF.open(p,q);
        }

        System.out.println(testUF.isFull(5,5));
        System.out.println(testUF.isOpen(2,1));
        System.out.println(testUF.percolates());

//        System.out.println(testUF.isOpen(1,1));
//        System.out.println(testUF.isFull(1,1));
//        System.out.println(testUF.numberOfOpenSites());
//        System.out.println(testUF.percolates());
    }
}
