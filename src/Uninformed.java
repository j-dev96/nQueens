import java.util.Scanner;

//Depth-first search
class Uninformed {

    //Initialize variables
    private static int[] board;
    private int count;
    private int boardCount = 0;
    private int N;

    Uninformed() {
        int counter = 0;
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.print("Size N?: ");
            N = s.nextInt();
            if (N == 2 || N == 3) {
                System.out.println("Not possible, please enter another number.");
            } else
                break;
        }
        new Uninformed(N);
        Uninformed board = new Uninformed(N);
        Uninformed nQueens = DFS(board);
        int[] solution = new int[N]; //solution set (Ex, 4-Queens: {1, 3, 0, 2})
        int[][] b = new int[N][N];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                b[i][j] = counter;
                counter++;
                //create an incrementing 2d array of the board
                //Ex, 4-Queens: 0 1 2 3
                //              4 5 6 7
                //              8 9 10 11
                //              12 13 14 15

            }
        }
        //iterate through each column until the ith number of the solution set is found ((num*N)+i) and place a Queen there.
        if (nQueens != null) {
            if (nQueens.N >= 0) System.arraycopy(Uninformed.board, 0, solution, 0, nQueens.N);
            for (int i = 0; i < nQueens.N; i++) {
                int num = solution[i];// Ex, 4-Queens: i = 0, thus num = 1. (set is 1,3,0,2)
                num = (num * N) + i;//1*4 + 0 = 4, check where 4 is in first column and place Q there.
                System.out.println();
                for (int j = 0; j < nQueens.N; j++) {
                    if (num == b[j][i])
                        System.out.print(" Q ");
                    else
                        System.out.print(" - ");
                }
            }
        }
        System.out.println();
        System.out.println("\nTotal # of boards created: " + boardCount);
    }

    private Uninformed(int n) {
        N = n;
        board = new int[N];
        for (int i = 0; i < n; i++)
            board[i] = 0;
        count = 0;
    }

    private boolean isSafe(int c) {
        for (int i = 0; i < count; i++) {
            if ((board[i] == c) || Math.abs(c - board[i]) == (count - i))
                return false;
        }
        return true;
    }

    private void Place(int c){
        if (c >= 0 && c < N) {
            board[count] = c;
            count++;
        }
    }

    private boolean isGoal() {
        return count == N;
    }

    private void UnPlace() {
        if (count > 0) count--;
    }

    private Uninformed DFS(Uninformed board) {
        if (board.isGoal()) return board;
        else {
            for (int i = 0; i < board.N; i++) {
                if (board.isSafe(i)) {
                    boardCount++;
                    board.Place(i);
                    Uninformed nQueens = DFS(board);
                    if (nQueens != null) return nQueens;
                    board.UnPlace();
                }
            }
        }
        return null;
    }
}
