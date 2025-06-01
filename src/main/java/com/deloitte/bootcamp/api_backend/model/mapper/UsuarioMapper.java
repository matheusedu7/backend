package com.deloitte.bootcamp.api_backend.model.mapper;

import com.deloitte.bootcamp.api_backend.model.dto.UsuarioDTO;
import com.deloitte.bootcamp.api_backend.model.entity.RoleName;
import com.deloitte.bootcamp.api_backend.model.entity.User;


/**
 * Classe utilitária responsável por mapear (converter) objetos entre a entidade Usuario
 * e o DTO UsuarioDTO.
 *
 * Funções principais:
 * - Converte um objeto Usuario (entidade do banco de dados) em um UsuarioDTO,
 *   que é utilizado para transferir dados de forma segura e controlada pela API.
 * - Converte um objeto UsuarioDTO (recebido da API) em uma entidade Usuario,
 *   permitindo que os dados sejam persistidos no banco de dados.
 *
 * O uso dessa classe evita o acoplamento direto entre as entidades do domínio e as
 * representações expostas na API, facilitando manutenção, evolução e segurança
 * da aplicação.
 *
 * Exemplos:
 * - Ao buscar um usuário, converte a entidade para DTO antes de retornar ao cliente.
 * - Ao receber dados de um novo usuário via API, converte o DTO para entidade antes de salvar.
 */

public class UsuarioMapper {


    /**
     * Converte um objeto Usuario (entidade) em um UsuarioDTO.
     * Útil para expor apenas os dados necessários na API.
     *
     * @param usuario Entidade Usuario vinda do banco de dados.
     * @return UsuarioDTO com os dados convertidos.
     */
    public static UsuarioDTO toDTO(User usuario) {
        if (usuario == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setSenha(usuario.getPassword());
        dto.setTipoUsuario(String.valueOf(usuario.getRoleName()))    ; // Converte enum para String
        return dto;
    }



    /**
     * Converte um objeto UsuarioDTO em um Usuario (entidade).
     * Útil para transformar dados recebidos da API em entidade para persistência.
     *
     * @param dto DTO recebido da API.
     * @return Entidade Usuario pronta para ser salva no banco.
     */
    public static User toEntity(UsuarioDTO dto) {
        if (dto == null) return null;
        User usuario = new User();
        usuario.setId(dto.getId());
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getSenha());
        usuario.setRoleName(RoleName.valueOf(dto.getTipoUsuario())); // Converte String para enum
        return usuario;
    }
}
