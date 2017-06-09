package auth;

import dao.UserDAO;
import dto.UserDTO;
import jdbclib.*;

//import javax.ws.rs.*;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

/**
 * Created by awo on 08/06/17.
 */
@Path("user")
public class Authentication {
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserDTO actualUser) {
        String output;

        IConnector db;
        UserDAO userDAO;
        UserDTO dbUser = null;

        try {
            db = new DBConnector(new DatabaseConnection("h12-dev.wiberg.tech", 3306, "cdio_final", "hold12", "2017_h0lD!2"));
            db.connectToDatabase();
            userDAO = new UserDAO(db);
            dbUser = userDAO.getUser(actualUser.getUserId());
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
        } catch (DALException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("No JDBC. " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }

        if (dbUser == null) {
            output = "User (" + actualUser.getUserId() + ")does not exist in the database.";
            return Response.status(401).entity(output).build();
        }

        if (actualUser.getUserId() == dbUser.getUserId() && actualUser.getPassword().equals(dbUser.getPassword()) && dbUser.isActive()) {
            output = "Authenticated as " + dbUser.getFirstname() + " " + dbUser.getLastname() + ".";
            return Response.status(200).entity(output).build();
        }

        output = "Unauthorized. User : " + actualUser.toString();
        return Response.status(401).entity(output).build();
    }

    @GET
    @Path("get/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDTO getUser(@PathParam("userId") int id) {
        // TODO: How do I import my jdbclib-1.2 in maven?
        IConnector db = null;

        try {
            db = new DBConnector(new DatabaseConnection());
            db.connectToDatabase();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Response.temporaryRedirect(URI.create("/auth/user/error"));
        }

        UserDAO userDAO = new UserDAO(db);
        UserDTO userDTO = null;

        try {
            userDTO = userDAO.getUser(id);

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDTO;
    }

    @GET
    @Path("error")
    public String error() {
        System.out.println("Error");
        return "An error has occured.";
    }
}
