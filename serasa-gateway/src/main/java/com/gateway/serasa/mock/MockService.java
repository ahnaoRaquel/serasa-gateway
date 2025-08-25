package com.gateway.serasa.mock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.serasa.entity.*;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MockService {

    private final ObjectMapper objectMapper;

    public MockService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Optional<Pessoa> buscarPorDocumento(String documento) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/mock/mock-dados.json");
            List<PessoaJson> mockPessoas = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<PessoaJson>>() {}
            );

            for (PessoaJson pessoa : mockPessoas) {
                if (pessoa.getDocumento().equals(documento)) {
                    return Optional.of(transformarParaEntidade(pessoa));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Pessoa transformarParaEntidade(PessoaJson json) {
        Pessoa pessoa = new Pessoa();
        pessoa.setDocumento(json.getDocumento());
        pessoa.setNome(json.getNome());
        pessoa.setTipo(TipoPessoa.valueOf(json.getTipo()));

        Score score = new Score();
        score.setPontos(json.getScore().getPontos());
        score.setPessoa(pessoa);
        pessoa.setScore(score);

        List<Restricao> restricoes = json.getRestricoes().stream().map(r -> {
            Restricao restricao = new Restricao();
            restricao.setDescricao(r.getDescricao());
            restricao.setDataInclusao(LocalDate.parse(r.getDataInclusao()));
            restricao.setDataExclusao(LocalDate.parse(r.getDataExclusao()));
            restricao.setEmAberto(r.isEmAberto());
            restricao.setPessoa(pessoa);
            return restricao;
        }).toList();
        pessoa.setRestricoes(restricoes);

        List<Divida> dividas = json.getDividas().stream().map(d -> {
            Divida divida = new Divida();
            divida.setCredor(d.getCredor());
            divida.setValor(BigDecimal.valueOf(d.getValor()));
            divida.setEmAberto(d.isEmAberto());
            divida.setPessoa(pessoa);
            return divida;
        }).toList();
        pessoa.setDividas(dividas);

        return pessoa;
    }

}