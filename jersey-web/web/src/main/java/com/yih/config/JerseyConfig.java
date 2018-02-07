package com.yih.config;

import com.yih.filter.request.RecordFilter;
import com.yih.filter.request.RouteFilter;
import com.yih.filter.response.CorsResponseFilter;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        //register(JwtFilter.class);
        register(RequestContextFilter.class);
        register(RecordFilter.class);
        packages("com.yih");
        register(LoggingFilter.class);
        register(CorsResponseFilter.class);
        //register(RouteFilter.class);
    }
}