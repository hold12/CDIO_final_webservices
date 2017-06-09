package dao;

import auth.Authentication;
import dto.UserDTO;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.Null;
import javax.ws.rs.core.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

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
        final UserDTO expectedUser = new UserDTO(2, "Antonella", "B", "AB", "atoJ21v", true);
        final IUserDAO userDAO = new UserDAO(db);
        UserDTO actualUser = null;

        actualUser = userDAO.getUser(expectedUser.getUserId());

        assertTrue(expectedUser.getUserId() == actualUser.getUserId());
        assertTrue(expectedUser.getFirstname().equals(actualUser.getFirstname()));
        assertTrue(expectedUser.getLastname().equals(actualUser.getLastname()));
        assertTrue(expectedUser.getInitials().equals(actualUser.getInitials()));
        assertTrue(expectedUser.getPassword().equals(actualUser.getPassword()));
        assertTrue(expectedUser.isActive() == actualUser.isActive());
    }

    @Test
    public void logIn_ExistingActiveUser_Returns200() throws Exception {
        final int expectedStatusCode = 200;
        final UserDTO validUser = new UserDTO(1, "", "", "", "root", true);
        final Authentication auth = new Authentication();
        int actualStatusCode = 0;

        actualStatusCode = auth.login(validUser).getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    public void logIn_ArbitraryInvalidUser_Return401() throws Exception {
        final int expectedStatusCode = 401;
        final UserDTO invalidUser = new UserDTO(10000, "", "", "", "qwerty", true);
        final Authentication auth = new Authentication();
        int actualStatusCode = 0;

        actualStatusCode = auth.login(invalidUser).getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    public void logIn_ExistingInactiveUser_Returns401() throws Exception {
        final int expectedStatusCode = 401;
        final UserDTO inactiveUser = new UserDTO(3, "", "", "", "jEfm5aQ", false);
        final Authentication auth = new Authentication();
        int actualStatusCode = 0;

        actualStatusCode = auth.login(inactiveUser).getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    public void logIn_ExisistingActiveUser_IncorrectPassword_Returns401() throws Exception {
        final int expectedStatusCode = 401;
        final UserDTO user = new UserDTO(1, "", "", "", "wrong_password", true);
        final Authentication auth = new Authentication();
        int actualStatusCode = 0;

        actualStatusCode = auth.login(user).getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    public void logIn_ExistingInactiveUser_IncorrectPassword_Returns401() throws Exception {
        final int expectedStatusCode = 401;
        final UserDTO user = new UserDTO(3, "", "", "", "wrong_password", false);
        final Authentication auth = new Authentication();
        int actualStatusCode = 0;

        actualStatusCode = auth.login(user).getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    public void logIn_ArbitraryInvalidUser_CorrectPassword_Returns401() throws Exception {
        final int expectedStatusCode = 401;
        final UserDTO user = new UserDTO(1000, "", "", "", "password", true);
        final Authentication auth = new Authentication();
        int actualStatusCode = 0;

        actualStatusCode = auth.login(user).getStatus();

        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    public void getUser_ExistingUser_ReturnsUser() {
        final UserDTO expectedUser = new UserDTO(1, "admin", null, "adm", "root", true);
        final Authentication auth = new Authentication();
        UserDTO actualUser = null;

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
        final UserDTO expectedUser = null;
        final Authentication auth = new Authentication();
        UserDTO actualUser = null;

        actualUser = auth.getUser(1000);

        assertEquals(actualUser, expectedUser);
    }
}