package com.lucassivolella.webchamados_backend.dto;

public record ChamadoResponseDTO(
        Integer id,

        String titulo,

        String descricao,

        String prioridade,

        String status,

        Integer responsavelId,

        String responsavelNome,

        String dataAbertura,

        String dataAtualizacao) {
}
