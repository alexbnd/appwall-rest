package com.radware.appwall.rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.radware.appwall.domain.entities.HostBindings;
import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.json.JsonFormatter;
import com.radware.appwall.logging.AppWallLogger;
import com.radware.appwall.repository.HostBindingsRepository;
import com.radware.appwall.repository.WebServersRepository;
import com.radware.appwall.rest.wrappers.HostBindingsCollectionWrapper;
import com.radware.appwall.validation.ValidHostBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Component
@Path("/v3/config/aw/HostBindings")
public class HostBindingsEndpoint {

    public static final String WEB_SERVER_BINDING_SAVED = "WebServerBinding saved : ";
    public static final String WEB_SERVER_BINDING_DELETED = "WebServerBinding deleted : ";
    public static final String ENTITY_NOT_FOUND = "Entity not found ";

    @Autowired
    private JsonFormatter jsonFormatter;

    @Autowired
    private HostBindingsRepository hostBindingsRepository;

    @Autowired
    private WebServersRepository webServersRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllBindings() {
        List<HostBindings> bindings = new ArrayList<HostBindings>();
        Iterable<HostBindings> hostBindings = hostBindingsRepository.findAll();
        for (HostBindings binding: hostBindings) {
            bindings.add(binding);
        }
        HostBindingsCollectionWrapper wrapper = new HostBindingsCollectionWrapper();
        wrapper.setCollection(bindings);
        return jsonFormatter.toJson(wrapper);
    }

    public HostBindings save(HostBindings hostBindings) {
        HostBindings saved;

        List<WebServerBinding> bindingList = new ArrayList<>();


        List<WebServerBinding> webServers = hostBindings.getWebServers();
        hostBindings.setWebServers(new ArrayList<WebServerBinding>());
        hostBindings = hostBindingsRepository.save(hostBindings);
        for(WebServerBinding binding : webServers) {
            binding = webServersRepository.findById(binding.getId());
            bindingList.add(binding);
            webServersRepository.save(binding);
        }
        hostBindings.setWebServers(bindingList);
        saved = hostBindingsRepository.save(hostBindings);

        return saved;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response createWebServer(@Valid @ValidHostBinding HostBindings hostBindings) {
        hostBindings.setId(null);
        HostBindings saved;
        try {
            saved = save(hostBindings);
        } catch(Exception ex) {
            AppWallLogger.error(this.getClass(), ex, "ERROR_SAVING_ENTITYx1", hostBindings);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(hostBindings).build();
        }
        String result = WEB_SERVER_BINDING_SAVED + saved;
        return Response.status(Response.Status.CREATED).entity(result).build();

    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response updateWebServer(@Valid @ValidHostBinding HostBindings webServerBinding) {
        HostBindings byId = hostBindingsRepository.findById(webServerBinding.getId());
        if(webServerBinding.getId() == null || byId == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(webServerBinding).build();
        }
        HostBindings saved;
        try {
            saved = save(webServerBinding);
        } catch(Exception ex) {
            AppWallLogger.error(this.getClass(), ex, "ERROR_SAVING_ENTITYx1", webServerBinding);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(webServerBinding).build();
        }
        String result = WEB_SERVER_BINDING_SAVED + saved;
        return Response.status(Response.Status.ACCEPTED).entity(result).build();

    }

    @DELETE
    @Path("/{id}")
    public Response deleteWebServer(@PathParam("id") String id) {
        if(id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(id).build();
        }

        long longId = Long.parseLong(id);
        HostBindings byId = hostBindingsRepository.findById(longId);
        if(byId == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(ENTITY_NOT_FOUND + id).build();
        }
        hostBindingsRepository.delete(byId.getId());
        String result = WEB_SERVER_BINDING_DELETED + id;
        return Response.status(Response.Status.OK).entity(result).build();

    }

    @GET
    @Path("/{default: .*}")
    public Response defaultMethod(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        return Response.noContent().build();
    }

}