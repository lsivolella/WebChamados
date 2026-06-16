package com.lucassivolella.webchamados_backend.dto;

import jakarta.validation.constraints.NotNull;

public record ChamadoRequestDTO(
        @NotNull String titulo,

        @NotNull String descricao,

        @NotNull String prioridade,

        @NotNull String status,

        Integer responsavelId,

        Boolean atribuicaoAutomatica) {

}
