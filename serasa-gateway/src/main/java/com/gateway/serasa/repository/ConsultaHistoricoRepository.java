package com.gateway.serasa.repository;

import com.gateway.serasa.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaHistoricoRepository extends JpaRepository<Pessoa, Long> {
}
