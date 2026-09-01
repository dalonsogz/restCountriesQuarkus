package com.apitest.countries.service;

import com.apitest.countries.dto.UserDto;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.smallrye.mutiny.Uni;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@QuarkusTest
class JsonPlaceholderServiceTest {

    @Inject
    JsonPlaceholderService jsonPlaceholderService;

    @InjectMock
    @RestClient
    JsonPlaceholderClient jsonPlaceholderClient;

    @Test
    void testGetUsers() {
        Response mockResponse = Response.ok("[{\"id\":1,\"name\":\"Test\"}]").build();
        
        when(jsonPlaceholderClient.getUsers(anyString()))
            .thenReturn(Uni.createFrom().item(mockResponse));

        Response result = jsonPlaceholderService.getUsers("").await().indefinitely();
        
        assertEquals(200, result.getStatus());
    }

    @Test
    void testGetUser() {
        Response mockResponse = Response.ok("{\"id\":1,\"name\":\"Test\"}").build();
        
        when(jsonPlaceholderClient.getUser(anyString()))
            .thenReturn(Uni.createFrom().item(mockResponse));

        Response result = jsonPlaceholderService.getUser("1").await().indefinitely();
        
        assertEquals(200, result.getStatus());
    }

    @Test
    void testPostUser() {
        UserDto userDto = new UserDto();
        userDto.setName("New User");
        userDto.setEmail("new@test.com");
        
        Response mockResponse = Response.status(201).entity("{\"id\":2,\"name\":\"New User\"}").build();
        
        when(jsonPlaceholderClient.postUser(any(UserDto.class)))
            .thenReturn(Uni.createFrom().item(mockResponse));

        Response result = jsonPlaceholderService.postUser(userDto).await().indefinitely();
        
        assertEquals(201, result.getStatus());
    }

    @Test
    void testDeleteUser() {
        Response mockResponse = Response.ok("{}").build();
        
        when(jsonPlaceholderClient.deleteUser(anyString()))
            .thenReturn(Uni.createFrom().item(mockResponse));

        Response result = jsonPlaceholderService.deleteUser("1").await().indefinitely();
        
        assertEquals(200, result.getStatus());
    }
}