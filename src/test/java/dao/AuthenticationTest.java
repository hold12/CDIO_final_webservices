package dao;

import auth.AppConfig;
import auth.Authentication;
import dto.Credentials;
import dto.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Hold 12 - Anders Wiberg Olsen
 */
public class AuthenticationTest {
    private final Authentication auth = new Authentication();

    @Test
    public void auth_CanGetToken() throws Exception {
//        final User user = new User(1, "John", "Doe", "JD", "password", true);
        final Credentials credentials = new Credentials(1, "root");
        final String expectedIssuer = "hold12";
        final Date currentDate = new Date();

        String actualToken = auth.issueToken(credentials);
        Claims claims = Jwts.parser().setSigningKey(AppConfig.AUTH_KEY).parseClaimsJws(actualToken).getBody();

        assertEquals(expectedIssuer, claims.getIssuer());
        assertTrue(currentDate.getTime() < claims.getExpiration().getTime());
        assertTrue(Jwts.parser().isSigned(actualToken));
    }

    @Test
    public void auth_ValidUser_Active_CanAuthenticate() throws Exception {
        // Testing user admin//root from db
        final Credentials credentials = new Credentials(1, "root");
        final Response.Status expectedReponseCode = Response.Status.OK;

        final int actualResponseCode = auth.authenticateUser(credentials).getStatus();

        assertEquals(expectedReponseCode.getStatusCode(), actualResponseCode);
    }

    @Test
    public void auth_InvalidUser_CannotAuthenticate() throws Exception {
        final Credentials credentials = new Credentials(10000, "qwerty");
        final Response.Status expectedResponseCode = Response.Status.UNAUTHORIZED;

        final int actualResponseCode = auth.authenticateUser(credentials).getStatus();

        assertEquals(expectedResponseCode.getStatusCode(), actualResponseCode);
    }

    @Test
    public void auth_ValidUser_Inactive_CannotAuthenticate() throws Exception {
        final Credentials credentials = new Credentials(3, "jEfm5aQ");
        final Response.Status expectedResponseCode = Response.Status.UNAUTHORIZED;

        final int actualResponseCode = auth.authenticateUser(credentials).getStatus();

        assertEquals(expectedResponseCode.getStatusCode(), actualResponseCode);
    }

    @Test
    public void auth_ValidUser_Active_IncorrectPassword_CannotAuthenticate() throws Exception {
        final Credentials credentials = new Credentials(1, "incorrect_password");
        final Response.Status expectedReponseCode = Response.Status.UNAUTHORIZED;

        final int actualResponseCode = auth.authenticateUser(credentials).getStatus();

        assertEquals(expectedReponseCode.getStatusCode(), actualResponseCode);
    }

    @Test
    public void validate_ValidUser_Active_ReturnsTrue() throws Exception {
        // Testing user admin//root in database (default test user)
        final Credentials credentials = new Credentials(1, "root");
        final boolean expectedReturnValue = true;

        final boolean actualReturnValue = auth.authenticate(credentials);

        assertEquals(expectedReturnValue, actualReturnValue);
    }

    @Test
    public void validate_InvalidUser_ReturnsFalse() throws Exception {
        // Testing an arbitrary non-existent user
        final Credentials credentials = new Credentials(10000, "qwerty");
        final boolean expectedReturnValue = false;

        final boolean actualReturnValue = auth.authenticate(credentials);

        assertEquals(expectedReturnValue, actualReturnValue);
    }

    @Test
    public void validate_ValidUser_Inactive_ReturnsFalse() throws Exception {
        // Testing user Luigi//jEfm5aQ
        final Credentials credentials = new Credentials(3, "jEfm5aQ");
        final boolean expectedReturnValue = false;

        final boolean actualReturnValue = auth.authenticate(credentials);

        assertEquals(expectedReturnValue, actualReturnValue);
    }

    @Test
    public void validate_ValidUser_Active_IncorrectPassword_ReturnsFalse() throws Exception {
        // Testing user admin//root (with incorrect password)
        final Credentials credentials = new Credentials(1, "incorrect_password");
        final boolean expectedReturnValue = false;

        final boolean actualReturnValue = auth.authenticate(credentials);

        assertEquals(expectedReturnValue, actualReturnValue);
    }

    @Test
    public void validate_ValidUser_Inactive_IncorrectPassword_ReturnsFalse() throws Exception {
        // Testing user Luigi//jEfm5aQ (with incorrect password)
        final Credentials credentials = new Credentials(3, "incorrect_password");
        final boolean expectedReturnValue = false;

        final boolean actualReturnValue = auth.authenticate(credentials);

        assertEquals(expectedReturnValue, actualReturnValue);
    }

    @Test
    public void validate_InvalidUser_CorrectPassword_Returns_False() throws Exception {
        // Testing arbitrary user but with a password that matches admin's password
        final Credentials credentials = new Credentials(10000, "root");
        final boolean expectedReturnValue = false;

        final boolean actualReturnValue = auth.authenticate(credentials);

        assertEquals(expectedReturnValue, actualReturnValue);
    }
}
