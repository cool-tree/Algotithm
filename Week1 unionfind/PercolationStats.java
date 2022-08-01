/*
to perform a series of computational experiments
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] threshold;
    private final int t;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("n<=0 || trials <=0");
        t = trials;
        threshold = new double[trials];

        for (int i = 0; i < trials; i++) {

            Percolation gridss = new Percolation(n);
            while (!gridss.percolates()) {
                int row = StdRandom.uniform(1, n + 1); // return int type
                int col = StdRandom.uniform(1, n + 1);

                while (gridss.isOpen(row, col)) {
                    row = StdRandom.uniform(1, n + 1);
                    col = StdRandom.uniform(1, n + 1);
                }

                gridss.open(row, col);
            }

            threshold[i] = ((double) gridss.numberOfOpenSites())/(n*n);

        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean()-1.96*this.stddev()/ Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean()+1.96*this.stddev()/ Math.sqrt(t);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats test = new PercolationStats(n, trials);

        StdOut.println("mean                    = "+test.mean());
        StdOut.println("stddev                  = "+test.stddev());
        StdOut.println("95% confidence interval = ["+test.confidenceLo()+","+test.confidenceHi()+"]");
    }
}
