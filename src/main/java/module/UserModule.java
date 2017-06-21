package module;

import auth.AuthenticationEndpoint;
import config.Permission;
import config.Routes;
import dao.DataValidationException;
import dao.IUserDAO;
import dao.UserDAO;
import dto.User;
import dto.UserNoPerms;
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
    @Path(Routes.MODULE_USER_GET)
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam(Routes.MODULE_USER_GET_ID) int id) {
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

    @AuthenticationEndpoint.Secured(Permission.USER_READ)
    @POST
    @Path(Routes.MODULE_USER_GET_NOPERMS)
    @Produces(MediaType.APPLICATION_JSON)
    public UserNoPerms getUserNoPerms(@PathParam(Routes.MODULE_USER_GET_ID) int userId) {
        try {
            IConnector db = new DBConnector(new DatabaseConnection());
            IUserDAO userDAO = new UserDAO(db);

            UserNoPerms user = userDAO.getUserAndRoles(userId);

            return user;
        } catch (DALException | IOException e) {
            System.err.println(e);
            return null; // TODO: Better exception handling
        }
    }

	@AuthenticationEndpoint.Secured(Permission.USER_UPDATE)
    @POST
    @Path(Routes.MODULE_USER_UPDATE)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void updateUser(User user) throws DataValidationException {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IUserDAO userDAO = new UserDAO(db);

            userDAO.updateUser(user);
        } catch (IOException | DALException e) {
			System.err.println(e);
            return; // TODO: Better exception handling
        }
    }

    @POST
    @Path(Routes.MODULE_USER_GENERATEPASSWORD)
    @Produces(MediaType.TEXT_PLAIN)
    public String generatePassword(User user) {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IUserDAO userDAO = new UserDAO(db);

            return userDAO.generatePassword(user);
        } catch (IOException | DALException | DataValidationException e) {
            return ""; // TODO: Better exception handling
        }
    }

	@AuthenticationEndpoint.Secured(Permission.USER_CREATE)
    @POST
    @Path(Routes.MODULE_USER_CREATE)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(User user) throws DataValidationException {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IUserDAO userDAO = new UserDAO(db);

            final int userId = userDAO.createUser(user);

            user.setUserId(userId);
            return user;
        } catch (IOException | DALException e) {
			System.err.println(e);
            return null; // TODO: Better exception handling
        }
    }
}