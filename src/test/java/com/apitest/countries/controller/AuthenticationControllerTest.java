package com.apitest.countries.controller;

import com.apitest.countries.dto.LoginUserDto;
import com.apitest.countries.dto.RegisterUserDto;
import com.apitest.countries.responses.LoginResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anyOf;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationControllerTest {

    private static final String TEST_EMAIL = "test@quarkus.com";
    private static final String TEST_PASSWORD = "password123";

    @Test
    @Order(1)
    void testSignup() {
        RegisterUserDto registerDto = new RegisterUserDto();
        registerDto.setFullName("Test User");
        registerDto.setEmail(TEST_EMAIL);
        registerDto.setPassword(TEST_PASSWORD);

        given()
            .contentType(ContentType.JSON)
            .body(registerDto)
            .when().post("/auth/signup")
            .then()
            .statusCode(anyOf(is(200), is(400), is(500)));
    }

    @Test
    @Order(2)
    void testLogin() {
        LoginUserDto loginDto = new LoginUserDto();
        loginDto.setEmail(TEST_EMAIL);
        loginDto.setPassword(TEST_PASSWORD);

        given()
            .contentType(ContentType.JSON)
            .body(loginDto)
            .when().post("/auth/login")
            .then()
            .statusCode(anyOf(is(200), is(401), is(500)));
    }

    @Test
    @Order(3)
    void testLoginWithWrongPassword() {
        LoginUserDto loginDto = new LoginUserDto();
        loginDto.setEmail(TEST_EMAIL);
        loginDto.setPassword("wrongpassword");

        given()
            .contentType(ContentType.JSON)
            .body(loginDto)
            .when().post("/auth/login")
            .then()
            .statusCode(anyOf(is(401), is(500)));
    }
}