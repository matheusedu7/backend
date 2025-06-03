package com.deloitte.bootcamp.api_backend.service;

import com.deloitte.bootcamp.api_backend.exception.ServicoNotFoundException;
import com.deloitte.bootcamp.api_backend.model.dto.ServicoDTO;
import com.deloitte.bootcamp.api_backend.model.entity.Servico;
import com.deloitte.bootcamp.api_backend.model.entity.RoleName;
import com.deloitte.bootcamp.api_backend.model.entity.User;
import com.deloitte.bootcamp.api_backend.repository.ServicoRepository;
import com.deloitte.bootcamp.api_backend.repository.UserRepository;
import com.deloitte.bootcamp.api_backend.model.mapper.ServicoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository;

    // ============================= POST METHODS =============================

    /**
     * Cria um novo serviço para um profissional.
     * Valida o ID do usuário, os dados do serviço e garante que o usuário é um profissional.
     * @param usuarioId ID do profissional que irá cadastrar o serviço
     * @param dto Dados do serviço a ser criado
     * @return DTO do serviço salvo
     */
    public ResponseEntity<ServicoDTO> criarServico(Long usuarioId, ServicoDTO dto) {
        validarId(usuarioId, "Usuário"); // Garante que o ID do usuário é válido
        validarServicoDTO(dto); // Garante que os dados do serviço estão corretos

        // Busca o usuário pelo ID, lança exceção se não existir
        User usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // Verifica se o usuário é do tipo PROFISSIONAL
        if (usuario.getRoleName() != RoleName.ROLE_PROFISSIONAL) {
            throw new IllegalArgumentException("Usuário não é um profissional");
        }

        // Mapeia o DTO para entidade, associa o profissional e salva no banco
        Servico servico = ServicoMapper.toEntity(dto);
        servico.setProfissional(usuario);
        Servico salvo = servicoRepository.save(servico);

        // Retorna o DTO do serviço salvo
        return ResponseEntity.status(HttpStatus.CREATED).body(ServicoMapper.toDTO(salvo));
    }

    // ============================= GET METHODS =============================

    public ResponseEntity<List<ServicoDTO>> listarTodos() {
        List<Servico> servicos = servicoRepository.findAll();
        List<ServicoDTO> dtos = servicos.stream()
                .map(ServicoMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }


    public ResponseEntity<List<ServicoDTO>> listarServicosPorProfissional(Long usuarioId) {
        validarId(usuarioId, "Usuário");

        User usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        List<ServicoDTO> servicos = servicoRepository.findByProfissional_Id(usuarioId)
                .stream()
                .map(ServicoMapper::toDTO)
                .toList();

        return ResponseEntity.ok(servicos);
    }

    /**
     * Busca um serviço pelo seu ID.
     * Valida o ID e lança exceção se não encontrar.
     * @param id ID do serviço
     * @return DTO do serviço encontrado
     */
    public ResponseEntity<ServicoDTO> buscarPorId(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new ServicoNotFoundException("Serviço não encontrado com o id: " + id));
        return ResponseEntity.ok(ServicoMapper.toDTO(servico));
    }

    // ============================= PUT METHODS =============================

    /**
     * Atualiza os dados de um serviço existente.
     * Valida o ID, os dados do serviço e lança exceção se não encontrar.
     * @param id ID do serviço a ser atualizado
     * @param dto Novos dados do serviço
     * @return DTO do serviço atualizado
     */
    public ServicoDTO atualizarServico(Long id, Long usuarioId, ServicoDTO dto) {
        validarServicoDTO(dto);
        User usuario = userServices.buscarUsuarioEntidadePorId(usuarioId);
        Servico servico = buscarServicoEntidadePorId(id);

        if (!servico.getProfissional().getId().equals(usuarioId)) {
            throw new IllegalArgumentException("Apenas o profissional dono do serviço pode atualizá-lo");
        }

        servico.setNome(dto.getNome());
        servico.setDescricao(dto.getDescricao());
        servico.setDuracao(dto.getDuracao());
        servico.setPreco(dto.getPreco());
        Servico atualizado = servicoRepository.save(servico);
        return ServicoMapper.toDTO(atualizado);
    }

    // ============================= DELETE METHODS ===========================

    /**
     * Deleta um serviço pelo seu ID.
     * Valida o ID e lança exceção se não encontrar.
     * @param id ID do serviço a ser deletado
     */
    public void deletarServico(Long id, Long usuarioId) {

        User usuario = userServices.buscarUsuarioEntidadePorId(usuarioId);

        Servico servico = buscarServicoEntidadePorId(id);

        if (!servico.getProfissional().getId().equals(usuarioId)) {
            throw new IllegalArgumentException("Apenas o profissional dono do serviço pode deletá-lo");
        }

        servicoRepository.delete(servico);
    }

    // ============================= MÉTODOS AUXILIARES =======================

    private Servico buscarServicoEntidadePorId(Long id) {
        validarId(id, "Serviço");
        return servicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
    }

    /**
     * Valida se o ID fornecido é válido (não nulo e maior que zero).
     * @param id ID a ser validado
     * @param entidade Nome da entidade para mensagem de erro
     */
    private void validarId(Long id, String entidade) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(entidade + " ID inválido");
        }
    }

    /**
     * Valida os dados obrigatórios do serviço antes de salvar ou atualizar.
     * @param dto DTO do serviço a ser validado
     */
    private void validarServicoDTO(ServicoDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Dados do serviço não informados");
        }
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do serviço é obrigatório");
        }
        if (dto.getPreco() == null || dto.getPreco() < 0) {
            throw new IllegalArgumentException("Preço do serviço inválido");
        }
        if (dto.getDuracao() == null || dto.getDuracao() <= 0) {
            throw new IllegalArgumentException("Duração do serviço inválida");
        }
    }
}