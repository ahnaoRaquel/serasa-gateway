package com.gateway.serasa.controller;

import com.gateway.serasa.dto.ConsultaHistoricoPeriodoRequestDTO;
import com.gateway.serasa.dto.ConsultaHistoricoResponseDTO;
import com.gateway.serasa.dto.ConsultaHistoricoResumoResponseDTO;
import com.gateway.serasa.service.ConsultaHistoricoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historico")
@RequiredArgsConstructor
public class ConsultaHistoricoController {

    private final ConsultaHistoricoService historicoService;

    @GetMapping("/consultas")
    public List<ConsultaHistoricoResponseDTO> listarTodasConsultas() {
        return historicoService.listarTodasConsultas();
    }

    @GetMapping("/consultas/resumo")
    public List<ConsultaHistoricoResumoResponseDTO> listarResumoTotalConsultas() {
        return historicoService.listarResumoTotalConsultas();
    }

    @PostMapping("/consultas/periodo")
    public List<ConsultaHistoricoResponseDTO> listarConsultasPorPeriodo(
            @RequestBody ConsultaHistoricoPeriodoRequestDTO request) {
        return historicoService.listarConsultasPorPeriodo(request);
    }

    @PostMapping("/consultas/resumo/periodo")
    public List<ConsultaHistoricoResumoResponseDTO> listarResumoPorPeriodo(
            @RequestBody ConsultaHistoricoPeriodoRequestDTO request) {
        return historicoService.listarResumoPorPeriodo(request);
    }
}