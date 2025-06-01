package com.deloitte.bootcamp.api_backend.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ChangePasswordDTO {

    @JsonProperty("reset_token")
    private String resetToken;
    private String password;
}
