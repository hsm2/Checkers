import java.util.Scanner;

/**
 * Created by harishmanikantan on 3/13/17.
 */
public class Checkers {

    private Board board;

    private String PLAYER_RED_NAME = "Player Red";
    private String PLAYER_BLUE_NAME = "Player Blue";
    public static final int RED = 0;
    public static final int BLUE = 1;

    private Player redPlayer;
    private Player bluePlayer;

    private int currentPlayer = RED;

    private String positionToMove = board.INVALID_POSITION;

    public Checkers() {
        initializePlayers();

        board = new Board();
        board.redSide = PLAYER_RED_NAME;
        board.blueSide = PLAYER_BLUE_NAME;

        board.printBoard();
    }

    public void removeRow(int row) {
        for (int i = 0; i < board.DIMENSION; i++) {
            board.removePiece(row, i);
        }
    }

    public void play() {
        while (isGameOn()) {
            displayStatus();

            Move move = getNextMove(positionToMove);
            updateBoard(move);
            boolean pieceWon = pieceWonOnMove(move);

            updateCurrentPlayer(pieceWon);
        }

        String winPlayer = PLAYER_BLUE_NAME;
        if (board.getBluePiecesLeft() == 0) {
            winPlayer = PLAYER_RED_NAME;
        }

        board.printBoard();
        System.out.println(winPlayer + " has won!");
    }

    private boolean pieceWonOnMove(Move move) {
        if (move == null || Math.abs(move.getTargetY() - move.getSourceY()) != 2) {
            positionToMove = Board.INVALID_POSITION;
            return false;
        }
        positionToMove = move.getTargetX() + "," + move.getTargetY();
        return true;
    }

    private void updateCurrentPlayer(boolean pieceWon) {
        if (pieceWon) {
            return;
        }

        if (currentPlayer == RED) {
            currentPlayer = BLUE;
        }
        else {
            currentPlayer = RED;
        }
    }

    private void updateBoard(Move move) {
        if (move == null)
            return;

        board.movePiece(move);
        if (Math.abs(move.getTargetY() - move.getSourceY()) == 2) {
            board.removePiece((move.getSourceX() + move.getTargetX()) / 2, (move.getTargetY() + move.getSourceY()) / 2);
        }
        if (currentPlayer == RED && move.getTargetX() == board.DIMENSION - 1) {
            board.setPiece(board.RED_PIECE_KING, move.getTargetX(), move.getTargetY());
        }
        else if (currentPlayer == BLUE && move.getTargetX() == 0) {
            board.setPiece(board.BLUE_PIECE_KING, move.getTargetX(), move.getTargetY());
        }
    }

    private Move getNextMove(String pieceToMove) {
        Move move = null;

        do {
            if (currentPlayer == RED) {
                move = redPlayer.doMove(board, pieceToMove);
            }
            else {
                //System.out.println("Blue");
                move = bluePlayer.doMove(board, pieceToMove);
                //System.out.println(move.getSourceX() + "," + move.getSourceY());
                //System.out.println(move.getTargetX() + "," + move.getTargetY());
            }
        }
        while (pieceToMove.equals(Board.INVALID_POSITION) && !isValidMove(move));

        return move;
    }

    private boolean isValidMove(Move move) {

        if (move.getSourceX() >= board.DIMENSION || move.getSourceX() < 0 || move.getSourceY() >= board.DIMENSION || move.getSourceY() < 0)
            return false;

        int sourceX = move.getSourceX();
        int sourceY = move.getSourceY();
        int targetX = move.getTargetX();
        int targetY = move.getTargetY();

        String sourcePiece = board.getPosition(sourceX, sourceY);
        String targetPiece = board.getPosition(targetX, targetY);

        if ((sourcePiece.equals(board.RED_PIECE_NORMAL) || sourcePiece.equals(board.RED_PIECE_KING)) && currentPlayer == RED) {
            if (sourcePiece.equals(board.RED_PIECE_NORMAL)) {
                if (targetX - sourceX == 1 && Math.abs(targetY - sourceY) == 1) {
                    if (targetPiece.equals(board.FREE_POSITION)) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (targetX - sourceX == 2 && Math.abs(targetY - sourceY) == 2) {
                    if (board.getPosition((targetX + sourceX) / 2, (targetY + sourceY) / 2).equals(board.BLUE_PIECE_NORMAL)
                            || board.getPosition((targetX + sourceX) / 2, (targetY + sourceY) / 2).equals(board.BLUE_PIECE_KING)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            else {
                if (Math.abs(targetX - sourceX) == 1 && Math.abs(targetY - sourceY) == 1) {
                    if (targetPiece.equals(board.FREE_POSITION)) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (Math.abs(targetX - sourceX) == 2 && Math.abs(targetY - sourceY) == 2) {
                    if (board.getPosition((targetX + sourceX) / 2, (targetY + sourceY) / 2).equals(board.BLUE_PIECE_NORMAL)
                            || board.getPosition((targetX + sourceX) / 2, (targetY + sourceY) / 2).equals(board.BLUE_PIECE_KING)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        if ((sourcePiece.equals(board.BLUE_PIECE_NORMAL) || sourcePiece.equals(board.BLUE_PIECE_KING)) && currentPlayer == BLUE) {
            if (sourcePiece.equals(board.BLUE_PIECE_NORMAL)) {
                if (targetX - sourceX == -1 && Math.abs(targetY - sourceY) == 1) {
                    if (targetPiece.equals(board.FREE_POSITION)) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (targetX - sourceX == -2 && Math.abs(targetY - sourceY) == 2) {
                    if (board.getPosition((targetX + sourceX) / 2, (targetY + sourceY) / 2).equals(board.RED_PIECE_NORMAL)
                            || board.getPosition((targetX + sourceX) / 2, (targetY + sourceY) / 2).equals(board.RED_PIECE_KING)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            else {
                if (Math.abs(targetX - sourceX) == 1 && Math.abs(targetY - sourceY) == 1) {
                    if (targetPiece.equals(board.FREE_POSITION)) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (Math.abs(targetX - sourceX) == 2 && Math.abs(targetY - sourceY) == 2) {
                    if (board.getPosition((targetX + sourceX) / 2, (targetY + sourceY) / 2).equals(board.RED_PIECE_NORMAL)
                            || board.getPosition((targetX + sourceX) / 2, (targetY + sourceY) / 2).equals(board.RED_PIECE_KING)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        else {
            return false;
        }

    }

    private void displayStatus() {
        board.printBoard();
        System.out.println(PLAYER_RED_NAME + " has won " + (Board.MAXIMUM_PIECES_OF_PLAYER - board.getBluePiecesLeft()) + "piece(s)");
        System.out.println(PLAYER_BLUE_NAME + " has won " + (Board.MAXIMUM_PIECES_OF_PLAYER - board.getRedPiecesLeft()) + "piece(s)");

        if (currentPlayer == RED) {
            System.out.println(PLAYER_RED_NAME + "'s turn to play");
        }
        else {
            System.out.println(PLAYER_BLUE_NAME + "'s turn to play");
        }
    }

    private boolean isGameOn() {
        if (board.getRedPiecesLeft() == 0 || board.getBluePiecesLeft() == 0) {
            return false;
        }
        return true;
    }

    private void initializePlayers() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Checkers");

        System.out.println("Enter name for Player Red");
        PLAYER_RED_NAME = scanner.next();
        redPlayer = new UserPlayer(RED);

        System.out.println("Enter name for Player Blue");
        PLAYER_BLUE_NAME = scanner.next();
        bluePlayer = new UserPlayer(BLUE);
    }
}
