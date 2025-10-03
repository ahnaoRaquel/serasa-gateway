package com.gateway.serasa.app.repository;

import com.gateway.serasa.common.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional<Pessoa> findByDocumento(String documento);

    List<Pessoa> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);

    List<Pessoa> findDistinctByRestricoesDataInclusaoBetween(LocalDate inicio, LocalDate fim);
}
