import java.util.Stack;

public class Board {

    private final int[][] board;
    private int hammingCount;
    private int manhattanCount;
    private final int[][] goalBoard;
    private int emptyI;
    private int emptyJ;
    
    // @param 2 <= n <= 128
    public Board(int[][] tiles) {
        
        board = tiles;
        hammingCount = 0;
        manhattanCount = 0;
        
        goalBoard = new int[board.length][board[0].length];
        int tempCounter = 1;
        
        for (int i = 0; i < goalBoard.length; i++) {
            for (int j = 0; j < goalBoard[i].length; j++) {
                
                
                goalBoard[i][j] = tempCounter++;
                if (tempCounter == goalBoard.length * goalBoard.length) {
                    break;
                }
            }
            
        }
        
        calculateDistances();
        
    }
    
    public String toString() {
        
        String boardString = "" + this.dimension() + "\n ";
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                boardString += board[i][j] + "  ";
            }
            boardString += "\n ";
        }
        
        return boardString;
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
        
        int tempCounter = 1;
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                
                // for the empty position
                if (board[i][j] == 0) {
                    emptyI = i;
                    emptyJ = j;
                    continue;
                }
                
                if (board[i][j] != tempCounter++) {
                    hammingCount++;
                    manhattanCount += calculateManhattan(i, j, board[i][j]);
                }
                // this prevents to check the supposed empty position
                if (tempCounter == board.length * board.length) return; 
            }
        }
    }
    
    private int calculateManhattan(int currentI, int currentJ, int value) {
        
        int correctI = 0;
        int correctJ = 0;

        if (value % board.length == 0) {
            correctI = value / board.length;
            correctJ = board.length;
        } else {
            correctI = (value / board.length) + 1;
            correctJ = (value % board.length) - 1;
            
        }
        
        return Math.abs(currentI - correctI) + Math.abs(currentJ - correctJ);
    }
    
    public boolean isGoal() {
        
        return manhattanCount == 0;
    }
    
    public boolean equals(Object y) {
        
        Board temp = (Board) y;
        
        String tempString1 = this.toString();
        String tempString2 = temp.toString();
        
        return tempString1.equals(tempString2);        
    }
    
    public Iterable<Board> neighbors() {
        
        Stack<Board> stackBoard = new Stack<Board>();
        
        // this will set true for corners that is out of bounds/edges
        boolean addedTop = emptyI == 0;
        boolean addedBottom = emptyI == board.length;
        boolean addedLeft = emptyJ == 0;
        boolean addedRight = emptyJ == board.length;
        
        
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
        int[][] tempBoard = board;
        
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
        
        return null;
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
