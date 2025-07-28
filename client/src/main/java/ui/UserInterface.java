package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class UserInterface {
    boolean quit = false;
    boolean postLogin = true;
    public void run() {
        String rawInput = "";
        System.out.println("welcome chess. type help for help. type quit for unhelp");
        Scanner scanner = new Scanner(System.in);
        while (!quit) {
            rawInput = scanner.nextLine();
            String[] input = rawInput.split(" ");
            if (postLogin) {
                switch (input[0]) {
                    case "help":
                        System.out.println("lol no help for you, sucks ig");
                        break;
                    case "logout":
                        handleLogout();
                        break;
                    case "create":
                        if(input.length != 2) {
                            System.out.println("invalid input. try again, or type help");
                            break;
                        }
                        handleCreateGame(input[1]);
                        break;
                    case "list":
                        handleListGames();
                        break;
                    case "play":
                        if(input.length != 3) {
                            System.out.println("invalid input. try again, or type help");
                            break;
                        }
                        //todo/note: handle incorrect team choice
                        handlePlayGame(input[1], input[2]);
                        break;
                    case "view":
                        if(input.length != 2) {
                            System.out.println("invalid input. try again, or type help");
                            break;
                        }
                        handleViewGame(input[1]);
                        break;
                    default:
                        System.out.println("invalid input. try again, or type help");
                }
            } else {
                switch (input[0]) {
                    case "help":
                        System.out.println("lol no help for you, sucks ig");
                        break;
                    case "quit":
                        quit = true;
                        System.out.println("goodb");
                        break;
                    case "login":
                        if(input.length != 3) {
                            System.out.println("invalid input. try again, or type help");
                            break;
                        }
                        handleLogin(input[1], input[2]);
                        break;
                    case "register":
                        if(input.length != 4) {
                            System.out.println("invalid input. try again, or type help");
                            break;
                        }
                        handleRegister(input[1], input[2], input[3]);
                        break;
                    default:
                        System.out.println("invalid input. try again, or type help");
                }
            }
        }
    }

    private void handleListGames() {
    }

    private void handleCreateGame(String s) {
    }

    private void handlePlayGame(String s, String s1) {
    }

    private void handleLogin(String s, String s1) {
    }

    private void handleRegister(String s, String s1, String s2) {
    }

    private void handleLogout() {
    }

    private void handleViewGame(String s) {
        //todo: add string parsing, for now just display initial board
        ChessGame testGame = new ChessGame();
        System.out.println(viewGame(testGame, true));

    }

    private String viewGame(ChessGame testGame, boolean isWhitePerspective) {
        ChessBoard board = testGame.getBoard();
        String output = "";
        boolean isOddSpace = true;
        if(isWhitePerspective) {
            output = SET_BG_COLOR_DARK_GREY + EMPTY + A_CHAR + B_CHAR + C_CHAR + D_CHAR + E_CHAR + F_CHAR + G_CHAR + H_CHAR+ EMPTY + RESET_BG_COLOR + RESET_TEXT_COLOR + "\n";
            ;
            for (int i = 1; i <= 8; i++) {
                output += getNum(i);
                for (int j = 1; j <= 8; j++) {
                    output += isOddSpace ? SET_BG_COLOR_GREEN : SET_BG_COLOR_DARK_GREEN;
                    isOddSpace = !isOddSpace;
                    output += getCharacter(board.getPiece(new ChessPosition(i, j)));
                }
                output += SET_BG_COLOR_DARK_GREY + getNum(i) + '\n';
                isOddSpace = !isOddSpace;
            }
            output += EMPTY + A_CHAR + B_CHAR + C_CHAR + D_CHAR + E_CHAR + F_CHAR + G_CHAR + H_CHAR+ EMPTY + RESET_BG_COLOR + RESET_TEXT_COLOR;
        } else {
            output = SET_BG_COLOR_DARK_GREY + EMPTY + H_CHAR + G_CHAR + F_CHAR + E_CHAR + D_CHAR + C_CHAR + B_CHAR + A_CHAR + EMPTY + "\n";
            for (int i = 8; i >= 1; i--) {
                output += RESET_BG_COLOR + RESET_TEXT_COLOR + String.valueOf(i);
                for (int j = 8; j >= 1; j--) {
                    output += isOddSpace ? SET_BG_COLOR_WHITE : SET_BG_COLOR_BLACK;
                    isOddSpace = !isOddSpace;
                    output += getCharacter(board.getPiece(new ChessPosition(i, j)));
                }
                output += SET_BG_COLOR_DARK_GREY + i + '\n';
            }
            output += EMPTY + H_CHAR + G_CHAR + F_CHAR + E_CHAR + D_CHAR + C_CHAR + B_CHAR + A_CHAR + EMPTY + RESET_BG_COLOR + RESET_TEXT_COLOR;
        }
        return output;
    }

    private String getNum(int i) {
        switch (i) {
            case 1:
                return ONE_CHAR;
            case 2:
                return TWO_CHAR;
            case 3:
                return THREE_CHAR;
            case 4:
                return FOUR_CHAR;
            case 5:
                return FIVE_CHAR;
            case 6:
                return SIX_CHAR;
            case 7:
                return SEVEN_CHAR;
            case 8:
                return EIGHT_CHAR;
        }
        return "INVALID";
    }

    private String getCharacter(ChessPiece piece) {
        String output = "";
        if(piece == null) {
            return EMPTY;
        }

        if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            output += SET_TEXT_COLOR_WHITE;
        } else {
            output += SET_TEXT_COLOR_BLACK;
        }
        switch (piece.getPieceType()) {
            case KING -> {
                output += BLACK_KING;
            }
            case QUEEN -> {
                output += BLACK_QUEEN;
            }
            case BISHOP -> {
                output += BLACK_BISHOP;
            }
            case KNIGHT -> {
                output += BLACK_KNIGHT;
            }
            case ROOK -> {
                output += BLACK_ROOK;
            }
            case PAWN -> {
                output+= BLACK_PAWN;
            }
        }
        return output;
    }
}
