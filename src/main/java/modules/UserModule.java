package modules;

import auth.AuthenticationEndpoint;
import com.sun.org.apache.bcel.internal.generic.ICONST;
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

        try {
            db = new DBConnector(new DatabaseConnection());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Response.temporaryRedirect(URI.create("/auth/user/error"));
        }

        UserDAO userDAO = new UserDAO(db);
        User user = null;

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


        } catch (IOException e) {

        }
        throw new NotImplementedException();
    }

}
