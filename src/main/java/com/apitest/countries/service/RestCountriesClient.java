package com.apitest.countries.service;

import com.apitest.countries.dto.CountryResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import io.smallrye.mutiny.Uni;

@RegisterRestClient(configKey = "restcountries")
@Path("/")
public interface RestCountriesClient {

    @GET
    @Path("/")
    Uni<CountryResponse> searchCountries(
            @QueryParam("q") String q,
            @QueryParam("limit") Integer limit,
            @QueryParam("offset") Integer offset
    );

    @GET
    @Path("/name")
    Uni<CountryResponse> searchCountriesByName(
            @QueryParam("q") String q,
            @QueryParam("limit") Integer limit,
            @QueryParam("offset") Integer offset
    );
}