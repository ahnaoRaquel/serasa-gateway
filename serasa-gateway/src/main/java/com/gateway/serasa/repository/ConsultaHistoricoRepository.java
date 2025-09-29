package com.gateway.serasa.repository;

import com.gateway.serasa.dto.ConsultaHistoricoResumoResponseDTO;
import com.gateway.serasa.entity.ConsultaHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaHistoricoRepository extends JpaRepository<ConsultaHistorico, Long> {

    @Query("""
           SELECT new com.gateway.serasa.dto.ConsultaHistoricoResumoResponseDTO(
               h.pessoa.documento,
               h.pessoa.nome,
               COUNT(h)
           )
           FROM ConsultaHistorico h
           GROUP BY h.pessoa.documento, h.pessoa.nome
           ORDER BY COUNT(h) DESC
           """)
    List<ConsultaHistoricoResumoResponseDTO> findResumoTotalConsultas();

    @Query("""
           SELECT new com.gateway.serasa.dto.ConsultaHistoricoResumoResponseDTO(
               h.pessoa.documento,
               h.pessoa.nome,
               COUNT(h)
           )
           FROM ConsultaHistorico h
           WHERE h.dataConsulta BETWEEN :inicio AND :fim
           GROUP BY h.pessoa.documento, h.pessoa.nome
           ORDER BY COUNT(h) DESC
           """)
    List<ConsultaHistoricoResumoResponseDTO> findResumoConsultasPorPeriodo(LocalDateTime inicio, LocalDateTime fim);

    List<ConsultaHistorico> findByDataConsultaBetween(LocalDateTime inicio, LocalDateTime fim);
}