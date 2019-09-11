import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by zhou on 2017/6/18.
 */
public class PercolationStats
{
        private double[] thresholdResults;
        private int T;

        public PercolationStats(int N, int T)    // perform trials independent experiments on an n-by-n grid
        {
            if(N < 1|| T < 1)
            {
                throw new IllegalArgumentException("error");
            }

            this.T = T;
            thresholdResults = new double[T];
            for(int t = 0;t < T; t++)
            {
                Percolation percolation = new Percolation(N);
                int openSites = 0;
                while(!percolation.percolates())
                {
                    int i = StdRandom.uniform(1, N+1);
                    int j = StdRandom.uniform(1,N+1);

                    if (!percolation.isOpen(i,j))
                    {
                        percolation.open(i,j);
                        openSites += 1;
                    }
                }
                double threshold = (double)openSites/(double)(N*N);
                thresholdResults[t] = threshold;
            }
        }
        public double mean()                          // sample mean of percolation threshold
        {
            return StdStats.mean(thresholdResults);
        }
        public double stddev()                        // sample standard deviation of percolation threshold
        {
           return  StdStats.stddev(thresholdResults);
        }
        public double confidenceLo()                  // low  endpoint of 95% confidence interval
        {
            return mean() - (1.96 * stddev()/Math.sqrt(T));
        }
        public double confidenceHi()                  // high endpoint of 95% confidence interval
        {
            return mean() +(1.96 * stddev()/Math.sqrt(T));
        }
        public static void main(String[] args)        // test client (described below)
        {
            int N = Integer.parseInt(args[0]);
            int T = Integer.parseInt(args[1]);
            PercolationStats stats = new PercolationStats(N,T);
            System.out.println("mean = "+ stats.mean());
            System.out.println("standard deviation = "+ stats.stddev());
            System.out.println("95% confidence interval = "+ stats.confidenceLo() + " , " + stats.confidenceHi());
        }
}
