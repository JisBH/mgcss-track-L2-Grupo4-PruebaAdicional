package com.mgcss.infraestructure.persistence;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.SolicitudRepository;
import com.mgcss.domain.Tecnico;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de persistencia para la entidad {@link Solicitud}.
 * Implementa el puerto de salida {@link SolicitudRepository} definido en el dominio.
 * * Este componente se encarga de la traducción técnica entre los objetos de negocio
 * y las entidades de JPA, permitiendo que el servicio de dominio trabaje sin
 * conocimiento de la estructura de las tablas o de la tecnología Spring Data.
 */
@Component
public class SolicitudRepositoryAdapter implements SolicitudRepository {

    private final JpaSolicitudRepository jpaRepository;

    /**
     * Constructor para la inyección de dependencias.
     * @param jpaRepository Repositorio de Spring Data JPA.
     */
    public SolicitudRepositoryAdapter(JpaSolicitudRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Solicitud save(Solicitud solicitud) {
        SolicitudEntity entity = toEntity(solicitud);
        SolicitudEntity savedEntity = jpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Solicitud> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Solicitud> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    // --- MAPPERS (Traducción de capas) ---

    /**
     * Transforma un objeto de dominio en una entidad de persistencia.
     * Maneja la conversión del técnico asignado y el historial de estados.
     */
    private SolicitudEntity toEntity(Solicitud solicitud) {
        SolicitudEntity entity = new SolicitudEntity();
        
        // Mapeo de campos básicos
        if (solicitud.getId() > 0) {
            entity.setId(solicitud.getId());
        }
        entity.setEstado(solicitud.getEstado());
        entity.setFechaCreacion(solicitud.getFechaCreacion());
        
        entity.setDescripcion(solicitud.getDescripcion());
        // Mapeo de la colección de historial
        entity.setHistorialEstados(solicitud.getHistorialEstados());
        
        // Mapeo de la relación con Técnico
        if (solicitud.getTecnico() != null) {
            TecnicoEntity tecnicoEntity = new TecnicoEntity(solicitud.getTecnico().isActivo());
            tecnicoEntity.setId(solicitud.getTecnico().getId());
            entity.setTecnico(tecnicoEntity);
        }
        
        if (solicitud.getCliente() != null) {
            ClienteEntity clienteEntity = new ClienteEntity(solicitud.getCliente().getNombre());
            clienteEntity.setId(solicitud.getCliente().getId());
            entity.setCliente(clienteEntity);
        }
        return entity;
    }

    /**
     * Transforma una entidad de persistencia en un objeto de dominio puro.
     * Reconstruye el objeto usando el constructor específico de reconstrucción.
     */
    private Solicitud toDomain(SolicitudEntity entity) {
        Tecnico tecnico = null;
        if (entity.getTecnico() != null) {
            tecnico = new Tecnico(entity.getTecnico().getId(), entity.getTecnico().isActivo());
        }
        
        Solicitud solicitud = new Solicitud(
                entity.getId(), entity.getFechaCreacion(),
                entity.getEstado(), tecnico, entity.getHistorialEstados()
        );
        
        solicitud.setDescripcion(entity.getDescripcion()); // ¡NUEVO!
        
        // ¡NUEVO! Mapeo del cliente
        if (entity.getCliente() != null) {
            solicitud.setCliente(new Cliente(entity.getCliente().getId(), entity.getCliente().getNombre()));
        }
        return solicitud;
    }
}