package service;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import passoff.model.TestUser;
import server.Server;

public class ServiceTests {
    private static Server server;
    private static Service service;
    private static UserData firstUser;
    private static AuthData firstAuth;
    @AfterAll
    static void fin() {server.stop();}
    @BeforeAll
    public static void init() {
        server = new Server();
        server.run(0);
        service = Service.getInstance();
        firstUser = new UserData("ExistingUser", "existingUserPassword", "eu@mail.com");
        //server run is used to simulate nominal startup conditions; http connection is not used
    }
    @BeforeEach
    public void setup() throws ServiceException {
        service.clear();

        firstAuth = service.register("ExistingUser", "existingUserPassword", "eu@mail.com");

    }
}
