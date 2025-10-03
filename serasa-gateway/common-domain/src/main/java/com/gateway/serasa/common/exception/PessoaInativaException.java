package com.gateway.serasa.common.exception;

public class PessoaInativaException extends RuntimeException {

    public PessoaInativaException(String documento) {
        super("Documento " + documento + " já está desativada no sistema.");
    }

}