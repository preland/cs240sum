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
                addStraights(board, myPosition, positions, 1);
                addDiagonals(board, myPosition, positions, 1);
            }
            case QUEEN -> {
                addStraights(board, myPosition, positions, 8);
                addDiagonals(board, myPosition, positions, 8);
            }
            case BISHOP -> {
                addDiagonals(board, myPosition, positions, 8);
            }
            case KNIGHT -> {
                handleKnight(board, myPosition, positions);

            }
            case ROOK -> {
                addStraights(board, myPosition, positions, 8);
            }
            case PAWN -> {
                if(team==WHITE) {
                    addForwards(board, myPosition, positions);
                } else {
                    addBackwards(board, myPosition, positions);
                }
            }
        }
        Collection<ChessMove> moves = new ArrayList<>(List.of());

        for(ChessPosition p : positions) {
            if(type == PieceType.PAWN && (p.getRow() == 8 | p.getRow() == 1)) {
                moves.add(new ChessMove(myPosition, p, PieceType.QUEEN));
                moves.add(new ChessMove(myPosition, p, PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, p, PieceType.KNIGHT));
                moves.add(new ChessMove(myPosition, p, PieceType.ROOK));
            } else {
                moves.add(new ChessMove(myPosition, p, null));
            }
        }
        //check if there is a piece "between" the checked position
        //check if the position has a piece on it that is the same color as the position
        //throw new RuntimeException("Not implemented");
        return moves;
    }

    private void handleKnight(ChessBoard board, ChessPosition myPosition, ArrayList<ChessPosition> positions) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor team = board.getPiece(myPosition).getTeamColor();

        blockCheck(board, myPosition, positions, 1, 2);
        blockCheck(board, myPosition, positions, 1, -2);
        blockCheck(board, myPosition, positions, 2, 1);
        blockCheck(board, myPosition, positions, 2, -1);
        blockCheck(board, myPosition, positions, -1, -2);
        blockCheck(board, myPosition, positions, -2, -1);
        blockCheck(board, myPosition, positions, -1, 2);
        blockCheck(board, myPosition, positions, -2, 1);
    }

    private void blockCheck(ChessBoard board, ChessPosition myPosition, ArrayList<ChessPosition> positions, int rowadd, int coladd) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor team = board.getPiece(myPosition).getTeamColor();
        try {
            if (board.getPiece(new ChessPosition(row + rowadd, col + coladd)) == null) {
                safeAddPos(myPosition, positions, rowadd, coladd);
            } else if (team != board.getPiece(new ChessPosition(row + rowadd, col + coladd)).getTeamColor()) {
                safeAddPos(myPosition, positions, rowadd, coladd);
            }
        } catch (InvalidPositionException e) {
            //nothing
        }
    }

    private void addForwards(ChessBoard board, ChessPosition myPosition, ArrayList<ChessPosition> positions) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        try {
            if (board.getPiece(new ChessPosition(row + 1, col)) == null) {
                safeAddPos(myPosition, positions, 1, 0);
                if (board.getPiece(new ChessPosition(row + 2, col)) == null && row == 2) {
                    safeAddPos(myPosition, positions, 2, 0);
                }
            }
        } catch (InvalidPositionException e) {
            //do nothing
        }
        try {
        if(board.getPiece(new ChessPosition(row+1, col+1)) != null) {
            if(board.getPiece(new ChessPosition(row+1, col+1)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                safeAddPos(myPosition, positions, 1, 1);
            }
        }
        } catch (InvalidPositionException e) {
            //do nothing
        }
        try {
            if(board.getPiece(new ChessPosition(row+1, col-1)) != null) {
                if(board.getPiece(new ChessPosition(row+1, col-1)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    safeAddPos(myPosition, positions, 1, -1);
                }
            }
        } catch (InvalidPositionException e) {
            //do nothing
        }

    }

    private void addBackwards(ChessBoard board, ChessPosition myPosition, ArrayList<ChessPosition> positions) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        try {
            if (board.getPiece(new ChessPosition(row - 1, col)) == null) {
                safeAddPos(myPosition, positions, -1, 0);
                if (board.getPiece(new ChessPosition(row - 2, col)) == null && row == 7) {
                    safeAddPos(myPosition, positions, -2, 0);
                }
            }
        } catch (InvalidPositionException e) {
            //do nothing
        }
        try {
            if(board.getPiece(new ChessPosition(row-1, col-1)) != null) {
                if(board.getPiece(new ChessPosition(row-1, col-1)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    safeAddPos(myPosition, positions, -1, -1);
                }
            }
        } catch (InvalidPositionException e) {
            //do nothing
        }
        try {
            if(board.getPiece(new ChessPosition(row-1, col+1)) != null) {
                if(board.getPiece(new ChessPosition(row-1, col+1)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    safeAddPos(myPosition, positions, -1, 1);
                }
            }
        } catch (InvalidPositionException e) {
            //do nothing
        }

    }

    private void safeAddPos(ChessPosition myPosition, ArrayList<ChessPosition> positions, int rowadd, int coladd) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        if(!(row+rowadd <= 0 | row+rowadd > 8 | col+coladd <= 0 | col+coladd > 8)){
            positions.add(new ChessPosition(row+rowadd, col+coladd));
        }
    }

    private void addDiagonals(ChessBoard board, ChessPosition myPosition, ArrayList<ChessPosition> positions, int i) {
        boolean northeast = checkNext(board, myPosition, 1, 1);
        boolean southeast = checkNext(board, myPosition, -1, +1);
        boolean southwest = checkNext(board, myPosition, -1, -1);
        boolean northwest = checkNext(board, myPosition, +1, -1);
        for (int j = 0; j < i; j++) {
            if(northeast) {
                safeAddPos(myPosition, positions, j + 1, j + 1);
                northeast = checkNext(board, myPosition, j+2, j+2);
                ChessPosition currentPos = new ChessPosition(myPosition.getRow()+j+1, myPosition.getColumn()+j+1);
                if(board.getPiece(currentPos) != null) {
                    northeast = false;
                }
            }
            if(southwest) {
                safeAddPos(myPosition, positions, -1*j - 1, -1*j - 1);
                southwest = checkNext(board, myPosition, -1*j-2, -1*j-2);
                ChessPosition currentPos = new ChessPosition(myPosition.getRow()+(-1*j)-1, myPosition.getColumn()+(-1*j)-1);
                if(board.getPiece(currentPos) != null) {
                    southwest = false;
                }
            }
            if(southeast) {
                safeAddPos(myPosition, positions, -1*j - 1, j + 1);
                southeast = checkNext(board, myPosition, -1*j-2, j+2);
                ChessPosition currentPos = new ChessPosition(myPosition.getRow()+(-1*j)-1, myPosition.getColumn()+j+1);
                if(board.getPiece(currentPos) != null) {
                    southeast = false;
                }
            }
            if(northwest) {
                safeAddPos(myPosition, positions, j + 1, -1*j - 1);
                northwest = checkNext(board, myPosition, j+2, -1*j-2);
                ChessPosition currentPos = new ChessPosition(myPosition.getRow()+j+1, myPosition.getColumn()+(-1*j)-1);
                if(board.getPiece(currentPos) != null) {
                    northwest = false;
                }
            }
        }
    }

    private void addStraights(ChessBoard board, ChessPosition myPosition, ArrayList<ChessPosition> positions, int i) {
        boolean north = checkNext(board, myPosition, 1, 0);
        boolean south = checkNext(board, myPosition, -1, 0);
        boolean east = checkNext(board, myPosition, 0, 1);
        boolean west = checkNext(board, myPosition, 0, -1);
        for (int j = 0; j < i; j++) {
            if(north) {
                safeAddPos(myPosition, positions, j + 1, 0);
                north = checkNext(board, myPosition, j+2, 0);
                try {
                    ChessPosition currentPos = new ChessPosition(myPosition.getRow()+ j+1, myPosition.getColumn() );
                    if (board.getPiece(currentPos) != null) {
                        north = false;
                    }
                } catch (InvalidPositionException e) {
                    north = false;
                }
            }
            if(south) {
                safeAddPos(myPosition, positions, -1*j - 1, 0);
                south = checkNext(board, myPosition, -1*j-2, 0);
                try {
                    ChessPosition currentPos = new ChessPosition(myPosition.getRow()+ (-1 * j) - 1, myPosition.getColumn() );
                    if (board.getPiece(currentPos) != null) {
                        south = false;
                    }
                } catch (InvalidPositionException e) {
                    south = false;
                }
            }
            if(east) {
                safeAddPos(myPosition, positions, 0, j + 1);
                east = checkNext(board, myPosition, 0, j+2);
                try {
                    ChessPosition currentPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn() + j + 1);
                    if (board.getPiece(currentPos) != null) {
                        east = false;
                    }
                } catch (InvalidPositionException e) {
                    east = false;
                }
            }
            if(west) {
                safeAddPos(myPosition, positions, 0, -1*j - 1);
                west = checkNext(board, myPosition, 0, -1*j-2);
                try {
                    ChessPosition currentPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn() + (-1 * j) - 1);
                    if (board.getPiece(currentPos) != null) {
                        west = false;
                    }
                } catch (InvalidPositionException e) {
                    west = false;
                }
            }
        }
    }
    private boolean checkNext(ChessBoard board, ChessPosition myPosition, int rowadd, int coladd) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPiece startPiece = board.getPiece(myPosition);
        ChessPiece targetPiece = null;
        if(!(row+rowadd <= 0 | row+rowadd > 8 | col+coladd <= 0 | col+coladd > 8)){
            targetPiece = board.getPiece(new ChessPosition(row+rowadd, col+coladd));
            if(targetPiece == null) {
                return true;
            }
            return targetPiece.getTeamColor() != startPiece.getTeamColor();

        }
        return false;
    }

}
