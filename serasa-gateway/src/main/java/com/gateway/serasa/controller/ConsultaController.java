package com.gateway.serasa.controller;

import com.gateway.serasa.dto.PessoaResponseDTO;
import com.gateway.serasa.service.ConsultaSerasaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consulta")
public class ConsultaController {

    private final ConsultaSerasaService consultaService;

    public ConsultaController(ConsultaSerasaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping("/{documento}")
    public ResponseEntity<PessoaResponseDTO> consultarPorDocumento(@PathVariable String documento) {
        return ResponseEntity.ok(consultaService.consultarPorDocumento(documento));
    }

    @PostMapping("/lote")
    public ResponseEntity<List<PessoaResponseDTO>> consultarPorLote(@RequestBody List<String> documentos) {
        return ResponseEntity.ok(consultaService.consultarPorLote(documentos));
    }
}
