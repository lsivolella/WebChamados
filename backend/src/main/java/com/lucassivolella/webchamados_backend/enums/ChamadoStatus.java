package com.lucassivolella.webchamados_backend.enums;

public enum ChamadoStatus {
	ABERTO("Aberto"),
	EM_ANDAMENTO("Em andamento"),
	RESOLVIDO("Resolvido"),
	FECHADO("Fechado");

	private final String descricao;

	private ChamadoStatus(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
