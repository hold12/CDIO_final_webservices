package auth;

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
        UserDTO expectedUser = new UserDTO(3, "John", "Doe", "JD", "password123", true);

        System.out.println("Testing");
        if (actualUser.getUserId() == expectedUser.getUserId() && actualUser.getPassword().equals(expectedUser.getPassword())) {
            output = "Authenticated as " + expectedUser.getFirstname() + " " + expectedUser.getLastname() + ".";
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
        IConnector db;

        try {
            db = new DBConnector(new DatabaseConnection());
        } catch (IOException e) {
            Response.temporaryRedirect(URI.create("/auth/user/error"));
        }
        return null;
    }

    @GET
    @Path("error")
    public String error() {
        System.out.println("Error");
        return "An error has occured.";
    }
}
