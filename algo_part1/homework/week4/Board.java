import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.List;

public class Board {

    private int row;
    private int hammDist, manhDist;
    private int[][] blocks;
    private List<Board> neighbors;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        row = tiles.length;

        blocks = new int[row][row];
        for(int i = 0; i < row; i++){
            for (int j = 0; j < row; j++){
                int temp = tiles[i][j];
                blocks[i][j] = temp;
                if (temp != 0){
                    if (temp != i*row+j+1) hammDist++;
                    temp -= 1;
                    int temp_row = temp/row;
                    int temp_col = temp%row;
                    manhDist += Math.abs(i - temp_row) + Math.abs(j - temp_col);
                }
            }
        }
    }

    // string representation of this board
    public String toString(){
        // String的构造
        StringBuilder stringOutput = new StringBuilder();
        stringOutput.append(dimension());
        for(int i = 0; i < dimension(); i++){
            stringOutput.append("\n");
            for(int j = 0; j < dimension(); j++){
                stringOutput.append(" " + blocks[i][j]);
            }
        }

        return stringOutput.toString();
    }

    // board dimension n
    public int dimension(){
        return row;
    }

    // number of tiles out of place
    public int hamming(){
        return hammDist;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        return manhDist;
    }

    // is this board the goal board?
    public boolean isGoal(){
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if(y == null) return false;
        if(y.getClass() != this.getClass()) return false;

        Board aux = (Board) y;
        if(aux.dimension() != this.dimension()) return false;

        for(int i = 0; i < row; i++){
            for(int j = 0; j < row; j++){
                if(aux.blocks[i][j] != blocks[i][j]) return false;
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        /** logic: 找到0的row,col,在0的上下左右四个方向上交换元素 */

        neighbors = new LinkedList<>();

        int cur_row = 0, cur_col = 0;

        // find 0's row and col
        // 新学的
        outerloop:
        for (int i = 0; i < dimension(); i++){
            for (int j = 0; j < dimension(); j++){
                if (blocks[i][j] == 0){
                    cur_row = i;
                    cur_col = j;
                    break outerloop;
                }
            }
        }

        // add new neighbor
        int[] dx = {1,-1,0,0};
        int[] dy = {0,0,1,-1};
        for (int i = 0; i < dx.length;i++){
            int posX = cur_row + dx[i];
            int posY = cur_col + dy[i];
            if(posX < dimension() && posX >= 0 && posY < dimension() && posY >= 0){
                int[][] neighbor =  copyBoard();
                int temp = neighbor[cur_row][cur_col];
                neighbor[cur_row][cur_col] = neighbor[posX][posY];
                neighbor[posX][posY] = temp;
                neighbors.add(new Board(neighbor));
            }
        }

        return neighbors;
    }

    private int[][] copyBoard(){
        int[][] aux = new int[dimension()][dimension()];
        for (int i = 0;i < dimension(); i++){
            for(int j = 0; j < dimension(); j++){
                aux[i][j] = blocks[i][j];
            }
        }
        return aux;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        /** the blank square is not a tile */
        // 任意交换,考虑不要超过row范围
        int[][] twinBoard = copyBoard();
        if(twinBoard[0][0] != 0 && blocks[1][0] != 0){
            twinBoard[0][0] = blocks[1][0];
            twinBoard[1][0] = blocks[0][0];
        }else{
            twinBoard[0][1] = blocks[1][1];
            twinBoard[1][1] = blocks[0][1];
        }

        return new Board(twinBoard);

    }

    // unit testing (not graded)
    public static void main(String[] args){
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board b = new Board(blocks);
        StdOut.println(b);
        StdOut.println(b.isGoal());
        StdOut.println(b.dimension());
        StdOut.println(b.hamming());
        StdOut.println(b.manhattan());
//        StdOut.println(b.neighbors());
    }

}
