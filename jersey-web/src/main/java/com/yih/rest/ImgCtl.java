package com.yih.rest;

import com.yih.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
@Component
public class ImgCtl {

    @Autowired
    private ImgService messageService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/imgs")
    public String imgs(@QueryParam("page") int page) {
        return "Hello World";
    }
}