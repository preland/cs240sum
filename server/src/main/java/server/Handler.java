package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.DataAccess;
import model.GameData;
import service.Service;
import service.ServiceException;

import java.util.ArrayList;

public class Handler {
    private static final Handler instance = new Handler();
    private static Service service;
    private Handler() {
        service = Service.getInstance();
    }
    public static Handler getInstance() {return instance; }
    public void clear() throws ServiceException {
        //call dataaccess thingy to delete everything
        service.clear();
    }
    public String register(String body) throws ServiceException {
        try {
        var serializer = new Gson();
        JsonObject jsonObject = serializer.fromJson(body, JsonObject.class);
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();
        String email = jsonObject.get("email").getAsString();

        return serializer.toJson(service.register(username, password, email));
        } catch (NullPointerException e) {
            throw new ServiceException(400);
        }
    }

    public String login(String body) throws ServiceException {
        try {
        var serializer = new Gson();
        JsonObject jsonObject = serializer.fromJson(body, JsonObject.class);
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();

        return serializer.toJson(service.login(username, password));
        } catch (NullPointerException e) {
            throw new ServiceException(400);
        }
    }

    public void logout(String body) throws ServiceException {
        try {
        var serializer = new Gson();
        JsonObject jsonObject = serializer.fromJson(body, JsonObject.class);
        String authToken = jsonObject.get("authToken").getAsString();
        service.logout(authToken);
        } catch (NullPointerException e) {
            throw new ServiceException(400);
        }
    }

    public ArrayList<GameData> listGames(String body) throws ServiceException {
        try {
        var serializer = new Gson();
        JsonObject jsonObject = serializer.fromJson(body, JsonObject.class);
        String authToken = jsonObject.get("authToken").getAsString();
        return service.listGames(authToken);
        } catch (NullPointerException e) {
            throw new ServiceException(400);
        }
    }

    public int createGame(String body) throws ServiceException {
        try {
        var serializer = new Gson();
        JsonObject jsonObject = serializer.fromJson(body, JsonObject.class);
        String authToken = jsonObject.get("authToken").getAsString();
        String gameName = jsonObject.get("gameName").getAsString();
        return service.createGame(authToken, gameName);
        } catch (NullPointerException e) {
            throw new ServiceException(400);
        }
    }

    public void joinGame(String body) throws ServiceException {
        try {
        var serializer = new Gson();
        JsonObject jsonObject = serializer.fromJson(body, JsonObject.class);
        String authToken = jsonObject.get("authToken").getAsString();
        String playerColor = jsonObject.get("playerColor").getAsString();
        int gameID = jsonObject.get("gameID").getAsInt();
        service.joinGame(authToken, playerColor, gameID);
        } catch (NullPointerException e) {
            throw new ServiceException(400);
        }
    }
}

