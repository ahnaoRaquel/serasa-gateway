package com.gateway.serasa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ConsultaHistoricoResponseDTO {
    private String documento;
    private String nomePessoa;
    private LocalDateTime dataConsulta;
}