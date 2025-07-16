package service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.DataAccess;
import model.UserData;

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
        UserData userData = dataAccess.getUser(username, password);
        //todo: error handling for userData stuffs
        userData = dataAccess.createUser(username, password, email);
        return dataAccess.createAuth(userData);
    }

}
