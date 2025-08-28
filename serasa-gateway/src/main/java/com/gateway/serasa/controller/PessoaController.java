package com.gateway.serasa.controller;

import com.gateway.serasa.dto.PessoaResponseDTO;
import com.gateway.serasa.service.PessoaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/documento")
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