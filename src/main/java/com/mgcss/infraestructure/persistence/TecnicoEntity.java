package com.mgcss.infraestructure.persistence;

import jakarta.persistence.*;

/**
 * Entidad de persistencia (JPA) que representa a un Técnico en la base de datos.
 * <p>
 * Esta clase es una representación técnica necesaria para el mapeo objeto-relacional (ORM).
 * A diferencia de la clase de dominio, su propósito es definir la estructura de la tabla,
 * las claves primarias y los tipos de datos físicos.
 * </p>
 */
@Entity
public class TecnicoEntity {

    /** * Identificador único del técnico en la base de datos. 
     * Se genera automáticamente mediante una estrategia de identidad (autoincremento).
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** * Estado de disponibilidad del técnico.
     * Mapeado directamente a una columna booleana.
     */
    private boolean activo;

    /**
     * Constructor predeterminado requerido por la especificación JPA.
     * Permite a Hibernate instanciar la entidad mediante reflexión al recuperar datos.
     */
	public TecnicoEntity() {
	}

    /**
     * Constructor de conveniencia para crear una entidad con un estado específico.
     *
     * @param activo Indica si el técnico se crea como activo o inactivo.
     */
	public TecnicoEntity(boolean activo) {
		this.activo = activo;
	}

    // --- GETTERS Y SETTERS ---

    /**
     * @return El identificador único persistido.
     */
	public Long getId() {
		return id;
	}

    /**
     * @param id El nuevo identificador para la entidad.
     */
	public void setId(Long id) {
		this.id = id;
	}

    /**
     * @return true si el registro indica que el técnico está activo.
     */
	public boolean isActivo() {
		return activo;
	}

    /**
     * @param activo El nuevo estado de disponibilidad a persistir.
     */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}