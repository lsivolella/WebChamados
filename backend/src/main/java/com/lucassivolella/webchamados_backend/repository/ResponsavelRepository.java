package com.lucassivolella.webchamados_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lucassivolella.webchamados_backend.entity.Responsavel;
import java.util.Optional;

public interface ResponsavelRepository extends JpaRepository<Responsavel, Integer> {

    Optional<Responsavel> findByNome(String nome);

    Optional<Responsavel> findByEmail(String email);

    @Query("SELECT r FROM Responsavel r LEFT JOIN Chamado c ON c.responsavel = r AND c.status NOT IN ('RESOLVIDO', 'FECHADO') GROUP BY r ORDER BY COUNT(c) ASC LIMIT 1")
    Optional<Responsavel> findResponsavelComMenosChamadosAbertos();
}
