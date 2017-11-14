package com.radware.appwall.rest.providers;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationException implements ExceptionMapper<javax.validation.ValidationException> {

    @Override
    public Response toResponse(javax.validation.ValidationException e) {
        final StringBuilder strBuilder = new StringBuilder();
        for(ConstraintViolation<?> cv : ((ConstraintViolationException) e).getConstraintViolations()) {
            strBuilder.append(cv.getMessage());
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode()).type(MediaType.TEXT_PLAIN)
                       .entity(strBuilder.toString()).build();
    }
}