public class Board {

    private int row, col;
    private int[][] goal;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        row = tiles.length;
        col = tiles[0].length;
        int 
        for(int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                goal[i][j] =
            }
        }
    }

    // string representation of this board
    public String toString(){

    }

    // board dimension n
    public int dimension(){
        return row;
    }

    // number of tiles out of place
    public int hamming(){

    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){

    }

    // is this board the goal board?
    public boolean isGoal(){

    }

    // does this board equal y?
    public boolean equals(Object y){

    }

    // all neighboring boards
    public Iterable<Board> neighbors(){

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){

    }

    // unit testing (not graded)
    public static void main(String[] args){

    }

}
