package module;

import config.Routes;
import dao.IUserDAO;
import dao.UserDAO;
import dto.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by AndersWOlsen on 11-06-2017.
 */
@Path(Routes.MODULE_HOME)
public class HomeModule {
    @Path(Routes.MODULE_HOME_GETLOGGEDUSER)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User getLoggedUser(@Context HttpServletRequest servletRequest) {
        final String header = servletRequest.getHeader("Authorization");
        final String token = header.substring("Bearer".length()).trim();

        final IUserDAO userDAO = new UserDAO();
        final User user = userDAO.getUser(token);

        return user;
    }

    @Path(Routes.MODULE_HOME_TEST)
    @POST
    public String test(@Context HttpServletRequest serv) {
        String header = serv.getHeader("Authorization");
        String token = header.substring("Bearer".length()).trim();

        return token;
    }
}
