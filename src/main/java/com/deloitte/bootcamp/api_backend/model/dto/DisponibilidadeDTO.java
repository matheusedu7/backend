package com.deloitte.bootcamp.api_backend.model.dto;


import com.deloitte.bootcamp.api_backend.model.entity.DiaSemana;
import lombok.Data;

@Data
public class DisponibilidadeDTO {
    private Long id;
    private Long profissionalId;
    private String diaSemana;
    private String horaInicio;
    private String horaFim;
}
