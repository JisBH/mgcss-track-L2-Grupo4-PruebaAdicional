package com.mgcss.api.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO (Data Transfer Object) que representa la respuesta de una Solicitud.
 * Se utiliza para enviar los datos consolidados desde el servidor hacia el cliente (frontend).
 * Al ser un 'record', es inmutable y garantiza la integridad de la respuesta.
 *
 * @param id            Identificador único de la solicitud.
 * @param estado        Estado actual de la solicitud en formato de texto (ej. "ABIERTA", "EN_PROCESO").
 * @param fechaCreacion Fecha en la que se registró la solicitud en el sistema.
 * @param tecnicoId     Identificador del técnico asignado (puede ser null si aún no ha sido asignado).
 * @param historial     Lista ordenada cronológicamente con los nombres de los estados por los que ha pasado.
 */
public record SolicitudResponseDTO(
    long id,
    String estado,
    LocalDate fechaCreacion,
    Long tecnicoId, 
    List<String> historial 
) { 
}