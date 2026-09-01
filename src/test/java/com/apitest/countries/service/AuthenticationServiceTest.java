package com.apitest.countries.service;

import com.apitest.countries.dto.LoginUserDto;
import com.apitest.countries.dto.RegisterUserDto;
import com.apitest.countries.entities.User;
import com.apitest.countries.repositories.UserRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.smallrye.mutiny.Uni;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;

@QuarkusTest
class AuthenticationServiceTest {

    @Inject
    AuthenticationService authenticationService;

    @InjectMock
    UserRepository userRepository;

    @Test
    @Transactional
    void testSignup() {
        RegisterUserDto registerDto = new RegisterUserDto();
        registerDto.setFullName("Test User");
        registerDto.setEmail("newuser@test.com");
        registerDto.setPassword("password123");

        // Simulate persistAndFlush setting an ID
        doAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(1);
            return null;
        }).when(userRepository).persistAndFlush(any(User.class));

        User result = authenticationService.signup(registerDto).await().indefinitely();
        
        assertEquals("newuser@test.com", result.getEmail());
        assertEquals("Test User", result.getFullName());
        assertNotNull(result.getId());
    }

    @Test
    void testAuthenticate() {
        LoginUserDto loginDto = new LoginUserDto();
        loginDto.setEmail("existing@test.com");
        loginDto.setPassword("password123");

        User user = new User();
        user.setId(1);
        user.setEmail("existing@test.com");
        user.setPassword(org.mindrot.jbcrypt.BCrypt.hashpw("password123", org.mindrot.jbcrypt.BCrypt.gensalt()));

        when(userRepository.findByEmail(anyString()))
            .thenReturn(Optional.of(user));

        User result = authenticationService.authenticate(loginDto).await().indefinitely();
        
        assertEquals("existing@test.com", result.getEmail());
    }

    @Test
    void testAuthenticateWrongPassword() {
        LoginUserDto loginDto = new LoginUserDto();
        loginDto.setEmail("existing@test.com");
        loginDto.setPassword("wrongpassword");

        User user = new User();
        user.setId(1);
        user.setEmail("existing@test.com");
        user.setPassword(org.mindrot.jbcrypt.BCrypt.hashpw("password123", org.mindrot.jbcrypt.BCrypt.gensalt()));

        when(userRepository.findByEmail(anyString()))
            .thenReturn(Optional.of(user));

        try {
            authenticationService.authenticate(loginDto).await().indefinitely();
        } catch (RuntimeException e) {
            assertEquals("Invalid credentials", e.getMessage());
        }
    }

    @Test
    void testGenerateToken() {
        User user = new User();
        user.setId(1);
        user.setEmail("test@test.com");
        user.setFullName("Test User");

        String token = authenticationService.generateToken(user);
        
        assertNotNull(token);
        assertEquals(3, token.split("\\.").length);
    }
}