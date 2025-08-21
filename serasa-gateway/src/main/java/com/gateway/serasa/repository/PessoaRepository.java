package com.gateway.serasa.repository;

import com.gateway.serasa.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional<Pessoa> findByDocumento(String documento);

    List<Pessoa> findByDocumentoIn(List<String> documentos);
}
