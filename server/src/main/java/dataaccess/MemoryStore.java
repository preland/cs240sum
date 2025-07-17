package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class MemoryStore {
    private int iter = 1;
    private static final MemoryStore INSTANCE = new MemoryStore();
    ArrayList<UserData> userData = new ArrayList<>();
    ArrayList<AuthData> authData = new ArrayList<>();
    ArrayList<GameData> gameData = new ArrayList<>();
    private MemoryStore() {}
    public static MemoryStore getInstance() { return INSTANCE; }
    public void clear() {
        userData.clear();
        authData.clear();
        gameData.clear();
    }

    public UserData getUser(String username, String password) {
        UserData testData = userData.stream().filter(ud -> username.equals(ud.username())).findFirst().orElse(null);
        if(testData == null) {
            return null;
        }
        //todo: implement non-plaintext password stuff
        if(Objects.equals(testData.password(), password)) {
            return testData;
        } else {
            return null;
        }
    }

    public UserData createUser(String username, String password, String email) {
        UserData newUser = new UserData(username, password, email);
        userData.add(newUser);
        return newUser;
    }
    public AuthData createAuth(String username, String password) {
        AuthData newAuth = new AuthData(generateToken(), username);
        authData.add(newAuth);
        return newAuth;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public boolean deleteAuth(String authToken) {
        AuthData testData = authData.stream().filter(ad -> authToken.equals(ad.authToken())).findFirst().orElse(null);
        if(testData == null) {
            return false;
        }
        return authData.remove(testData);
    }

    public String getUsername(String authToken) {
        AuthData testData = authData.stream().filter(ad -> authToken.equals(ad.authToken())).findFirst().orElse(null);
        if(testData == null) {
            return null;
        }
        return testData.username();
    }

    public ArrayList<GameData> listGames() {
        return gameData;
    }

    public GameData createGame(String gameName) {
        GameData newGame = new GameData(iter, null, null, gameName, new ChessGame());
        iter++;
        gameData.add(newGame);
        return newGame;
    }

    public GameData getGame(int gameID) {
        return gameData.stream().filter(g -> gameID==g.gameID()).findFirst().orElse(null);
    }

    public void modifyGame(int gameID, String username, String playerColor) {
        GameData game = getGame(gameID);
        int index = gameData.indexOf(game);
        String whiteUsername = game.whiteUsername();
        String blackUsername = game.blackUsername();
        String gameName = game.gameName();
        ChessGame chessGame = game.game();
        if(Objects.equals(playerColor, "WHITE")) {
            gameData.set(index, new GameData(gameID, username, blackUsername, gameName, chessGame));
        } else {
            gameData.set(index, new GameData(gameID, whiteUsername, username, gameName, chessGame));

        }
    }
}
