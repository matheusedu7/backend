package com.deloitte.bootcamp.api_backend.controller;

import com.deloitte.bootcamp.api_backend.model.dto.ChangePasswordDTO;
import com.deloitte.bootcamp.api_backend.model.dto.EmailDTO;
import com.deloitte.bootcamp.api_backend.service.register_login.RequestPasswordService;
import com.deloitte.bootcamp.api_backend.service.register_login.ResetPasswordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reset")
@AllArgsConstructor
public class ResetController {

    private final ResetPasswordService resetPasswordService;
    private final RequestPasswordService requestPasswordService;

    @PostMapping("/request")
    public String requestResetPassword(@RequestBody EmailDTO emailDTO) {
        requestPasswordService.resetPasswordUser(emailDTO);
        return "Token sent";
    }

    @PostMapping("/change")
    public String changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        resetPasswordService.resetPassword(changePasswordDTO.getResetToken(), changePasswordDTO.getPassword());
        return "Password altered";
    }
}
