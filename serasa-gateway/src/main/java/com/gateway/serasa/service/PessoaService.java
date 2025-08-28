package com.gateway.serasa.service;

import com.gateway.serasa.dto.PessoaMapper;
import com.gateway.serasa.dto.PessoaResponseDTO;
import com.gateway.serasa.entity.Pessoa;
import com.gateway.serasa.exception.DocumentoInvalidoException;
import com.gateway.serasa.exception.PessoaNaoEncontradaException;
import com.gateway.serasa.repository.PessoaRepository;
import com.gateway.serasa.util.ValidadorDocumento;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public PessoaResponseDTO ativarPessoa(String documento) {
        if (!ValidadorDocumento.validarDocumento(documento)) {
            throw new DocumentoInvalidoException(documento);
        }

        Pessoa pessoa = pessoaRepository.findByDocumento(documento)
                .orElseThrow(() -> new PessoaNaoEncontradaException(documento));

        if (pessoa.isAtivo()) {
            throw new IllegalStateException("Pessoa já está ativa no sistema.");
        }

        pessoa.setAtivo(true);
        pessoaRepository.save(pessoa);

        return PessoaMapper.toDTO(pessoa);
    }

    public PessoaResponseDTO desativarPessoa(String documento) {
        if (!ValidadorDocumento.validarDocumento(documento)) {
            throw new DocumentoInvalidoException(documento);
        }

        Pessoa pessoa = pessoaRepository.findByDocumento(documento)
                .orElseThrow(() -> new PessoaNaoEncontradaException(documento));

        if (!pessoa.isAtivo()) {
            throw new IllegalStateException("Pessoa já está desativada no sistema.");
        }

        pessoa.setAtivo(false);
        pessoaRepository.save(pessoa);

        return PessoaMapper.toDTO(pessoa);
    }
}