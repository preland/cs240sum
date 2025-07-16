package dataaccess;

import model.AuthData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

public class DataAccess {
    private MemoryStore storage;
    private static final DataAccess instance = new DataAccess();
    private DataAccess() {
        this.storage = MemoryStore.getInstance();
        //above should be trivially switchable from memory to sql
    }
    public static DataAccess getInstance() {return instance;}
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
}
