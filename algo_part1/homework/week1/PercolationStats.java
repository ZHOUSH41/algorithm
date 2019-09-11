import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform independent trials on an n-by-n grid
    private double[] op;
    private double mean;
    private double std;
    public PercolationStats(int n, int trials){
        if(n <=0 || trials <= 0) throw new IllegalArgumentException();
        op = new double[trials];
        for(int i = 0; i < trials; i++){
            Percolation uf = new Percolation(n);
            while (!uf.percolates()){
                int row = StdRandom.uniform(1,n+1);
                int col = StdRandom.uniform(1,n+1);
                uf.open(row,col);
            }
            op[i] = (double) uf.numberOfOpenSites() / (double) (n*n);
        }
        mean = StdStats.mean(op);
        std = StdStats.stddev(op);
    }

    // sample mean of percolation threshold
    public double mean(){
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return std;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean - (1.96 * std/Math.sqrt(op.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean + (1.96 * std/Math.sqrt(op.length));
    }

    // test client (see below)
    public static void main(String[] args){
        PercolationStats stat = new PercolationStats(2, 1000);
        System.out.println("mean   = " + stat.mean());
        System.out.println("stddev = " + stat.stddev());
        System.out.println("95% confidence interval = [" + stat.confidenceLo() + ", " + stat.confidenceHi() + "]" );
    }
}
