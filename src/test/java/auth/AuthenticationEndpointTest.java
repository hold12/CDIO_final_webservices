package auth;

import config.Config;
import dao.IUserDAO;
import dao.UserDAO;
import dto.Credentials;
import dto.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by AndersWOlsen on 11-06-2017.
 */
public class AuthenticationEndpointTest extends AuthenticationEndpoint {
    private final Credentials validCredentials = new Credentials(1, "root");
    private final Credentials invalidCredentials = new Credentials(10000, "incorrect_password");
//    private final Credentials validCredentials_IncorrectPassword = new Credentials(1, "incorrect_password");

    private final int RESPONSE_OK = Response.Status.OK.getStatusCode();
    private final int RESPONSE_UNAUTHORIZED = Response.Status.UNAUTHORIZED.getStatusCode();

    @Test
    public void canGetToken() throws Exception {
        final String expectedIssuer = "hold12";
        final Date now = new Date();

        String token = issueToken(validCredentials.getUserId());
        Claims claims = Jwts.parser().setSigningKey(Config.AUTH_KEY).parseClaimsJws(token).getBody();

        assertEquals(expectedIssuer, claims.getIssuer());
        assertTrue(now.getTime() < claims.getExpiration().getTime());
        assertTrue(Jwts.parser().isSigned(token));
    }

    @Test
    public void validUser_Active_CanAuthenticate() throws Exception {
        final int actualReponseCode = authenticateUser(validCredentials).getStatus();

        assertEquals(RESPONSE_OK, actualReponseCode);
    }

    @Test
    public void invalidUser_CannotAuthenticate() throws Exception {
        final int actualReponseCode = authenticateUser(invalidCredentials).getStatus();

        assertEquals(RESPONSE_UNAUTHORIZED, actualReponseCode);
    }

    @Test
    public void validUser_Inactive_CannotAuthenticate() throws Exception {
        final Credentials validCredentials_Inactive = new Credentials(3, "jEfm5aQ");

        final int actualReponseCode = authenticateUser(validCredentials_Inactive).getStatus();

        assertEquals(RESPONSE_UNAUTHORIZED, actualReponseCode);
    }

    @Test
    public void validUser_Active_IncorrectPassword() throws Exception {
        final Credentials validCredentials_IncorrectPassword = new Credentials(1, "incorrect_password");

        final int actualReponseCode = authenticateUser(validCredentials_IncorrectPassword).getStatus();

        assertEquals(RESPONSE_UNAUTHORIZED, actualReponseCode);
    }

    // TODO: Add in report: the authenticate method only tests validCredentials and invaludCredentials because all other cases are covered in authenticateUserTests (above)
    @Test
    public void validUser_CanAuthenticate() throws Exception {
        authenticate(validCredentials);
    }

    @Test(expected = Exception.class)
    public void invaludUser_CannotAuthenticate() throws Exception {
        authenticate(invalidCredentials);
    }

    @Test
    public void readUserFromToken() throws Exception {
        final IConnector db = new DBConnector(new DatabaseConnection());
        final IUserDAO userDAO = new UserDAO(db);
        final User EXPECTED_USER = userDAO.getUser(validCredentials.getUserId());

        final String token = issueToken(EXPECTED_USER.getUserId());
        final User ACTUAL_USER = userDAO.getUser(token);

        assertEquals(EXPECTED_USER.getUserId(), ACTUAL_USER.getUserId());
        assertEquals(EXPECTED_USER.getFirstname(), ACTUAL_USER.getFirstname());
        assertTrue(EXPECTED_USER.getLastname() == ACTUAL_USER.getLastname());
        assertEquals(EXPECTED_USER.getInitials(), ACTUAL_USER.getInitials());
        assertEquals("[hidden]", ACTUAL_USER.getPassword());

//        assertEquals(EXPECTED_USER, ACTUAL_USER);
    }
}