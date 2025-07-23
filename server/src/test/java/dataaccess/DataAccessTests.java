package dataaccess;

import model.UserData;
import org.junit.jupiter.api.*;
import service.ServiceException;

public class DataAccessTests {
    private static DataAccess da;
    private String password;
    @BeforeEach
    public void init(){
        da = DataAccess.getInstance();
    }
    @AfterEach
    public void fin() throws ServiceException {
        da.clear();
        UserData userData = da.createUser("username", "password", "email");
        password = userData.password();
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
        Assertions.assertDoesNotThrow(() -> da.clear());
    }
    @Test
    void createAuthNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () -> da.getUser("username", "wrongPassword"));

    }

    @Test
    void deleteAuthPositive() {
        Assertions.assertDoesNotThrow(() -> da.clear());
    }
    @Test
    void deleteAuthNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () -> da.getUser("username", "wrongPassword"));

    }

    @Test
    void getUsernamePositive() {
        Assertions.assertDoesNotThrow(() -> da.clear());
    }
    @Test
    void getUsernameNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () -> da.getUser("username", "wrongPassword"));

    }

    @Test
    void listGamesPositive() {
        Assertions.assertDoesNotThrow(() -> da.clear());
    }
    @Test
    void listGamesNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () -> da.getUser("username", "wrongPassword"));

    }

    @Test
    void createGamePositive() {
        Assertions.assertDoesNotThrow(() -> da.clear());
    }
    @Test
    void createGameNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () -> da.getUser("username", "wrongPassword"));

    }

    @Test
    void doesGameExistPositive() {
        Assertions.assertDoesNotThrow(() -> da.clear());
    }
    @Test
    void doesGameExistNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () -> da.getUser("username", "wrongPassword"));

    }

    @Test
    void checkForColorPositive() {
        Assertions.assertDoesNotThrow(() -> da.clear());
    }
    @Test
    void checkForColorNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () -> da.getUser("username", "wrongPassword"));

    }

    @Test
    void joinGamePositive() {
        Assertions.assertDoesNotThrow(() -> da.clear());
    }
    @Test
    void joinGameNegative() {
        Assertions.assertThrowsExactly(ServiceException.class, () -> da.getUser("username", "wrongPassword"));
    }

}
