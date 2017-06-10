package secured;

import auth.Authentication;
import config.Config;
import io.jsonwebtoken.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by AndersWOlsen on 10-06-2017.
 */
@AppConfig.Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Get HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("hold12")) {
            throw new NotAuthorizedException("Authorization header must be provided.");
        }

        String token = authorizationHeader.substring("hold12".length()).trim();

        try {
            validateToken(token);
        } catch (Exception e) {
            // TODO: Implement something here. Also, catch another exception than just Exception
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    public static Claims validateToken(String tokenString) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return Jwts.parser().setSigningKey(Config.AUTH_KEY).parseClaimsJws(tokenString).getBody();
    }
}
