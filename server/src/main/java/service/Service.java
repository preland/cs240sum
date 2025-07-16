package service;

import dataaccess.DataAccess;

public class Service {
    private static final Service instance = new Service();
    private Service() {}
    public static Service getInstance() {return instance; }
    public void clear() throws ServiceException {
        //call dataaccess thingy to delete everything
        DataAccess.getInstance().clear();
    }
}
