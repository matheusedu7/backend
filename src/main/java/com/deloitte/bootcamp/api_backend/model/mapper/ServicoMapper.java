package com.deloitte.bootcamp.api_backend.model.mapper;

import com.deloitte.bootcamp.api_backend.model.dto.ServicoDTO;
import com.deloitte.bootcamp.api_backend.model.entity.Servico;
import com.deloitte.bootcamp.api_backend.model.entity.Usuario;


/**
 * Classe utilitária responsável por mapear (converter) objetos entre a entidade Servico
 * e o DTO ServicoDTO.
 *
 * Funções principais:
 * - Converte um objeto Servico (entidade do banco de dados) em um ServicoDTO,
 *   que é utilizado para transferir dados de forma segura e controlada pela API.
 * - Converte um objeto ServicoDTO (recebido da API) em uma entidade Servico,
 *   permitindo que os dados sejam persistidos no banco de dados.
 *
 * O uso dessa classe evita o acoplamento direto entre as entidades do domínio e as
 * representações expostas na API, facilitando manutenção, evolução e segurança
 * da aplicação.
 *
 * Exemplos:
 * - Ao buscar um serviço, converte a entidade para DTO antes de retornar ao cliente.
 * - Ao receber dados de um novo serviço via API, converte o DTO para entidade antes de salvar.
 */

    public class ServicoMapper {


        /**
         * Converte um objeto Servico (entidade) em um ServicoDTO.
         * Isso é útil para expor apenas os dados necessários na API,
         * evitando expor toda a estrutura da entidade.
         */
        public static ServicoDTO toDTO(Servico servico) {
            if (servico == null) return null;
            ServicoDTO dto = new ServicoDTO();
            dto.setId(servico.getId());
            dto.setNome(servico.getNome());
            dto.setDescricao(servico.getDescricao());
            dto.setPreco(servico.getPreco());
            dto.setDuracao(servico.getDuracao());
            // Se houver um profissional associado, pega apenas o ID dele
            dto.setProfissionalId(
                    servico.getProfissional() != null ? servico.getProfissional().getId() : null
            );
            return dto;
        }



        /**
         * Converte um objeto ServicoDTO em um Servico (entidade).
         * Isso é usado para transformar os dados recebidos da API em uma entidade
         * que pode ser salva no banco de dados.
         */
        public static Servico toEntity(ServicoDTO dto) {
            if (dto == null) return null;
            Servico servico = new Servico();
            servico.setId(dto.getId());
            servico.setNome(dto.getNome());
            servico.setDescricao(dto.getDescricao());
            servico.setPreco(dto.getPreco());
            servico.setDuracao(dto.getDuracao());
            if (dto.getProfissionalId() != null) {
                Usuario profissional = new Usuario();
                profissional.setId(dto.getProfissionalId());
                servico.setProfissional(profissional);
            } else {
                servico.setProfissional(null);
            }
            return servico;
        }
    }
