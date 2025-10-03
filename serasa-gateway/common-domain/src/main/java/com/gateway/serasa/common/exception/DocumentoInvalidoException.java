package com.gateway.serasa.common.exception;

public class DocumentoInvalidoException extends RuntimeException {

    public DocumentoInvalidoException(String documento) {
        super("Documento inválido: " + documento);
    }

}