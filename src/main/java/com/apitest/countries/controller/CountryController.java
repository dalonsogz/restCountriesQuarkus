package com.apitest.countries.controller;

import com.apitest.countries.dto.CountryResponse;
import com.apitest.countries.service.CountryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/countries/v5")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Countries", description = "REST Countries API endpoints")
public class CountryController {

    @Inject
    CountryService countryService;

    @GET
    @Path("/api")
    @Operation(summary = "Get API info", description = "Returns information about the REST Countries API")
    @APIResponse(responseCode = "200", description = "API info returned")
    public Response getApiInfo() {
        return Response.ok(countryService.getApiInfo()).build();
    }

    @GET
    @Operation(summary = "Search countries", description = "Search countries by query")
    @APIResponse(responseCode = "200", description = "Countries found")
    @APIResponse(responseCode = "404", description = "No countries found")
    public Uni<Response> searchCountries(
            @QueryParam("q") String q,
            @QueryParam("limit") @DefaultValue("25") Integer limit,
            @QueryParam("offset") @DefaultValue("0") Integer offset
    ) {
        return countryService.searchCountries(q, limit, offset)
                .onItem().transform(response -> Response.ok(response).build())
                .onFailure().recoverWithItem(throwable -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/name")
    @Operation(summary = "Search countries by name", description = "Search countries by name")
    @APIResponse(responseCode = "200", description = "Countries found")
    @APIResponse(responseCode = "404", description = "No countries found")
    public Uni<Response> searchCountriesByName(
            @QueryParam("q") String q,
            @QueryParam("limit") @DefaultValue("25") Integer limit,
            @QueryParam("offset") @DefaultValue("0") Integer offset
    ) {
        return countryService.searchCountriesByName(q, limit, offset)
                .onItem().transform(response -> Response.ok(response).build())
                .onFailure().recoverWithItem(throwable -> Response.status(Response.Status.NOT_FOUND).build());
    }
}