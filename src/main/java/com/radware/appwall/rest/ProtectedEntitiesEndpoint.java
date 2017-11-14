package com.radware.appwall.rest;

import com.google.gson.annotations.SerializedName;
import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.json.JsonFormatter;
import com.radware.appwall.repository.HostBindingsWebServersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/v3/config/aw/WebServers/")
public class ProtectedEntitiesEndpoint {

    @Autowired
    private JsonFormatter gson;

    @Autowired
    private HostBindingsWebServersRepository webServersRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllWebServers() {
        Iterable<WebServerBinding> webServers = webServersRepository.findAll();
        CollectionWrapper wrapper = new CollectionWrapper();
        wrapper.collection = webServers;
        return gson.toJson(wrapper);
    }


    public class CollectionWrapper {
        @SerializedName("WebServers")
        private Iterable collection;
    }

}