package dataaccess;

public class DataAccess {
    private static final DataAccess instance = new DataAccess();
    private DataAccess() {}
    public static DataAccess getInstance() {return instance;}
    public void clear() {
        //get static reference to memorystore and use it to run function
        MemoryStore.getInstance().clear();
    }
}
