package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import service.ServiceException;

public class DataAccessTests {
    private static DataAccess da;
    private static AuthData authData;
    @BeforeEach
    public void init() throws ServiceException {
        da = DataAccess.getInstance();
        authData = da.createAuth(da.createUser("username", "password", "email"));
    }
    @AfterEach
    public void fin() throws ServiceException {
        da.clear();
    }
    @Test
    public void clear() {
        Assertions.assertDoesNotThrow(() -> da.clear());
    }
    @Test
    void getUserPositive() {
        Assertions.assertDoesNotThrow(() -> da.getUser("username", "password"));
    }
    @Test
    void getUserNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () -> da.getUser("username", "wrongPassword"));
    }

    @Test
    void createUserPositive() {
        Assertions.assertDoesNotThrow(() -> da.createUser("newUser", "newPassword", "email"));
    }
    @Test
    void createUserNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () -> da.createUser("username", "password", "email"));
    }

    @Test
    void createAuthPositive() {
        Assertions.assertDoesNotThrow(() -> da.createAuth(new UserData("username", "password", "email")));
    }
    @Test
    void createAuthNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () -> da.createAuth(new UserData("username", "wrongPassword", "email")));
    }

    @Test
    void deleteAuthPositive() {
        Assertions.assertDoesNotThrow(() -> da.deleteAuth(authData.authToken()));
    }
    @Test
    void deleteAuthNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () -> da.deleteAuth("fakeAuthToken"));

    }

    @Test
    void getUsernamePositive() {
        Assertions.assertDoesNotThrow(() -> da.getUsername(authData.authToken()));
    }
    @Test
    void getUsernameNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () -> da.getUsername("falseToken"));
    }

    @Test
    void listGamesPositive() throws ServiceException {
        da.createGame("testGame");
        Assertions.assertDoesNotThrow(() -> da.listGames());
    }
    @Test
    void listGamesNegative() throws ServiceException {
        Assertions.assertTrue(da.listGames().isEmpty());
    }

    @Test
    void createGamePositive() {
        Assertions.assertDoesNotThrow(() -> da.createGame("testGame"));
    }
    @Test
    void createGameNegative() throws ServiceException {
        Assertions.assertNotEquals(null, da.createGame("testGame"));
    }

    @Test
    void doesGameExistPositive() throws ServiceException {
        int gameID = da.createGame("testGame").gameID();
        Assertions.assertTrue(da.doesGameExist(gameID));
    }
    @Test
    void doesGameExistNegative() throws ServiceException {
        Assertions.assertFalse(da.doesGameExist(1));
    }

    @Test
    void checkForColorPositive() throws ServiceException {
        int gameID = da.createGame("testGame").gameID();
        da.joinGame("username", "WHITE", gameID);
        Assertions.assertTrue(da.checkForColor(gameID, "WHITE"));
    }
    @Test
    void checkForColorNegative() throws ServiceException {
        int gameID = da.createGame("testGame").gameID();
        da.joinGame("username", "BLACK", gameID);
        Assertions.assertFalse(da.checkForColor(gameID, "WHITE"));
    }

    @Test
    void joinGamePositive() throws ServiceException {
        int gameID = da.createGame("testGame").gameID();
        Assertions.assertDoesNotThrow(() -> da.joinGame("username", "WHITE", gameID));
    }
    @Test
    void joinGameNegative() throws ServiceException {
        int gameID = da.createGame("testGame").gameID();
        da.joinGame("username", "WHITE", gameID);
        String username2 = da.createUser("user2", "password", "email").username();
        Assertions.assertThrowsExactly(ServiceException.class, () -> da.joinGame(username2, "WHITE", gameID));
    }

}
