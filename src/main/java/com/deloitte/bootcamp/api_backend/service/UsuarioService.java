package com.deloitte.bootcamp.api_backend.service;

import com.deloitte.bootcamp.api_backend.model.dto.UsuarioDTO;
import com.deloitte.bootcamp.api_backend.model.entity.TipoUsuario;
import com.deloitte.bootcamp.api_backend.model.entity.Usuario;
import com.deloitte.bootcamp.api_backend.model.mapper.UsuarioMapper;
import com.deloitte.bootcamp.api_backend.repository.UsuarioRepository;
import com.deloitte.bootcamp.api_backend.exception.UsuarioNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ============================= GET METHODS =============================


    public ResponseEntity<List<UsuarioDTO>> exibirTodosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> dtos = usuarios.stream()
                .map(UsuarioMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }



    public ResponseEntity<UsuarioDTO> buscarPorId(Long id) {
        Usuario usuario = buscarUsuarioEntidadePorId(id);
        UsuarioDTO dto = UsuarioMapper.toDTO(usuario);
        return ResponseEntity.ok(dto);
        }

    // ============================= POST METHODS ============================

    public ResponseEntity<UsuarioDTO> salvar(UsuarioDTO usuarioDTO) {
        Usuario usuario = UsuarioMapper.toEntity(usuarioDTO);
        validarUsuario(usuario);
        Usuario salvo = usuarioRepository.save(usuario);
        return ResponseEntity.ok(UsuarioMapper.toDTO(salvo));
    }

    // ============================= PUT METHODS =============================

    public ResponseEntity<UsuarioDTO> atualizar(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = buscarUsuarioEntidadePorId(id);
        usuarioExistente.setNome(usuarioDTO.getNome());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setSenha(usuarioDTO.getSenha());
        usuarioExistente.setTipoUsuario(TipoUsuario.valueOf(usuarioDTO.getTipoUsuario()));
        Usuario atualizado = usuarioRepository.save(usuarioExistente);
        return ResponseEntity.ok(UsuarioMapper.toDTO(atualizado));
    }

    // ============================= DELETE METHODS ===========================

    public void deletarPorId(Long id) {
        Usuario usuario = buscarUsuarioEntidadePorId(id); // Garante que existe ou lança exceção com método auxiliar
        usuarioRepository.delete(usuario); // Remove o usuário do banco
    }
    // ============================= MÉTODOS AUXILIARES =======================

        public Usuario buscarUsuarioEntidadePorId(Long id) {
            validarId(id, "Usuário");
            return usuarioRepository.findById(id)
                    .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado"));
        }


    private void validarId(Long id, String entidade) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(entidade + " ID inválido");
        }
    }

    private void validarUsuario(Usuario usuario) {
        List<String> erros = new ArrayList<>();
        if (usuario == null) {
            throw new IllegalArgumentException("Dados do usuário não informados");
        }
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            erros.add("Nome do usuário é obrigatório");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            erros.add("Email do usuário é obrigatório");
        }
        if (usuario.getTipoUsuario() == null) {
            erros.add("Tipo de usuário é obrigatório");
        }
        if (!erros.isEmpty()) {
            throw new IllegalArgumentException(String.join("; ", erros));
        }
    }
}