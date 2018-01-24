package com.yih.rest;

import com.yih.model.User;
import com.yih.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/v1")
@Component
public class UserCtl {

    @Autowired
    private UserService userService;

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
}