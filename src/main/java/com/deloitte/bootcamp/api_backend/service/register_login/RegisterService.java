package com.deloitte.bootcamp.api_backend.service.register_login;

import com.deloitte.bootcamp.api_backend.model.entity.RoleName;
import com.deloitte.bootcamp.api_backend.model.entity.User;
import com.deloitte.bootcamp.api_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(String email, String nome, String password, RoleName roleName) {
        User user = new User(email, nome, passwordEncoder.encode(password), LocalDateTime.now());
        user.setRoleName(roleName);
        userRepository.save(user);
    }
}