package auth;

import dto.Credentials;
import io.jsonwebtoken.SignatureException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by AndersWOlsen on 11-06-2017.
 */
public class AuthenticationFilterTest extends AuthenticationFilter {
    private final Credentials validCredentials = new Credentials(1, "root");
    private final Credentials invalidCredentials = new Credentials(10000, "incorrect_password");

    @Test
    public void validateValidToken_NoExceptions() throws Exception {
        final AuthenticationEndpoint auth = new AuthenticationEndpoint();
        final String token = auth.issueToken(validCredentials.getUserId());

        validateToken(token);
    }

    @Test(expected = SignatureException.class)
    public void modifiedtoken_Exception() throws Exception {
        final AuthenticationEndpoint auth = new AuthenticationEndpoint();
        String token = auth.issueToken(validCredentials.getUserId());

        token += "_added-text";

        validateToken(token);
    }

    // TODO: Maybe add some more test cases to cover everything
}