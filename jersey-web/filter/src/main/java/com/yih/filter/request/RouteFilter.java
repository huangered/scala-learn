package com.yih.filter.request;

import javax.annotation.Priority;
import javax.ws.rs.client.*;
import javax.ws.rs.container.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.logging.Logger;

@PreMatching
@Priority(-1)
public class RouteFilter implements ContainerRequestFilter , ContainerResponseFilter {
    private static Logger logger = Logger.getLogger(RouteFilter.class.getName());


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        UriInfo info = requestContext.getUriInfo();
        logger.info("Receive request from " + info.getPath());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget
                = client.target("http://httpbin.org");
        String path = requestContext.getUriInfo().getPath();
        Response response = null;
        switch (requestContext.getMethod()) {
            case "GET":
                response = webTarget.path(path).request().get();
                break;
            case "POST":
                break;
            default:
                break;
        }
        Object obj = response.getEntity();

        responseContext.setEntity(obj);
        responseContext.setStatus(response.getStatus());
    }
}