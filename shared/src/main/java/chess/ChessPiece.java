package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    ChessGame.TeamColor pieceColor;
    PieceType type;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    public ChessPiece copy() {
        return new ChessPiece(this.pieceColor, this.type);
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
        return this.pieceColor;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
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
        Collection<ChessMove> ret = new ArrayList<>();
        int rowidx = myPosition.getRow()-1;
        int colidx = myPosition.getColumn()-1;
        ChessPiece piece = board.getPiece(myPosition);
        switch(piece.getPieceType()) {
            case KING:
                checkStraights(board, ret, myPosition, 1);
                checkDiagonals(board, ret, myPosition, 1);
                break;
            case QUEEN:
                checkStraights(board, ret, myPosition, 8);
                checkDiagonals(board, ret, myPosition, 8);
                break;
            case ROOK:
                checkStraights(board, ret, myPosition, 8);
                break;
            case BISHOP:
                checkDiagonals(board, ret, myPosition, 8);
                break;
            case PAWN:
                handlePawn(board, ret, myPosition);
                break;
            case KNIGHT:
                safeAddPos(board, ret, myPosition, 1, 2, false);
                safeAddPos(board, ret, myPosition, 2, 1, false);
                safeAddPos(board, ret, myPosition, -1, -2, false);
                safeAddPos(board, ret, myPosition, -2, -1, false);
                safeAddPos(board, ret, myPosition, -1, 2, false);
                safeAddPos(board, ret, myPosition, -2, 1,false);
                safeAddPos(board, ret, myPosition, 1, -2, false);
                safeAddPos(board, ret, myPosition, 2, -1, false);

                break;

        }

        return ret;
        //throw new RuntimeException("Not implemented");
    }

    private void handlePawn(ChessBoard board, Collection<ChessMove> moves, ChessPosition myPosition) {
        int rowidx = myPosition.getRow()-1;
        int colidx = myPosition.getColumn()-1;
        ChessGame.TeamColor team = board.getPiece(myPosition).getTeamColor();
        boolean blocked = false;
        if(team == ChessGame.TeamColor.WHITE) {
            blocked = !safeAddPos(board, moves, myPosition, 1, 0, false);
            safeAddPos(board, moves, myPosition, 1, 1, true);
            safeAddPos(board, moves, myPosition, 1, -1, true);
            if(myPosition.getRow()==2 && !blocked) {
                safeAddPos(board, moves, myPosition, 2, 0, false);
            }
        } else {
            blocked = !safeAddPos(board, moves, myPosition, -1, 0, false);
            safeAddPos(board, moves, myPosition, -1, 1, true);
            safeAddPos(board, moves, myPosition, -1, -1, true);
            if(myPosition.getRow()==7 && !blocked) {
                safeAddPos(board, moves, myPosition, -2, 0, false);
            }
        }
    }

    private void checkDiagonals(ChessBoard board, Collection<ChessMove> moves, ChessPosition myPosition, int range) {
        boolean northeast = true;
        boolean southwest = true;
        boolean southeast = true;
        boolean northwest = true;
        for (int i = 0; i < range; i++) {
            if(northeast) {
                northeast = safeAddPos(board, moves, myPosition, i+1, i+1, false);
            }
            if(southwest) {
                southwest = safeAddPos(board, moves, myPosition, (-i)-1, (-i)-1, false);
            }
            if(southeast) {
                southeast = safeAddPos(board, moves, myPosition, (-i)-1, i+1, false);
            }
            if(northwest) {
                northwest = safeAddPos(board, moves, myPosition, i+1, (-i)-1, false);
            }
        }
    }

    private void checkStraights(ChessBoard board, Collection<ChessMove> moves, ChessPosition myPosition, int range) {
        boolean north = true;
        boolean south = true;
        boolean east = true;
        boolean west = true;
        for (int i = 0; i < range; i++) {
            if(north) {
                north = safeAddPos(board, moves, myPosition, 0, i+1, false);
            }
            if(south) {
                south = safeAddPos(board, moves, myPosition, 0, (-i)-1, false);
            }
            if(east) {
                east = safeAddPos(board, moves, myPosition, i+1, 0, false);
            }
            if(west) {
                west = safeAddPos(board, moves, myPosition, (-i)-1, 0, false);
            }
        }
    }

    public boolean safeAddPos(ChessBoard board, Collection<ChessMove> moves,
                              ChessPosition myPosition, int rowOffset, int colOffset, boolean mustCapture) {
        ChessPosition targetPosition = new ChessPosition(myPosition.getRow()+rowOffset, myPosition.getColumn()+colOffset);
        int rowidx = targetPosition.getRow()-1;
        int colidx = targetPosition.getColumn()-1;
        boolean isNotBlocked = true;
        if(rowidx < 0 | colidx < 0 | rowidx > 7 | colidx > 7) {
            return false;
        }
        ChessPiece targetPiece = board.getPiece(targetPosition);
        ChessPiece startPiece = board.getPiece(myPosition);
        if(targetPiece != null) {
            if(targetPiece.getTeamColor() == startPiece.getTeamColor()) {
                return false;
            } else {
                //isNotBlocked = false;
                if(mustCapture | startPiece.getPieceType() != PieceType.PAWN) {
                    if((startPiece.getPieceType() == PieceType.PAWN &&
                            (startPiece.pieceColor == ChessGame.TeamColor.WHITE && targetPosition.getRow() == 8) |
                            (startPiece.pieceColor == ChessGame.TeamColor.BLACK && targetPosition.getRow() == 1))) {
                        moves.add(new ChessMove(myPosition, targetPosition, PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, targetPosition, PieceType.ROOK));
                        moves.add(new ChessMove(myPosition, targetPosition, PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, targetPosition, PieceType.KNIGHT));
                    } else {
                        moves.add(new ChessMove(myPosition, targetPosition, null));
                    }
                }
                return false;
            }
        }
        if( !mustCapture && (startPiece.getPieceType() == PieceType.PAWN) &&
                ((startPiece.pieceColor == ChessGame.TeamColor.WHITE && targetPosition.getRow() == 8) |
                        (startPiece.pieceColor == ChessGame.TeamColor.BLACK && targetPosition.getRow() == 1))) {
            moves.add(new ChessMove(myPosition, targetPosition, PieceType.QUEEN));
            moves.add(new ChessMove(myPosition, targetPosition, PieceType.ROOK));
            moves.add(new ChessMove(myPosition, targetPosition, PieceType.BISHOP));
            moves.add(new ChessMove(myPosition, targetPosition, PieceType.KNIGHT));
        } else if(mustCapture){
            return false;
        }
        else {
            moves.add(new ChessMove(myPosition, targetPosition, null));
        }
        return true;
    }
}
