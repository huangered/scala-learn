package com.yih.filter.request;

import com.yih.filter.annotation.Record;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.client.*;
import javax.ws.rs.container.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

@Provider
@Record
@PreMatching
@Priority(Priorities.USER)
public class RecordFilter implements ContainerRequestFilter {
    private static Logger logger = Logger.getLogger(RecordFilter.class.getName());


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        UriInfo info = requestContext.getUriInfo();
        logger.info("Receive request from " + info.getPath());
    }
}