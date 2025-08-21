package com.gateway.serasa.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RestricaoDTO {

    private String descricao;

    private LocalDate dataInclusao;

}
