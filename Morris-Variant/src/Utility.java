import java.util.ArrayList;

public class Utility {

    public static ArrayList < char[] > generateAdd(char[] board) {
        ArrayList < char[] > listGeneBoardPositions = new ArrayList < char[] > ();
        char[] b;
        for (int loc = 0; loc < board.length; loc++) {
            if (board[loc] == 'x') {
                b = board.clone();
                b[loc] = 'W';
                if (closeMill(loc, b)) {
                    generateRemove(b, listGeneBoardPositions);
                } else {
                    listGeneBoardPositions.add(b);
                }
            }
        }
        return listGeneBoardPositions;
    }

    public static ArrayList < char[] > generateHopping(char[] board) {
        ArrayList < char[] > listGeneBoardPositions = new ArrayList < char[] > ();
        char[] b;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 'W') {
                for (int j = 0; j < board.length; j++) {
                    if (board[j] == 'x') {
                        b = board.clone();
                        b[i] = 'x';
                        b[j] = 'W';
                        if (closeMill(j, b)) {
                            generateRemove(b, listGeneBoardPositions);
                        } else {
                            listGeneBoardPositions.add(b);
                        }
                    }
                }
            }
        }
        return listGeneBoardPositions;
    }

    public static ArrayList < char[] > generateMove(char[] board) {
        ArrayList < char[] > listGeneBoardPositions = new ArrayList < char[] > ();
        char[] b;
        int[] n;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 'W') {
                //get list of neighbors of location;
                n = neighbors(i);
                for (int j: n) {
                    if (board[j] == 'x') {
                        b = board.clone();
                        b[i] = 'x';
                        b[j] = 'W';
                        if (closeMill(j, b)) {
                            generateRemove(b, listGeneBoardPositions);
                        } else {
                            listGeneBoardPositions.add(b);
                        }
                    }
                }
            }
        }
        return listGeneBoardPositions;
    }


    private static ArrayList < char[] > generateRemove(char[] board, ArrayList < char[] > listGeneBoardPositions) {
        char[] b;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 'B') {
                if (!closeMill(i, board)) {
                    b = board.clone();
                    b[i] = 'x';
                    listGeneBoardPositions.add(b);
                }
            }
        }
        if (listGeneBoardPositions.isEmpty()) {
            listGeneBoardPositions.add(board);
        }
        return listGeneBoardPositions;
    }

	private static int[] neighbors(int i) {
		switch(i) {
		case 0:		//a0 : d0,b1,a3
			return new int[] { 1,3,8 };
			
		case 1:		//d0 : a0,d1,g0
			return new int[] { 0,2,4 };
			
		case 2:		//g0 : d0,f1,g3
			return new int[] { 1,5,13 };
			
		case 3:		//
			return new int[] { 0,4,6,9 };
			
		case 4:
			return new int[] { 1,3,5 };
			
		case 5:
			return new int[] { 2,4,7,12 };
			
		case 6:
			return new int[] { 3,7,10 };

		case 7:
			return new int[] { 5,6,11 };
			
		case 8:
			return new int[] { 0,9,20 };
			
		case 9:
			return new int[] { 3,8,10,17 };
			
		case 10:
			return new int[] { 6,9,14 };

		case 11:
			return new int[] { 7,12,16 };
			
		case 12:
			return new int[] { 5,11,13,19 };
			
		case 13:
			return new int[] { 2,12,22 };

		case 14:
			return new int[] { 10,15,17 };

		case 15:
			return new int[] { 14,16,18 };
			
		case 16:
			return new int[] { 11,15,19 };
			
		case 17:
			return new int[] { 9,14,18,20 };
			
		case 18:
			return new int[] { 15,17,19,21 };
			
		case 19:
			return new int[] { 12,16,18,22 };
			
		case 20:
			return new int[] { 8,17,21 };
			
		case 21:
			return new int[] { 18,20,22 };
			
		case 22:
			return new int[] { 13,19,21 };	
			
		default:
			return new int[] {};
		}
	}
	
    static boolean closeMill(int loc, char[] b) {
        char c = b[loc];
        if (c != 'x') {
            switch (loc) {
                case 0: //a0
                    if ((b[1] == c && b[2] == c) || (b[3] == c && b[6] == c) || (b[8] == c && b[20] == c))
                        return true;
                    else return false;
                case 1: //d0
                    if (b[0] == c && b[2] == c)
                        return true;
                    else return false;

                case 2: //g0
                    if ((b[0] == c && b[1] == c) || (b[5] == c && b[7] == c) || (b[13] == c && b[22] == c))
                        return true;
                    else return false;

                case 3: //b1
                    if ((b[0] == c && b[6] == c) || (b[4] == c && b[5] == c) || (b[9] == c && b[17] == c))
                        return true;
                    else return false;

                case 4: //d1
                    if (b[3] == c && b[5] == c)
                        return true;
                    else return false;

                case 5: //f1
                    if ((b[2] == c && b[7] == c) || (b[3] == c && b[4] == c) || (b[12] == c && b[19] == c))
                        return true;
                    else return false;

                case 6: //c2
                    if ((b[0] == c && b[3] == c) || (b[10] == c && b[14] == c))
                        return true;
                    else return false;

                case 7: //e2
                    if ((b[2] == c && b[5] == c) || (b[11] == c && b[16] == c))
                        return true;
                    else return false;

                case 8: //a3
                    if ((b[0] == c && b[20] == c) || (b[9] == c && b[10] == c))
                        return true;
                    else return false;

                case 9: //b3
                    if ((b[8] == c && b[10] == c) || (b[3] == c && b[17] == c))
                        return true;
                    else return false;

                case 10: //c3
                    if ((b[8] == c && b[9] == c) || (b[6] == c && b[14] == c))
                        return true;
                    else return false;

                case 11: //e3
                    if ((b[7] == c && b[16] == c) || (b[12] == c && b[13] == c))
                        return true;
                    else return false;

                case 12: //f3
                    if ((b[11] == c && b[13] == c) || (b[5] == c && b[19] == c))
                        return true;
                    else return false;

                case 13: //g3
                    if ((b[11] == c && b[12] == c) || (b[2] == c && b[22] == c))
                        return true;
                    else return false;

                case 14: //c4
                    if ((b[6] == c && b[10] == c) || (b[15] == c && b[16] == c) || (b[17] == c && b[20] == c))
                        return true;
                    else return false;

                case 15: //d4
                    if ((b[14] == c && b[16] == c) || (b[18] == c && b[21] == c))
                        return true;
                    else return false;

                case 16: //e4
                    if ((b[14] == c && b[15] == c) || (b[19] == c && b[22] == c) || (b[7] == c && b[11] == c))
                        return true;
                    else return false;

                case 17: //b5
                    if ((b[3] == c && b[9] == c) || (b[18] == c && b[19] == c) || (b[14] == c && b[20] == c))
                        return true;
                    else return false;

                case 18: //d5
                    if ((b[15] == c && b[21] == c) || (b[17] == c && b[19] == c))
                        return true;
                    else return false;

                case 19: //f5
                    if ((b[17] == c && b[18] == c) || (b[5] == c && b[12] == c) || (b[16] == c && b[22] == c))
                        return true;
                    else return false;

                case 20: //a6
                    if ((b[0] == c && b[8] == c) || (b[21] == c && b[22] == c) || (b[14] == c && b[17] == c))
                        return true;
                    else return false;

                case 21: //d6
                    if ((b[20] == c && b[22] == c) || (b[15] == c && b[18] == c))
                        return true;
                    else return false;

                case 22: //g6
                    if ((b[20] == c && b[21] == c) || (b[16] == c && b[19] == c) || (b[2] == c && b[13] == c))
                        return true;
                    else return false;
                default:
                    return false;
            }
        }
        return false;
    }

    public static char[] swapBoard(char[] x) {

        char[] board = x.clone();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 'W') {
                board[i] = 'B';
                continue;
            }
            if (board[i] == 'B') {
                board[i] = 'W';
            }
        }
        return board;
    }

}