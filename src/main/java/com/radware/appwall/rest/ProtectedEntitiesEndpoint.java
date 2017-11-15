package com.radware.appwall.rest;

import com.google.gson.annotations.SerializedName;
import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.json.JsonFormatter;
import com.radware.appwall.logging.AppWallLogger;
import com.radware.appwall.repository.HostBindingsWebServersRepository;
import com.radware.appwall.validation.ValidWebServerBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/v3/config/aw/WebServers")
public class ProtectedEntitiesEndpoint {

    public static final String WEB_SERVER_BINDING_SAVED = "WebServerBinding saved : ";
    public static final String WEB_SERVER_BINDING_DELETED = "WebServerBinding deleted : ";
    public static final String ENTITY_NOT_FOUND = "Entity not found ";

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

    @POST
    @Path("/post")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response createWebServer(@Valid @ValidWebServerBinding WebServerBinding webServerBinding) {
        WebServerBinding saved;
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
    @Path("/put")
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
    @Path("/delete/{id}")
    public Response deleteWebServer(@PathParam("id") String id) {
        if(id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(id).build();
        }

        long longId = Long.parseLong(id);
        WebServerBinding byId = webServersRepository.findById(longId);
        if ( byId == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(ENTITY_NOT_FOUND +id).build();
        }
        webServersRepository.delete(byId);
        String result = WEB_SERVER_BINDING_DELETED + id;
        return Response.status(Response.Status.OK).entity(result).build();

    }

    @GET
    @Path("/{default: .*}")
    public Response defaultMethod(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        return Response.noContent().build();
    }

    @POST
    @Path("/{default: .*}")
    public Response defaultPostMethod(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        return Response.noContent().build();
    }


    public class CollectionWrapper {
        @SerializedName("WebServers")
        private Iterable collection;
    }

}