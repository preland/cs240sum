package dataaccess;

import org.junit.jupiter.api.*;
import service.ServiceException;

public class DataAccessTests {
    private static DataAccess da;
    @BeforeEach
    public void init(){
        da = DataAccess.getInstance();
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
    }
    @Test
    void getUserNegative() {
    }

    @Test
    void createUserPositive() {
    }
    @Test
    void createUserNegative() {
    }

    @Test
    void createAuthPositive() {
    }
    @Test
    void createAuthNegative() {
    }

    @Test
    void deleteAuthPositive() {
    }
    @Test
    void deleteAuthNegative() {
    }

    @Test
    void getUsernamePositive() {
    }
    @Test
    void getUsernameNegative() {
    }

    @Test
    void listGamesPositive() {
    }
    @Test
    void listGamesNegative() {
    }

    @Test
    void createGamePositive() {
    }
    @Test
    void createGameNegative() {
    }

    @Test
    void doesGameExistPositive() {
    }
    @Test
    void doesGameExistNegative() {
    }

    @Test
    void checkForColorPositive() {
    }
    @Test
    void checkForColorNegative() {
    }

    @Test
    void joinGamePositive() {
    }
    @Test
    void joinGameNegative() {
    }

}
