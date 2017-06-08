package auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by awo on 08/06/17.
 */
@Path("user")
public class Authentication {
    @GET
    @Path("login")
    public String login() {
        return "500 - Not authorized!";
    }
}
