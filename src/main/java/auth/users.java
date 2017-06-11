package auth;

import dao.UserDAO;
import dto.User;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Created by AndersWOlsen on 10-06-2017.
 */
@Path("gettest")
public class users {
    @GET
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
