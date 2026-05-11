package com.mgcss.domain;

/**
 * Entidad de dominio que representa a un Técnico en el sistema.
 * Un técnico es el encargado de atender y resolver las solicitudes de mantenimiento.
 */
public class Tecnico {

    /** Identificador único del técnico. */
    private long id;

    /** Indica si el técnico se encuentra actualmente disponible para asignar nuevas tareas. */
    private boolean activo;

    /**
     * Constructor para la creación de un nuevo técnico.
     * Se utiliza cuando el técnico aún no persiste en la base de datos (no tiene ID).
     *
     * @param activo Estado de disponibilidad inicial.
     */
    public Tecnico(boolean activo) {
        this.activo = activo;
    }

    /**
     * Constructor para la reconstrucción de un técnico desde la capa de persistencia.
     *
     * @param id     Identificador único recuperado.
     * @param activo Estado de disponibilidad recuperado.
     */
    public Tecnico(long id, boolean activo) {
        this.id = id;
        this.activo = activo;
    }

    // --- GETTERS Y SETTERS ---

    /**
     * @return El identificador único del técnico.
     */
    public long getId() {
        return id;
    }

    /**
     * @param id El nuevo identificador para el técnico.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return true si el técnico está activo, false en caso contrario.
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * @param activo El nuevo estado de disponibilidad del técnico.
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}