package com.gateway.serasa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Divida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String credor;

    private BigDecimal valor;

    private boolean emAberto;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

}