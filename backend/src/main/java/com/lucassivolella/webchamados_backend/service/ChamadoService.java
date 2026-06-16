package com.lucassivolella.webchamados_backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lucassivolella.webchamados_backend.dto.ChamadoRequestDTO;
import com.lucassivolella.webchamados_backend.dto.ChamadoResponseDTO;
import com.lucassivolella.webchamados_backend.entity.Chamado;
import com.lucassivolella.webchamados_backend.entity.Responsavel;
import com.lucassivolella.webchamados_backend.enums.ChamadoStatus;
import com.lucassivolella.webchamados_backend.enums.Prioridade;
import com.lucassivolella.webchamados_backend.repository.ChamadoRepository;

import jakarta.transaction.Transactional;

@Service
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final ResponsavelService responsavelService;

    public ChamadoService(
            ChamadoRepository chamadoRepository,
            ResponsavelService responsavelService) {
        this.chamadoRepository = chamadoRepository;
        this.responsavelService = responsavelService;
    }

    @Transactional
    public ChamadoResponseDTO criar(ChamadoRequestDTO dto) {
        Responsavel responsavel;

        if (Boolean.TRUE.equals(dto.atribuicaoAutomatica())) {
            responsavel = responsavelService.buscarComMenosChamadosAbertos();
        } else {
            responsavel = getResponsavel(dto.responsavelId());
        }

        Chamado chamado = criarChamado(dto, responsavel);

        return toResponseDTO(chamadoRepository.save(chamado));
    }

    @Transactional
    public ChamadoResponseDTO atualizar(Integer id, ChamadoRequestDTO dto) {
        Chamado chamado = getChamado(id);

        Responsavel responsavel;
        if (Boolean.TRUE.equals(dto.atribuicaoAutomatica())) {
            responsavel = responsavelService.buscarComMenosChamadosAbertos();
        } else {
            responsavel = getResponsavel(dto.responsavelId());
        }

        atualizarChamado(dto, chamado, responsavel);

        return toResponseDTO(chamadoRepository.save(chamado));
    }

    public ChamadoResponseDTO buscarPorId(Integer id) {
        Chamado chamado = getChamado(id);

        return toResponseDTO(chamado);
    }

    public List<ChamadoResponseDTO> listar() {
        return chamadoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public void deletar(Integer id) {
        Chamado chamado = getChamado(id);

        chamado.setStatus(ChamadoStatus.FECHADO);

        chamadoRepository.save(chamado);
    }

    // #region Helpers
    private Chamado getChamado(Integer id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Chamado não encontrado."));
    }

    private Responsavel getResponsavel(Integer id) {
        return responsavelService.buscarEntidadePorId(id);
    }

    private Chamado criarChamado(ChamadoRequestDTO dto, Responsavel responsavel) {
        Chamado chamado = new Chamado();

        chamado.setTitulo(dto.titulo());
        chamado.setDescricao(dto.descricao());
        try {
            chamado.setPrioridade(Prioridade.valueOf(dto.prioridade()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Prioridade inválida: " + dto.prioridade());
        }
        chamado.setStatus(ChamadoStatus.ABERTO);
        chamado.setResponsavel(responsavel);

        return chamado;
    }

    private void atualizarChamado(ChamadoRequestDTO dto, Chamado chamado, Responsavel responsavel) {
        chamado.setTitulo(dto.titulo());
        chamado.setDescricao(dto.descricao());
        try {
            chamado.setPrioridade(Prioridade.valueOf(dto.prioridade()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Prioridade inválida: " + dto.prioridade());
        }
        try {
            chamado.setStatus(ChamadoStatus.valueOf(dto.status()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Status inválido: " + dto.status());
        }
        chamado.setResponsavel(responsavel);
    }

    private ChamadoResponseDTO toResponseDTO(Chamado chamado) {
        return new ChamadoResponseDTO(
                chamado.getId(),
                chamado.getTitulo(),
                chamado.getDescricao(),
                chamado.getPrioridade().name(),
                chamado.getStatus().name(),
                chamado.getResponsavel().getId(),
                chamado.getResponsavel().getNome(),
                chamado.getDataAbertura().toString(),
                chamado.getDataAtualizacao().toString());
    }
    // //#endregion
}
