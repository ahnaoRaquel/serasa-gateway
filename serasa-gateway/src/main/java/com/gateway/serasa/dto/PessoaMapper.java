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
                    RestricaoDTO rdto = new RestricaoDTO();
                    rdto.setDescricao(r.getDescricao());
                    rdto.setDataInclusao(r.getDataInclusao());
                    return rdto;
                }).collect(Collectors.toList());
        dto.setRestricoes(restricoesDTO);

        List<DividaDTO> dividasDTO = pessoa.getDividas().stream()
                .map(d -> {
                    DividaDTO ddto = new DividaDTO();
                    ddto.setCredor(d.getCredor());
                    ddto.setValor(d.getValor());
                    ddto.setEmAberto(d.isEmAberto());
                    return ddto;
                }).collect(Collectors.toList());
        dto.setDividas(dividasDTO);

        return dto;
    }
}
