package chess;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    ArrayList<ArrayList<ChessPiece>> board = new ArrayList<>();
    public ChessBoard() {
        for (int i = 0; i < 8; i++) {
            ArrayList<ChessPiece> buf = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                buf.add(null);
            }
            this.board.add(buf);
        }
        //iirc dont run reset here
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.equals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(board);
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "board=" + board +
                '}';
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int rowidx = position.getRow()-1;
        int colidx = position.getColumn()-1;
        board.get(rowidx).set(colidx, piece);
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int rowidx = position.getRow()-1;
        int colidx = position.getColumn()-1;
        return board.get(rowidx).get(colidx);
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        String startingBoard = """
                RNBQKBNR
                PPPPPPPP
                ........
                ........
                ........
                ........
                pppppppp
                rnbqkbnr
                """;
        int row = 1;
        int col = 1;
        for(char c: startingBoard.toCharArray()) {
            ChessPiece piece = null;
            switch(c) {
                case '\n':
                    col = 1;
                    row++;
                    continue;
                case 'R':
                    piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
                    break;
                case 'N':
                    piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
                    break;
                case 'B':
                    piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
                    break;
                case 'K':
                    piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
                    break;
                case 'Q':
                    piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
                    break;
                case 'P':
                    piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
                    break;
                case 'r':
                    piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
                    break;
                case 'n':
                    piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
                    break;
                case 'b':
                    piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
                    break;
                case 'k':
                    piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
                    break;
                case 'q':
                    piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
                    break;
                case 'p':
                    piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
                    break;
                default:
                    break;
            }
            this.addPiece(new ChessPosition(row, col), piece);
            col++;
        }
        //throw new RuntimeException("Not implemented");
    }
}
