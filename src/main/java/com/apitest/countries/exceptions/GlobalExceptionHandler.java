package com.apitest.countries.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @Override
    public Response toResponse(Throwable exception) {
        LOG.error("Unhandled exception", exception);
        
        if (exception instanceof jakarta.persistence.NoResultException ||
            exception instanceof jakarta.persistence.EntityNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Resource not found: " + exception.getMessage())
                    .build();
        }
        
        if (exception instanceof IllegalArgumentException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid argument: " + exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Internal server error: " + exception.getMessage())
                .build();
    }
}