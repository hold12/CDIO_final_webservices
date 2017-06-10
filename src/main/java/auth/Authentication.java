package auth;

import dao.UserDAO;
import dto.Credentials;
import dto.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jdbclib.*;

//import javax.ws.rs.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by awo on 08/06/17.
 */
@Path("user")
public class Authentication {
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(Credentials inputCredentials) {
        String token = issueToken(inputCredentials);

        return authenticate(inputCredentials) ?
            Response.ok(token).build() :
            Response.status(Response.Status.UNAUTHORIZED).build();
    }

    public boolean authenticate(Credentials credentials) {
        try {
            IConnector db = new DBConnector(new DatabaseConnection());
            db.connectToDatabase();

            UserDAO userDAO = new UserDAO(db);
            User dbUser = userDAO.getUser(credentials.getUserId());

            return dbUser != null &&  credentials.getPassword().equals(dbUser.getPassword()) && dbUser.isActive();

        } catch (IOException e) {
            System.err.println("Environment has not been set (.env).");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC library is missing.");
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        } catch (DALException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
        }

        return false;
    }

    public String issueToken(Credentials credentials) {
        Date today = new Date();
        Date twoHoursFromNow = new Date(today.getTime() + (1000 * 60 * 60 * 2));
        return Jwts.builder()
                .setIssuer("hold12")
                .claim("user", new User(credentials))
                .setExpiration(twoHoursFromNow)
                .signWith(SignatureAlgorithm.HS512, AppConfig.AUTH_KEY)
                .compact();
    }

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

    @GET
    @Path("error")
    public String error() {
        System.out.println("Error");
        return "An error has occured.";
    }
}
