package com.gateway.serasa.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PessoaJuridica extends Pessoa{

    private String cnpj;

}