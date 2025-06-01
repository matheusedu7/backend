package com.deloitte.bootcamp.api_backend.repository;

import com.deloitte.bootcamp.api_backend.model.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    List<Servico> findByProfissional_Id(Long profissionalId);
}