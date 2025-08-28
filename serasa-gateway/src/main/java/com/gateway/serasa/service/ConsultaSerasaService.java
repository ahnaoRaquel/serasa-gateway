package com.gateway.serasa.service;

import com.gateway.serasa.dto.PessoaMapper;
import com.gateway.serasa.dto.PessoaResponseDTO;
import com.gateway.serasa.entity.Divida;
import com.gateway.serasa.entity.Pessoa;
import com.gateway.serasa.entity.Restricao;
import com.gateway.serasa.exception.DocumentoInvalidoException;
import com.gateway.serasa.exception.PessoaInativaException;
import com.gateway.serasa.exception.PessoaNaoEncontradaException;
import com.gateway.serasa.mock.MockService;
import com.gateway.serasa.repository.PessoaRepository;
import com.gateway.serasa.util.ValidadorDocumento;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConsultaSerasaService {

    private final PessoaRepository pessoaRepository;

    private final MockService mockService;

    public ConsultaSerasaService(PessoaRepository pessoaRepository, MockService mockService) {
        this.pessoaRepository = pessoaRepository;
        this.mockService = mockService;
    }

    public PessoaResponseDTO consultarPorDocumento(String documento) {
        if (!ValidadorDocumento.validarDocumento(documento)) {
            throw new DocumentoInvalidoException(documento);
        }

        Pessoa pessoa = pessoaRepository.findByDocumento(documento)
                .orElseGet(() -> mockService.buscarPorDocumento(documento)
                        .map(pessoaRepository::save)
                        .orElseThrow(() -> new PessoaNaoEncontradaException(documento)));

        if (!pessoa.isAtivo()) {
            throw new PessoaInativaException(documento);
        }

        filtrarDividasEmAberto(pessoa);
        filtrarRestricoesEmAberto(pessoa);

        return PessoaMapper.toDTO(pessoa);
    }

    private void filtrarRestricoesEmAberto(Pessoa pessoa) {
        List<Restricao> restricoesAtivas = pessoa.getRestricoes().stream()
                .filter(r -> r.isEmAberto() && r.getDataExclusao() == null)
                .toList();
        pessoa.setRestricoes(restricoesAtivas);
    }

    private void filtrarDividasEmAberto(Pessoa pessoa) {
        List<Divida> dividasAbertas = pessoa.getDividas().stream()
                .filter(Divida::isEmAberto)
                .toList();
        pessoa.setDividas(dividasAbertas);
    }

    public List<PessoaResponseDTO> consultarPorLote(List<String> documentos) {
        List<String> documentosInvalidos = documentos.stream()
                .filter(doc -> !ValidadorDocumento.validarDocumento(doc))
                .toList();

        if (!documentosInvalidos.isEmpty()) {
            throw new DocumentoInvalidoException(String.join(", ", documentosInvalidos));
        }

        return documentos.stream()
                .map(this::consultarPorDocumento)
                .toList();
    }

    public List<PessoaResponseDTO> buscarPorNome(String nome) {
        return pessoaRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome)
                .stream()
                .map(PessoaMapper::toDTO)
                .toList();
    }

    public List<PessoaResponseDTO> buscarPorRestricoesEntreDatas(LocalDate inicio, LocalDate fim) {
        return pessoaRepository.findDistinctByRestricoesDataInclusaoBetween(inicio, fim)
                .stream()
                .map(PessoaMapper::toDTO)
                .toList();
    }
}
