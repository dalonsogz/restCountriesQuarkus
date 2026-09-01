package com.apitest.countries.service;

import com.apitest.countries.dto.CountryResponse;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.smallrye.mutiny.Uni;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@QuarkusTest
class CountryServiceTest {

    @Inject
    CountryService countryService;

    @InjectMock
    @RestClient
    RestCountriesClient restCountriesClient;

    @Test
    void testGetApiInfo() {
        String result = countryService.getApiInfo();
        assertEquals("REST Countries API - Información de países", result);
    }

    @Test
    void testSearchCountries() {
        CountryResponse mockResponse = new CountryResponse();
        mockResponse.setSuccess(true);
        
        when(restCountriesClient.searchCountries(anyString(), anyInt(), anyInt()))
            .thenReturn(Uni.createFrom().item(mockResponse));

        CountryResponse result = countryService.searchCountries("spain", 10, 0)
            .await().indefinitely();
        
        assertEquals(true, result.isSuccess());
    }

    @Test
    void testSearchCountriesByName() {
        CountryResponse mockResponse = new CountryResponse();
        mockResponse.setSuccess(true);
        
        when(restCountriesClient.searchCountriesByName(anyString(), anyInt(), anyInt()))
            .thenReturn(Uni.createFrom().item(mockResponse));

        CountryResponse result = countryService.searchCountriesByName("spain", 10, 0)
            .await().indefinitely();
        
        assertEquals(true, result.isSuccess());
    }
}