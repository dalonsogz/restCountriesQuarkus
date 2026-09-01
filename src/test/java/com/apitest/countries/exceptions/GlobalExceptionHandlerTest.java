package com.apitest.countries.exceptions;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anyOf;

@QuarkusTest
class GlobalExceptionHandlerTest {

    @Test
    void testGlobalExceptionHandler() {
        given()
            .when().get("/nonexistent-endpoint")
            .then()
            .statusCode(anyOf(is(404), is(500)));
    }
}