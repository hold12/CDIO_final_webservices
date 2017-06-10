package rest;

import auth.AuthenticationEndpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by awo on 08/06/17.
 */
@Path("hello")
public class hellorest {
    @GET
    @Path("insecure")
    public String insecureData() {
        return "Hello World from rest!";
    }

    @AuthenticationEndpoint.Secured
    @GET
    @Path("secure")
    public String secureData() { return "this is secured data"; }
}
