package com.gateway.serasa.common.exception;

public class PessoaNaoEncontradaException extends RuntimeException {

    public PessoaNaoEncontradaException(String documento) {
        super("Documento " + documento + " não foi encontrada no sistema.");
    }

}