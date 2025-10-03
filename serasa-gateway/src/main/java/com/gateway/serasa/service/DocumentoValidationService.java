package com.gateway.serasa.service;

import com.gateway.serasa.client.DocumentoValidatorClient;
import com.gateway.serasa.dto.DocumentoValidatorResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentoValidationService {

    private final DocumentoValidatorClient validatorClient;

    @Value("${invertexto.api.token}")
    private String token;

    public DocumentoValidatorResponseDTO validarDocumento(String documento) {
        DocumentoValidatorResponseDTO response = validatorClient.validarDocumento(token, documento);

        if (response == null) {
            throw new RuntimeException("Erro ao consultar API externa.");
        }

        return response;
    }
}