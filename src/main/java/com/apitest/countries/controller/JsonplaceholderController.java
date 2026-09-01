package com.apitest.countries.controller;

import com.apitest.countries.dto.UserDto;
import com.apitest.countries.service.JsonPlaceholderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/jsonplaceholder")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "JsonPlaceholder", description = "JsonPlaceholder API endpoints")
public class JsonplaceholderController {

    @Inject
    JsonPlaceholderService jsonPlaceholderService;

    @GET
    @Path("/users")
    @Operation(summary = "Get all users", description = "Returns list of users with optional filter")
    @APIResponse(responseCode = "200", description = "Users returned")
    public Uni<Response> getUsers(@QueryParam("filter") String filter) {
        return jsonPlaceholderService.getUsers(filter)
                .onItem().transform(response -> Response.ok(response.getEntity()).build())
                .onFailure().recoverWithItem(throwable -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    @GET
    @Path("/users/{id}")
    @Operation(summary = "Get user by ID", description = "Returns a single user")
    @APIResponse(responseCode = "200", description = "User found")
    @APIResponse(responseCode = "404", description = "User not found")
    public Uni<Response> getUser(@PathParam("id") String id) {
        return jsonPlaceholderService.getUser(id)
                .onItem().transform(response -> {
                    if (response.getStatus() == 200) {
                        return Response.ok(response.getEntity()).build();
                    }
                    return Response.status(response.getStatus()).build();
                })
                .onFailure().recoverWithItem(throwable -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Path("/users")
    @Operation(summary = "Create user", description = "Creates a new user")
    @APIResponse(responseCode = "201", description = "User created")
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Uni<Response> postUser(UserDto userDto) {
        return jsonPlaceholderService.postUser(userDto)
                .onItem().transform(response -> Response.status(response.getStatus()).entity(response.getEntity()).build())
                .onFailure().recoverWithItem(throwable -> Response.status(Response.Status.BAD_REQUEST).build());
    }

    @DELETE
    @Path("/users/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user by ID")
    @APIResponse(responseCode = "200", description = "User deleted")
    @APIResponse(responseCode = "404", description = "User not found")
    public Uni<Response> deleteUser(@PathParam("id") String id) {
        return jsonPlaceholderService.deleteUser(id)
                .onItem().transform(response -> Response.ok(response.getEntity()).build())
                .onFailure().recoverWithItem(throwable -> Response.status(Response.Status.NOT_FOUND).build());
    }
}