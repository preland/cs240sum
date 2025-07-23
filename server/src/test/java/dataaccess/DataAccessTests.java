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
    }

    @Test
    void createUserPositive() {
        Assertions.assertDoesNotThrow(() -> da.clear());
    }
    @Test
    void createUserNegative() {
    }

    @Test
    void createAuthPositive() {
        Assertions.assertDoesNotThrow(() -> da.clear());
    }
    @Test
    void createAuthNegative() {
    }

    @Test
    void deleteAuthPositive() {
        Assertions.assertDoesNotThrow(() -> da.clear());
    }
    @Test
    void deleteAuthNegative() {
    }

    @Test
    void getUsernamePositive() {
        Assertions.assertDoesNotThrow(() -> da.clear());
    }
    @Test
    void getUsernameNegative() {
    }

    @Test
    void listGamesPositive() {
        Assertions.assertDoesNotThrow(() -> da.clear());
    }
    @Test
    void listGamesNegative() {
    }

    @Test
    void createGamePositive() {
        Assertions.assertDoesNotThrow(() -> da.clear());
    }
    @Test
    void createGameNegative() {
    }

    @Test
    void doesGameExistPositive() {
        Assertions.assertDoesNotThrow(() -> da.clear());
    }
    @Test
    void doesGameExistNegative() {
    }

    @Test
    void checkForColorPositive() {
        Assertions.assertDoesNotThrow(() -> da.clear());
    }
    @Test
    void checkForColorNegative() {
    }

    @Test
    void joinGamePositive() {
        Assertions.assertDoesNotThrow(() -> da.clear());
    }
    @Test
    void joinGameNegative() {
    }

}
