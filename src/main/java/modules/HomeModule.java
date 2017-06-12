package modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.Config;
import dao.IUserDAO;
import dao.UserDAO;
import dto.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.IConnector;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.sql.SQLException;

/**
 * Created by AndersWOlsen on 11-06-2017.
 */
@Path("home")
public class HomeModule {
    @Path("getLoggedUser")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User getLoggedUser(@Context HttpServletRequest servletRequest) {
        final String header = servletRequest.getHeader("Authorization");
        final String token = header.substring("Bearer".length()).trim();
//        System.out.println("Token received: " + token);

        final IUserDAO userDAO = new UserDAO();
        final User user = userDAO.getUser(token);
        System.out.println("User logged in: " + user.getFirstname() + " " + user.getLastname());
        return user;
    }

    @Path("test")
    @POST
    public String test(@Context HttpServletRequest serv) {
        String header = serv.getHeader("Authorization");
        String token = header.substring("Bearer".length()).trim();

        return token;
    }
}
