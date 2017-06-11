package modules;

import auth.AuthenticationEndpoint;
import dao.UserDAO;
import dto.User;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Created by AndersWOlsen on 10-06-2017.
 */
@Path("user")
public class UserAdmin {
    @AuthenticationEndpoint.Secured
    @POST
    @Path("get/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("userId") int id) {
        IConnector db = null;

        try {
            db = new DBConnector(new DatabaseConnection());
            db.connectToDatabase();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Response.temporaryRedirect(URI.create("/auth/user/error"));
        }

        UserDAO userDAO = new UserDAO(db);
        User user = null;

        try {
            user = userDAO.getUser(id);
            db.close();
        } catch (DALException | NullPointerException e) {
            e.printStackTrace();
        }
        return user;
    }

}
