package com.gateway.serasa.app.mock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.serasa.common.entity.*;
import com.gateway.serasa.common.enums.TipoPessoa;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class MockDataLoader {

    private final ObjectMapper objectMapper;

    public MockDataLoader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Pessoa> carregarTodasPessoas() {
        try (InputStream inputStream = getClass().getResourceAsStream("/mock/mock-dados.json")) {
            List<PessoaJson> mockPessoas = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<PessoaJson>>() {}
            );

            return mockPessoas.stream()
                    .map(this::transformarParaEntidade)
                    .toList();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar dados do mock", e);
        }
    }

    public Optional<Pessoa> buscarPorDocumento(String documento) {
        return carregarTodasPessoas().stream()
                .filter(p -> p.getDocumento().equals(documento))
                .findFirst();
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
            restricao.setDataExclusao(r.getDataExclusao() != null ? LocalDate.parse(r.getDataExclusao()) : null);
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
