package com.yih.rest;

import com.yih.service.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/jokes")
@Component
public class JokeCtl {

    @Autowired
    private JokeService jokeService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List jokes(@QueryParam("page") int page) {
        return jokeService.getMessages();
    }
}