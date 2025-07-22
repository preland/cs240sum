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
        var statement = """
                TRUNCATE user
                TRUNCATE auth
                TRUNCATE game
                """;
        try(var conn = DatabaseManager.getConnection()) {
            conn.prepareStatement(statement).executeUpdate();
        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
    }

    public UserData getUser(String username, String password) {
        var statement = """
                SELECT username, password, email 
                FROM user 
                WHERE username=?
                """;
        try(var conn = DatabaseManager.getConnection()) {
            var query = conn.prepareStatement(statement);
            query.setString(1, username);
            var result = query.executeQuery();
            return new UserData(result.getString("username"), result.getString("username"), result.getString("username"));
        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
        return new UserData("a","a","a");
    }

    public UserData createUser(String username, String password, String email) {
        var statement = "do stuff here";
        try(var conn = DatabaseManager.getConnection()) {

        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
        return new UserData("a","a","a");
    }
    public AuthData createAuth(String username, String password) {
        var statement = "do stuff here";
        try(var conn = DatabaseManager.getConnection()) {

        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
        return new AuthData("a","a");
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
        return true;
    }

    public String getUsername(String authToken) {
        var statement = "do stuff here";
        try(var conn = DatabaseManager.getConnection()) {

        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
        return "a";
    }

    public ArrayList<GameData> listGames() {
        var statement = "do stuff here";
        try(var conn = DatabaseManager.getConnection()) {

        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
        return new ArrayList<>();
    }

    public GameData createGame(String gameName) {
        var statement = "do stuff here";
        try(var conn = DatabaseManager.getConnection()) {

        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
        return new GameData(0,"","","", new ChessGame());
    }

    public GameData getGame(int gameID) {
        var statement = "do stuff here";
        try(var conn = DatabaseManager.getConnection()) {

        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
        return new GameData(0,"","","", new ChessGame());
    }

    public void modifyGame(int gameID, String username, String playerColor) {
        var statement = "do stuff here";
        try(var conn = DatabaseManager.getConnection()) {

        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
    }
}
