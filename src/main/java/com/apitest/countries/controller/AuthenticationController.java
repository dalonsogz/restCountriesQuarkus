package com.apitest.countries.controller;

import com.apitest.countries.dto.LoginUserDto;
import com.apitest.countries.dto.RegisterUserDto;
import com.apitest.countries.entities.User;
import com.apitest.countries.responses.LoginResponse;
import com.apitest.countries.service.AuthenticationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.smallrye.mutiny.Uni;
import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthenticationController {

    @Inject
    AuthenticationService authenticationService;

    @POST
    @Path("/signup")
    @Blocking
    @Operation(summary = "Register new user", description = "Creates a new user account")
    @APIResponse(responseCode = "200", description = "User registered successfully")
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Uni<Response> register(RegisterUserDto registerUserDto) {
        return authenticationService.signup(registerUserDto)
                .onItem().transform(user -> Response.ok(user).build())
                .onFailure().recoverWithItem(throwable -> Response.status(Response.Status.BAD_REQUEST).entity(throwable.getMessage()).build());
    }

    @POST
    @Path("/login")
    @Blocking
    @Operation(summary = "Authenticate user", description = "Returns JWT token for authenticated user")
    @APIResponse(responseCode = "200", description = "Login successful, returns JWT token")
    @APIResponse(responseCode = "401", description = "Invalid credentials")
    public Uni<Response> authenticate(LoginUserDto loginUserDto) {
        return authenticationService.authenticate(loginUserDto)
                .onItem().transform(user -> {
                    String token = authenticationService.generateToken(user);
                    LoginResponse loginResponse = new LoginResponse(token, 86400000L);
                    return Response.ok(loginResponse).build();
                })
                .onFailure().recoverWithItem(throwable -> Response.status(Response.Status.UNAUTHORIZED).entity(throwable.getMessage()).build());
    }
}