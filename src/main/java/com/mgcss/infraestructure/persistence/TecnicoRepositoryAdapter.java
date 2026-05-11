package com.mgcss.infraestructure.persistence;

import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de persistencia para la entidad Técnico.
 * * En la Arquitectura Hexagonal, esta clase es un Adaptador Secundario (Driven Adapter).
 * Implementa el puerto de salida {@link TecnicoRepository} definido en el dominio,
 * aislando así la lógica de negocio de la tecnología específica de base de datos (Spring Data JPA).
 * * Se encarga de mapear/traducir los objetos de Dominio a Entidades de persistencia y viceversa.
 */
@Component
public class TecnicoRepositoryAdapter implements TecnicoRepository {

    private final JpaTecnicoRepository jpaRepository;

    /**
     * Inyecta el repositorio de Spring Data JPA mediante el constructor.
     * * @param jpaRepository Repositorio de base de datos generado por Spring.
     */
    public TecnicoRepositoryAdapter(JpaTecnicoRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Tecnico save(Tecnico tecnico) {
        // 1. Convertimos de Dominio a Entidad
        TecnicoEntity entity = toEntity(tecnico);
        
        // 2. Guardamos en la base de datos usando Spring Data
        TecnicoEntity savedEntity = jpaRepository.save(entity);
        
        // 3. Convertimos la Entidad guardada de vuelta a Dominio
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Tecnico> findById(Long id) {
        // Buscamos por ID y, si existe, mapeamos la entidad a un objeto de dominio
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Tecnico> findAll() {
        // Recuperamos todas las entidades y las convertimos a una lista de dominio
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    // --- MÉTODOS DE MAPEO (TRADUCTORES) ---

    /**
     * Convierte un objeto del Dominio (Tecnico) a una Entidad de JPA (TecnicoEntity).
     * * @param tecnico Objeto de dominio puro.
     * @return Entidad lista para ser guardada en base de datos.
     */
    private TecnicoEntity toEntity(Tecnico tecnico) {
        TecnicoEntity entity = new TecnicoEntity(tecnico.isActivo());
        if (tecnico.getId() > 0) {
            entity.setId(tecnico.getId()); // Mantenemos el ID si ya existe en BD
        }
        return entity;
    }

    /**
     * Convierte una Entidad de JPA (TecnicoEntity) a un objeto del Dominio (Tecnico).
     * * @param entity Entidad recuperada de la base de datos.
     * @return Objeto de dominio puro.
     */
    private Tecnico toDomain(TecnicoEntity entity) {
        // Usamos el constructor de reconstrucción que creamos en el Paso 2.2
        return new Tecnico(entity.getId(), entity.isActivo());
    }
}