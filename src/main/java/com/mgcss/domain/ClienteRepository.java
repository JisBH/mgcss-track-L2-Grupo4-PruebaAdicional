package com.mgcss.domain;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida que define el contrato de persistencia para la entidad {@link Cliente}.
 * Abstrae las tecnologías de almacenamiento secundario del núcleo del dominio.
 */
public interface ClienteRepository {
    
    /**
     * Persiste o actualiza un cliente en el sistema.
     * * @param cliente Instancia del dominio a guardar.
     * @return El cliente guardado con su ID asignado.
     */
    Cliente save(Cliente cliente);
    
    /**
     * Recupera un cliente por su identificador único.
     * * @param id Identificador numérico del cliente.
     * @return Un {@link Optional} que contiene al cliente si existe, o vacío en caso contrario.
     */
    Optional<Cliente> findById(Long id);
    
    /**
     * Recupera todos los clientes registrados en la plataforma.
     * * @return Lista de todos los clientes.
     */
    List<Cliente> findAll();
}