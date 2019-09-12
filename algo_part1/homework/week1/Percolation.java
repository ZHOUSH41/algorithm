import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    /** solution 1: 用两个uf,规避isFull的bug,但是memory就超过了 */

    /** 参考的解法: https://segmentfault.com/a/1190000005345079 可能以后上不了,所以写了下来
     * solution 2: 用一个只有top virtual site的uf来构造,重点的是open array的赋值:
     * 最终形成并实现了解决方案，方案核心如下（corner case另行处理）：
     *
     * 将原方案中表示open状态的boolean数组改为byte数组，设定规则如下：初始化的默认值0代表blocked site，赋1代表open site，赋2代表与尾行相连的open site；
     * 每open一个site，如果位于尾行则赋2，否则赋1；
     * 分别对每个邻接site检测：如任何一方的root site对应byte值为2，将双方Union后的root site设为2。（root为Find()的返回值）
     *
     * 此方案下，判断open只需要对应byte>0，判断full使用UF结果准确，判断percolates检测virtual top的root site对应byte是否为2。*/

    // creates n-by-n grid, with all sites initially blocked
    private WeightedQuickUnionUF uf;
    //private WeightedQuickUnionUF ufTop;
    private int N;
    private int[] op;
    private int size;
    public Percolation(int n){
        if(n <= 0) throw new IllegalArgumentException();
        //uf = new WeightedQuickUnionUF(n*n+2);
        uf = new WeightedQuickUnionUF(n*n+1);
        N = n;
        op = new int[n*n+1];
        op[0] = 1;
        for(int i = 1; i < n*n; i++){
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
        /** op[index] 值为1表示打开但没有和最后一行相连,值为2表示打开并和最后一行相连 */
        if(row == N){
            op[index] = 2;
        }else {
            op[index] = 1;
        }
        size += 1;

        int root_self,root_other;
        // union up
        if (row == 1) {
            root_other = uf.find(0);
            root_self = uf.find(index);
            if(op[root_self] == 2 || op[root_other] == 2){
                op[root_self] = 2;
                op[root_other] = 2;
            }
            uf.union(0, col);

        }
//        if (row == N) {
//            uf.union(N * N+1, index);
//            root_other = uf.find(col);
//            if(op[root_self] == 2 || op[root_other] == 2){
//                op[root_self] = 2;
//                op[root_other] = 2;
//            }
//        }

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        for (int i = 0; i < 4; i++) {
            int posX = row + dx[i];
            int posY = col + dy[i];
            if (posX <= N && posX >= 1
                    && posY <= N && posY >= 1
                    && isOpen(posX, posY)) {
                root_other = uf.find((posX-1)*N+posY);
                /** corner case: important,这里需要更新一下root_self,
                 * 因为row == 1的情况可能会更新人root_self */
                root_self  =uf.find((row-1)*N+col);
                if(op[root_self] == 2 || op[root_other] == 2){
                    op[root_self] = 2;
                    op[root_other] = 2;
                }
                uf.union(index, (posX - 1) * N + posY);

            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        throwError(row,col);
        return op[(row-1)*N+col] > 0;
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
        int root = uf.find(0);
        return op[root] == 2;
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
        for(int i = 0;i < 4;i++){
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            testUF.open(p,q);
        }

//        Percolation testUF = new Percolation(2);
//        testUF.open(2,2);
//        testUF.open(2,1);
//        testUF.open(1,1);
//        System.out.println(testUF.isFull(6,2));
//        System.out.println(testUF.isOpen(2,1));
        System.out.println(testUF.percolates());

//        System.out.println(testUF.isOpen(1,1));
//        System.out.println(testUF.isFull(1,1));
//        System.out.println(testUF.numberOfOpenSites());
//        System.out.println(testUF.percolates());
    }
}
