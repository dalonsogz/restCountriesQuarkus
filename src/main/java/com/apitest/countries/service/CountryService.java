package com.apitest.countries.service;

import com.apitest.countries.dto.CountryResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class CountryService {

    @Inject
    @RestClient
    RestCountriesClient restCountriesClient;

    public String getApiInfo() {
        return "REST Countries API - Información de países";
    }

    public Uni<CountryResponse> searchCountries(String q, Integer limit, Integer offset) {
        return restCountriesClient.searchCountries(q, limit, offset);
    }

    public Uni<CountryResponse> searchCountriesByName(String q, Integer limit, Integer offset) {
        return restCountriesClient.searchCountriesByName(q, limit, offset);
    }
}