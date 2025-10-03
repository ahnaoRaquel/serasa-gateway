package com.gateway.serasa.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PessoaResponseDTO {

    private String documento;

    private String nome;

    private String tipo;

    private ScoreDTO score;

    private List<RestricaoDTO> restricoes;

    private List<DividaDTO> dividas;
}
