package auth;

import config.Config;
import dao.UserDAO;
import dto.Credentials;
import dto.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.SQLException;
import java.util.Date;

@Path("authentication")
public class AuthenticationEndpoint {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials credentials) {
        try {
            // Authenticate the user using the credentials provided
//            System.out.println("Authenticating");
            authenticate(credentials);

            // Issue a token for the user
//            System.out.println("Authenticated. Issuing token");
            String token = issueToken(credentials.getUserId());

//            System.out.println("Yay, 200 ok!");
            return Response.ok(token).build();
        } catch (Exception e) {
//            System.out.println("Could not authenticate");
//            System.err.println("Error: " + e.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    void authenticate(Credentials credentials) throws Exception { // TODO: Find a better Exception to use
        // Authenticate against database
        // Throw exception if credentials is
        try {
            IConnector db = new DBConnector(new DatabaseConnection());
            db.connectToDatabase();

//            System.out.println("Database connected");

            UserDAO userDAO = new UserDAO(db);
            User dbUser = userDAO.getUser(credentials.getUserId());

//            System.out.println("dbuser = " + dbUser.toString());

            if  (!(dbUser != null && credentials.getPassword().equals(dbUser.getPassword()) && dbUser.isActive())) // TODO: Pretty this up plz
                throw new Exception("could not authenticate");
        } catch (DALException | SQLException e) {
            System.err.println("well.... " + e.getMessage());
        }
    }

    String issueToken(int userId) {
        // Issue a token
        // TODO: should the token get saved in the database?

        Date today = new Date();
        Date twoHoursFromNow = new Date(today.getTime() + (1000 * 60 * 60 * 2));

        User dbUser = null;

        try {
            IConnector db = new DBConnector(new DatabaseConnection());
            db.connectToDatabase();

//            System.out.println("Database connected");

            UserDAO userDAO = new UserDAO(db);
            dbUser = userDAO.getUser(userId);
            dbUser.setPassword("[hidden]");
        } catch (Exception e) { /* TODO: Catch something here... */ }

        return Jwts.builder() // TODO: Might throw NullPointerException...
                .setIssuer("hold12")
                .claim("user", dbUser) // TODO: Get full user DTO??
                .setExpiration(twoHoursFromNow)
                .signWith(SignatureAlgorithm.HS512, Config.AUTH_KEY)
                .compact();
    }

    @NameBinding
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD})
    public @interface Secured {

    }
}
