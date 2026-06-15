package com.lucassivolella.webchamados_backend.entity;

import java.time.LocalDateTime;

import com.lucassivolella.webchamados_backend.enums.Prioridade;
import com.lucassivolella.webchamados_backend.enums.ChamadoStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "chamados")
public class Chamado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 100)
	private String titulo;

	@Column(nullable = false, length = 255)
	private String descricao;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Prioridade prioridade;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ChamadoStatus status;

	@ManyToOne
	@JoinColumn(name = "responsavel_id", nullable = false)
	private Responsavel responsavel;

	@Column(nullable = false, updatable = false)
	private LocalDateTime dataAbertura;

	@Column(nullable = false)
	private LocalDateTime dataAtualizacao;

	public Chamado() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

	public ChamadoStatus getStatus() {
		return status;
	}

	public void setStatus(ChamadoStatus status) {
		this.status = status;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public LocalDateTime getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	@PrePersist
	private void prePersist() {
		LocalDateTime agora = LocalDateTime.now();

		dataAbertura = agora;
		dataAtualizacao = agora;
	}

	@PreUpdate
	private void preUpdate() {
		dataAtualizacao = LocalDateTime.now();
	}
}
