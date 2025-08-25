package com.gateway.serasa.dto;

import com.gateway.serasa.entity.Pessoa;
import com.gateway.serasa.entity.Score;

import java.util.List;
import java.util.stream.Collectors;

public class PessoaMapper {

    public static PessoaResponseDTO toDTO(Pessoa pessoa) {
        PessoaResponseDTO dto = new PessoaResponseDTO();
        dto.setDocumento(pessoa.getDocumento());
        dto.setNome(pessoa.getNome());
        dto.setTipo(pessoa.getTipo().name());

        Score score = pessoa.getScore();
        if (score != null) {
            ScoreDTO scoreDTO = new ScoreDTO();
            scoreDTO.setPontos(score.getPontos());
            dto.setScore(scoreDTO);
        }

        List<RestricaoDTO> restricoesDTO = pessoa.getRestricoes().stream()
                .map(r -> {
                    RestricaoDTO restricao = new RestricaoDTO();
                    restricao.setDescricao(r.getDescricao());
                    restricao.setDataInclusao(r.getDataInclusao());
                    restricao.setDataExclusao(r.getDataExclusao());
                    restricao.setEmAberto(r.isEmAberto());
                    return restricao;
                }).collect(Collectors.toList());
        dto.setRestricoes(restricoesDTO);

        List<DividaDTO> dividasDTO = pessoa.getDividas().stream()
                .map(d -> {
                    DividaDTO divida = new DividaDTO();
                    divida.setCredor(d.getCredor());
                    divida.setValor(d.getValor());
                    divida.setEmAberto(d.isEmAberto());
                    return divida;
                }).collect(Collectors.toList());
        dto.setDividas(dividasDTO);

        return dto;
    }
}
