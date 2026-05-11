package com.mgcss.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz de Spring Data JPA para la persistencia de la entidad {@link SolicitudEntity}.
 * * En el contexto de la Arquitectura Hexagonal, esta interfaz es un detalle de implementación
 * tecnológico que pertenece puramente a la capa de Infraestructura.
 * No debe ser utilizada directamente por la capa de Dominio ni por los Servicios de Aplicación.
 * Su uso está restringido a los Adaptadores (como {@link SolicitudRepositoryAdapter}), 
 * quienes se encargan de traducir las operaciones del dominio a consultas de base de datos.
 */
@Repository
public interface JpaSolicitudRepository extends JpaRepository<SolicitudEntity, Long> {
    // Spring Data JPA provee automáticamente la implementación de los métodos CRUD
    // (save, findById, findAll, etc.) en tiempo de ejecución.
}