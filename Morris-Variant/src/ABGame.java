import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ABGame {

    int positionsEvaluated = 0;
    int ABEstimate = 0;

    public char[] MaxMin(char[] c, int depth, int alpha, int beta) {
        if (depth == 0) {
            positionsEvaluated++;
        } else if (depth > 0) {
            depth--;
            ArrayList < char[] > children = new ArrayList < char[] > ();
            char[] minBoard;
            char[] maxBoardchoice = new char[50];
            children = generateMovesMidgameEndgame(c);
            int v = Integer.MIN_VALUE;

            for (char[] y: children) {
                minBoard = MinMax(y, depth, alpha, beta);
                if (v < staticEstimationMidgameEndgame(minBoard)) {
                    v = staticEstimationMidgameEndgame(minBoard);
                    ABEstimate = v;
                    maxBoardchoice = y;
                }
                if (v >= beta) {
                    return maxBoardchoice;
                } else {
                    alpha = Math.max(v, alpha);
                }
            }
            return maxBoardchoice;
        }
        return c;
    }

    public char[] MinMax(char[] c, int depth, int alpha, int beta) {
        if (depth == 0) {
            positionsEvaluated++;
        } else if (depth > 0) {
            depth--;
            ArrayList < char[] > bchildren = new ArrayList < char[] > ();
            char[] maxBoard;
            char[] minBoardchoice = new char[50];
            bchildren = generateMovesMidgameEndgameBlack(c);
            int v = Integer.MAX_VALUE;
            for (char[] y: bchildren) {
                maxBoard = MaxMin(y, depth, alpha, beta);
                if (v > staticEstimationMidgameEndgame(maxBoard)) {
                    v = staticEstimationMidgameEndgame(maxBoard);
                    minBoardchoice = y;
                }
                if (v <= alpha) {
                    return minBoardchoice;
                } else {
                    beta = Math.min(v, beta);
                }
            }
            return minBoardchoice;
        }
        return c;
    }

    private ArrayList < char[] > generateMovesMidgameEndgame(char[] board) {
        ArrayList < char[] > listGeneBoardPositions = new ArrayList < char[] > ();
        int numWhitePieces = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 'W') {
                numWhitePieces++;
            }
        }
        if (numWhitePieces == 3) {
            listGeneBoardPositions = Utility.generateHopping(board);
        } else {
            listGeneBoardPositions = Utility.generateMove(board);
        }
        return listGeneBoardPositions;
    }

    public ArrayList < char[] > generateMovesMidgameEndgameBlack(char[] x) {

        char[] lboard = x.clone();
        for (int i = 0; i < lboard.length; i++) {
            if (lboard[i] == 'W') {
                lboard[i] = 'B';
                continue;
            }
            if (lboard[i] == 'B') {
                lboard[i] = 'W';
            }
        }

        ArrayList < char[] > genBlkMoves = new ArrayList < char[] > ();
        ArrayList < char[] > genBlkMovesSwap = new ArrayList < char[] > ();

        genBlkMoves = generateMovesMidgameEndgame(lboard);
        for (char[] y: genBlkMoves) {
            char[] lsboard = y;
            for (int i = 0; i < lsboard.length; i++) {
                if (lsboard[i] == 'W') {
                    lsboard[i] = 'B';
                    continue;
                }
                if (lsboard[i] == 'B') {
                    lsboard[i] = 'W';
                }
            }
            genBlkMovesSwap.add(y);
        }
        return genBlkMovesSwap;
    }

    //Static estimation
    public int staticEstimationMidgameEndgame(char[] board) {
        int numWhitePieces = 0;
        int numBlackPieces = 0;
        //MidgameEndgame positions generated from b by a black move
        ArrayList < char[] > numBlackMovesList = generateMovesMidgameEndgameBlack(board);
        int blackMoveCount = numBlackMovesList.size();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 'W') {
                numWhitePieces++;
            } else if (board[i] == 'B') {
                numBlackPieces++;
            }
        }
        if (numBlackPieces <= 2) {
            return 10000;
        } else if (numWhitePieces <= 2) {
            return -10000;
        } else if (blackMoveCount == 0) {
            return 10000;
        } else {
            return ((1000 * (numWhitePieces - numBlackPieces)) - blackMoveCount);
        }
    }

    public static void main(String[] args) throws IOException {
//    	Scanner myObj = new Scanner(System.in);  // Create a Scanner object
//        System.out.println("Enter input: ");
//        String[] input = myObj.nextLine().split(" ");  // Read user input
//        myObj.close();
        
        File board1 = new File(args[0]);
        File board2 = new File(args[1]);
        int depth = Integer.valueOf(args[2]);
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        try {
            FileInputStream inputboard = new FileInputStream(board1);
            PrintWriter outputboard = new PrintWriter(new FileWriter(board2));
            Scanner sc = new Scanner(inputboard);
            char[] inpboard = sc.next().toCharArray();
            ABGame ab = new ABGame();
            char[] outboard = ab.MaxMin(inpboard, depth, alpha, beta);
            System.out.println("Board Position: " + new String(outboard));
            System.out.println("Positions evaluated by static estimation: " + ab.positionsEvaluated);
            System.out.println("AB estimate: " + ab.ABEstimate);
            outputboard.println("Board Position : " + new String(outboard));
            outputboard.println("Positions evaluated by static estimation : " + ab.positionsEvaluated);
            outputboard.println("AB estimate : " + ab.ABEstimate);
            sc.close();
            outputboard.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}