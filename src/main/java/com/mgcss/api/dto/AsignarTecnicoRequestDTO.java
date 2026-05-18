package com.mgcss.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * DTO (Data Transfer Object) utilizado para recibir y validar la petición de asignación de un técnico.
 * Captura el payload JSON enviado por el cliente para la operación de asignación.
 *
 * @param tecnicoId El identificador único del técnico que se desea asignar a la solicitud.
 * Esta propiedad es obligatoria para procesar la petición.
 */
public record AsignarTecnicoRequestDTO(
    @NotNull(message = "El ID del técnico no puede ser nulo")
    @Schema(description = "ID del técnico que se va a asignar", example = "5")
    Long tecnicoId
) {
}