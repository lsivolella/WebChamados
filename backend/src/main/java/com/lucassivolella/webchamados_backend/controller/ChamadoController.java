package com.lucassivolella.webchamados_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lucassivolella.webchamados_backend.dto.ChamadoRequestDTO;
import com.lucassivolella.webchamados_backend.dto.ChamadoResponseDTO;
import com.lucassivolella.webchamados_backend.service.ChamadoService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService service;

    public ChamadoController(ChamadoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChamadoResponseDTO criar(@Valid @RequestBody ChamadoRequestDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    public ChamadoResponseDTO atualizar(@PathVariable Integer id,
            @Valid @RequestBody ChamadoRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @GetMapping("/{id}")
    public ChamadoResponseDTO buscar(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @GetMapping
    public List<ChamadoResponseDTO> listar() {
        return service.listar();
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}
