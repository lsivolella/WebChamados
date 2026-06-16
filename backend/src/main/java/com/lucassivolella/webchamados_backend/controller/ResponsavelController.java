package com.lucassivolella.webchamados_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lucassivolella.webchamados_backend.dto.ResponsavelRequestDTO;
import com.lucassivolella.webchamados_backend.dto.ResponsavelResponseDTO;
import com.lucassivolella.webchamados_backend.service.ResponsavelService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/responsaveis")
public class ResponsavelController {

    private final ResponsavelService service;

    public ResponsavelController(ResponsavelService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponsavelResponseDTO criar(@Valid @RequestBody ResponsavelRequestDTO dto) {
        return service.criar(dto);
    }

    @GetMapping("/{id}")
    public ResponsavelResponseDTO buscar(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @GetMapping
    public List<ResponsavelResponseDTO> listar() {
        return service.listar();
    }
}
