package com.mgcss.infraestructure.persistence;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.ClienteRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de infraestructura secundaria que implementa el puerto {@link ClienteRepository}
 * utilizando repositorios basados en Spring Data JPA.
 */
@Component
public class ClienteRepositoryAdapter implements ClienteRepository {

    private final JpaClienteRepository jpaRepository;

    /**
     * Constructor con inyección de dependencias.
     * * @param jpaRepository Repositorio Spring Data nativo.
     */
    public ClienteRepositoryAdapter(JpaClienteRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntity entity = new ClienteEntity(cliente.getNombre());
        if (cliente.getId() > 0) {
            entity.setId(cliente.getId());
        }
        ClienteEntity saved = jpaRepository.save(entity);
        return new Cliente(saved.getId(), saved.getNombre());
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return jpaRepository.findById(id).map(e -> new Cliente(e.getId(), e.getNombre()));
    }

    @Override
    public List<Cliente> findAll() {
        return jpaRepository.findAll().stream()
                .map(e -> new Cliente(e.getId(), e.getNombre()))
                .collect(Collectors.toList());
    }
}