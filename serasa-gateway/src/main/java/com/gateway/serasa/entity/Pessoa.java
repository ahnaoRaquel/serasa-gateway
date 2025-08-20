package com.gateway.serasa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "score_id")
    private Score score;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Restricao> restricoes;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Divida> dividas;

}
