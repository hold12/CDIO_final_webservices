package auth;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.security.Key;

/**
 * Created by awo on 08/06/17.
 */
@ApplicationPath("auth")
public class AppConfig extends Application {
    public static final Key AUTH_KEY = MacProvider.generateKey(SignatureAlgorithm.HS512);
}
