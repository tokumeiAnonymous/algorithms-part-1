
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * 
 * @author jerometaguba
 *
 */


public class Percolation {
    
    private boolean[][] board;
    private final int N;
    
    private WeightedQuickUnionUF grid;          
    private WeightedQuickUnionUF full;
    
    private int openCell;
    private int top;
    private int bottom;
    
    public Percolation(int N) {
        if(N<=0) {
            throw new IllegalArgumentException("N must be greater than 0");
        }
        
        this.N=N;
        board=new boolean[N][N];
        grid=new WeightedQuickUnionUF(N*N+2);  
        full=new WeightedQuickUnionUF(N*N+1);
        openCell=0;
        
        top=0;
        bottom=N*N+1;
        
        //connectTopBottom();                         //connects the top most part to 0(virtual top)
                                                      //connects the bottom most part to 101(virtual bottom)
        /*
         * do not initialize the connection here
         * only connect it to the virtual top as soon as it opens the topmost part
         * only connect it to the virtual bottom as soon as it opens the bottommost part
         * only connect the specific indexes to their virtual counterpart
         * for the bottom connect the grid only not the full to prevent backwash
         */
    }
    
    public void open(int row, int col) {
        if(isOpen(row, col)) {
            return;
        }
        
        board[row-1][col-1]=true;
        openCell++;
        
        connectToNeighbor(row, col);
        
    }
    
    public boolean isOpen(int row, int col) {
        
        isOutOfBounds(row, col);
        
        return board[row-1][col-1];
    }
    /*
     * it is considered full when the the index is connected to the virtual top
     */
    public boolean isFull(int row, int col) {
        int index=getSingleIndex(row,col);
        
        return full.find(top)==full.find(index);
    }
    
    public int numberOfOpenSites() {
        return openCell;
    }
    
    public boolean percolates() {
        
        return grid.find(top)==grid.find(bottom);
    }
    
    private void connectTopBottom() {
        
        for (int i=1;i<=N;i++) {
            grid.union(top, i);
        }
        for (int i=N*(N-1)+1;i<=N*N;i++) {
            grid.union(bottom, i);
        }
    }
    
    private void isOutOfBounds(int row, int col) {
        
        if(row<=0 || row>N || col<=0 || col>N) {
            throw new IllegalArgumentException("You are out of bounds");
        }
            
    }
    
   /* public boolean neighborBounds(int i, int j) {
        
        if(i<0 || j<0) {
            return false;
        }
        
        return true;
    } */
    
    private void connectToNeighbor(int row, int col) {
        int i=row-1;
        int j=col-1;
        int index=getSingleIndex(row, col);
        
        if(i==0) {
            grid.union(index, top);
            full.union(index, top);
            
        }
        if(i==N-1) {
            grid.union(index, bottom);
        }
        
        if((row>1)&&(board[i-1][j])) {
           grid.union(index, index-N);
           full.union(index, index-N);
        }
        if((row<N)&&(board[i+1][j])) {
            grid.union(index, index+N);
            full.union(index, index+N);
        }
        if((col>1)&&(board[i][j-1])) {
            grid.union(index, index-1);
            full.union(index, index-1);
        }
        if((col<N)&&(board[i][j+1])) {
            grid.union(index, index+1);
            full.union(index, index+1);
        }
        /*if((row>1)&&(col>1)&&(board[i-1][j-1])) {
            grid.union(index, index-1-N);
        }
        if((row<N)&&(col>1)&&(board[i+1][j-1])) {
            grid.union(index, index-1+N);
        }
        if((row>1)&&(col<N)&&(board[i-1][j+1])) {
            grid.union(index, index+1-N);
        }
        if((row<N)&&(col<N)&&(board[i+1][j+1])) {
            grid.union(index, index+1+N);
        }*/
        
    }
    
    private int getSingleIndex(int row, int col) {
        
        isOutOfBounds(row, col);
        
        return(N*(row-1)+col);
    }
    
    public static void main(String[] args) {
        
        Percolation test=new Percolation(10);
        while(!test.percolates()) {
            int row=StdIn.readInt();
            int col=StdIn.readInt();
            test.open(row, col);
        }
        
        System.out.println(test.openCell);
        
        for (int i=0;i<test.N;i++) {
            for (int ii=0;ii<test.N;ii++) {
                int temp=0;
                if(test.board[i][ii]) temp=1;
                System.out.print(temp + "  ");
            }
            System.out.println();
        }
        
    }
}
