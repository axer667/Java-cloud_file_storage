package ru.netology.cloudstorage.service;

import ru.netology.cloudstorage.dto.request.AuthRequest;
import ru.netology.cloudstorage.dto.response.AuthResponse;

public interface UserService {
    AuthResponse loginUser(AuthRequest authRequest);
}