package com.yih.rest;

import com.yih.db.WebUserRepo;
import com.yih.filter.secret.SecretKeyGenerator;
import com.yih.model.User;
import com.yih.model.user.WebUser;
import com.yih.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("/api/v1")
@Component
public class UserCtl {

    @Autowired
    private UserService userService;

    @Autowired
    private WebUserRepo webUserRepo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users")
    public List<User> user() {
        return userService.getUsers();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users/{id}")
    public User userById(@PathParam("id") Long id) {
        return userService.getUserById(id);
    }

    @POST
    @Path("/register")
    @Consumes(APPLICATION_FORM_URLENCODED)
    public Response register(@FormParam("username") String username,
                             @FormParam("password") String password) {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        WebUser webUser = new WebUser(username, password);
        webUser.setCreatedDate(now);
        webUser.setModifiedDate(now);
        webUserRepo.save(webUser);
        return Response.ok().build();
    }

    @POST
    @Path("/login")
    @Consumes(APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password) {
        try {

            // Authenticate the user using the credentials provided
             authenticate(username, password);

            // Issue a token for the user
            String token = issueToken(username);

            // Return the token on the response
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).entity("ok").build();

        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }

    private void authenticate(String user, String password) {
        if (webUserRepo.findByUsernameAndPassword(user, password).size() < 0) {
            throw new RuntimeException("not user");
        }
    }

    private String issueToken(String login) {

        SecretKey key = SecretKeyGenerator.key();

        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer("dd")
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(15L).toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return jwtToken;
    }

}