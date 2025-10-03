package com.gateway.serasa.app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ConsultaHistoricoResumoResponseDTO {
    private String documento;
    private String nomePessoa;
    private Long totalConsultas;
}