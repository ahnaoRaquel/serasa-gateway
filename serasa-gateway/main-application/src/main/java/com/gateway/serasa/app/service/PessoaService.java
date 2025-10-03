package com.gateway.serasa.app.service;

import com.gateway.serasa.externalapi.dto.DocumentoValidatorResponseDTO;
import com.gateway.serasa.app.mapper.PessoaMapper;
import com.gateway.serasa.app.dto.PessoaResponseDTO;
import com.gateway.serasa.common.entity.Pessoa;
import com.gateway.serasa.common.exception.DocumentoInvalidoException;
import com.gateway.serasa.common.exception.PessoaNaoEncontradaException;
import com.gateway.serasa.app.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final DocumentoValidationService validationService;

    public PessoaResponseDTO ativarPessoa(String documento) {
        DocumentoValidatorResponseDTO validatorResponse = validationService.validarDocumento(documento);
        if (!validatorResponse.isValid()) {
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
        DocumentoValidatorResponseDTO validatorResponse = validationService.validarDocumento(documento);
        if (!validatorResponse.isValid()) {
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