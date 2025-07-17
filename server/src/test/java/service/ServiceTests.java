package service;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import passoff.model.TestUser;
import server.Server;

public class ServiceTests {
    private static Server server;
    private static Service service;
    private static AuthData firstAuth;
    @AfterAll
    static void fin() {server.stop();}
    @BeforeAll
    public static void init() {
        server = new Server();
        server.run(0);
        service = Service.getInstance();
        //firstUser = new UserData("ExistingUser", "existingUserPassword", "eu@mail.com");
        //server run is used to simulate nominal startup conditions; http connection is not used
    }
    @BeforeEach
    public void setup() throws ServiceException {
        service.clear();

        firstAuth = service.register("ExistingUser", "existingUserPassword", "eu@mail.com");

    }

    @Test
    void clearPositive() {
        Assertions.assertDoesNotThrow(() -> service.clear());
    }
    @Test
    void registerPositive() {
        Assertions.assertDoesNotThrow(() -> service.register("newuser", "newpassword", "asdf@asdf.org"));
    }
    @Test
    void registerNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () ->
                service.register("ExistingUser", "existingUserPassword", "eu@mail.com"), "{ \"message\": \"Error: already taken\" }");
    }

    @Test
    void loginPositive() throws ServiceException{
        service.logout(firstAuth.authToken());
        Assertions.assertDoesNotThrow(() -> service.login("ExistingUser", "existingUserPassword"));
    }
    @Test
    void loginNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () ->
                service.login("ExistingUser","notit"), "{ \"message\": \"Error: bad request\" }");
    }

    @Test
    void logoutPositive() {
        Assertions.assertDoesNotThrow(() -> service.logout(firstAuth.authToken()));
    }
    @Test
    void logoutNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () -> service.logout("notthetoken"), "{ \"message\": \"Error: unauthorized\" }");
    }

    @Test
    void listGamesPositive() {
        Assertions.assertDoesNotThrow(() -> service.listGames(firstAuth.authToken()));
    }
    @Test
    void listGamesNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () -> service.listGames("notthetoken"), "{ \"message\": \"Error: unauthorized\" }");
    }

    @Test
    void createGamePositive() {
        Assertions.assertDoesNotThrow(() -> service.createGame(firstAuth.authToken(), "gameName"));
    }
    @Test
    void createGameNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () ->
                service.createGame("nottehtoken", "doesntmatter"), "{ \"message\": \"Error: unauthorized\" }");
    }

    @Test
    void joinGamePositive() throws ServiceException{
        int gameID = service.createGame(firstAuth.authToken(), "gameName");
        Assertions.assertDoesNotThrow(() -> service.joinGame(firstAuth.authToken(), "WHITE", gameID));
    }
    @Test
    void joinGameNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () ->
                service.joinGame("nottoken", "asdf", -1), "{ \"message\": \"Error: unauthorized\" }");
    }
}
