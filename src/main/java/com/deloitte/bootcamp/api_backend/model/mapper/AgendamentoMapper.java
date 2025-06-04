package com.deloitte.bootcamp.api_backend.model.mapper;

import com.deloitte.bootcamp.api_backend.model.dto.AgendamentoDTO;
import com.deloitte.bootcamp.api_backend.model.entity.Agendamento;
import com.deloitte.bootcamp.api_backend.model.entity.Servico;
import com.deloitte.bootcamp.api_backend.model.entity.User;

public class AgendamentoMapper {

    public static Agendamento toEntity(AgendamentoDTO dto, User cliente, User profissional, Servico servico) {
        if (dto == null) return null;
        Agendamento agendamento = new Agendamento();
        agendamento.setId(dto.getId());
        agendamento.setDataHoraInicio(dto.getDataHoraInicio());
        agendamento.setDataHoraFim(dto.getDataHoraFim());
        agendamento.setCliente(cliente);
        agendamento.setProfissional(profissional);
        agendamento.setServico(servico);
        agendamento.setStatus(dto.getStatus());
        return agendamento;
    }

    public static AgendamentoDTO toDto(Agendamento agendamento) {
        if (agendamento == null) return null;
        AgendamentoDTO dto = new AgendamentoDTO();
        dto.setId(agendamento.getId());
        dto.setDataHoraInicio(agendamento.getDataHoraInicio());
        dto.setDataHoraFim(agendamento.getDataHoraFim());
        dto.setClienteId(agendamento.getCliente() != null ? agendamento.getCliente().getId() : null);
        dto.setProfissionalId(agendamento.getProfissional() != null ? agendamento.getProfissional().getId() : null);
        dto.setServicoId(agendamento.getServico() != null ? agendamento.getServico().getId() : null);
        dto.setStatus(agendamento.getStatus());
        return dto;
    }
}