package com.apitest.countries.service;

import com.apitest.countries.entities.User;
import com.apitest.countries.repositories.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import io.smallrye.mutiny.Uni;
import io.quarkus.panache.common.Sort;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public Uni<List<User>> allUsers() {
        return Uni.createFrom().item(() -> userRepository.findAll(Sort.by("id")).list())
            .runSubscriptionOn(executor);
    }

    @Transactional
    public Uni<User> createUser(User user) {
        userRepository.persistAndFlush(user);
        return Uni.createFrom().item(user);
    }
}