package modules;

import auth.AuthenticationEndpoint;
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
@Path("user")
public class UserModule {
    @AuthenticationEndpoint.Secured
    @POST
    @Path("get/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("userId") int id) {
        IConnector db = null;
        User user = null;

        try {
            db = new DBConnector(new DatabaseConnection());
            UserDAO userDAO = new UserDAO(db);
            user = userDAO.getUser(id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Response.temporaryRedirect(URI.create("/auth/user/error"));
        }

        return user;
    }

    @AuthenticationEndpoint.Secured
    @POST
    @Path("get/all")
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

    @AuthenticationEndpoint.Secured
    @POST
    @Path("update}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUser(User user) {
        throw new NotImplementedException();
    }
}
