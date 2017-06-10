package auth;

import dao.IUserDAO;
import dao.UserDAO;
import dto.User;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import org.glassfish.jersey.internal.util.Base64;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by awo on 08/06/17.
 */
public class AuthenticationFilterFirst implements javax.ws.rs.container.ContainerRequestFilter {
    private ResourceInfo resourceInfo;

    private final String AUTHORIZATION_PROPERTY = "Authorization";
    private final String AUTHENTICATION_SCHEME = "Basic";
    private final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED).entity("You cannot access this resource!").build();
    private final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).entity("Access blocked for all users!").build();

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        // http://howtodoinjava.com/jersey/jersey-rest-security/
        Method method = resourceInfo.getResourceMethod();

        // Access allowed for all
        if (!method.isAnnotationPresent(PermitAll.class)) {
            // Access is denied for all
            if (method.isAnnotationPresent(DenyAll.class)) {
                containerRequestContext.abortWith(ACCESS_FORBIDDEN);
                return;
            }

            // Get request headers
            final MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();

            // Fetch authorization header
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

            // If not authorized, block access
            if (authorization == null || authorization.isEmpty()) {
                containerRequestContext.abortWith(ACCESS_DENIED);
                return;
            }

            // Get encoded userId and password
            final String encodedUserAndPass = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

            // Decode userId and password
            final String userIDAndPassword = new String(Base64.decode(encodedUserAndPass.getBytes()));

            // Split UserId and tokens
            final StringTokenizer tokenizer = new StringTokenizer(userIDAndPassword, ":");
            final int userId = Integer.parseInt(tokenizer.nextToken());
            final String password = tokenizer.nextToken();

            // For verifying userId and password
            // TODO: Below is only for debugging - remove when needed
            System.out.println(userId);
            System.out.println(password);

            // Verify User Access
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

                // Is the user valid?
                if (!isUserAllowed(userId, password, rolesSet)) {
                    containerRequestContext.abortWith(ACCESS_DENIED);
                    return;
                }
            }
        }
    }

    private boolean isUserAllowed(final int userId, final String password, final Set<String> rolesSet) {
        // TODO: Change this so it uses permissions instead of roles

        IUserDAO userDAO = null;
        User dbUser = null;

        try {
            userDAO = new UserDAO(new DBConnector(new DatabaseConnection()));
            dbUser = userDAO.getUser(userId);

        } catch (IOException e) {
            System.err.println("Error while connecting to database. Error message: " + e.getMessage());
            return false; // TODO: Thow an exception
        }  catch (DALException e) {
            System.err.println("Failed to fetch user from database. Error message: " + e.getMessage());
            return false; // TODO: Thow an exception
        }


        if (userId == dbUser.getUserId() && password.equals(dbUser.getPassword())) {
            String userRole = "Admin";

            if (rolesSet.contains(userRole)) {
                return true;
            }
        }
        return false;
    }
}
