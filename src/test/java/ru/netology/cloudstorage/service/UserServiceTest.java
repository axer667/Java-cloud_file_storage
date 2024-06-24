package ru.netology.cloudstorage.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import ru.netology.cloudstorage.dto.request.AuthRequest;
import ru.netology.cloudstorage.dto.response.AuthResponse;
import ru.netology.cloudstorage.model.entity.RoleEntity;
import ru.netology.cloudstorage.model.entity.UserEntity;
import ru.netology.cloudstorage.repository.TokenRepository;
import ru.netology.cloudstorage.repository.UserRepository;
import ru.netology.cloudstorage.security.TokenManager;

import java.util.Optional;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private static UserServiceImpl userService;

    private static AuthenticationManager authenticationManager;

    private static TokenManager tokenManager;

    private static UserRepository userRepository;
    private static TokenRepository tokenRepository;

    @BeforeAll
    public static void beforeAll() {
        userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.existsByLogin(Mockito.anyString())).thenReturn(true);
        tokenManager = Mockito.mock(TokenManager.class);
        authenticationManager = Mockito.mock(AuthenticationManager.class);

        tokenRepository = Mockito.mock(TokenRepository.class);
        Mockito.when(tokenRepository.removeToken(Mockito.anyString())).thenReturn(Optional.of("Success logout!"));

        userService = new UserServiceImpl(
                userRepository,
                tokenManager,
                authenticationManager,
                tokenRepository
        );
    }

    @Test
    void testCreateAuthToken() {
        when(tokenManager.generateToken(Mockito.<UserDetails>any())).thenReturn("testToken");
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

        UserEntity user = new UserEntity(
                1L,
                "login",
                "password",
                "fName",
                "lName",
                RoleEntity.USER
        );
        Mockito.when(userRepository.getUsersByLogin(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(user));

        AuthRequest login = new AuthRequest(
                "login",
                "password"
        );
        AuthResponse authResponse = userService.loginUser(login);
        Assert.assertEquals("testToken", authResponse.getAuthToken());
        Mockito.verify(tokenRepository, Mockito.atLeastOnce()).save(Mockito.any());
    }
}
