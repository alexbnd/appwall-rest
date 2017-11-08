package com.radware.appwall.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.radware.appwall.domain.scrawler.Scrawler;
import com.radware.appwall.old.AppWall;
import com.radware.appwall.old.ClientContent;
import com.radware.appwall.old.UserPass;
import com.radware.appwall.old.fileManagers.HostsBindingsFileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/v3/config/aw/HostBindings/")
public class EndPoint {

    private final Service service;

    @Autowired
    private HostsBindingsFileManager hostsBindingsFileManager;


    public EndPoint(Service service) {
        this.service = service;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String message(@PathParam("id") String id) {
        UserPass up = new UserPass("admin", "kavado");
        AppWall appWall = new AppWall("10.206.159.35", 8200);
        hostsBindingsFileManager.setAppWall(appWall);

        try {
            hostsBindingsFileManager.refresh(up, new ClientContent());
        } catch(Exception e) {
            //TODO
        }
        Scrawler cache = hostsBindingsFileManager.getCache();

        return "Hello " + this.service.message();
    }

}
