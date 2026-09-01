package com.apitest.countries.service;

import com.apitest.countries.dto.UserDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class JsonPlaceholderService {

    @Inject
    @RestClient
    JsonPlaceholderClient jsonPlaceholderClient;

    public Uni<Response> getUsers(String filter) {
        return jsonPlaceholderClient.getUsers(filter);
    }

    public Uni<Response> getUser(String id) {
        return jsonPlaceholderClient.getUser(id);
    }

    public Uni<Response> postUser(UserDto userDto) {
        return jsonPlaceholderClient.postUser(userDto);
    }

    public Uni<Response> deleteUser(String id) {
        return jsonPlaceholderClient.deleteUser(id);
    }
}