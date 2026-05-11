package com.mgcss.domain;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para la persistencia de la entidad {@link Tecnico}.
 * Abstrae el mecanismo de almacenamiento de los técnicos del sistema.
 */
public interface TecnicoRepository {

    /**
     * Guarda un técnico en el sistema de persistencia.
     *
     * @param tecnico La entidad de dominio del técnico a guardar.
     * @return El técnico guardado con su estado actualizado.
     */
    Tecnico save(Tecnico tecnico);

    /**
     * Busca un técnico por su identificador único.
     *
     * @param id El identificador único del técnico.
     * @return Un {@link Optional} que contiene el técnico si se encuentra, o vacío si no existe.
     */
    Optional<Tecnico> findById(Long id);

    /**
     * Recupera todos los técnicos registrados en el sistema.
     *
     * @return Una lista de todos los técnicos de dominio.
     */
    List<Tecnico> findAll();
}