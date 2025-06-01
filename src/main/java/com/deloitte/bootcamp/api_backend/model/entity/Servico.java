package com.deloitte.bootcamp.api_backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private Double preco;

    private Integer duracao; // em minutos


    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private Usuario profissional;









}


