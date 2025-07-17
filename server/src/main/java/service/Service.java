package service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.DataAccess;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.Objects;

public class Service {
    private static final Service instance = new Service();
    private static DataAccess dataAccess;
    private Service() {
        dataAccess = DataAccess.getInstance();
    }
    public static Service getInstance() {return instance; }
    public void clear() throws ServiceException {
        //call dataaccess thingy to delete everything
        dataAccess.clear();
    }
    public AuthData register(String username, String password, String email) throws ServiceException {
        if(dataAccess.getUser(username, password) != null) {
            throw new ServiceException(403);
        }
        //todo: 400 error handling
        UserData userData = dataAccess.createUser(username, password, email);
        return dataAccess.createAuth(userData);
    }

    public AuthData login(String username, String password) throws ServiceException {
        UserData userData = dataAccess.getUser(username, password);
        if(userData == null) {
            throw new ServiceException(401);
        }
        /*if(!dataAccess.isLoggedIn(userData.username())) {
            throw new ServiceException(401);
        }*/
        return dataAccess.createAuth(userData);
    }

    public void logout(String authToken) throws ServiceException {
        if(!dataAccess.deleteAuth(authToken)) {
            throw new ServiceException(401);
        }
    }

    public ArrayList<GameData> listGames(String authToken) throws ServiceException {
        String username = dataAccess.getUsername(authToken);
        if(username == null) {
            throw new ServiceException(401);
        }
        return dataAccess.listGames();
    }

    public int createGame(String authToken, String gameName) throws ServiceException {
        String username = dataAccess.getUsername(authToken);
        if(username == null) {
            throw new ServiceException(401);
        }
        //todo: implement 400 code
        return dataAccess.createGame(gameName).gameID();
    }

    public void joinGame(String authToken, String playerColor, int gameID) throws ServiceException {
        String username = dataAccess.getUsername(authToken);
        if(username == null) {
            throw new ServiceException(401);
        }
        if(!dataAccess.doesGameExist(gameID)) {
            throw new ServiceException(400);
        }
        if(!Objects.equals(playerColor, "WHITE") && !Objects.equals(playerColor, "BLACK")) {
            throw new ServiceException(400);
        }
        if(!dataAccess.checkForColor(gameID, playerColor)) {
            throw new ServiceException(403);
        }
        dataAccess.joinGame(username, playerColor, gameID);
    }
}
