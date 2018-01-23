package com.yih.rest;

import com.yih.model.Img;
import com.yih.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Component
public class ImgCtl {

    @Autowired
    private ImgService messageService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/imgs")
    public List<Img> imgs(@QueryParam("page") int page) {
        return messageService.getImgs(page);
    }
}