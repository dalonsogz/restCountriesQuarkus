package com.apitest.countries.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/")
@Produces(MediaType.TEXT_HTML)
@Tag(name = "Landing", description = "Landing page endpoint")
public class LandingController {

    @GET
    @Operation(summary = "Landing page", description = "Returns the landing page HTML")
    @APIResponse(responseCode = "200", description = "HTML landing page")
    public Response landing() {
        String html = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>REST Countries API</title>
                    <style>
                        body { font-family: Arial, sans-serif; max-width: 800px; margin: 50px auto; padding: 20px; }
                        h1 { color: #333; }
                        .endpoint { background: #f5f5f5; padding: 10px; margin: 10px 0; border-radius: 4px; }
                        .method { color: #fff; padding: 2px 8px; border-radius: 3px; font-size: 12px; }
                        .get { background: #61affe; }
                        .post { background: #49cc90; }
                    </style>
                </head>
                <body>
                    <h1>REST Countries API - Quarkus</h1>
                    <p>Welcome to the REST Countries API built with Quarkus.</p>
                    <h2>Available Endpoints:</h2>
                    <div class="endpoint"><span class="method get">GET</span> <strong>/countries/v5/api</strong> - API Info</div>
                    <div class="endpoint"><span class="method get">GET</span> <strong>/countries/v5?q={query}&limit={limit}&offset={offset}</strong> - Search Countries</div>
                    <div class="endpoint"><span class="method get">GET</span> <strong>/countries/v5/name?q={query}&limit={limit}&offset={offset}</strong> - Search by Name</div>
                    <div class="endpoint"><span class="method post">POST</span> <strong>/auth/signup</strong> - Register User</div>
                    <div class="endpoint"><span class="method post">POST</span> <strong>/auth/login</strong> - Login User</div>
                    <div class="endpoint"><span class="method get">GET</span> <strong>/users</strong> - Get All Users (requires auth)</div>
                    <div class="endpoint"><span class="method get">GET</span> <strong>/jsonplaceholder/users</strong> - JsonPlaceholder Users</div>
                    <div class="endpoint"><span class="method get">GET</span> <strong>/swagger-ui.html</strong> - Swagger UI</div>
                </body>
                </html>
                """;
        return Response.ok(html).build();
    }
}