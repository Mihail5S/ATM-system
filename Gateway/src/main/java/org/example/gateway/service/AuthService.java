package org.example.gateway.service;

import org.example.gateway.entity.UserEntity;
import org.example.gateway.repository.UserRepository;
import org.example.gateway.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;


    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostConstruct
    public void initDefaultAdmin() {
        if (userRepository.count() == 0) {
            UserEntity admin = new UserEntity();
            admin.setUsername("superadmin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles("ROLE_ADMIN");
            userRepository.save(admin);
        }
    }

    @Transactional
    public UserEntity createUser(String username, String rawPassword, String roles) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public String login(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        List<GrantedAuthority> authoritiesList = new ArrayList<>(userDetails.getAuthorities());
        return jwtTokenProvider.createToken(
                userDetails.getUsername(),
                authoritiesList
        );

    }
}