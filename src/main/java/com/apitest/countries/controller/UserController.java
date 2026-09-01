package com.apitest.countries.controller;

import com.apitest.countries.entities.User;
import com.apitest.countries.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.smallrye.mutiny.Uni;
import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Users", description = "User management endpoints")
public class UserController {

    @Inject
    UserService userService;

    @GET
    @Blocking
    @Operation(summary = "Get all users", description = "Returns all registered users")
    @APIResponse(responseCode = "200", description = "Users returned")
    public Uni<Response> getAllUsers() {
        return userService.allUsers()
                .onItem().transform(users -> Response.ok(users).build())
                .onFailure().recoverWithItem(throwable -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }
}