package service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.DataAccess;

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
        var serializer = new Gson();
        JsonObject jsonObject = serializer.fromJson(body, JsonObject.class);
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();
        String email = jsonObject.get("email").getAsString();

        return service.register(username, password, email);
    }
}

