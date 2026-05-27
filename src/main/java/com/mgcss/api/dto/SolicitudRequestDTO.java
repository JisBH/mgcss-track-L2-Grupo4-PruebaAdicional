package com.mgcss.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Objeto de Transferencia de Datos (Record DTO) utilizado para encapsular
 * y validar la información requerida al crear una nueva solicitud desde el exterior.
 * * @param descripcion Descripción detallada de la incidencia o tarea a realizar. No puede estar en blanco.
 * @param clienteId Identificador numérico del cliente al que se le asociará esta nueva tarea. No puede ser nulo.
 */
public record SolicitudRequestDTO(
    @NotBlank(message = "La descripción es obligatoria")
    String descripcion,
    
    @NotNull(message = "El ID del cliente es obligatorio")
    Long clienteId
) {}