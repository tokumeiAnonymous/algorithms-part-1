import edu.princeton.cs.algs4.Stack;

public class Board {

    private final int[][] board;
    private int hammingCount;
    private int manhattanCount;
    private int emptyI;
    private int emptyJ;
    private final StringBuilder boardString;
    
    // @param 2 <= n <= 128
    public Board(int[][] tiles) {
        
        if (tiles == null) throw new IllegalArgumentException("Board can't be null");
        board = new int[tiles.length][tiles.length];
        
        hammingCount = 0;
        manhattanCount = 0;
        
        boardString = new StringBuilder("" + this.dimension() + "\n ");
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = tiles[i][j];
                boardString.append(board[i][j] + "  ");
            }
            boardString.append("\n ");
        }
        
        calculateDistances();
        
    }
    
    public String toString() {
        
        return boardString.toString();
    }
    
    public int dimension() {
        
        return board.length;
    }
    
    public int hamming() {
        
        return hammingCount;
    }
    
    public int manhattan() {
        
        return manhattanCount;
    }
    
    private void calculateDistances() {
        
        int tempCounter = 0;
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                tempCounter++;
                // for the empty position
                if (board[i][j] == 0) {
                    emptyI = i;
                    emptyJ = j;
                   continue;
                }
                
                if (board[i][j] != tempCounter) {
                    hammingCount++;
                    manhattanCount += calculateManhattan(i, j, board[i][j]);
                }
            }
        }
    }
    
    private int calculateManhattan(int currentI, int currentJ, int value) {
        
        int correctI = 0;
        int correctJ = 0;

        if (value % board.length == 0) {
            // since array starts at 0
            correctI = (value / board.length) - 1;
            correctJ = board.length - 1;
        } else {
            correctI = (value / board.length);
            correctJ = (value % board.length) - 1;
            
        }
        
        return Math.abs(currentI - correctI) + Math.abs(currentJ - correctJ);
    }
    
    public boolean isGoal() {
        
        return manhattanCount == 0;
    }
    
    public boolean equals(Object y) {
        
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;
        
        return this.toString().equals(y.toString());
    }
    
    public Iterable<Board> neighbors() {
        
        Stack<Board> stackBoard = new Stack<Board>();
        
        // this will set true for corners that is out of bounds/edges
        boolean addedTop = emptyI == 0;
        boolean addedBottom = emptyI == board.length - 1;
        boolean addedLeft = emptyJ == 0;
        boolean addedRight = emptyJ == board.length - 1;
        
        
        if (!addedTop) {
            Board topNeighbor = getNeighborBoard(1);
            stackBoard.push(topNeighbor);
        }
        if (!addedBottom) {
            Board bottomNeighbor = getNeighborBoard(2);
            stackBoard.push(bottomNeighbor);
        }
        if (!addedLeft) {
            Board leftNeighbor = getNeighborBoard(3);
            stackBoard.push(leftNeighbor);
        }
        if (!addedRight) {
            Board rightNeighbor = getNeighborBoard(4);
            stackBoard.push(rightNeighbor);
        }
        
        return stackBoard;
    }
    
    // code 1 = top, 2 = bottom, 3 = left, 4 = right;
    private Board getNeighborBoard(int pos) {
        int[][] tempBoard = copyBoard();
        
        if (pos == 1) {
            tempBoard[emptyI][emptyJ] = tempBoard[emptyI - 1][emptyJ];
            tempBoard[emptyI - 1][emptyJ] = 0;
        } else if (pos == 2) {
            tempBoard[emptyI][emptyJ] = tempBoard[emptyI + 1][emptyJ];
            tempBoard[emptyI + 1][emptyJ] = 0;
        } else if (pos == 3) {
            tempBoard[emptyI][emptyJ] = tempBoard[emptyI][emptyJ - 1];
            tempBoard[emptyI][emptyJ - 1] = 0;
        } else if (pos == 4) {
            tempBoard[emptyI][emptyJ] = tempBoard[emptyI][emptyJ + 1];
            tempBoard[emptyI][emptyJ + 1] = 0;
        }
        
        return new Board(tempBoard);
    }
    
    public Board twin() {
        
        int rowA = 0;
        int colA = 0;
        int rowB = 0;
        int colB = 0;
        
        if (rowA == emptyI && colA == emptyJ) {
            if (rowA < board.length - 1) rowA++;
            else colA++;
        }
        while ((rowA == rowB && colA == colB) || (rowB == emptyI && colB == emptyJ)) {
            if (rowB < board.length - 1) rowB++;
            else colB++;
        }
        
        
        int[][] tempBoard = copyBoard();
        
        int temp = tempBoard[rowA][colA];
        tempBoard[rowA][colA] = tempBoard[rowB][colB];
        tempBoard[rowB][colB] = temp;
        
        return new Board(tempBoard);
    }
    
    private int[][] copyBoard() {
        
        int[][] tempBoard = new int[board.length][board.length];
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                tempBoard[i][j] = board[i][j];
            }
        }
        return tempBoard;
    }
    
    public static void main(String[] args) {
        
        int[][] test = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board testBoard = new Board(test);
        System.out.println(testBoard.toString());
        
        int[][] test2 = {{1, 2, 3}, {4, 8, 6}, {7, 5, 0}};
        
        Board testBoard2 = new Board(test2);
        
        System.out.println(testBoard.equals(testBoard2));
    }
    
}
