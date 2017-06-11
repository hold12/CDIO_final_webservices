package dao;

import dto.User;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by AndersWOlsen on 11-06-2017.
 */
public class IUserDAOTest {
    @Test
    public void getValidUser() throws Exception {
        final IUserDAO userDAO = new TestUserDAO();
        final User expectedUser = new User(1, "John", "Doe", "JD", "password", true);

        final User actualUser = userDAO.getUser(expectedUser.getUserId());

        assertTrue(expectedUser.getUserId() == actualUser.getUserId());
        assertTrue(expectedUser.getFirstname().equals(actualUser.getFirstname()));
        assertTrue(expectedUser.getLastname().equals(actualUser.getLastname()));
        assertTrue(expectedUser.getInitials().equals(actualUser.getInitials()));
        assertTrue(expectedUser.getPassword().equals(actualUser.getPassword()));
        assertTrue(expectedUser.isActive() == actualUser.isActive());
    }

    @Test
    public void getInvalidUser() throws Exception {
        final IUserDAO userDAO = new TestUserDAO();

        final User actualUser = userDAO.getUser(0);

        assertNull(actualUser);
    }
}