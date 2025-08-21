package com.gateway.serasa.mock;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PessoaJson {

    private String documento;

    private String nome;

    private String tipo;

    private ScoreJson score;

    private List<RestricaoJson> restricoes;

    private List<DividaJson> dividas;
}
