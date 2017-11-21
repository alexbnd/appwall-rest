package com.radware.appwall.rest;

import com.radware.appwall.rest.providers.GsonJerseyProvider;
import com.radware.appwall.rest.providers.ValidationException;
import com.radware.appwall.rest.providers.WebServerValidator;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(ProtectedEntitiesEndpoint.class);
        register(HostBindingsEndpoint.class);
        register(ApplyEndpoint.class);
        register(GsonJerseyProvider.class);
        register(ValidationException.class);
        //register(WebServerValidator.class);
        //register(LoggingFilter.class);
        //packages("com.radware.appwall.rest.providers");
    }

}