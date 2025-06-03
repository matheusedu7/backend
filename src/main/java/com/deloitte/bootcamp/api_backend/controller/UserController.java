package com.deloitte.bootcamp.api_backend.controller;

import com.deloitte.bootcamp.api_backend.model.dto.UserRegisterDTO;
import com.deloitte.bootcamp.api_backend.model.dto.UserResponseDTO;
import com.deloitte.bootcamp.api_backend.service.UserServices;
import com.deloitte.bootcamp.api_backend.service.register_login.RegisterService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {


    private final UserServices userServices;

    @GetMapping("/me")
    public UserResponseDTO getLoggedUser() {
        return userServices.getLoggedUser();
    }

}
