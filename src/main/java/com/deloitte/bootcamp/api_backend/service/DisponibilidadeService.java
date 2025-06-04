
package com.deloitte.bootcamp.api_backend.service;

import com.deloitte.bootcamp.api_backend.exception.DisponibilidadeNotFoundException;
import com.deloitte.bootcamp.api_backend.exception.AcessoNegadoDisponibilidadeException;
import com.deloitte.bootcamp.api_backend.exception.DisponibilidadeSobrepostaException;
import com.deloitte.bootcamp.api_backend.model.dto.DisponibilidadeDTO;
import com.deloitte.bootcamp.api_backend.model.entity.DiaSemana;
import com.deloitte.bootcamp.api_backend.model.entity.Disponibilidade;
import com.deloitte.bootcamp.api_backend.model.entity.RoleName;
import com.deloitte.bootcamp.api_backend.model.mapper.DisponibilidadeMapper;
import com.deloitte.bootcamp.api_backend.repository.DisponibilidadeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DisponibilidadeService {

    private final DisponibilidadeRepository disponibilidadeRepository;
    private final UserServices userServices;

    // =============================  GET METHODS  =============================

    public List<DisponibilidadeDTO> findByProfissionalId(Long profissionalId) {
        return disponibilidadeRepository.findByProfissionalId(profissionalId)
                .stream()
                .map(DisponibilidadeMapper::toDTO)
                .collect(Collectors.toList());
    }
    public List<DisponibilidadeDTO> findAll() {
        return disponibilidadeRepository.findAll()
                .stream()
                .map(DisponibilidadeMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ==============================  POST METHOD  ================================

    public List<DisponibilidadeDTO> save(DisponibilidadeDTO dto) {
        var loggedUser = userServices.getLoggedUser();
        if (loggedUser.getRoleName() != RoleName.ROLE_PROFISSIONAL) {
            throw new AcessoNegadoDisponibilidadeException("Apenas profissionais podem postar disponibilidade.");
        }
        List<DisponibilidadeDTO> salvos = new ArrayList<>();
        for (String dia : dto.getDiasSemana()) {
            dto.setDiaSemana(dia);
            validarDisponibilidadeDTO(dto);
            verificarSobreposicao(loggedUser.getId(), dia, dto.getHoraInicio(), dto.getHoraFim(), null);
            dto.setProfissionalId(loggedUser.getId());
            Disponibilidade entity = DisponibilidadeMapper.toEntity(dto);
            Disponibilidade saved = disponibilidadeRepository.save(entity);
            salvos.add(DisponibilidadeMapper.toDTO(saved));
        }
        return salvos;
    }

    // ===========================  PUT METHOD  ==================================

    public DisponibilidadeDTO update(Long id, DisponibilidadeDTO dto) {
        validarDisponibilidadeDTO(dto); // Chamada de metodo auxiliar para validação
        var loggedUser = userServices.getLoggedUser();
        Disponibilidade disponibilidade = disponibilidadeRepository.findById(id)
                .orElseThrow(() -> new DisponibilidadeNotFoundException("Disponibilidade não encontrada com o id: " + id));
        if (!disponibilidade.getProfissional().getId().equals(loggedUser.getId())) {
            throw new AcessoNegadoDisponibilidadeException("Apenas o profissional que criou a disponibilidade pode atualizá-la.");
        }
        dto.setId(id);
        dto.setProfissionalId(loggedUser.getId());

        // Verifica sobreposição, ignorando o ID atual para não considerar a própria disponibilidade
        verificarSobreposicao(
                dto.getProfissionalId(),
                dto.getDiaSemana(),
                dto.getHoraInicio(),
                dto.getHoraFim(),
                id
        );

        Disponibilidade updated = disponibilidadeRepository.save(DisponibilidadeMapper.toEntity(dto));
        return DisponibilidadeMapper.toDTO(updated);
    }

    // ==========================  DELETE METHOD  ================================

    public void delete(Long id) {
        var loggedUser = userServices.getLoggedUser();
        Disponibilidade disponibilidade = disponibilidadeRepository.findById(id)
                .orElseThrow(() -> new DisponibilidadeNotFoundException("Disponibilidade não encontrada com o id: " + id));
        if (!disponibilidade.getProfissional().getId().equals(loggedUser.getId())) {
            throw new AcessoNegadoDisponibilidadeException("Apenas o profissional que criou a disponibilidade pode excluí-la.");
        }
        disponibilidadeRepository.deleteById(id);
    }


    // =========================  MÉTODOS AUXILIARES  =============================

    private void verificarSobreposicao(Long profissionalId, String diaSemana, String horaInicio, String horaFim, Long idIgnorar) {
        DiaSemana diaSemanaEnum = DiaSemana.valueOf(diaSemana);
        List<Disponibilidade> existentes = disponibilidadeRepository
                .findByProfissionalIdAndDiaSemana(profissionalId, diaSemanaEnum);

        for (Disponibilidade d : existentes) {
            if (idIgnorar != null && idIgnorar.equals(d.getId())) continue;
            if (!(horaFim.compareTo(d.getHoraInicio()) <= 0 || horaInicio.compareTo(d.getHoraFim()) >= 0)) {
                throw new DisponibilidadeSobrepostaException("Conflito de horários para o profissional no dia " + diaSemana);
            }
        }
    }

    private void validarDisponibilidadeDTO(DisponibilidadeDTO dto) {
        if (dto.getDiaSemana() == null || dto.getDiaSemana().isBlank()) {
            throw new IllegalArgumentException("Dia da semana é obrigatório.");
        }
        try {
            DiaSemana.valueOf(dto.getDiaSemana());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Dia da semana inválido.");
        }
        if (dto.getHoraInicio() == null || dto.getHoraFim() == null) {
            throw new IllegalArgumentException("Horário inicial e final são obrigatórios.");
        }
        if (dto.getHoraInicio().compareTo(dto.getHoraFim()) >= 0) {
            throw new IllegalArgumentException("Horário inicial deve ser antes do horário final.");
        }
    }
}