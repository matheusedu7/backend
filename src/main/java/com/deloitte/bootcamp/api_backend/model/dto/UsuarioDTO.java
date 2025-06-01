package com.deloitte.bootcamp.api_backend.model.dto;


import lombok.Data;


/**
 * DTO (Data Transfer Object) utilizado para transferir dados do usuário
 * entre as camadas da aplicação, especialmente entre a API e o backend.
 *
 * Função principal:
 * - Transportar apenas as informações necessárias do usuário, evitando expor
 *   toda a estrutura da entidade e facilitando a validação e segurança dos dados.
 *
 * Campos:
 * - id: Identificador único do usuário.
 * - nome: Nome completo do usuário.
 * - email: Endereço de e-mail do usuário.
 * - senha: Senha do usuário.
 * - tipoUsuario: Tipo do usuário (ADMIN, CLIENTE, PROFISSIONAL).
 *
 * Esta classe promove desacoplamento entre as entidades do domínio e as
 * representações expostas na API.
 */
@Data
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String tipoUsuario;
}
