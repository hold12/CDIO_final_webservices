package dao;

import auth.Authentication;
import dto.Credentials;
import dto.User;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by awo on 09/06/17.
 */
public class UserDatabaseTest {
    private IConnector db = null;

    @Before
    public void setup() throws Exception {
        db = new DBConnector(new DatabaseConnection());
    }

    @Test
    public void database_CanFetchUsers() throws Exception {
        final User expectedUser = new User(2, "Antonella", "B", "AB", "atoJ21v", true);
        final IUserDAO userDAO = new UserDAO(db);
        User actualUser = null;

        actualUser = userDAO.getUser(expectedUser.getUserId());

        assertTrue(expectedUser.getUserId() == actualUser.getUserId());
        assertTrue(expectedUser.getFirstname().equals(actualUser.getFirstname()));
        assertTrue(expectedUser.getLastname().equals(actualUser.getLastname()));
        assertTrue(expectedUser.getInitials().equals(actualUser.getInitials()));
        assertTrue(expectedUser.getPassword().equals(actualUser.getPassword()));
        assertTrue(expectedUser.isActive() == actualUser.isActive());
    }

    @Test
    public void getUser_ExistingUser_ReturnsUser() {
        final User expectedUser = new User(1, "admin", null, "adm", "root", true);
        final Authentication auth = new Authentication();
        User actualUser = null;

        actualUser = auth.getUser(expectedUser.getUserId());

        assertTrue(expectedUser.getUserId() == actualUser.getUserId());
        assertTrue(expectedUser.getFirstname().equals(actualUser.getFirstname()));
        assertTrue(expectedUser.getLastname() == actualUser.getLastname());
        assertTrue(expectedUser.getInitials().equals(actualUser.getInitials()));
        assertTrue(expectedUser.getPassword().equals(actualUser.getPassword()));
        assertTrue(expectedUser.isActive() == actualUser.isActive());
    }

    @Test
    public void getUser_ArbitraryInvalidUser_ReturnsNull() {
        final User expectedUser = null;
        final Authentication auth = new Authentication();
        User actualUser = null;

        actualUser = auth.getUser(1000);

        assertEquals(actualUser, expectedUser);
    }
}