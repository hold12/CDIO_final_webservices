package auth;

import config.Config;
import dao.IUserDAO;
import dao.UserDAO;
import dto.User;
import io.jsonwebtoken.*;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by AndersWOlsen on 10-06-2017.
 */
@AuthenticationEndpoint.Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        IConnector db = null;
        try {
            db = new DBConnector(new DatabaseConnection());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        final IUserDAO userDAO = new UserDAO(db);

        // Get HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            System.err.println("Not authorized!");
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            validateToken(token);
        } catch(Exception e) { // TODO: Better exception handling here
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build()
            );
        }

        // Identifying users
        final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return new Principal() {
                    @Override
                    public String getName() {
                        User authenticatedUser = null;
                        try {
                            authenticatedUser = userDAO.getUser(authorizationHeader.substring("Bearer".length()).trim());
                        } catch (DALException e) { /* TODO: Handle this exception */ }
                        return Integer.toString(authenticatedUser.getUserId());
                    }
                };
            }

            @Override
            public boolean isUserInRole(String s) {
                return false; // TODO: check if the user has the role 's'
            }

            @Override
            public boolean isSecure() {
                return currentSecurityContext.isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return "Bearer";
            }
        });
    }

    void validateToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException { // TODO: Maybe test some more exceptions
        // Check if it was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid

        Jwts.parser().setSigningKey(Config.AUTH_KEY).parseClaimsJws(token).getBody();
    }
}
