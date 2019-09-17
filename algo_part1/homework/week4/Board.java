import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.List;

public class Board {

    private int row;
    private int[][] goal;
    private int[][] blocks;
    private List<Board> neighbors;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        row = tiles.length;
        goal = new int[row][row];
        blocks = new int[row][row];
        for(int i = 0; i < row; i++){
            for (int j = 0; j < row; j++){
                goal[i][j] = i*row + j+1;
                blocks[i][j] = tiles[i][j];
            }
        }
        goal[row-1][row-1] = 0; // 最后一位设置为0
    }

    // string representation of this board
    public String toString(){
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
        int dist = 0;
        for(int i = 0; i < row; i++){
            for(int j = 0; j < row; j++){
                if(goal[i][j] != blocks[i][j]) dist++;
            }
        }
        return dist;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int dist = 0;
        for(int i = 0; i < row; i++){
            for(int j = 0; j < row; j++){
                int num = blocks[i][j];
                if(num == 0) num = row*row;
                num -= 1;
                int cur_row = num/row;
                int cur_col = num%row;
                dist += Math.abs(i - cur_row) + Math.abs(j - cur_col);
            }
        }
        return dist;
    }

    // is this board the goal board?
    public boolean isGoal(){
        for (int i = 0; i < row; i++){
            for (int j = 0; j < row; j++){
                if(goal[i][j] != blocks[i][j]) return false;
            }
        }
        return true;
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
        int[][] twinBoard = copyBoard();
        if(twinBoard[0][0] != 0 && blocks[1][0] != 0){
            twinBoard[0][0] = blocks[1][0];
            twinBoard[1][0] = blocks[0][0];
        }else{
            twinBoard[1][1] = blocks[1][2];
            twinBoard[1][2] = blocks[1][1];
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
        StdOut.println(b.neighbors());
    }

}
