package com.apitest.countries.service;

import com.apitest.countries.entities.User;
import com.apitest.countries.repositories.UserRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.panache.common.Sort;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.smallrye.mutiny.Uni;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;

@QuarkusTest
class UserServiceTest {

    @Inject
    UserService userService;

    @InjectMock
    UserRepository userRepository;

    @Test
    void testAllUsers() {
        User user1 = new User();
        user1.setId(1);
        user1.setEmail("user1@test.com");
        user1.setFullName("User One");

        User user2 = new User();
        user2.setId(2);
        user2.setEmail("user2@test.com");
        user2.setFullName("User Two");

        PanacheQuery<User> mockQuery = Mockito.mock(PanacheQuery.class);
        when(mockQuery.list()).thenReturn(List.of(user1, user2));
        when(userRepository.findAll(any(Sort.class))).thenReturn(mockQuery);

        List<User> result = userService.allUsers().await().indefinitely();
        
        assertEquals(2, result.size());
        assertEquals("user1@test.com", result.get(0).getEmail());
    }

    @Test
    @Transactional
    void testCreateUser() {
        User user = new User();
        user.setEmail("new@test.com");
        user.setFullName("New User");
        user.setPassword("password");

        doAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(1);
            return null;
        }).when(userRepository).persistAndFlush(any(User.class));

        User result = userService.createUser(user).await().indefinitely();
        
        assertEquals(1, result.getId());
        assertEquals("new@test.com", result.getEmail());
    }
}