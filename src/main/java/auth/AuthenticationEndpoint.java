package auth;

import config.Config;
import config.Permission;
import config.Routes;
import dao.UserDAO;
import dto.Credentials;
import dto.User;
import io.jsonwebtoken.*;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.SQLException;
import java.util.Date;

@Path(Routes.AUTH_AUTHENTICATION)
public class AuthenticationEndpoint {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials credentials) {
        try {
            // Authenticate the' user using the credentials provided
            authenticate(credentials);

            // Issue a token for the user
            String token = issueToken(credentials.getUserId());

            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    void authenticate(Credentials credentials) throws Exception { // TODO: Find a better Exception to use
        // Authenticate against database
        // Throw exception if credentials is
        try {
            IConnector db = new DBConnector(new DatabaseConnection());

            UserDAO userDAO = new UserDAO(db);
            User dbUser = userDAO.getUser(credentials.getUserId());

            if  (!(dbUser != null && credentials.getPassword().equals(dbUser.getPassword()) && dbUser.isActive())) // TODO: Pretty this up plz
                throw new Exception("could not authenticate");
        } catch (DALException | SQLException e) {
            System.err.println("well.... " + e.getMessage());
        }
    }

    String issueToken(int userId) {
        // Issue a token

        Date today = new Date();
        Date twoHoursFromNow = new Date(today.getTime() + (1000 * 60 * 60 * 2));

        User dbUser = null;

        try {
            IConnector db = new DBConnector(new DatabaseConnection());

            UserDAO userDAO = new UserDAO(db);
            dbUser = userDAO.getFullUser(userId);
            dbUser.setPassword("[hidden]");
        } catch (Exception e) { /* TODO: Catch something here... */ }

        return Jwts.builder() // TODO: Might throw NullPointerException...
                .setIssuer(Config.AUTH_ISSUER)
                .claim(Config.CLAIM_USER, dbUser) // TODO: Get full user DTO??
                .setExpiration(twoHoursFromNow)
                .signWith(SignatureAlgorithm.HS512, Config.AUTH_KEY)
                .compact();
    }

    @POST
    @Path(Routes.AUTH_VALIDATE)
    @Produces(MediaType.TEXT_PLAIN)
    public static Response validateToken(String token) { // TODO: Maybe test some more exceptions
        try {
            Jwts.parser().setSigningKey(Config.AUTH_KEY).parseClaimsJws(token).getBody();
            return Response.status(Response.Status.OK).build();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Token not valid: " + e.getMessage()).build();
        }
    }

    @NameBinding
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD})
    public @interface Secured {
        Permission[] value() default {};
    }
}
