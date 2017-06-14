package module;

import auth.AuthenticationEndpoint;
import config.Permission;
import config.Routes;
import dao.RoleDAO;
import dto.Role;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

/**
 * Created by AndersWOlsen on 14-06-2017.
 */
@Path(Routes.MODULE_ROLE)
public class RoleModule {
    @AuthenticationEndpoint.Secured(Permission.ROLE_READ)
    @POST
    @Path(Routes.MODULE_ROLE_GET_ALL)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Role> getAllRoles() {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            RoleDAO roleDAO = new RoleDAO(db);
            return roleDAO.getRoleList();
        } catch (DALException | IOException e) {
            return null;
        }
    }
}
