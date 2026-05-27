package com.mgcss.infraestructure.persistence;

import jakarta.persistence.*;

/**
 * Entidad de persistencia relacional que mapea la tabla del modelo de base de datos
 * para la gestión de Clientes.
 */
@Entity
@Table(name = "clientes")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    /**
     * Constructor por defecto requerido obligatoriamente por la especificación de JPA.
     */
    public ClienteEntity() {}

    /**
     * Constructor rápido para mapear entidades desde el dominio.
     * * @param nombre Nombre del cliente.
     */
    public ClienteEntity(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}