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
        ChessPiece piece = this.board.getPiece(startPosition);
        Collection<ChessMove> prelimMoves = piece.pieceMoves(board, startPosition);
        Collection<ChessMove> ret = new ArrayList<>();
        for(ChessMove move : prelimMoves) {
            ChessBoard tempBoard = this.board.copy();
            unsafeMakeMove(move, tempBoard);
            if(!isInCheck(piece.getTeamColor(), tempBoard)) {
                ret.add(move);
            }
        }
        return ret;
        //throw new RuntimeException("Not implemented");
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
        ChessBoard tempBoard = this.board.copy();//= unsafeMakeMove(move);
        unsafeMakeMove(move, tempBoard);
        if(isInCheck(team, tempBoard) || isInCheckmate(team, tempBoard) || isInStalemate(team, tempBoard)){
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
        return unsafeMakeMove(move, this.board);
    }
    public ChessBoard unsafeMakeMove(ChessMove move, ChessBoard tempBoard) {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition targetPosition = move.getEndPosition();
        ChessPiece startPiece = board.getPiece(startPosition);
        ChessPiece targetPiece = board.getPiece(targetPosition);
        if(startPiece == null) {
            //ex nihilo protection
            return tempBoard;
        }
        ChessPiece endPiece = startPiece;
        //ChessBoard tempBoard = board;

        if(move.getPromotionPiece() != null) {
            endPiece = new ChessPiece(startPiece.getTeamColor(), move.getPromotionPiece());
        }

        tempBoard.addPiece(targetPosition, endPiece);
        tempBoard.addPiece(startPosition, null);
        if(findKingPiece(startPiece.pieceColor, tempBoard) == null) {
            //reverse in case of no king
            tempBoard.addPiece(targetPosition, null);
            tempBoard.addPiece(startPosition, endPiece);
        }
        return tempBoard;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return isInCheck(teamColor, this.board);
    }
    public boolean isInCheck(TeamColor teamColor, ChessBoard givenBoard) {
        ChessPosition kingPiece = findKingPiece(teamColor, givenBoard);
        TeamColor otherTeam = teamColor == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE;
        // step 0: is there a king piece
        if(kingPiece == null) {
            throw new RuntimeException("how");
        }



        Collection<ChessMove> allAttacksOpposing = allTeamAttacking(otherTeam, givenBoard);
        for(ChessMove move : allAttacksOpposing) {
            if (move.getEndPosition().equals(kingPiece)) {
                return true;
            }
        }


 /*       for(ChessMove move : allAttacksOpposing) {
            if (move.getEndPosition().equals(kingPiece)) {
                for(ChessMove move2 : allAttacksSame) {
                    ChessBoard tempBoard = this.board;
                    unsafeMakeMove(move2, tempBoard);
                    Collection<ChessMove> finalMoves = allTeamAttacking(otherTeam, tempBoard);
                    for(ChessMove move3 : finalMoves) {
                        if (move3.getEndPosition() != findKingPiece(teamColor, tempBoard)) {
                            return true;
                        }
                    }
                }
            }
        }*/
        return false;
        //throw new RuntimeException("Not implemented");
    }

    private ChessPosition findKingPiece(TeamColor teamColor, ChessBoard givenBoard) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition tempPosition = new ChessPosition(i, j);
                if(Objects.equals(givenBoard.getPiece(tempPosition), new ChessPiece(teamColor, ChessPiece.PieceType.KING))) {
                    return tempPosition;//board.getPiece(tempPosition);
                }
            }
        }
        return null;
    }
    private Collection<ChessMove> allTeamAttacking(TeamColor teamColor, ChessBoard givenBoard) {
        Collection<ChessMove> positions = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition tempPosition = new ChessPosition(i, j);
                ChessPiece tempPiece = givenBoard.getPiece(tempPosition);
                if(tempPiece == null) {
                    continue;
                }
                Collection<ChessMove> moves = tempPiece.pieceMoves(givenBoard, tempPosition);
                if(moves == null) {
                    continue;
                }
                for(ChessMove move: moves) {
                    if(tempPiece.getTeamColor() == teamColor) {
                        positions.add(move);
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
        return isInCheckmate(teamColor, this.board);
    }
    public boolean isInCheckmate(TeamColor teamColor, ChessBoard givenBoard) {
        Collection<ChessMove> allAttacksSame = allTeamAttacking(teamColor, givenBoard);
        if(!isInCheck(teamColor, givenBoard)) {
            return false;
        }
        //step 1: is the king piece currently in check
        //step 1.5: if so, what pieces are checking it
        //step 2: if it is in check, can the king be moved to a position not in check
        //step 3: if it can't be moved, can the attacking piece be taken
        //step 4: if it can't be taken, can the attack be blocked?
        ChessPosition kingPiece = findKingPiece(teamColor, givenBoard);
        //ChessBoard tempBoard = givenBoard;
        TeamColor otherTeam = teamColor == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE;
        for(ChessMove move : allAttacksSame) {
            ChessBoard tempBoard = givenBoard.copy();
            unsafeMakeMove(move, tempBoard);
            if(!isInCheck(teamColor, tempBoard)) {
                return false;
            }
        }
        return true;
        /*for(ChessMove move: givenBoard.getPiece(kingPiece).pieceMoves(board, kingPiece)) {
            if (!allTeamAttacking(otherTeam, givenBoard).contains(move.getEndPosition())) {
                return true;
            }
        }
        return false;*/
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
        return isInStalemate(teamColor, this.board);
        //throw new RuntimeException("Not implemented");
    }
    public boolean isInStalemate(TeamColor teamColor, ChessBoard givenBoard) {
        Collection<ChessMove> allAttacksSame = allTeamAttacking(teamColor, givenBoard);
        if(isInCheck(teamColor, givenBoard)) {
            return false;
        }
        for(ChessMove move : allAttacksSame) {
            ChessBoard tempBoard = givenBoard.copy();
            unsafeMakeMove(move, tempBoard);
            if(!isInCheck(teamColor, tempBoard)) {
                return false;
            }
        }
        return true;
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
