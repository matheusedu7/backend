package com.deloitte.bootcamp.api_backend.controller;

import com.deloitte.bootcamp.api_backend.model.dto.LoginUserDTO;
import com.deloitte.bootcamp.api_backend.model.dto.UserResponseDTO;
import com.deloitte.bootcamp.api_backend.service.UserServices;
import com.deloitte.bootcamp.api_backend.service.register_login.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserServices userServices;

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginUserDTO body) {
        return authService.authenticateUser(body.getEmail(), body.getPassword());
    }

    @GetMapping("/me")
    public UserResponseDTO getLoggedUser() {
        return userServices.getLoggedUser();
    }
}
