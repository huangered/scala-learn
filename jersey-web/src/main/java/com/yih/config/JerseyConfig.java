package com.yih.config;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        //register(JwtFilter.class);
        register(RequestContextFilter.class);
        packages("com.yih");
        register(LoggingFilter.class);
    }
}