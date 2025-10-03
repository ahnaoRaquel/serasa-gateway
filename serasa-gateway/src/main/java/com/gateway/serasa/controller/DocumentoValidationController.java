package com.gateway.serasa.controller;

import com.gateway.serasa.dto.DocumentoValidatorResponseDTO;
import com.gateway.serasa.service.DocumentoValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documentos")
@RequiredArgsConstructor
public class DocumentoValidationController {

    private final DocumentoValidationService validationService;

    @GetMapping("/validar/{documento}")
    public ResponseEntity<DocumentoValidatorResponseDTO> validarDocumento(@PathVariable String documento) {
        DocumentoValidatorResponseDTO response = validationService.validarDocumento(documento);
        return ResponseEntity.ok(response);
    }
}