package com.mgcss.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Entidad de dominio que representa una Solicitud de mantenimiento.
 * Gestiona su ciclo de vida a través de estados y mantiene un registro cronológico de su evolución.
 */
public class Solicitud {

    /** Identificador único de la solicitud. */
    private long id;

    /** Fecha en la que se registró la solicitud. */
    private LocalDate fechaCreacion;

    /** Estado actual de la solicitud. */
    private Estado estado;

    /** Técnico asignado para resolver la solicitud. */
    private Tecnico tecnico;
    
    /** Cliente asignado para la solicitud. */
    private Cliente cliente;

    /** Registro de los cambios de estado por los que ha pasado la solicitud. */
    private List<Estado> historialEstados = new ArrayList<>();
    
    private String descripcion;

    /**
     * Posibles estados en los que puede encontrarse una solicitud.
     */
    public enum Estado {
        /** Solicitud recién creada, pendiente de revisión. */
        ABIERTA,
        /** La solicitud está siendo atendida por un técnico. */
        EN_PROCESO,
        /** El trabajo ha sido finalizado. */
        CERRADA
    }

    /**
     * Constructor para la creación de una nueva solicitud.
     * Inicializa el estado en ABIERTA, establece la fecha actual y registra el inicio en el historial.
     */
    public Solicitud() {
        this.estado = Estado.ABIERTA;
        this.historialEstados.add(this.estado);
        this.fechaCreacion = LocalDate.now();
    }

    /**
     * Constructor para la reconstrucción de una solicitud desde la capa de persistencia.
     *
     * @param id               Identificador único recuperado.
     * @param fechaCreacion    Fecha de creación original.
     * @param estado           Estado actual recuperado.
     * @param tecnico          Técnico asignado (puede ser nulo).
     * @param historialEstados Lista de estados previos.
     */
    public Solicitud(long id, LocalDate fechaCreacion, Estado estado, Tecnico tecnico, List<Estado> historialEstados) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.tecnico = tecnico;
        if (historialEstados != null) {
            this.historialEstados.addAll(historialEstados);
        }
    }

    // --- MÉTODOS DE LÓGICA DE NEGOCIO ---

    /**
     * Cambia el estado de la solicitud a EN_PROCESO y lo registra en el historial.
     */
    public void iniciarProceso() {
        this.estado = Estado.EN_PROCESO;
        this.historialEstados.add(this.estado);
    }

    /**
     * Finaliza la solicitud cambiando su estado a CERRADA.
     * Solo es posible cerrar solicitudes que estén actualmente EN_PROCESO.
     *
     * @throws ReglaNegocio Si el estado actual no permite realizar el cierre.
     */
    public void cerrar() {
        if (estado != Estado.EN_PROCESO) {
            throw new ReglaNegocio("La solicitud debe estar en proceso para poder cerrarse");
        }
        this.estado = Estado.CERRADA;
        this.historialEstados.add(this.estado);
    }

    /**
     * Asigna un técnico responsable a la solicitud.
     * El técnico debe estar en estado activo para que la asignación sea válida.
     *
     * @param tecnico El técnico a asignar.
     * @throws ReglaNegocio Si el técnico no se encuentra activo.
     */
    public void asignarTecnico(Tecnico tecnico) {
        if (!tecnico.isActivo()) {
            throw new ReglaNegocio("El técnico debe estar activo para ser asignado");
        }
        this.tecnico = tecnico;
    }

    /**
     * Permite reabrir una solicitud previamente cerrada, devolviéndola al estado EN_PROCESO.
     *
     * @throws RuntimeException Si la solicitud no se encuentra en estado CERRADA.
     */
    public void reabrir() {
        if (estado != Estado.CERRADA) {
            throw new RuntimeException("Solo se pueden reabrir solicitudes cerradas");
        }
        this.estado = Estado.EN_PROCESO;
        this.historialEstados.add(this.estado);
    }

    // --- GETTERS Y SETTERS ---

    /** @return El identificador único de la solicitud. */
    public long getId() { return id; }

    /** @param id El nuevo identificador de la solicitud. */
    public void setId(long id) { this.id = id; }

    /** @return La fecha de creación de la solicitud. */
    public LocalDate getFechaCreacion() { return fechaCreacion; }

    /** @param fechaCreacion La nueva fecha de creación. */
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    /** @return El estado actual de la solicitud. */
    public Estado getEstado() { return estado; }

    /** @param estado El nuevo estado de la solicitud. */
    public void setEstado(Estado estado) { this.estado = estado; }

    /** @return El técnico asignado (si existe). */
    public Tecnico getTecnico() { return tecnico; }

    /** @param tecnico El técnico a asignar. */
    public void setTecnico(Tecnico tecnico) { this.tecnico = tecnico; }

    /** * @return Una vista inmutable del historial de estados para proteger la integridad del dominio. 
     */
    public List<Estado> getHistorialEstados() {
        return Collections.unmodifiableList(historialEstados);
    }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}

