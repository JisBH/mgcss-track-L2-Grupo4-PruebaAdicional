package com.mgcss.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz de Spring Data JPA para la entidad de base de datos de Técnico.
 * Es de uso interno para la capa de infraestructura.
 */
@Repository
public interface JpaTecnicoRepository extends JpaRepository<TecnicoEntity, Long> {}

