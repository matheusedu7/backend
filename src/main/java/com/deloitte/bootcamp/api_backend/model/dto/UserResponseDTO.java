package com.deloitte.bootcamp.api_backend.model.dto;

import com.deloitte.bootcamp.api_backend.model.entity.RoleName;
import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    private String email;
    private String nome;
    private RoleName roleName;
}
