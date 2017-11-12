package com.radware.appwall.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.radware.appwall.domain.entities.HostBindings;
import com.radware.appwall.domain.scrawler.Scrawler;
import com.radware.appwall.old.AppWall;
import com.radware.appwall.old.ClientContent;
import com.radware.appwall.old.UserPass;
import com.radware.appwall.old.fileManagers.HostsBindingsFileManager;
import com.radware.appwall.repository.HostBindingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Path("/v3/config/aw/HostBindings/")
public class HostBindingsPoint {

    private final Service service;

    @Autowired
    private HostsBindingsFileManager hostsBindingsFileManager;

    @Autowired
    private HostBindingsRepository hostBindingsRepository;

    @Autowired
    private Gson gson;


    public HostBindingsPoint(Service service) {
        this.service = service;
    }

    @GET
    //@Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String message(){//@PathParam("id") String id) {
        UserPass up = new UserPass("admin", "kavado");
        AppWall appWall = new AppWall("10.206.159.35", 8200);
        hostsBindingsFileManager.setAppWall(appWall);

        try {
            hostsBindingsFileManager.refresh(up, new ClientContent());
        } catch(Exception e) {
            //TODO
        }
        Scrawler cache = hostsBindingsFileManager.getCache();
        Iterable<HostBindings> all = hostBindingsRepository.findAll();

        return gson.toJson(cache);
    }

}
