package com.apitest.countries.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
class LandingControllerTest {

    @Test
    void testLandingPage() {
        given()
            .when().get("/")
            .then()
            .statusCode(200)
            .contentType(ContentType.HTML)
            .body(containsString("REST Countries API - Quarkus"))
            .body(containsString("/countries/v5/api"))
            .body(containsString("/auth/signup"))
            .body(containsString("/swagger-ui.html"));
    }
}