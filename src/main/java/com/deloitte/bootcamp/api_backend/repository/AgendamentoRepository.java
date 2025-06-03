package com.deloitte.bootcamp.api_backend.repository;

import com.deloitte.bootcamp.api_backend.model.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    boolean existsByProfissionalIdAndDataHoraInicioAndDataHoraFim(Long profissionalId, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim);
    List<Agendamento> findByClienteId(Long clienteId);
}