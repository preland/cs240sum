package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static chess.ChessGame.TeamColor.WHITE;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    ChessGame.TeamColor pieceColor;
    PieceType type;
    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;

        //throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        //get piece type and piece color at the position
        ChessPiece piece = board.getPiece(myPosition);
        PieceType type = piece.getPieceType();
        ChessGame.TeamColor team = piece.getTeamColor();

        //depending on piece type, check tiles in a specific pattern
        ArrayList<ChessPosition> positions = new ArrayList<>();
        switch(type) {
            case KING -> {
                addStraights(myPosition, positions, 1);
                addDiagonals(myPosition, positions, 1);
            }
            case QUEEN -> {
                addStraights(myPosition, positions, 8);
                addDiagonals(myPosition, positions, 8);
            }
            case BISHOP -> {
                addDiagonals(myPosition, positions, 8);
            }
            case KNIGHT -> {
                safeAddPos(myPosition, positions, 1, 2);
                safeAddPos(myPosition, positions, 2, 1);
                safeAddPos(myPosition, positions, 1, -2);
                safeAddPos(myPosition, positions, 2, -1);
                safeAddPos(myPosition, positions, -1, -2);
                safeAddPos(myPosition, positions, -2, -1);
                safeAddPos(myPosition, positions, -1, 2);
                safeAddPos(myPosition, positions, -2, 1);
            }
            case ROOK -> {
                addStraights(myPosition, positions, 8);
            }
            case PAWN -> {
                if(team==WHITE) {
                    addForwards(myPosition, positions);
                } else {
                    addBackwards(myPosition, positions);
                }
            }
        }
        Collection<ChessMove> moves = new ArrayList<>(List.of());

        for(ChessPosition p : positions) {
            moves.add(new ChessMove(myPosition, p, null));
        }
        //check if there is a piece "between" the checked position
        //check if the position has a piece on it that is the same color as the position
        //throw new RuntimeException("Not implemented");
        return moves;
    }

    private void addBackwards(ChessPosition myPosition, ArrayList<ChessPosition> positions) {
            safeAddPos(myPosition, positions, 1,0);
            safeAddPos(myPosition, positions, 1,1);
            safeAddPos(myPosition, positions, 1,-1);
            safeAddPos(myPosition, positions, 2,0);
    }

    private void addForwards(ChessPosition myPosition, ArrayList<ChessPosition> positions) {
        safeAddPos(myPosition, positions, -1,0);
        safeAddPos(myPosition, positions, -1,1);
        safeAddPos(myPosition, positions, -1,-1);
        safeAddPos(myPosition, positions, -2,0);
    }

    private void safeAddPos(ChessPosition myPosition, ArrayList<ChessPosition> positions, int rowadd, int coladd) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        if(!(row+rowadd < 0 | row+rowadd > 7 | col+coladd < 0 | col+coladd > 7)){
            positions.add(new ChessPosition(row+rowadd, col+coladd));
        }
    }

    private void addDiagonals(ChessPosition myPosition, ArrayList<ChessPosition> positions, int i) {
        for (int j = 0; j < i; j++) {
            safeAddPos(myPosition, positions, j+1, j+1);
            safeAddPos(myPosition, positions, j-1, j-1);
            safeAddPos(myPosition, positions, j-1, j+1);
            safeAddPos(myPosition, positions, j+1, j-1);
        }
    }

    private void addStraights(ChessPosition myPosition, ArrayList<ChessPosition> positions, int i) {
        for (int j = 0; j < i; j++) {
            safeAddPos(myPosition, positions, j+1, 0);
            safeAddPos(myPosition, positions, j-1, 0);
            safeAddPos(myPosition, positions, 0, j+1);
            safeAddPos(myPosition, positions, 0, j-1);
        }
    }

}
