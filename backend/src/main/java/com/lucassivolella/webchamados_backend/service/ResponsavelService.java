package com.lucassivolella.webchamados_backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lucassivolella.webchamados_backend.dto.ResponsavelRequestDTO;
import com.lucassivolella.webchamados_backend.dto.ResponsavelResponseDTO;
import com.lucassivolella.webchamados_backend.entity.Responsavel;
import com.lucassivolella.webchamados_backend.repository.ResponsavelRepository;

import jakarta.transaction.Transactional;

@Service
public class ResponsavelService {
    private final ResponsavelRepository repository;

    public ResponsavelService(ResponsavelRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ResponsavelResponseDTO criar(ResponsavelRequestDTO dto) {
        Responsavel responsavel = criarResponsavel(dto);

        return toResponseDTO(repository.save(responsavel));
    }

    public ResponsavelResponseDTO buscarPorId(Integer id) {
        Responsavel responsavel = getResponsavel(id);
        return toResponseDTO(responsavel);
    }

    public Responsavel buscarEntidadePorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Responsável não encontrado."));
    }

    public List<ResponsavelResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Responsavel buscarComMenosChamadosAbertos() {
        return repository.findResponsavelComMenosChamadosAbertos()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Nenhum responsável cadastrado."));
    }

    // #region Helpers
    private Responsavel getResponsavel(Integer id) {
        Responsavel responsavel = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Responsável não encontrado."));
        return responsavel;
    }

    private Responsavel criarResponsavel(ResponsavelRequestDTO dto) {
        Responsavel responsavel = new Responsavel();

        responsavel.setNome(dto.nome());
        responsavel.setEmail(dto.email());

        return responsavel;
    }

    private ResponsavelResponseDTO toResponseDTO(Responsavel responsavel) {
        return new ResponsavelResponseDTO(
                responsavel.getId(),
                responsavel.getNome(),
                responsavel.getEmail());
    }
    // #endregion
}
