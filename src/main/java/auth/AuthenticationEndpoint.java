package auth;

import config.Config;
import dao.UserDAO;
import dto.Credentials;
import dto.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
import java.util.Date;

@Path("authentication")
public class AuthenticationEndpoint {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials credentials) {
        try {
            // Authenticate the user using the credentials provided
            authenticate(credentials);

            // Issue a token for the user
            String token = issueToken(credentials.getUserId());

            return Response.ok(token).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private void authenticate(Credentials credentials) throws Exception { // TODO: Find a better Exception to use
        // Authenticate against database
        // Throw exception if credentials is
        try {
            IConnector db = new DBConnector(new DatabaseConnection());
            db.connectToDatabase();

            UserDAO userDAO = new UserDAO(db);
            User dbUser = userDAO.getUser(credentials.getUserId());

            if  (!(dbUser != null && credentials.getPassword().equals(dbUser.getPassword()) && dbUser.isActive())) // TODO: Pretty this up plz
                throw new Exception("could not authenticate");
        } catch (Exception e) {
            System.err.println("well.... " + e.getMessage());
        }
    }

    private String issueToken(int userId) {
        // Issue a token
        // TODO: should the token get saved in the database?

        Date today = new Date();
        Date twoHoursFromNow = new Date(today.getTime() + (1000 * 60 * 60 * 2));
        return Jwts.builder()
                .setIssuer("hold12")
                .claim("user", new User(userId)) // TODO: Get full user DTO??
                .setExpiration(twoHoursFromNow)
                .signWith(SignatureAlgorithm.HS512, Config.AUTH_KEY)
                .compact();
    }

    @NameBinding
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD})
    public @interface Secured {  }
}
