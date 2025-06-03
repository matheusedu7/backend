
package com.deloitte.bootcamp.api_backend.service;

import com.deloitte.bootcamp.api_backend.exception.DisponibilidadeNotFoundException;
import com.deloitte.bootcamp.api_backend.exception.AcessoNegadoDisponibilidadeException;
import com.deloitte.bootcamp.api_backend.model.dto.DisponibilidadeDTO;
import com.deloitte.bootcamp.api_backend.model.entity.Disponibilidade;
import com.deloitte.bootcamp.api_backend.model.entity.RoleName;
import com.deloitte.bootcamp.api_backend.model.mapper.DisponibilidadeMapper;
import com.deloitte.bootcamp.api_backend.repository.DisponibilidadeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DisponibilidadeService {

    private final DisponibilidadeRepository disponibilidadeRepository;
    private final UserServices userServices;

    // =============================  GET METHODS  =============================

    public DisponibilidadeDTO findById(Long id) {
        Disponibilidade disponibilidade = disponibilidadeRepository.findById(id)
                .orElseThrow(() -> new DisponibilidadeNotFoundException("Disponibilidade não encontrada com o id: " + id));
        return DisponibilidadeMapper.toDTO(disponibilidade);
    }

    public List<DisponibilidadeDTO> findAll() {
        return disponibilidadeRepository.findAll()
                .stream()
                .map(DisponibilidadeMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ==============================  POST METHOD  ================================

    public DisponibilidadeDTO save(DisponibilidadeDTO dto) {
        var loggedUser = userServices.getLoggedUser();
        if (loggedUser.getRoleName() != RoleName.ROLE_PROFISSIONAL) {
            throw new AcessoNegadoDisponibilidadeException("Apenas profissionais podem postar disponibilidade.");
        }
        dto.setProfissionalId(loggedUser.getId());
        Disponibilidade entity = DisponibilidadeMapper.toEntity(dto);
        Disponibilidade saved = disponibilidadeRepository.save(entity);
        return DisponibilidadeMapper.toDTO(saved);
    }

    // ===========================  PUT METHOD  ==================================

    public DisponibilidadeDTO update(Long id, DisponibilidadeDTO dto) {
        var loggedUser = userServices.getLoggedUser();
        Disponibilidade disponibilidade = disponibilidadeRepository.findById(id)
                .orElseThrow(() -> new DisponibilidadeNotFoundException("Disponibilidade não encontrada com o id: " + id));
        if (!disponibilidade.getProfissional().getId().equals(loggedUser.getId())) {
            throw new AcessoNegadoDisponibilidadeException("Apenas o profissional que criou a disponibilidade pode atualizá-la.");
        }
        dto.setId(id);
        dto.setProfissionalId(loggedUser.getId());
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
}