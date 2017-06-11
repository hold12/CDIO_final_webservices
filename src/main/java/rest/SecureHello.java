package rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by awo on 08/06/17.
 */
@Path("secure")
public class SecureHello {
    @RolesAllowed("Admin")
    @GET
    @Path("hello")
    @Produces(MediaType.APPLICATION_JSON)
    public String secureHello() {
        return "Hello from Rest. This is a secured resource - only logged in UserModule can see this.";
    }
}
