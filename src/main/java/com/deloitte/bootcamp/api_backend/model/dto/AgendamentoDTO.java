package com.deloitte.bootcamp.api_backend.model.dto;

import com.deloitte.bootcamp.api_backend.model.entity.AgendamentoStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class AgendamentoDTO {
    private Long id;
    private Long clienteId;
    private Long profissionalId;
    private Long servicoId;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private AgendamentoStatus status;
}
