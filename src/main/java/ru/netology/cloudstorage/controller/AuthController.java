package ru.netology.cloudstorage.controller;

import ru.netology.cloudstorage.dto.request.AuthRequest;
import ru.netology.cloudstorage.dto.response.AuthResponse;
import ru.netology.cloudstorage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ru.netology.cloudstorage.exceptions.MessageConstant.LOGIN;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping(value = LOGIN)
    public ResponseEntity<AuthResponse> user(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity(userService.loginUser(authRequest), HttpStatus.OK);
    }

}