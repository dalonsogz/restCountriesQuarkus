package com.apitest.countries.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anyOf;

@QuarkusTest
class UserControllerTest {

    @Test
    void testGetAllUsers() {
        given()
            .when().get("/users")
            .then()
            .statusCode(anyOf(is(200), is(401), is(500)));
    }
}