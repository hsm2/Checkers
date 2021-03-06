/**
 * Created by harishmanikantan on 3/13/17.
 */
public class Board {

    public static final int DIMENSION = 8;
    public static final int MAXIMUM_PIECES_OF_PLAYER = 12;
    private String[][] boardElements = new String[DIMENSION][DIMENSION];

    public static final String RED_PIECE_NORMAL = "\033[31m" + "O";
    public static final String BLUE_PIECE_NORMAL = "\033[34m" + "O";
    public static final String RED_PIECE_KING = "\033[31m" + "K";
    public static final String BLUE_PIECE_KING = "\033[34m" + "K";
    public static final String FREE_POSITION = " ";
    public static final String INVALID_POSITION = "9,9";

    public static String redSide = "";
    public static String blueSide = "";

    private int bluePiecesLeft = MAXIMUM_PIECES_OF_PLAYER;
    private int redPiecesLeft = MAXIMUM_PIECES_OF_PLAYER;

    public Board() {
        createBoard();
    }

    public Board(Board copyBoard) {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                boardElements[i][j] = copyBoard.boardElements[i][j];
            }
        }

        bluePiecesLeft = copyBoard.bluePiecesLeft;
        redPiecesLeft = copyBoard.redPiecesLeft;
    }

    private void createBoard() {

        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if ((i + j) % 2 == 0 && i < (DIMENSION / 2) - 1) {
                    boardElements[i][j] = RED_PIECE_NORMAL;
                }
                else if ((i + j) % 2 == 0 && i > (DIMENSION / 2)) {
                    boardElements[i][j] = BLUE_PIECE_NORMAL;
                }
                else {
                    boardElements[i][j] = FREE_POSITION;
                }
            }
        }
    }

    public String getPosition(int x, int y) {
        if (x < 0 || x >= DIMENSION || y < 0 || y >= DIMENSION)
            return INVALID_POSITION;

        return boardElements[x][y];
    }

    public void removePiece(int x, int y) {

        if (boardElements[x][y].equals(RED_PIECE_NORMAL) || boardElements[x][y].equals(RED_PIECE_KING)) {
            redPiecesLeft -= 1;
        }
        else {
            bluePiecesLeft -= 1;
        }

        boardElements[x][y] = FREE_POSITION;
    }

    public void setPiece(String piece, int x, int y) {
        boardElements[x][y] = piece;
    }

    public void movePiece(Move move) {

        int currentX = move.getSourceX();
        int currentY = move.getSourceY();
        int targetX = move.getTargetX();
        int targetY = move.getTargetY();

        boardElements[targetX][targetY] = boardElements[currentX][currentY];
        boardElements[currentX][currentY] = FREE_POSITION;
    }

    public void printBoard() {

        System.out.println("  " + redSide + "'s Side");

        System.out.print("  ");

        for (int i = 0; i < DIMENSION; i++) {
            System.out.print(i + " ");
        }

        System.out.println();

        for (int i = 0; i < DIMENSION; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < DIMENSION; j++) {
                System.out.print("\033[4m"+ "\033[1m" +boardElements[i][j] +"\033[0m" + "|");
            }
            System.out.println(i);
        }

        System.out.print("  ");

        for (int i = 0; i < DIMENSION; i++) {
            System.out.print(i + " ");
        }

        System.out.println("\n  " + blueSide + "'s Side");
    }

    public int getBluePiecesLeft() {
        return bluePiecesLeft;
    }

    public int getRedPiecesLeft() {
        return redPiecesLeft;
    }
}
