package com.gateway.serasa.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PessoaFisica extends Pessoa{

    private String cpf;

}
