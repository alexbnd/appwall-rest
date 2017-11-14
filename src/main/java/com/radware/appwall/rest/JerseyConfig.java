package com.radware.appwall.rest;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(HostBindingsPoint.class);
        register(ProtectedEntitiesEndpoint.class);
        //register(LoggingFilter.class);
        packages("com.radware.appwall.rest.providers");
    }

}