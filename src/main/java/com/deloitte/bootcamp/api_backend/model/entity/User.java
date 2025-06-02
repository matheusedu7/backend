package com.deloitte.bootcamp.api_backend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;


@Data
@Entity
@RequiredArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    private String password;

    private String resetToken;

    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    public User(String email, String nome, String password, LocalDateTime dateTime) {
        this.email = email;
        this.nome = nome;
        this.password = password;
        this.dateTime = dateTime;
    }
}
