package dataaccess;

import model.UserData;
import org.eclipse.jetty.server.Authentication;

public class DataAccess {
    private static final DataAccess instance = new DataAccess();
    private DataAccess() {}
    public static DataAccess getInstance() {return instance;}
    public void clear() {
        //get static reference to memorystore and use it to run function
        MemoryStore.getInstance().clear();
    }

    public UserData getUser(String username, String password) {
        //todo:actually implement
        return new UserData(username, password, "testos");
    }

    public UserData createUser(String username, String password, String email) {
        //todo:actually implement
        return new UserData(username, password, email);
    }

    public String createAuth(UserData userData) {
        //todo:actually implement
        return "{}";
    }
}
