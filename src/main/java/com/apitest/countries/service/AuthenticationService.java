package com.apitest.countries.service;

import com.apitest.countries.dto.LoginUserDto;
import com.apitest.countries.dto.RegisterUserDto;
import com.apitest.countries.entities.User;
import com.apitest.countries.repositories.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;

import java.time.Duration;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class AuthenticationService {

    private static final Logger LOG = Logger.getLogger(AuthenticationService.class);

    @Inject
    UserRepository userRepository;

    @Transactional
    public Uni<User> signup(RegisterUserDto input) {
        User user = new User();
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(hashPassword(input.getPassword()));
        
        userRepository.persistAndFlush(user);
        return Uni.createFrom().item(user);
    }

    public Uni<User> authenticate(LoginUserDto input) {
        return Uni.createFrom().item(userRepository.findByEmail(input.getEmail()))
                .onItem().transform(opt -> opt.orElseThrow(() -> new RuntimeException("User not found")))
                .onItem().transform(user -> {
                    if (!verifyPassword(input.getPassword(), user.getPassword())) {
                        throw new RuntimeException("Invalid credentials");
                    }
                    return user;
                });
    }

    public String generateToken(User user) {
        Set<String> groups = new HashSet<>();
        groups.add("USER");
        
        return Jwt.issuer("rest-countries")
                .subject(user.getEmail())
                .groups(groups)
                .claim("userId", user.getId())
                .claim("fullName", user.getFullName())
                .expiresIn(Duration.ofMillis(86400000))
                .sign();
    }

    private String hashPassword(String password) {
        return org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
    }

    private boolean verifyPassword(String password, String hashed) {
        return org.mindrot.jbcrypt.BCrypt.checkpw(password, hashed);
    }
}