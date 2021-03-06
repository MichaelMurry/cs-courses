import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;
    private double[] est;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("Invalid input : n or trials must > 0 !");
        est = new double[trials];
        for (int k = 0; k < trials; k++) {
            Percolation perc = new Percolation(n);
            double count = 0;
            while (!perc.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                if (perc.isOpen(i, j)) continue;
                perc.open(i, j);
                count++;
            }
            est[k] = count / (n * n);
        }
        mean = StdStats.mean(est);
        stddev = StdStats.stddev(est);
        confidenceLo = mean - (1.96 * stddev) / Math.sqrt(trials);
        confidenceHi = mean + (1.96 * stddev) / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = " + stats.confidenceLo()
                + ", " + stats.confidenceHi());
    }


    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return confidenceLo;
    }

    public double confidenceHi() {
        return confidenceHi;
    }
}