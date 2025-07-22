package dataaccess;

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
            throw new RuntimeException("createDatabase failed; time to stop");
        }
        this.storage = DatabaseStore.getInstance();
        //above should be trivially switchable from memory to sql
    }
    public static DataAccess getInstance() {return INSTANCE;}
    public void clear() {
        //get static reference to memorystore and use it to run function
        storage.clear();
    }

    public UserData getUser(String username, String password) {
        //todo:actually implement
        //return new UserData(username, password, "testos");
        return storage.getUser(username, password);

    }

    public UserData createUser(String username, String password, String email) {
        //todo:actually implement
        //return new UserData(username, password, email);
        return storage.createUser(username, password, email);
    }

    public AuthData createAuth(UserData userData) {
        //todo:actually implement
        //return "{}";
        String username = userData.username();
        String password = userData.password();
        return storage.createAuth(username, password);
    }

    public boolean deleteAuth(String authToken) {
        return storage.deleteAuth(authToken);
    }

    public String getUsername(String authToken) {
        return storage.getUsername(authToken);
    }

    public ArrayList<GameData> listGames() {
        return storage.listGames();
    }

    public GameData createGame(String gameName) {
        return storage.createGame(gameName);
    }

    public boolean doesGameExist(int gameID) {
        return storage.getGame(gameID) != null;
    }

    public boolean checkForColor(int gameID, String playerColor) {
        if(Objects.equals(playerColor, "WHITE")) {
            return storage.getGame(gameID).whiteUsername() == null;
        }
        else {
            return storage.getGame(gameID).blackUsername() == null;
        }
    }

    public void joinGame(String username, String playerColor, int gameID) {
        storage.modifyGame(gameID, username, playerColor);
    }
}
