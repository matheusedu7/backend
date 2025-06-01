package com.deloitte.bootcamp.api_backend.model.dto;

import lombok.Data;


/**
 * DTO (Data Transfer Object) é utilizado para transferir dados do serviço
 * entre as camadas da aplicação, especialmente entre a API e o backend.
 *
 * Função principal:
 * - Transportar apenas as informações necessárias do serviço, evitando expor
 *   toda a estrutura da entidade e facilitando a validação e segurança dos dados.
 *
 * Campos:
 * - id: Identificador único do serviço.
 * - nome: Nome do serviço.
 * - descricao: Descrição detalhada do serviço.
 * - preco: Valor cobrado pelo serviço.
 * - duracao: Duração do serviço em minutos.
 * - profissionalId: ID do profissional associado ao serviço.
 *
 * Esta classe promove desacoplamento entre as entidades do domínio e as
 * representações expostas na API.
 */
@Data
public class ServicoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Integer duracao;
    private Long profissionalId;


}
