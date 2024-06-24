package ru.netology.cloudstorage.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    @JsonProperty("auth-token")
    private String authToken;
    @JsonProperty("is_admin")
    private Boolean isAdmin;
}