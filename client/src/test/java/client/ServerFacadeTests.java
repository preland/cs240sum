package client;

import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;

import java.util.ArrayList;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;
    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        facade = new ServerFacade(port);
        facade.clear();
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void first() {
        facade.clear();
    }
    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    void loginPositive() {
        facade.register("username", "password", "email");
        Assertions.assertDoesNotThrow(() -> facade.login("username", "password"));
    }
    @Test
    void loginNegative() {
        Assertions.assertEquals("autherror",facade.login("username", "password"));
    }

    @Test
    void registerPositive() {
        Assertions.assertNotEquals("takenerror", facade.register("username", "password", "email"));
    }
    @Test
    void registerNegative() {
        facade.register("username", "password", "email");
        Assertions.assertEquals("403",facade.register("username", "password", "email"));
    }

    @Test
    void logoutPositive() {
        String token = facade.register("username", "password", "email");
        Assertions.assertNotEquals("connerror", facade.logout(token));
    }
    @Test
    void logoutNegative() {
        Assertions.assertEquals("", facade.logout("faketoken"));
    }

    @Test
    void joinGamePositive() {
        String token = facade.register("username", "password", "email");
        String id = facade.createGame("anme", token);
        Assertions.assertNotEquals("connerror", facade.joinGame(1, true, token));
    }
    @Test
    void joinGameNegative() {
        String token = facade.register("username", "password", "email");
        Assertions.assertEquals("400", facade.joinGame(10, true, token));
    }

    @Test
    void listGamesPositive() {
        String token = facade.register("username", "password", "email");
        String id = facade.createGame("anme", token);
        Assertions.assertNotNull(facade.listGames(token));
    }
    @Test
    void listGamesNegative() {
        String token = facade.register("username", "password", "email");
        Assertions.assertEquals(new ArrayList<>(), facade.listGames(token));
    }

    @Test
    void createGamePositive() {
        String token = facade.register("username", "password", "email");
        Assertions.assertEquals("1.0", facade.createGame("name", token));
    }
    @Test
    void createGameNegative() {
        String token = facade.register("username", "password", "email");
        String id = facade.createGame("anme", token);
        Assertions.assertNotEquals("1.0", facade.createGame("name", token));
    }
}
