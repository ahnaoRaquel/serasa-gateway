package com.gateway.serasa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Restricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    private String descricao;

    private boolean ativa;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
}