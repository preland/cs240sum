package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class MemoryStore {
    private static final MemoryStore instance = new MemoryStore();
    ArrayList<UserData> userData = new ArrayList<>();
    ArrayList<AuthData> authData = new ArrayList<>();
    ArrayList<GameData> gameData = new ArrayList<>();
    private MemoryStore() {}
    public static MemoryStore getInstance() { return instance; }
    public void clear() {
        userData.clear();
        authData.clear();
        gameData.clear();
    }

    public UserData getUser(String username, String password) {
        UserData testData = userData.stream().filter(ud -> username.equals(ud.username())).findFirst().orElse(null);
        if(testData == null) {
            return null;
        }
        //todo: implement non-plaintext password stuff
        if(Objects.equals(testData.password(), password)) {
            return testData;
        } else {
            return null;
        }
    }

    public UserData createUser(String username, String password, String email) {
        UserData newUser = new UserData(username, password, email);
        userData.add(newUser);
        return newUser;
    }
    public AuthData createAuth(String username, String password) {
        AuthData newAuth = new AuthData(generateToken(), username);
        authData.add(newAuth);
        return newAuth;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public boolean deleteAuth(String authToken) {
        AuthData testData = authData.stream().filter(ad -> ad.equals(ad.authToken())).findFirst().orElse(null);
        if(testData == null) {
            return false;
        }
        return authData.remove(testData);
    }
}
