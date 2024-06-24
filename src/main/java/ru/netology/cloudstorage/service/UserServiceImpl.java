package ru.netology.cloudstorage.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ru.netology.cloudstorage.exceptions.LoginException;
import ru.netology.cloudstorage.dto.request.AuthRequest;
import ru.netology.cloudstorage.dto.response.AuthResponse;
import ru.netology.cloudstorage.model.entity.UserEntity;
import ru.netology.cloudstorage.repository.TokenRepository;
import ru.netology.cloudstorage.repository.UserRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.netology.cloudstorage.security.CloudUserDetails;
import ru.netology.cloudstorage.security.TokenManager;
import ru.netology.cloudstorage.model.entity.security.Token;

import static ru.netology.cloudstorage.exceptions.MessageConstant.*;
import static java.util.Objects.isNull;

@Data
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenManager tokenManager;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    @Override
    public AuthResponse loginUser(AuthRequest authRequest) {
        if (isNull(authRequest.getLogin()) && isNull(authRequest.getPassword())) {
            throw new LoginException(LOGIN_NON_VALID_VALUE);
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
        } catch (LoginException e) {
            log.error(LOGIN_NOT_VALID_PASSWORD);
            throw new LoginException(LOGIN_NOT_VALID_PASSWORD);
        }

        UserEntity userEntity = userRepository.getUsersByLogin(authRequest.getLogin()).orElseThrow(()
                -> new LoginException(LOGIN_NOT_FOUND_USER));

        CloudUserDetails cloudUserDetails = new CloudUserDetails(userEntity);

        Token newToken = new Token(tokenManager.generateToken(cloudUserDetails), userEntity);
        Token resultToken = newToken;

        if (tokenRepository.findTokenToUser(userEntity.getId()).isPresent()) {
            Token token = tokenRepository.findTokenToUser(userEntity.getId()).get();
            token.setAuthToken(newToken.getAuthToken());
            resultToken = token;
        }

        tokenRepository.save(resultToken);
        return new AuthResponse(resultToken.getAuthToken(), cloudUserDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")));
    }
}