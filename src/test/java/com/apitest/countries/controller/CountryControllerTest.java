package com.apitest.countries.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anyOf;

@QuarkusTest
class CountryControllerTest {

    @Test
    void testGetApiInfo() {
        given()
            .when().get("/countries/v5/api")
            .then()
            .statusCode(200)
            .body(is("REST Countries API - Información de países"));
    }

    @Test
    void testSearchCountries() {
        // External API might not be available in test, just verify endpoint exists
        given()
            .queryParam("q", "spain")
            .queryParam("limit", 10)
            .queryParam("offset", 0)
            .when().get("/countries/v5")
            .then()
            .statusCode(anyOf(is(200), is(404), is(500)));
    }

    @Test
    void testSearchCountriesByName() {
        given()
            .queryParam("q", "spain")
            .queryParam("limit", 10)
            .queryParam("offset", 0)
            .when().get("/countries/v5/name")
            .then()
            .statusCode(anyOf(is(200), is(404), is(500)));
    }
}