
    package com.deloitte.bootcamp.api_backend.service;

import com.deloitte.bootcamp.api_backend.model.dto.AgendamentoDTO;
import com.deloitte.bootcamp.api_backend.model.entity.*;
import com.deloitte.bootcamp.api_backend.model.mapper.AgendamentoMapper;
import com.deloitte.bootcamp.api_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

    @Service
    public class AgendamentoService {

        @Autowired
        private UserRepository userRepository;
        @Autowired
        private ServicoRepository servicoRepository;
        @Autowired
        private AgendamentoRepository agendamentoRepository;
        @Autowired
        private DisponibilidadeRepository disponibilidadeRepository;

        // =============================  GET METHODS  =============================
        public List<User> listarProfissionais() {
            return userRepository.findByRoleName("PROFISSIONAL");
        }

        public List<Servico> listarServicos() {
            return servicoRepository.findAll();
        }

        // 5. List user's appointments
        public List<AgendamentoDTO> listarAgendamentosCliente(Long clienteId) {
            return agendamentoRepository.findByClienteId(clienteId)
                    .stream()
                    .map(AgendamentoMapper::toDto)
                    .collect(Collectors.toList());
        }


        // ===============================  DELETE METHOD  ================================
        // 6. Cancel appointment with rules (e.g., minimum notice)
        public void cancelarAgendamento(Long agendamentoId, Long clienteId) {
            Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                    .orElseThrow();
            if (!agendamento.getCliente().getId().equals(clienteId)) {
                throw new SecurityException("Acesso negado.");
            }
            if (agendamento.getDataHoraInicio().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new IllegalArgumentException("Cancelamento permitido apenas com 2h de antecedência.");
            }
            agendamento.setStatus(AgendamentoStatus.CANCELADO_CLIENTE);
            agendamentoRepository.save(agendamento);
        }
    }
