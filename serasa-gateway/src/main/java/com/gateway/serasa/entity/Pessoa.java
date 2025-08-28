package com.gateway.serasa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String documento;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoPessoa tipo;

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private Score score;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private List<Restricao> restricoes;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private List<Divida> dividas;

    private boolean ativo = true;

}
