package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    TeamColor team;
    ChessBoard board = new ChessBoard();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return team == chessGame.team && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, board);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "team=" + team +
                ", board=" + board +
                '}';
    }

    public ChessGame() {

        this.team = TeamColor.WHITE;
        this.board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return team;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.team = team;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition targetPosition = move.getEndPosition();
        ChessPiece startPiece = board.getPiece(startPosition);
        ChessPiece targetPiece = board.getPiece(targetPosition);
        if(startPiece == null) {
            throw new InvalidMoveException("invalid move");
        }
        TeamColor teamPiece = startPiece.getTeamColor();
        //is the move for the right team
        if(teamPiece != team) {
            throw new InvalidMoveException("invalid move");
        }
        //is the move trivially valid
        if(!startPiece.pieceMoves(board, startPosition).contains(move)) {
            throw new InvalidMoveException("invalid move");
        }
        //create new board with move
        ChessBoard tempBoard = unsafeMakeMove(move);
        if(isInCheck(team) || isInCheckmate(team) || isInStalemate(team)){
            throw new InvalidMoveException("invalid move");
        }
        this.board = tempBoard;
        if(this.team == TeamColor.WHITE){
            this.team = TeamColor.BLACK;
        } else {
            this.team = TeamColor.WHITE;
        }
        //throw new RuntimeException("Not implemented");
    }
    public ChessBoard unsafeMakeMove(ChessMove move) {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition targetPosition = move.getEndPosition();
        ChessPiece startPiece = board.getPiece(startPosition);
        ChessPiece targetPiece = board.getPiece(targetPosition);
        ChessPiece endPiece = startPiece;
        ChessBoard tempBoard = board;

        if(move.getPromotionPiece() != null) {
            endPiece = new ChessPiece(startPiece.getTeamColor(), move.getPromotionPiece());
        }

        tempBoard.addPiece(targetPosition, endPiece);
        tempBoard.addPiece(startPosition, null);
        return tempBoard;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPiece = findKingPiece(teamColor);
        TeamColor otherTeam = teamColor == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE;
        if(allTeamAttacking(otherTeam).contains(kingPiece)) {
            return true;
        } else {
            return false;
        }
        //return false;
        //throw new RuntimeException("Not implemented");
    }

    private ChessPosition findKingPiece(TeamColor teamColor) {
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 8; j++) {
                ChessPosition tempPosition = new ChessPosition(i, j);
                if(Objects.equals(board.getPiece(tempPosition), new ChessPiece(teamColor, ChessPiece.PieceType.KING))) {
                    return tempPosition;//board.getPiece(tempPosition);
                }
            }
        }
        return null;
    }
    private Collection<ChessPosition> allTeamAttacking(TeamColor teamColor) {
        Collection<ChessPosition> positions = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 8; j++) {
                ChessPosition tempPosition = new ChessPosition(i, j);
                ChessPiece tempPiece = board.getPiece(tempPosition);
                if(tempPiece == null) {
                    continue;
                }
                Collection<ChessMove> moves = tempPiece.pieceMoves(board, tempPosition);
                if(moves == null) {
                    continue;
                }
                for(ChessMove move: moves) {
                    if(tempPiece.getTeamColor() == teamColor && !positions.contains(move.getEndPosition())) {
                        positions.add(move.getEndPosition());
                    }
                }
            }
        }
        return positions;
    }
    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return false;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return false;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
        //throw new RuntimeException("Not implemented");
    }
}
