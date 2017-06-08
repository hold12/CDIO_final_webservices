package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by awo on 08/06/17.
 */

@Path("hello")
public class hellorest {
    @GET
    public String test() {
        return "Hello World from rest!";
    }
}
