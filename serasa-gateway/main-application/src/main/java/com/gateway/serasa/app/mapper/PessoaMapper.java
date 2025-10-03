package com.gateway.serasa.app.mapper;

import com.gateway.serasa.app.dto.DividaDTO;
import com.gateway.serasa.app.dto.PessoaResponseDTO;
import com.gateway.serasa.app.dto.RestricaoDTO;
import com.gateway.serasa.app.dto.ScoreDTO;
import com.gateway.serasa.common.entity.Divida;
import com.gateway.serasa.common.entity.Pessoa;
import com.gateway.serasa.common.entity.Restricao;

import java.util.List;
import java.util.stream.Collectors;

public class PessoaMapper {

    public static PessoaResponseDTO toDTO(Pessoa pessoa) {
        PessoaResponseDTO dto = new PessoaResponseDTO();
        dto.setDocumento(pessoa.getDocumento());
        dto.setNome(pessoa.getNome());
        dto.setTipo(pessoa.getTipo().name());

        if (pessoa.getScore() != null) {
            ScoreDTO scoreDTO = new ScoreDTO();
            scoreDTO.setPontos(pessoa.getScore().getPontos());
            dto.setScore(scoreDTO);
        }

        dto.setRestricoes(mapRestricoes(pessoa.getRestricoes()));
        dto.setDividas(mapDividas(pessoa.getDividas()));

        return dto;
    }

    private static List<RestricaoDTO> mapRestricoes(List<Restricao> restricoes) {
        if (restricoes == null) return null;
        return restricoes.stream()
                .map(r -> {
                    RestricaoDTO dto = new RestricaoDTO();
                    dto.setDescricao(r.getDescricao());
                    dto.setDataInclusao(r.getDataInclusao());
                    dto.setDataExclusao(r.getDataExclusao());
                    dto.setEmAberto(r.isEmAberto());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private static List<DividaDTO> mapDividas(List<Divida> dividas) {
        if (dividas == null) return null;
        return dividas.stream()
                .map(d -> {
                    DividaDTO dto = new DividaDTO();
                    dto.setCredor(d.getCredor());
                    dto.setValor(d.getValor());
                    dto.setEmAberto(d.isEmAberto());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
