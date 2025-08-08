import chess.*;
import ui.UserInterface;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        var ui = UserInterface.getInstance();
        System.out.println("♕ 240 Chess Client: " + piece);
        ui.run();
    }
}