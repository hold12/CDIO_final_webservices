package secured;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.NameBinding;
import javax.ws.rs.core.Application;

/**
 * Created by AndersWOlsen on 10-06-2017.
 */
@ApplicationPath("secure")
public class AppConfig extends Application {
    @NameBinding
    public @interface Secured{}
}
