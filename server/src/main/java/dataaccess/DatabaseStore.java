package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class DatabaseStore {
    private int iter = 1;
    private static final DatabaseStore INSTANCE = new DatabaseStore();
    //ArrayList<UserData> userData = new ArrayList<>();
    //ArrayList<AuthData> authData = new ArrayList<>();
    //ArrayList<GameData> gameData = new ArrayList<>();
    private DatabaseStore() {}
    public static DatabaseStore getInstance() { return INSTANCE; }
    public void clear() {
    }

    public UserData getUser(String username, String password) {
    }

    public UserData createUser(String username, String password, String email) {

    }
    public AuthData createAuth(String username, String password) {

    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public boolean deleteAuth(String authToken) {
    }

    public String getUsername(String authToken) {

    }

    public ArrayList<GameData> listGames() {

    }

    public GameData createGame(String gameName) {

    }

    public GameData getGame(int gameID) {

    }

    public void modifyGame(int gameID, String username, String playerColor) {
    }
}
