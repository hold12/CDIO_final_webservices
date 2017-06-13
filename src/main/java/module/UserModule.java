package module;

import config.Routes;
import auth.AuthenticationEndpoint;
import config.Permission;
import dao.IUserDAO;
import dao.UserDAO;
import dto.User;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * Created by AndersWOlsen on 10-06-2017.
 */
@Path(Routes.MODULE_USER)
public class UserModule {
    @AuthenticationEndpoint.Secured(Permission.USER_READ)
    @POST
    @Path(Routes.MODULE_USER_GETUSER)
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam(Routes.MODULE_USER_GETUSER_USERID) int id) {
        IConnector db;
        User user = null;

        try {
            db = new DBConnector(new DatabaseConnection());
            UserDAO userDAO = new UserDAO(db);
            user = userDAO.getFullUser(id);
        } catch (Exception e) { // TODO: Implement better exception handling
            System.out.println("Error: " + e.getMessage());
            Response.temporaryRedirect(URI.create("/auth/user/error"));
        }

        return user;
    }

	@AuthenticationEndpoint.Secured(Permission.USER_READ)
    @POST
    @Path(Routes.MODULE_USER_ALL)
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IUserDAO userDAO = new UserDAO(db);

            return userDAO.getUserList();
        } catch (IOException | DALException e) {
            System.out.println(e); // TODO: Throw a better exception and catch frontend
        }
        throw new NotImplementedException();
    }

	@AuthenticationEndpoint.Secured(Permission.USER_UPDATE)
    @POST
    @Path(Routes.MODULE_USER_UPDATE)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void updateUser(User user) {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IUserDAO userDAO = new UserDAO(db);

            userDAO.updateUser(user);
        } catch (IOException | DALException e) {
            return; // TODO: Better exception handling
        }
    }

	@AuthenticationEndpoint.Secured(Permission.USER_CREATE)
    @POST
    @Path(Routes.MODULE_USER_CREATE)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(User user) {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IUserDAO userDAO = new UserDAO(db);

            final int userId = userDAO.createUser(user);

            user.setUserId(userId);
            return user;
        } catch (IOException | DALException e) {
            return null; // TODO: Better exception handling
        }
    }
}