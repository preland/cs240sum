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
        var statement = """
                INSERT into user (username, password, email) VALUES(?,?,?)
                """;
        try(var conn = DatabaseManager.getConnection()) {
            var update = conn.prepareStatement(statement);
            update.setString(1, username);
            update.setString(2, password);
            update.setString(3, email);
            update.executeUpdate();
        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
        return new UserData(username,password,email);
    }
    public AuthData createAuth(String username, String password) {
        var statement = """
                INSERT into auth (authToken, username) VALUES(?,?)""";
        String token = generateToken();
        try(var conn = DatabaseManager.getConnection()) {
            var update = conn.prepareStatement(statement);
            update.setString(1, token);
            update.setString(2, username);
            update.executeUpdate();
        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
        return new AuthData(token,username);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public boolean deleteAuth(String authToken) {
        var statement = """
                DELETE FROM auth WHERE authToken=?""";
        try(var conn = DatabaseManager.getConnection()) {
            var update = conn.prepareStatement(statement);
            update.setString(1, authToken);
            update.executeUpdate();
        } catch(DataAccessException | SQLException e) {
            //do something here!
        }
        return true;
    }

    public String getUsername(String authToken) {
        var statement = """
                SELECT username FROM auth WHERE authToken=?""";
        try(var conn = DatabaseManager.getConnection()) {
            var query = conn.prepareStatement(statement);
            query.setString(1, authToken);
            var result = query.executeQuery();
            return result.getString("username");
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
