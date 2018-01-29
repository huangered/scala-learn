package com.yih.rest;

import com.yih.filter.annotation.Record;
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

@Path("/api/v1")
@Component
public class ImgCtl {

    @Autowired
    private ImgService service;

    @Record
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/imgs")
    public List<Img> imgs(@QueryParam("page") int page) {
        return service.getImgs(page);
    }
}