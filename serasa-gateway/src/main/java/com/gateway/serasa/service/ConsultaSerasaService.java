package com.gateway.serasa.service;

import com.gateway.serasa.entity.Pessoa;
import com.gateway.serasa.mock.MockService;
import com.gateway.serasa.repository.PessoaRepository;
import com.gateway.serasa.util.ValidadorDocumento;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultaSerasaService {

    private final PessoaRepository pessoaRepository;

    private final MockService mockService;

    public ConsultaSerasaService(PessoaRepository pessoaRepository, MockService mockService) {
        this.pessoaRepository = pessoaRepository;
        this.mockService = mockService;
    }


    public Pessoa consultarPorDocumento(String documento) {
        if (!ValidadorDocumento.validarDocumento(documento)) {
            throw new IllegalArgumentException("Documento inválido: " + documento);
        }

        Optional<Pessoa> pessoaExistente = pessoaRepository.findByDocumento(documento);

        if (pessoaExistente.isPresent()) {
            return pessoaExistente.get();
        }

        Pessoa pessoa = mockService.buscarPorDocumento(documento)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado no serasa: " + documento));

        return pessoaRepository.save(pessoa);
    }

    public Collection<Pessoa> consultarPorLote(List<String> documentos) {
        List<String> documentosInvalidos = documentos.stream()
                .filter(doc -> !ValidadorDocumento.validarDocumento(doc))
                .collect(Collectors.toList());

        if (!documentosInvalidos.isEmpty()) {
            throw new IllegalArgumentException("Documentos inválidos no lote: " + documentosInvalidos);
        }

        return documentos.stream()
                .map(this::consultarPorDocumento)
                .toList();
    }
}
