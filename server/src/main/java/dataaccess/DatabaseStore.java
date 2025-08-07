package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import service.ServiceException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class DatabaseStore {
    //private int iter = 1;
    private static final DatabaseStore INSTANCE = new DatabaseStore();
    private DatabaseStore() {}
    public static DatabaseStore getInstance() { return INSTANCE; }
    public void clear() throws ServiceException{
        var statement = "TRUNCATE TABLE ";
        try(var conn = DatabaseManager.getConnection()) {
            var update = conn.prepareStatement(statement+"auth");
            update.executeUpdate();
            update = conn.prepareStatement(statement+"user");
            update.executeUpdate();
            update = conn.prepareStatement(statement+"game");
            update.executeUpdate();
        } catch(DataAccessException | SQLException e) {
            throw new ServiceException(500, e.getMessage());
        }
    }

    public UserData getUser(String username, String password) throws ServiceException{
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
            if(!BCrypt.checkpw(password, result.getString("passwordHash"))) {
                return null;
            }
            return new UserData(result.getString("username"), result.getString("passwordHash"), result.getString("email"));
        } catch(DataAccessException | SQLException e) {
            //do something here!
            throw new ServiceException(500, e.getMessage());
        }
        //return new UserData("a","a","a");
    }

    public UserData createUser(String username, String password, String email) throws ServiceException{
        var statement = """
                INSERT into user (username, passwordHash, email) VALUES(?,?,?)
                """;
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        try(var conn = DatabaseManager.getConnection()) {
            var update = conn.prepareStatement(statement);
            update.setString(1, username);
            update.setString(2, passwordHash);
            update.setString(3, email);
            update.executeUpdate();
        } catch(DataAccessException | SQLException e) {
            //do something here!
            throw new ServiceException(500, e.getMessage());
        }
        return new UserData(username,password,email);
    }
    public AuthData createAuth(String username, String password) throws ServiceException{
        var statement = """
                INSERT into auth (authToken, username) VALUES(?,?)""";
        String token = generateToken();
        try(var conn = DatabaseManager.getConnection()) {
            var update = conn.prepareStatement(statement);
            update.setString(1, token);
            update.setString(2, username);
            update.executeUpdate();
        } catch(DataAccessException | SQLException e) {
            throw new ServiceException(500, e.getMessage());
        }
        return new AuthData(token,username);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public boolean deleteAuth(String authToken) throws ServiceException{
        var statement = """
                DELETE FROM auth WHERE authToken=?""";
        try(var conn = DatabaseManager.getConnection()) {
            var update = conn.prepareStatement(statement);
            update.setString(1, authToken);
            return update.executeUpdate() != 0;
        } catch(DataAccessException | SQLException e) {
            throw new ServiceException(500, e.getMessage());
        }
        //return true; //todo: fix
    }

    public String getUsername(String authToken) throws ServiceException{
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
            throw new ServiceException(500, e.getMessage());
        }
        //return "a";
    }

    public ArrayList<GameData> listGames() throws ServiceException{
        var statement = "SELECT * FROM game";
        ArrayList<GameData> ret = new ArrayList<>();
        try(var conn = DatabaseManager.getConnection()) {
            var query = conn.prepareStatement(statement);
            var result = query.executeQuery();
            while(result.next()) {
                int gameID = result.getInt("gameID");
                String whiteUsername = result.getString("whiteUsername");
                String blackUsername = result.getString("blackUsername");
                String gameName = result.getString("gameName");
                boolean isActive = result.getBoolean("isActive");
                ChessGame game = new Gson().fromJson(result.getString("game"), ChessGame.class);
                ret.add(new GameData(gameID, whiteUsername, blackUsername, gameName, game, isActive));
            }
            return ret;
        } catch(DataAccessException | SQLException e) {
            throw new ServiceException(500, e.getMessage());
        }
        //return new ArrayList<>();
    }

    public GameData createGame(String gameName) throws ServiceException{
        var statement = "INSERT INTO game (whiteUsername,blackUsername,gameName,game,isActive) VALUES(?,?,?,?,?)";
        try(var conn = DatabaseManager.getConnection()) {
            var update = conn.prepareStatement(statement);
            update.setString(1, null);
            update.setString(2, null);
            update.setString(3, gameName);
            update.setString(4, new Gson().toJson(new ChessGame(), ChessGame.class));
            update.setBoolean(5, true);
            update.executeUpdate();
            return getGame(gameName);
            //return new GameData(0,"","","", new ChessGame());
        } catch(DataAccessException | SQLException e) {
            throw new ServiceException(500, e.getMessage());
        }
    }
    public GameData getGame(String gameName) throws ServiceException{
        var statement = """
                SELECT gameID,whiteUsername,blackUsername,gameName,game,isActive
                FROM game 
                WHERE gameName=?
                """;
        try(var conn = DatabaseManager.getConnection()) {
            var query = conn.prepareStatement(statement);
            query.setString(1,gameName);
            var result = query.executeQuery();
            if(!result.next()){
                return null;
            }
            String whiteUsername = result.getString("whiteUsername");
            String blackUsername = result.getString("blackUsername");
            int gameID = result.getInt("gameID");
            boolean isActive = result.getBoolean("isActive");
            ChessGame game = new Gson().fromJson(result.getString("game"), ChessGame.class);
            return new GameData(gameID, whiteUsername, blackUsername, gameName, game, isActive);
        } catch(DataAccessException | SQLException e) {
            throw new ServiceException(500, e.getMessage());
        }
        //return new GameData(0,"","","", new ChessGame());
    }
    public GameData getGame(int gameID) throws ServiceException{
        var statement = """
                SELECT gameID,whiteUsername,blackUsername,gameName,game 
                FROM game 
                WHERE gameID=?
                """;
        try(var conn = DatabaseManager.getConnection()) {
            var query = conn.prepareStatement(statement);
            query.setInt(1,gameID);
            var result = query.executeQuery();
            if(!result.next()){
                return null;
            }
            String whiteUsername = result.getString("whiteUsername");
            String blackUsername = result.getString("blackUsername");
            String gameName = result.getString("gameName");
            ChessGame game = new Gson().fromJson(result.getString("game"), ChessGame.class);
            return new GameData(gameID, whiteUsername, blackUsername, gameName, game, true);
        } catch(DataAccessException | SQLException e) {
            throw new ServiceException(500, e.getMessage());
        }
        //return new GameData(0,"","","", new ChessGame());
    }

    public void modifyGame(int gameID, String username, String playerColor) throws ServiceException{
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
            update.setInt(2, gameID);
            update.executeUpdate();
        } catch(DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGame(int gameID, ChessGame game) {
        var statement1 = "UPDATE game SET game=? WHERE gameID=?";
        try(var conn = DatabaseManager.getConnection()) {
            var update = conn.prepareStatement(statement1);

            update.setString(1, new Gson().toJson(game));
            update.setInt(2, gameID);
            update.executeUpdate();
        } catch(DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void endGame(int gameID) {
        var statement1 = "UPDATE game SET isActive=? WHERE gameID=?";
        try(var conn = DatabaseManager.getConnection()) {
            var update = conn.prepareStatement(statement1);

            update.setBoolean(1, false);
            update.setInt(2, gameID);
            update.executeUpdate();
        } catch(DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
