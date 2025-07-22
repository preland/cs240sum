package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.sql.SQLException;
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
        var statement = "do stuff here";
        try(var conn = DatabaseManager.getConnection()) {

        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
    }

    public UserData getUser(String username, String password) {
        var statement = "do stuff here";
        try(var conn = DatabaseManager.getConnection()) {

        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
    }

    public UserData createUser(String username, String password, String email) {
        var statement = "do stuff here";
        try(var conn = DatabaseManager.getConnection()) {

        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
    }
    public AuthData createAuth(String username, String password) {
        var statement = "do stuff here";
        try(var conn = DatabaseManager.getConnection()) {

        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public boolean deleteAuth(String authToken) {
        var statement = "do stuff here";
        try(var conn = DatabaseManager.getConnection()) {

        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
    }

    public String getUsername(String authToken) {
        var statement = "do stuff here";
        try(var conn = DatabaseManager.getConnection()) {

        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
    }

    public ArrayList<GameData> listGames() {
        var statement = "do stuff here";
        try(var conn = DatabaseManager.getConnection()) {

        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
    }

    public GameData createGame(String gameName) {
        var statement = "do stuff here";
        try(var conn = DatabaseManager.getConnection()) {

        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
    }

    public GameData getGame(int gameID) {
        var statement = "do stuff here";
        try(var conn = DatabaseManager.getConnection()) {

        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
    }

    public void modifyGame(int gameID, String username, String playerColor) {
        var statement = "do stuff here";
        try(var conn = DatabaseManager.getConnection()) {

        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
    }
}
