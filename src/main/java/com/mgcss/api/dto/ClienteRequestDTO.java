package com.mgcss.api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (Record) que representa la carga útil (Payload)
 * requerida de forma externa para dar de alta un Cliente.
 * * @param nombre Nombre del cliente. No se permiten valores vacíos o nulos.
 */
public record ClienteRequestDTO(
    @NotBlank(message = "El nombre del cliente es obligatorio")
    String nombre
) {}