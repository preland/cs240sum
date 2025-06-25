package chess;

import java.util.ArrayList;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    ArrayList<ArrayList<ChessPosition>> board;
    public ChessBoard() {
        resetBoard();
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        if(board != null) {
            board.clear();
        }
        String defaultBoard = """
                rnbqkbnr
                pppppppp
                ........
                ........
                ........
                ........
                PPPPPPPP
                RNBQKBNR
                """;
        int i = 0;
        int j = 0;
        for(char c: defaultBoard.toCharArray()) {
            ChessPosition position = new ChessPosition(i, j);
            switch (c) {
                case '\n':
                    i++;
                    break;
                case 'r':
                    addPiece(position, new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
                    j++;
                    break;
                case 'n':
                    addPiece(position, new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
                    j++;
                    break;
                case 'b':
                    addPiece(position, new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
                    j++;
                    break;
                case 'q':
                    addPiece(position, new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN));
                    j++;
                    break;
                case 'k':
                    addPiece(position, new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));
                    j++;
                    break;
                case 'p':
                    addPiece(position, new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
                    j++;
                    break;
                case 'R':
                    addPiece(position, new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
                    j++;
                    break;
                case 'N':
                    addPiece(position, new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
                    j++;
                    break;
                case 'B':
                    addPiece(position, new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
                    j++;
                    break;
                case 'Q':
                    addPiece(position, new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN));
                    j++;
                    break;
                case 'K':
                    addPiece(position, new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));
                    j++;
                    break;
                case 'P':
                    addPiece(position, new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
                    j++;
                    break;
                case '.':
                    addPiece(position, new ChessPiece(null, null));//because this is what is in spec.
                    j++;
                    break;

            }
        }
        //throw new RuntimeException("Not implemented");
    }
}
