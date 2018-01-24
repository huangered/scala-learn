package com.yih.filter;

import com.yih.filter.secret.SecretKeyGenerator;
import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

@Provider
@JwtTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter {
    private static Logger logger = Logger.getLogger(JwtFilter.class.getName());


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
// Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            // Validate the token
            Jwts.parser().setSigningKey(SecretKeyGenerator.key()).parseClaimsJws(token);
            logger.info("#### valid token : " + token);

        } catch (Exception e) {
            logger.severe("#### invalid token : " + token);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}