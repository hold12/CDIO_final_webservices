package modules;

import dao.IUserDAO;
import dao.UserDAO;
import dto.User;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.IConnector;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.sql.SQLException;

/**
 * Created by AndersWOlsen on 11-06-2017.
 */
@Path("home")
public class HomeModule {
    @Context ServletRequest servletRequest;

    @Path("getLoggedUser")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User getLoggedUser(@Context SecurityContext securityContext) {
        final Principal principal = securityContext.getUserPrincipal();
        HttpServletRequest h = (HttpServletRequest) servletRequest;
//        h.getHeader()

        int userId = 0;
        try {
            userId = Integer.parseInt(principal.getName());

            final IConnector db = new DBConnector();
            final IUserDAO userDAO = new UserDAO(db);
            final User authenticatedUser = userDAO.getUser(userId);

            return authenticatedUser;
        } catch (NumberFormatException e) {
            // Not authenticated???
            return new User(-1, "", "", "", "", false);
        } catch (DALException e) {
            return null; // TODO: throw an exception instead.
        }
    }
}
