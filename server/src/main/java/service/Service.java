package service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.DataAccess;
import model.GameData;
import model.UserData;

import java.util.ArrayList;

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
    public String register(String username, String password, String email) throws ServiceException {
        if(dataAccess.getUser(username, password) != null) {
            throw new ServiceException("username already taken");
        }
        //todo: error handling for userData stuffs
        UserData userData = dataAccess.createUser(username, password, email);
        return dataAccess.createAuth(userData);
    }

    public String login(String username, String password) throws ServiceException {
        UserData userData = dataAccess.getUser(username, password);
        if(userData == null) {
            throw new ServiceException("user doesn't exist");
        }
        return dataAccess.createAuth(userData);
    }

    public void logout(String authToken) throws ServiceException {
        if(!dataAccess.deleteAuth(authToken)) {
            throw new ServiceException("unauthorized");
        }
    }

    public ArrayList<GameData> listGames(String authToken) throws ServiceException {
        String username = dataAccess.getUsername(authToken);
        if(username == null) {
            throw new ServiceException("unauthorized");
        }
    }
}
