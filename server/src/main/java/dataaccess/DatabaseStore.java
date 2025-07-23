package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class DatabaseStore {
    //private int iter = 1;
    private static final DatabaseStore INSTANCE = new DatabaseStore();
    //ArrayList<UserData> userData = new ArrayList<>();
    //ArrayList<AuthData> authData = new ArrayList<>();
    //ArrayList<GameData> gameData = new ArrayList<>();
    private DatabaseStore() {}
    public static DatabaseStore getInstance() { return INSTANCE; }
    public void clear() {
        var statement = "TRUNCATE TABLE ";
        try(var conn = DatabaseManager.getConnection()) {
            var update = conn.prepareStatement(statement+"auth");
            update.executeUpdate();
            update = conn.prepareStatement(statement+"user");
            update.executeUpdate();
            update = conn.prepareStatement(statement+"game");
            update.executeUpdate();
        } catch(DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserData getUser(String username, String password) {
        var statement = """
                SELECT username, passwordHash, email 
                FROM user 
                WHERE username=?
                """;
        try(var conn = DatabaseManager.getConnection()) {
            var query = conn.prepareStatement(statement);
            query.setString(1, username);
            var result = query.executeQuery();
            if(!result.next()){
                return null;
            }
            return new UserData(result.getString("username"), result.getString("passwordHash"), result.getString("email"));
        } catch(DataAccessException | SQLException e) {
            //do something here!
            throw new RuntimeException(e);
        }
        //return new UserData("a","a","a");
    }

    public UserData createUser(String username, String password, String email) {
        var statement = """
                INSERT into user (username, passwordHash, email) VALUES(?,?,?)
                """;
        try(var conn = DatabaseManager.getConnection()) {
            var update = conn.prepareStatement(statement);
            update.setString(1, username);
            update.setString(2, password);
            update.setString(3, email);
            update.executeUpdate();
        } catch(DataAccessException | SQLException e) {
            //do something here!
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
        return true; //todo: fix
    }

    public String getUsername(String authToken) {
        var statement = """
                SELECT username FROM auth WHERE authToken=?""";
        try(var conn = DatabaseManager.getConnection()) {
            var query = conn.prepareStatement(statement);
            query.setString(1, authToken);
            var result = query.executeQuery();
            if(!result.next()){
                return null;
            }
            return result.getString("username");
        } catch(DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
        //return "a";
    }

    public ArrayList<GameData> listGames() {
        var statement = "SELECT * FROM game";
        ArrayList<GameData> ret = new ArrayList<>();
        try(var conn = DatabaseManager.getConnection()) {
            var query = conn.prepareStatement(statement);
            var result = query.executeQuery();
            /*if(!result.next()){
                return null;
            }*/
            while(result.next()) {
                int gameID = result.getInt("gameID");
                String whiteUsername = result.getString("whiteUsername");
                String blackUsername = result.getString("blackUsername");
                String gameName = result.getString("gameName");
                ChessGame game = new Gson().fromJson(result.getString("game"), ChessGame.class);
                ret.add(new GameData(gameID-1, whiteUsername, blackUsername, gameName, game));
            }
            return ret;
        } catch(DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
        //return new ArrayList<>();
    }

    public GameData createGame(String gameName) {
        var statement = "INSERT INTO game (whiteUsername,blackUsername,gameName,game) VALUES(?,?,?,?)";
        try(var conn = DatabaseManager.getConnection()) {
            var update = conn.prepareStatement(statement);
            update.setString(1, null);
            update.setString(2, null);
            update.setString(3, gameName);
            update.setString(4, new Gson().toJson(new ChessGame(), ChessGame.class));
            update.executeUpdate();
            return new GameData(0,"","","", new ChessGame());
        } catch(DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public GameData getGame(int gameID) {
        var statement = """
                SELECT gameID,whiteUsername,blackUsername,gameName,game 
                FROM game 
                WHERE gameID=?
                """;
        try(var conn = DatabaseManager.getConnection()) {
            var query = conn.prepareStatement(statement);
            query.setInt(1,gameID+1);
            var result = query.executeQuery();
            if(!result.next()){
                return null;
            }
            String whiteUsername = result.getString("whiteUsername");
            String blackUsername = result.getString("blackUsername");
            String gameName = result.getString("gameName");
            ChessGame game = new Gson().fromJson(result.getString("game"), ChessGame.class);
            return new GameData(gameID+1, whiteUsername, blackUsername, gameName, game);
        } catch(DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
        //return new GameData(0,"","","", new ChessGame());
    }

    public void modifyGame(int gameID, String username, String playerColor) {
        var statement1 = "UPDATE game SET ";
        var statement3 = "=? WHERE gameID=?";
        var statement2 = "";
        if(playerColor.equals("WHITE")) {
            statement2 = "whiteUsername";
        } else {
            statement2 = "blackUsername";
        }
        try(var conn = DatabaseManager.getConnection()) {
            var update = conn.prepareStatement(statement1+statement2+statement3);

            update.setString(1, username);
            update.setInt(2, gameID+1);
            update.executeUpdate();
        } catch(DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
