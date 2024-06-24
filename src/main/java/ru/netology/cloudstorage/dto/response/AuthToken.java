package ru.netology.cloudstorage.dto.response;

import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
public class AuthToken {

    private String authToken;
}