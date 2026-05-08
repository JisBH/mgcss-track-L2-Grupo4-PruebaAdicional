package com.mgcss.api.dto;

import java.time.LocalDate;
import java.util.List;

public record SolicitudResponseDTO(
    long id,
    String estado,
    LocalDate fechaCreacion,
    Long tecnicoId, 
    List<String> historial 
) {
}