package auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.Config;
import config.Permission;
import dto.User;
import io.jsonwebtoken.*;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by AndersWOlsen on 10-06-2017.
 */
@AuthenticationEndpoint.Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // TODO: user from context (cookie)


        // ======== GET HTTP HEADER ========
        // Get auth header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        // Check if the auth header exists and is correctly formatted
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            System.err.println("Not authorized!");
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // ======== EXTRACT USER FROM TOKEN ========
        // Extract the token
        String token = authorizationHeader.substring("Bearer".length()).trim();

        // Extract the user object from the token
        Claims claims = Jwts.parser().setSigningKey(Config.AUTH_KEY).parseClaimsJws(token).getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.convertValue(claims.get("user"), User.class);

        // ======== USER PERMISSIONS ========
        // Extract the permissions declared the resource class matching the requested URL
        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<Permission> classPermissions = extractPermissions(resourceClass);
        Method resourceMethod = resourceInfo.getResourceMethod();
        List<Permission> methodPermissions = extractPermissions(resourceMethod);

        try {
            // Check if the user is allowed to execute the method
            if (methodPermissions.isEmpty()) {
                checkPermissions(classPermissions, user);
            } else {
                checkPermissions(methodPermissions, user); // method annotations override class annotations
            }
        } catch (Exception e) { // TODO: Better exception handling
            requestContext.abortWith(
                    Response.status(Response.Status.FORBIDDEN).build()
            );
        }

        try {
            validateToken(token);
        } catch(Exception e) { // TODO: Better exception handling here
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build()
            );
        }
    }

    private List<Permission> extractPermissions(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<>();
        } else {
            AuthenticationEndpoint.Secured secured = annotatedElement.getAnnotation(AuthenticationEndpoint.Secured.class);
            if (secured == null)
                return new ArrayList<>();
            else {
                Permission[] allowedPermissions = secured.value();
                return Arrays.asList(allowedPermissions);
            }
        }
    }

    private void checkPermissions(List<Permission> allowedPermissions, User user) throws Exception { // TODO: Better exception handling
        System.out.println("Checking permission: ");
        System.out.println("permission: " + allowedPermissions.size());
        for (Permission p : allowedPermissions) {
            if (!user.hasPermission(p))
                throw new Exception("User does not have suffiecient permissions.");
        }
    }

    public static void validateToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException { // TODO: Maybe test some more exceptions
        // Check if it was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid

        Jwts.parser().setSigningKey(Config.AUTH_KEY).parseClaimsJws(token).getBody();
    }
}
