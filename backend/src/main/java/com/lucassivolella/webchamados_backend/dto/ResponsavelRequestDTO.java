package com.lucassivolella.webchamados_backend.dto;

import jakarta.validation.constraints.NotNull;

public record ResponsavelRequestDTO(

        @NotNull String nome,

        @NotNull String email) {
}
