import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    
    private double[] percolationRatio;
    private int trials;
    private final int N;
    
    
    public PercolationStats(int n, int trials) {
        
        if(n<=0 || trials <=0) {
            throw new IllegalArgumentException("Not valid Grid size or number of trials");
        }
        
        percolationRatio=new double[trials];
        this.trials=trials;
        N=n;
        
        startTrial();
        
    }
    
    public double mean() {
        
        return StdStats.mean(percolationRatio);
    }
    
    public double stddev() {
        
        return StdStats.stddev(percolationRatio);
    }
    
    public double confidenceLo() {
        
        return StdStats.min(percolationRatio);
    }
    
    public double confidenceHi() {
        
        return StdStats.max(percolationRatio);
    }
    
    private void startTrial() {
        
        for (int i=0;i<trials;i++) {
            Percolation percolation=new Percolation(N);
            
            
            while (!percolation.percolates()) {
                int row=StdRandom.uniform(1, N+1);
                int col=StdRandom.uniform(1, N+1);
                //int row=StdIn.readInt();
                //int col=StdIn.readInt();
                percolation.open(row, col);
                //System.out.println(row+"  " +col);
            }
            //System.out.println("Another set");
            percolationRatio[i]=(double) percolation.numberOfOpenSites()/(N*N);
        }
        
    }
    
    public static void main(String[] args) {
        int n=StdIn.readInt();
        int trial=StdIn.readInt();
        
        Stopwatch timer=new Stopwatch();
        
        PercolationStats test=new PercolationStats(n, trial);

        
        System.out.println("mean = " + test.mean());
        System.out.println("stddev = " + test.stddev());
        System.out.println("95% confidence interval = [ " + test.confidenceLo()
                            + ", " + test.confidenceHi() + " ]");
        
        System.out.println(timer.elapsedTime());
        
    }

}
