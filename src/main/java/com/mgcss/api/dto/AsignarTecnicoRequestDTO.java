package com.mgcss.api.dto;

import jakarta.validation.constraints.NotNull;

public record AsignarTecnicoRequestDTO(
    @NotNull(message = "El ID del técnico no puede ser nulo")
    Long tecnicoId
) {
}