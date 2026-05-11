package com.mgcss.domain;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para la persistencia de la entidad {@link Solicitud}.
 * Define las operaciones necesarias que el dominio requiere para almacenar y recuperar solicitudes,
 * aislando la lógica de negocio de la tecnología de base de datos subyacente.
 */
public interface SolicitudRepository {
	/**
     * Guarda una solicitud en el sistema de persistencia.
     * Si la solicitud es nueva, se crea; si ya existe, se actualiza.
     *
     * @param solicitud La entidad de dominio a guardar.
     * @return La solicitud guardada con su estado actualizado (por ejemplo, con el ID generado).
     */
    Solicitud save(Solicitud solicitud);
    
    /**
     * Busca una solicitud por su identificador único.
     *
     * @param id El identificador único de la solicitud.
     * @return Un {@link Optional} que contiene la solicitud si se encuentra, o vacío si no existe.
     */
    Optional<Solicitud> findById(Long id);
    
    /**
     * Recupera todas las solicitudes registradas en el sistema.
     *
     * @return Una lista de todas las solicitudes de dominio.
     */
    List<Solicitud> findAll();
}
