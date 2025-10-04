package com.gateway.serasa.app.controller;

import com.gateway.serasa.app.dto.PessoaResponseDTO;
import com.gateway.serasa.app.service.PessoaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documento")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PutMapping("/{documento}/ativar")
    public ResponseEntity<PessoaResponseDTO> ativar(@PathVariable String documento) {
        PessoaResponseDTO dto = pessoaService.ativarPessoa(documento);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{documento}/desativar")
    public ResponseEntity<PessoaResponseDTO> desativar(@PathVariable String documento) {
        PessoaResponseDTO dto = pessoaService.desativarPessoa(documento);
        return ResponseEntity.ok(dto);
    }
}