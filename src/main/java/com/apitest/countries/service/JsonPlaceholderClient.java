package com.apitest.countries.service;

import com.apitest.countries.dto.UserDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import io.smallrye.mutiny.Uni;

@RegisterRestClient(configKey = "jsonplaceholder")
@Path("/")
public interface JsonPlaceholderClient {

    @GET
    @Path("/users")
    Uni<Response> getUsers(@QueryParam("filter") String filter);

    @GET
    @Path("/users/{id}")
    Uni<Response> getUser(@PathParam("id") String id);

    @POST
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<Response> postUser(UserDto userDto);

    @DELETE
    @Path("/users/{id}")
    Uni<Response> deleteUser(@PathParam("id") String id);
}