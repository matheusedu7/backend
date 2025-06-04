package com.deloitte.bootcamp.api_backend.repository;

import com.deloitte.bootcamp.api_backend.model.entity.Disponibilidade;
import com.deloitte.bootcamp.api_backend.model.entity.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long> {
    List<Disponibilidade> findByProfissionalId(Long profissionalId);
    List<Disponibilidade> findByProfissionalIdAndDiaSemana(Long profissionalId, DiaSemana diaSemana);

}
