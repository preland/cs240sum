package dataaccess;

public class DataAccess {

    public void clear() {
        //get static reference to memorystore and use it to run function
        MemoryStore.getInstance().clear();
    }
}
