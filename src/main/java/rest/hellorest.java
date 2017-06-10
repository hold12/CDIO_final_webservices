package rest;

import auth.AuthenticationEndpoint;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by awo on 08/06/17.
 */
@Path("hello")
public class hellorest {
    @GET
    @Path("insecure")
    @Produces(MediaType.APPLICATION_JSON)
    public String insecureData() {
        return "Hello World from rest!";
    }

    @AuthenticationEndpoint.Secured
    @POST
    @Path("secure")
    @Produces(MediaType.APPLICATION_JSON)
    public String secureData() { return "this is secured data"; }
}
