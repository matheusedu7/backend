package com.deloitte.bootcamp.api_backend.model.mapper;

import com.deloitte.bootcamp.api_backend.model.dto.DisponibilidadeDTO;
import com.deloitte.bootcamp.api_backend.model.entity.Disponibilidade;
import com.deloitte.bootcamp.api_backend.model.entity.DiaSemana;
import com.deloitte.bootcamp.api_backend.model.entity.User;

public class DisponibilidadeMapper {

    public static DisponibilidadeDTO toDTO(Disponibilidade disponibilidade) {
        if (disponibilidade == null) return null;
        DisponibilidadeDTO dto = new DisponibilidadeDTO();
        dto.setId(disponibilidade.getId());
        dto.setProfissionalId(
                disponibilidade.getProfissional() != null ? disponibilidade.getProfissional().getId() : null
        );
        dto.setDiaSemana(
                disponibilidade.getDiaSemana() != null ? String.valueOf(disponibilidade.getDiaSemana()) : null
        );
        dto.setHoraInicio(disponibilidade.getHoraInicio());
        dto.setHoraFim(disponibilidade.getHoraFim());
        return dto;
    }

    public static Disponibilidade toEntity(DisponibilidadeDTO dto) {
        if (dto == null) return null;
        Disponibilidade disponibilidade = new Disponibilidade();
        disponibilidade.setId(dto.getId());
        if (dto.getProfissionalId() != null) {
            User profissional = new User();
            profissional.setId(dto.getProfissionalId());
            disponibilidade.setProfissional(profissional);
        } else {
            disponibilidade.setProfissional(null);
        }
        disponibilidade.setDiaSemana(
                dto.getDiaSemana() != null ? DiaSemana.valueOf(dto.getDiaSemana()) : null
        );
        disponibilidade.setHoraInicio(dto.getHoraInicio());
        disponibilidade.setHoraFim(dto.getHoraFim());
        return disponibilidade;
    }
}