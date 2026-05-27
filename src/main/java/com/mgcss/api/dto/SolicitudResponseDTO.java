package com.mgcss.api.dto;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(description = "DTO que representa la respuesta detallada de una solicitud")
public record SolicitudResponseDTO(
		@Schema(description = "Identificador único de la solicitud", example = "1")
	    long id,
	    String descripcion,
	    
	    @Schema(description = "Estado actual de la solicitud", example = "ABIERTA")
	    String estado,
	    
	    @Schema(description = "Fecha en la que se realizo la solicitud", example = "2026-05-15")
	    LocalDate fechaCreacion,
	    
	    @Schema(description = "ID del técnico asignado", example = "10")
	    Long tecnicoId, 
	    
	    ClienteDTO cliente,
	    
	    @Schema(description = "Lista de estados por los que ha pasado la solicitud", example = "[\"ABIERTA\", \"ASIGNADA\"]")
	    List<String> historial 
	) {
	public record ClienteDTO(Long id, String nombre) {}
}