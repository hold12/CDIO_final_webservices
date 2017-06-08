package auth;

import javax.ws.rs.container.ContainerRequestContext;
import java.io.IOException;

/**
 * Created by awo on 08/06/17.
 */
public class AuthenticationFilter implements javax.ws.rs.container.ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        // http://howtodoinjava.com/jersey/jersey-rest-security/
    }
}
