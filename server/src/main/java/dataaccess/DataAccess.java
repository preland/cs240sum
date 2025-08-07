package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import service.ServiceException;

import java.util.ArrayList;
import java.util.Objects;

public class DataAccess {
    private DatabaseStore storage;
    private static final DataAccess INSTANCE = new DataAccess();
    private DataAccess(){
        try {
            DatabaseManager.createDatabase();
        } catch (DataAccessException e) {
            //throw new ServiceException(500);
            throw new RuntimeException("createDatabase failed; time to stop" + e.getMessage());
        }
        this.storage = DatabaseStore.getInstance();
        //above should be trivially switchable from memory to sql
    }
    public static DataAccess getInstance() {return INSTANCE;}
    public void clear() throws ServiceException{
        //get static reference to memorystore and use it to run function
        storage.clear();
    }

    public UserData getUser(String username, String password) throws ServiceException{
        //todo:actually implement
        //return new UserData(username, password, "testos");
        return storage.getUser(username, password);

    }

    public UserData createUser(String username, String password, String email) throws ServiceException{
        //todo:actually implement
        //return new UserData(username, password, email);
        return storage.createUser(username, password, email);
    }

    public AuthData createAuth(UserData userData) throws ServiceException{
        //todo:actually implement
        //return "{}";
        String username = userData.username();
        String password = userData.password();
        return storage.createAuth(username, password);
    }

    public boolean deleteAuth(String authToken) throws ServiceException{
        return storage.deleteAuth(authToken);
    }

    public String getUsername(String authToken) throws ServiceException{
        return storage.getUsername(authToken);
    }

    public ArrayList<GameData> listGames() throws ServiceException{
        return storage.listGames();
    }

    public GameData createGame(String gameName) throws ServiceException{
        return storage.createGame(gameName);
    }

    public boolean doesGameExist(int gameID) throws ServiceException{
        return storage.getGame(gameID) != null;
    }

    public boolean checkForColor(int gameID, String playerColor) throws ServiceException {
        if(Objects.equals(playerColor, "WHITE")) {
            return storage.getGame(gameID).whiteUsername() == null;
        }
        else {
            return storage.getGame(gameID).blackUsername() == null;
        }
    }

    public void joinGame(String username, String playerColor, int gameID) throws ServiceException {
        storage.modifyGame(gameID, username, playerColor);
    }
    public void updateGame(int gameID, ChessGame game) {
        storage.updateGame(gameID, game);
    }
    public void endGame(int gameID) {
        storage.endGame(gameID);
    }
}
