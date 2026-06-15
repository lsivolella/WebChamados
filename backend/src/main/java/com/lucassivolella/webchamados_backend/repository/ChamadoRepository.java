package com.lucassivolella.webchamados_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucassivolella.webchamados_backend.entity.Chamado;
import java.time.LocalDateTime;
import com.lucassivolella.webchamados_backend.enums.Prioridade;
import com.lucassivolella.webchamados_backend.enums.ChamadoStatus;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

    List<Chamado> findByResponsavel_id(Integer id);

    List<Chamado> findByDataAberturaBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Chamado> findByPrioridade(Prioridade prioridade);

    List<Chamado> findByStatus(ChamadoStatus status);
}
