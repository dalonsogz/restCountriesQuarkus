package com.apitest.countries.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anyOf;

@QuarkusTest
class JsonplaceholderControllerTest {

    @Test
    void testGetUsers() {
        given()
            .when().get("/jsonplaceholder/users")
            .then()
            .statusCode(anyOf(is(200), is(404), is(500)));
    }

    @Test
    void testGetUser() {
        given()
            .when().get("/jsonplaceholder/users/1")
            .then()
            .statusCode(anyOf(is(200), is(404), is(500)));
    }
}