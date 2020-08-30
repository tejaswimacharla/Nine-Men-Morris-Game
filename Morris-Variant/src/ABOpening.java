import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ABOpening {

    int positionsEvaluated = 0;
    int ABEstimate = 0;


    public char[] MaxMin(char[] c, int depth, int alpha, int beta) {
        if (depth > 0) {
            depth--;
            ArrayList < char[] > children = new ArrayList < char[] > ();
            char[] minBoard;
            char[] maxBoardchoice = new char[50];
            children = generateMovesOpening(c);
            int v = Integer.MIN_VALUE;
            for (char[] y: children) {
                minBoard = MinMax(y, depth, alpha, beta);
                if (v < staticEstimationOpening(minBoard)) {
                    v = staticEstimationOpening(minBoard);
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
        } else if (depth == 0) {
            positionsEvaluated++;
        }
        return c;
    }

    public char[] MinMax(char[] c, int depth, int alpha, int beta) {
        if (depth > 0) {
            depth--;
            ArrayList < char[] > bchildren = new ArrayList < char[] > ();
            char[] maxBoard;
            char[] minBoardchoice = new char[50];
            bchildren = generateMovesOpeningBlack(c);
            int v = Integer.MAX_VALUE;
            for (char[] y: bchildren) {
                maxBoard = MaxMin(y, depth, alpha, beta);
                if (v > staticEstimationOpening(maxBoard)) {
                    v = staticEstimationOpening(maxBoard);
                    minBoardchoice = y;
                }
                if (v <= alpha) {
                    return minBoardchoice;
                } else {
                    beta = Math.min(v, beta);
                }
            }
            return minBoardchoice;
        } else if (depth == 0) {
            positionsEvaluated++;
        }
        return c;
    }

    private ArrayList < char[] > generateMovesOpening(char[] board) {
        return Utility.generateAdd(board);
    }

    public ArrayList < char[] > generateMovesOpeningBlack(char[] board) {

        char[] swpboard = board.clone();
        for (int i = 0; i < swpboard.length; i++) {
            if (swpboard[i] == 'W') {
                swpboard[i] = 'B';
                continue;
            }
            if (swpboard[i] == 'B') {
                swpboard[i] = 'W';
            }
        }

        ArrayList < char[] > genBlkMoves = new ArrayList < char[] > ();
        ArrayList < char[] > genBlkMovesSwap = new ArrayList < char[] > ();

        genBlkMoves = generateMovesOpening(swpboard);
        for (char[] y: genBlkMoves) {
            char[] reswpboard = y;
            for (int i = 0; i < reswpboard.length; i++) {
                if (reswpboard[i] == 'W') {
                    reswpboard[i] = 'B';
                    continue;
                }
                if (reswpboard[i] == 'B') {
                    reswpboard[i] = 'W';
                }
            }
            genBlkMovesSwap.add(y);
        }
        return genBlkMovesSwap;
    }

    //Static estimation
    public int staticEstimationOpening(char[] board) {
        int numWhitePieces = 0;
        int numBlackPieces = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 'W') {
                numWhitePieces++;
            } else if (board[i] == 'B') {
                numBlackPieces++;
            }
        }
        return numWhitePieces - numBlackPieces;
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
            ABOpening ab = new ABOpening();
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