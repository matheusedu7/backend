package com.deloitte.bootcamp.api_backend.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Disponibilidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private User profissional;

    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;

    private String horaInicio;
    private String horaFim;
}
