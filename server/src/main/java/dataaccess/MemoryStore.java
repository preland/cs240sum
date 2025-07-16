package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;

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
}
