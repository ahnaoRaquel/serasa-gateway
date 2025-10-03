package com.gateway.serasa.client;

import com.gateway.serasa.dto.DocumentoValidatorResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "documentoValidatorClient", url = "${invertexto.api.url}")
public interface DocumentoValidatorClient {

    @GetMapping("/validator")
    DocumentoValidatorResponseDTO validarDocumento(
            @RequestParam("token") String token,
            @RequestParam("value") String value
    );
}