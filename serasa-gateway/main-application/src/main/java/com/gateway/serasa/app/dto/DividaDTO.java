package com.gateway.serasa.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DividaDTO {

    private String credor;

    private BigDecimal valor;

    private boolean emAberto;

}
