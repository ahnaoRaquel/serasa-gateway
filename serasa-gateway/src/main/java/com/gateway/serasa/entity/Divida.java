package com.gateway.serasa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "divida")
public class Divida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String credor;

    private BigDecimal valor;


    //USAR PRA FAZER EXCLUSÃO LOGICA NA HORA DE CONSULTAR, SE TIVER EM ABERTO TRAZER A DIVIDA, SE FOR FALSE NÃO TRAZER NA CONSULTA
    private boolean emAberto;

    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

}