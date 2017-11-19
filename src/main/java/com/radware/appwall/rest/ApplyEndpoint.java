package com.radware.appwall.rest;

import com.radware.appwall.com.radware.appwall.service.XmlWatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/v3/config/aw")
//http://localhost/v3/config/aw?action=apply
public class ApplyEndpoint {


    @Autowired
    XmlWatcher xmlWatcher;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String apply(@QueryParam("action") String action) {
        boolean dumpResult = xmlWatcher.dumpDB();
        if (dumpResult) {
            return Response.status(Response.Status.OK).build().toString();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build().toString();
        }
    }

}