package rest;

import dto.UserDTO;
import org.glassfish.jersey.internal.util.Base64;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by awo on 08/06/17.
 */
//@Provider
//public class AuthenticationFilter implements ContainerRequestFilter {
//    private ResourceInfo resourceInfo;
//
//    private final String AUTHORIZATION_PROPERTY = "Authorization";
//    private final String AUTHENTICATION_SCHEME = "Basic";
//    private final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED).entity("You cannot access this resource!").build();
//    private final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).entity("Access blocked for all users!").build();
//
//    private final String AUTH_URL_PREFIX = "auth";
//
//    @Override
//    public void filter(ContainerRequestContext requestContext) throws IOException {
//        Method method = resourceInfo.getResourceMethod();
//
//        if (!method.isAnnotationPresent(PermitAll.class)) {
//            if (method.isAnnotationPresent(DenyAll.class)) {
//                requestContext.abortWith(ACCESS_FORBIDDEN);
//                return;
//            }
//
//            final MultivaluedMap<String, String> headers = requestContext.getHeaders();
//            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
//
//            // block access if not authorized
//            if (authorization == null || authorization.isEmpty()) {
//                requestContext.abortWith(ACCESS_DENIED);
//                return;
//            }
//
//            // Incomming message is: "Basic <base64 encoded string" - remove "Basic ":
//            final String encUserPass = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
//            final String decUserPass = new String(Base64.decodeAsString(encUserPass));
//
//            final StringTokenizer tokenizer = new StringTokenizer(decUserPass, ":");
//            final int userId = Integer.parseInt(tokenizer.nextToken());
//            final String password = tokenizer.nextToken();
//
//            // TODO: For testing purposes only
//            System.out.println(userId);
//            System.out.println(password);
//
//            // Verify access
//            if (method.isAnnotationPresent(RolesAllowed.class)) {
//                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
//                Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));
//
//                // Is the user a valid user?
//
//
//            }
//        }
//    }
//
//    private boolean isUserAllowed(final int userId, final String password, final Set<String> rolesSet) {
//        UserDTO dbUser = new UserDTO(1, "John", "Doe", "JD", "password123", true);
//
//        if (userId == dbUser.getUserId() && password.equals(dbUser.getPassword()) && dbUser.isActive()) {
//            String userRole = "Admin";
//            return rolesSet.contains(userRole);
//        }
//        return false;
//    }
//}
