package rest;

import auth.AuthenticationEndpoint;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by awo on 08/06/17.
 */
@Path("hello")
public class hellorest {
    @POST
    @Path("insecure")
    @Produces(MediaType.APPLICATION_JSON)
    public Response insecureData() {
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "http://localhost:8080")
                .header("Access-Control-Request-Method", "*")
                .build();
    }

    @AuthenticationEndpoint.Secured
    @POST
    @Path("secure")
    @Produces(MediaType.APPLICATION_JSON)
    public String secureData() { return "this is secured data"; }
}
