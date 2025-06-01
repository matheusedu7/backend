package com.deloitte.bootcamp.api_backend.model.dto;

import com.deloitte.bootcamp.api_backend.model.entity.RoleName;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTO {

    @NotNull
    @Email(message = "Invalid email format")
    private String email;
    private String nome;
    private String password;
    @JsonProperty("role_name")
    private RoleName roleName;
}
