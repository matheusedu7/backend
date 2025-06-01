package com.deloitte.bootcamp.api_backend.service.register_login;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendEmailService {

    private final JavaMailSender javaMailSender;

    public void sendEmailResetPassword(String email, String resetToken) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(email);
        helper.setSubject("Deloitte - Password Reset");
        helper.setText("Your password reset code: " + resetToken);
        javaMailSender.send(mimeMessage);
    }
}
