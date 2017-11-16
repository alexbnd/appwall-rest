package com.radware.appwall.rest;

import com.radware.appwall.com.radware.appwall.service.XmlWatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Component
@Path("/v3/config/aw")
//http://localhost/v3/config/aw?action=apply
public class ApplyEndpoint {


    @Autowired
    XmlWatcher xmlWatcher;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String apply(@QueryParam("action") String action) {
        xmlWatcher.dumpDB();
        System.out.println(action);
        return action;
    }

}