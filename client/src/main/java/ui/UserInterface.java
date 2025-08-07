package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import model.GameData;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class UserInterface {
    boolean quit = false;
    boolean postLogin = false;
    boolean inGame = false;
    int currentGame = -1;
    ServerFacade facade;
    String authToken;
    public UserInterface() {
        this.facade = new ServerFacade();
    }
    public void run() {
        String rawInput = "";
        System.out.println("welcome chess. type help for help. type quit for unhelp");
        Scanner scanner = new Scanner(System.in);
        while (!quit) {
            rawInput = scanner.nextLine();
            String[] input = rawInput.split(" ");
            if(inGame) {
                switch (input[0]) {
                    case "help":
                        System.out.println("""
                                help: shows this
                                redraw: redraws the game
                                move <startPosition> <endPosition> (promotionPiece): make a move
                                leave: leave the game
                                resign: forfeit the game
                                moves <position>: show valid moves for given player
                                """);
                        break;
                    case "redraw":
                        handleRedraw();
                        break;
                    case "move":
                        if(input.length != 3 && input.length != 4) {
                            System.out.println("invalid input. try again, or type help");
                            break;
                        }
                        if(input.length != 4) {
                            handleMove(parsePos(input[1]), parsePos(input[2]), null);
                        } else {
                            handleMove(parsePos(input[1]), parsePos(input[2]), input[3]);
                        }
                        break;
                    case "leave":
                        handleLeave();
                        break;
                    case "resign":
                        handleResign();
                        break;
                    case "moves":
                        if(input.length != 2) {
                            System.out.println("invalid input. try again, or type help");
                            break;
                        }
                        handleMoves(parsePos(input[1]));
                        break;
                    default:
                        System.out.println("invalid input. try again, or type help");
                }
            } else if (postLogin) {
                switch (input[0]) {
                    case "help":
                        System.out.println("""
                                help: shows this
                                logout: logs out
                                create <Game Name>: creates a game with name
                                list: lists games
                                play <id> <WHITE|BLACK>: plays game with given id and team
                                view <id>: observe game with given id
                                """);
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
                        try {
                            handlePlayGame(Integer.parseInt(input[1]), input[2]);
                        } catch(NumberFormatException e) {
                            System.out.println("invalid input. try again, or type help");
                            break;
                        }
                        break;
                    case "view":
                        if(input.length != 2) {
                            System.out.println("invalid input. try again, or type help");
                            break;
                        }
                        try {
                            handleViewGame(Integer.parseInt(input[1]));
                        } catch (NumberFormatException e) {
                            System.out.println("invalid input. try again, or type help");
                            break;
                        }
                        break;
                    default:
                        System.out.println("invalid input. try again, or type help");
                }
            } else {
                switch (input[0]) {
                    case "help":
                        System.out.println("""                         
                                help: shows this message
                                login <username> <password>: logs in
                                register <username> <password> <email>: registers new user
                                quit: quits the application
                                """);
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

    private void handleRedraw() {

    }

    private void handleMove(ChessPosition chessPosition, ChessPosition chessPosition1, String s) {
    }

    private void handleLeave() {
    }

    private void handleResign() {
    }

    private void handleMoves(ChessPosition chessPosition) {
    }

    private ChessPosition parsePos(String s) {
        if(s.length() != 2) {
            return null;
        }
        int row = Integer.parseInt(String.valueOf(s.charAt(1)));
        int col = -1;
        char colChar = s.charAt(0);
        switch(colChar) {
            case 'a':
                col = 1;
                break;
            case 'b':
                col = 2;
                break;
            case 'c':
                col = 3;
                break;
            case 'd':
                col = 4;
                break;
            case 'e':
                col = 5;
                break;
            case 'f':
                col = 6;
                break;
            case 'g':
                col = 7;
                break;
            case 'h':
                col = 8;
                break;
            default:
                return null;
        }
        if(col==-1) {
            return null;
        }
        return new ChessPosition(row, col);
    }

    private void handleListGames() {
        System.out.println("listing games: ");
        ArrayList<GameData> req = facade.listGames(authToken);
        for (int i = 1; i <= req.size(); i++) {
            GameData game = req.get(i - 1);
            System.out.println((game.gameID())+": "+ game.gameName() + "," + (game.whiteUsername()==null
                    ? " WHITE OPEN" : game.whiteUsername())  + "," + (game.blackUsername()==null
                    ? "BLACK OPEN" : game.blackUsername()));
        }
        if(req.isEmpty()) {
            System.out.println("no games");
        }
    }

    private void handleCreateGame(String gameName) {
        System.out.println("creating game " + gameName);
        String req = facade.createGame(gameName, authToken);
        if(req.equals("500")) {
            System.out.println("failed to create game: internal error");
        } else {
            System.out.println("successfully created game " + gameName);
            //System.out.println("created game with gameid: " + Float.valueOf(req).intValue());
        }
    }

    private void handlePlayGame(int gameID, String teamName) {
        String req;
        if(teamName.equals("WHITE")) {
            System.out.println("joining game");
            req = facade.joinGame(gameID, true, authToken);
        } else if(teamName.equals("BLACK")) {
            System.out.println("joining game");
            req = facade.joinGame(gameID, false, authToken);
        } else {
            System.out.println("invalid color choice");
            return;
        }
        if(req.equals("500")) {
            System.out.println("fail: connection failed");
        } else if(req.equals("400")) {
            System.out.println("game not found");
        } else if(req.equals("403")) {
            System.out.println("player already taken");
        }
        else {
            System.out.println("successfully joined");
            System.out.println(viewGame(new ChessGame(), Objects.equals("WHITE", teamName)));
        }

    }

    private void handleLogin(String username, String password) {
        System.out.println("trying to login");
        String req = facade.login(username, password);
        if(Objects.equals(req, "401")) {
            System.out.println("bad username or password");

        } else if(Objects.equals(req, "500")) {
            System.out.println("fail: connection failed");
        } else {
            System.out.println("logged in");
            authToken = req;
            postLogin = true;
        }
    }

    private void handleRegister(String username, String password, String email) {
        System.out.println("trying to register");
        String req = facade.register(username, password, email);
        if(Objects.equals(req, "400")) {
            System.out.println("failure to authenticate");
        } else if(Objects.equals(req, "500")) {
            System.out.println("fail: connection failed");
        } else if(Objects.equals("403", req)) {
            System.out.println("fail: already taken");
        }
        else {
            System.out.println("success");
            authToken = req;
            postLogin = true;
        }
    }

    private void handleLogout() {
        System.out.println("logging out");
        String req = facade.logout(authToken);
        if(Objects.equals(req, "500")) {
            System.out.println("fail: connection failed");
        } else {
            System.out.println("successfully logged out");
            postLogin = false;
        }
    }

    private void handleViewGame(int gameID) {
        //todo: add string parsing, for now just display initial board
        boolean isMatched = false;
        ArrayList<GameData> req = facade.listGames(authToken);
        for (int i = 0; i < req.size(); i++) {
            if(req.get(i).gameID() == gameID) {
                isMatched = true;
            }
        }
        if(!isMatched) {
            System.out.println("game not found");
            return;
        }
        ChessGame testGame = new ChessGame();
        System.out.println(viewGame(testGame, true));

    }

    private String viewGame(ChessGame testGame, boolean isWhitePerspective) {
        ChessBoard board = testGame.getBoard();
        String output = "";
        boolean isOddSpace = true;
        if(isWhitePerspective) {
            output = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_BLUE + EMPTY + A_CHAR + B_CHAR + C_CHAR +
                    D_CHAR + E_CHAR + F_CHAR + G_CHAR + H_CHAR+ EMPTY + RESET_BG_COLOR + "\n";
            ;
            for (int i = 8; i >= 1; i--) {
                output += getNum(i);
                for (int j = 1; j <= 8; j++) {
                    output += isOddSpace ? SET_BG_COLOR_GREEN : SET_BG_COLOR_DARK_GREEN;
                    isOddSpace = !isOddSpace;
                    output += getCharacter(board.getPiece(new ChessPosition(i, j)));
                }
                output += SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_BLUE + getNum(i) + RESET_BG_COLOR + "\n";
                isOddSpace = !isOddSpace;
            }
            output += SET_BG_COLOR_DARK_GREY + EMPTY + A_CHAR + B_CHAR + C_CHAR + D_CHAR + E_CHAR +
                    F_CHAR + G_CHAR + H_CHAR+ EMPTY + RESET_BG_COLOR + RESET_TEXT_COLOR;
        } else {
            output = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_BLUE + EMPTY + H_CHAR + G_CHAR + F_CHAR +
                    E_CHAR + D_CHAR + C_CHAR + B_CHAR + A_CHAR + EMPTY + RESET_BG_COLOR + "\n";
            for (int i = 1; i <= 8; i++) {
                output += SET_BG_COLOR_DARK_GREY + getNum(i);
                for (int j = 8; j >= 1; j--) {
                    output += isOddSpace ? SET_BG_COLOR_GREEN : SET_BG_COLOR_DARK_GREEN;
                    isOddSpace = !isOddSpace;
                    output += getCharacter(board.getPiece(new ChessPosition(i, j)));
                }
                output += SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_BLUE + getNum(i) + RESET_BG_COLOR + '\n';
                isOddSpace = !isOddSpace;
            }
            output += SET_BG_COLOR_DARK_GREY + EMPTY + H_CHAR + G_CHAR + F_CHAR + E_CHAR + D_CHAR +
                    C_CHAR + B_CHAR + A_CHAR + EMPTY + RESET_BG_COLOR + RESET_TEXT_COLOR;
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
