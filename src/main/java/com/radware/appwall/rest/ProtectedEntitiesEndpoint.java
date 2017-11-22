package com.radware.appwall.rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.radware.appwall.domain.entities.HostBindings;
import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.json.JsonFormatter;
import com.radware.appwall.logging.AppWallLogger;
import com.radware.appwall.repository.HostBindingsRepository;
import com.radware.appwall.repository.WebServersRepository;
import com.radware.appwall.validation.ValidWebServerBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/v3/config/aw/WebServers")
public class ProtectedEntitiesEndpoint {

    public static final String WEB_SERVER_BINDING_SAVED = "WebServerBinding saved : ";
    public static final String WEB_SERVER_BINDING_DELETED = "WebServerBinding deleted : ";
    public static final String ENTITY_NOT_FOUND = "Entity not found ";
    public static final String CAN_T_DELETE_WERBSERVER_ASSIGNED_PROTECTED_ENTITIES_EXIST =
            "Can't delete werbserver. Assigned protected  entities exist";

    @Autowired
    private JsonFormatter jsonFormatter;

    @Autowired
    private WebServersRepository webServersRepository;

    @Autowired
    private HostBindingsRepository hostBindingsRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllWebServers() {
        Iterable<WebServerBinding> webServers = webServersRepository.findAll();
        CollectionWrapper wrapper = new CollectionWrapper();
        wrapper.collection = webServers;
        return jsonFormatter.toJson(wrapper);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response createWebServer(@Valid @ValidWebServerBinding WebServerBinding webServerBinding) {
        WebServerBinding saved;
        webServerBinding.setId(null);
        try {
            saved = webServersRepository.save(webServerBinding);
        } catch(Exception ex) {
            AppWallLogger.error(this.getClass(), ex, "ERROR_SAVING_ENTITYx1", webServerBinding);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(webServerBinding).build();
        }
        String result = WEB_SERVER_BINDING_SAVED + saved;
        return Response.status(Response.Status.CREATED).entity(result).build();

    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response updateWebServer(@Valid @ValidWebServerBinding WebServerBinding webServerBinding) {
        if(webServerBinding.getId() == null || webServersRepository.findById(webServerBinding.getId()) == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(webServerBinding).build();
        }
        WebServerBinding saved;
        try {
            saved = webServersRepository.save(webServerBinding);
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
        WebServerBinding byId = webServersRepository.findById(longId);
        if(byId == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(ENTITY_NOT_FOUND + id).build();
        }
        List<HostBindings> byWebServerId = hostBindingsRepository.findByWebServerId(longId);
        if (byWebServerId != null && !byWebServerId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    CAN_T_DELETE_WERBSERVER_ASSIGNED_PROTECTED_ENTITIES_EXIST).build();
        }


        webServersRepository.delete(byId);
        String result = WEB_SERVER_BINDING_DELETED + id;
        return Response.status(Response.Status.OK).entity(result).build();

    }

    public class CollectionWrapper {
        @Expose
        @SerializedName("WebServers")
        private Iterable collection;
    }

}