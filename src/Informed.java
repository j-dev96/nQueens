import java.util.Random;
import java.util.Scanner;

//HillClimbing Random Restart
class Informed {

    //Initialize variables
    private static int N;
    private static int climbedStepsLastRestart = 0;
    private static int climbedSteps = 0;
    private static int h = 0;
    private static int restartCount = 0;
    private static int totalBoards = 0;
    private static long seed = 0;

    Informed() {
        int currH;
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.print("Size N?: ");
            N = s.nextInt();
            if (N == 2 || N == 3) {
                System.out.println("Not possible, please enter another number.");
            } else
                break;
        }
        //Create first board
        NQueens[] currBoard = createBoard();
        totalBoards++;
        //test if initial board is a solution board.
        currH = getHcost(currBoard);
        //Loop until we find a solution
        while (currH != 0) {
            //Keep generating a new board until h(heuristic) = 0
            //when found, loop breaks and printBoard(currBoard) is executed
            currBoard = getOptimalBoard(currBoard);
            currH = h;
        }
        //print solution and information
        printBoard(currBoard);
        System.out.println();
        System.out.println("Total Steps Climbed: " + climbedSteps);
        System.out.println("Total Random Restarts: " + restartCount);
        System.out.println("Steps Climbed after previous Restart: " + climbedStepsLastRestart);
        System.out.println("Total number of boards created: " + totalBoards);
        System.out.println("Seed: " + seed);
    }

    // finds the h cost of a given state
    private static int getHcost(NQueens[] state) {
        int h = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = i + 1; j < state.length; j++) {
                if (state[i].isSafe(state[j])) {
                    h++;
                }
            }
        }
        return h;
    }

    private NQueens[] createBoard() {
        NQueens[] initialBoard = new NQueens[N];
        Random r = new Random();
        seed = System.currentTimeMillis();
        r.setSeed(seed);
        for (int i = 0; i < N; i++) {
            initialBoard[i] = new NQueens(r.nextInt(N), i);
        }
        return initialBoard;
    }

    private static void printBoard(NQueens[] state) {
        int[][] tempBoard = new int[N][N];
        for (int i = 0; i < N; i++) {
            tempBoard[state[i].getRow()][state[i].getColumn()] = 1;
        }
        System.out.println();
        //print board
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tempBoard[i][j] == 1)
                    System.out.print(" Q ");
                else
                    System.out.print(" - ");
            }
            System.out.println();
        }
    }

    // generates the next board that has the lowest h
    private NQueens[] getOptimalBoard(NQueens[] presentBoard) {
        NQueens[] nextBoard = new NQueens[N];
        NQueens[] tempBoard = new NQueens[N];
        int presentH = getHcost(presentBoard);
        int bestH = presentH;
        int tempH;
        //present board is copied as best & temp.
        for (int i = 0; i < N; i++) {
            nextBoard[i] = new NQueens(presentBoard[i].getRow(), presentBoard[i].getColumn());
            tempBoard[i] = nextBoard[i];
            totalBoards++;
        }
        //Iterates through each column of the board
        int i = 0;
        while (i < N) {
            if (i > 0)
                tempBoard[i - 1] = new NQueens(presentBoard[i - 1].getRow(), presentBoard[i - 1].getColumn());
            tempBoard[i] = new NQueens(0, tempBoard[i].getColumn());
            //Iterates through each row of the board
            for (int j = N - 1; j >= 0; j--) {
                //Get the h
                tempH = getHcost(tempBoard);
                //Check if temp > best board
                if (tempH < bestH) {
                    bestH = tempH;
                    //if so, copy best board as temp board
                    for (int k = 0; k < N; k++) {
                        nextBoard[k] = new NQueens(tempBoard[k].getRow(), tempBoard[k].getColumn());
                    }
                }
                //Move Queen
                if (tempBoard[i].getRow() != N - 1)
                    tempBoard[i].move();
            }
            i++;
        }

        if (bestH != presentH) {
            h = bestH;
        } else {
            restartCount++;
            climbedStepsLastRestart = 0;
            nextBoard = createBoard();
            h = getHcost(nextBoard);
        }
        climbedSteps++;
        climbedStepsLastRestart++;
        return nextBoard;
    }

    static class NQueens {

        private int row;
        private final int column;

        NQueens(int row, int column) {
            this.row = row;
            this.column = column;
        }

        boolean isSafe(NQueens q) {
            //Columns and Rows
            if (column == q.getColumn() || row == q.getRow())
                return true;
                //Diagonals
            else return Math.abs(column - q.getColumn()) == Math.abs(row - q.getRow());
        }

        int getColumn() {
            return column;
        }

        int getRow() {
            return row;
        }

        void move() {
            row++;
        }
    }
}

