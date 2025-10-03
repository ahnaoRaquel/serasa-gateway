package com.gateway.serasa.service;

import com.gateway.serasa.dto.DocumentoValidatorResponseDTO;
import com.gateway.serasa.dto.PessoaMapper;
import com.gateway.serasa.dto.PessoaResponseDTO;
import com.gateway.serasa.entity.ConsultaHistorico;
import com.gateway.serasa.entity.Divida;
import com.gateway.serasa.entity.Pessoa;
import com.gateway.serasa.entity.Restricao;
import com.gateway.serasa.exception.DocumentoInvalidoException;
import com.gateway.serasa.exception.PessoaInativaException;
import com.gateway.serasa.exception.PessoaNaoEncontradaException;
import com.gateway.serasa.mock.MockService;
import com.gateway.serasa.repository.ConsultaHistoricoRepository;
import com.gateway.serasa.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultaSerasaService {

    private final PessoaRepository pessoaRepository;
    private final MockService mockService;
    private final ConsultaHistoricoRepository historicoRepository;
    private final DocumentoValidationService validationService;

    public PessoaResponseDTO consultarPorDocumento(String documento) {
        DocumentoValidatorResponseDTO validatorResponse = validationService.validarDocumento(documento);
        if (!validatorResponse.isValid()) {
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
        registrarConsultaHistorico(pessoa);

        return PessoaMapper.toDTO(pessoa);
    }

    private void registrarConsultaHistorico(Pessoa pessoa) {
        ConsultaHistorico historico = new ConsultaHistorico();
        historico.setPessoa(pessoa);
        historico.setDataConsulta(LocalDateTime.now());
        historicoRepository.save(historico);
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
        List<String> documentosInvalidos = new ArrayList<>();
        List<Pessoa> documentosValidos = new ArrayList<>();

        for (String doc : documentos) {
            DocumentoValidatorResponseDTO resultado = validationService.validarDocumento(doc);

            if (!resultado.isValid()) {
                documentosInvalidos.add(doc);
            } else {
                Pessoa pessoa = pessoaRepository.findByDocumento(doc)
                        .orElseGet(() -> mockService.buscarPorDocumento(doc)
                                .map(pessoaRepository::save)
                                .orElseThrow(() -> new PessoaNaoEncontradaException(doc)));

                if (!pessoa.isAtivo()) {
                    throw new PessoaInativaException(doc);
                }

                filtrarDividasEmAberto(pessoa);
                filtrarRestricoesEmAberto(pessoa);

                documentosValidos.add(pessoa);
            }
        }

        if (!documentosInvalidos.isEmpty()) {
            throw new DocumentoInvalidoException(String.join(", ", documentosInvalidos));
        }

        return documentosValidos.stream()
                .map(PessoaMapper::toDTO)
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
