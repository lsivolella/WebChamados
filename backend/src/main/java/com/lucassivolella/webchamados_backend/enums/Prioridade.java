package com.lucassivolella.webchamados_backend.enums;

public enum Prioridade {
	BAIXA("Baixa"),
	MEDIA("Média"),
	ALTA("Alta");

	private final String descricao;

	private Prioridade(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
