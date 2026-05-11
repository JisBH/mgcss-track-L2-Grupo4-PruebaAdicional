package com.mgcss.infraestructure.persistence;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mgcss.domain.Solicitud;

/**
 * Entidad de persistencia (JPA) que representa una Solicitud en la base de datos.
 * <p>
 * Esta clase es un objeto de infraestructura diseñado para interactuar con el motor de base de datos.
 * Contiene las anotaciones de mapeo relacional y se encarga de almacenar tanto el estado actual
 * como el historial de cambios de una solicitud.
 * </p>
 */
@Entity
public class SolicitudEntity {
	
    /** * Colección de estados que conforman el historial de la solicitud.
     * Se almacena en una tabla secundaria automática gracias a @ElementCollection.
     */
	@ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Solicitud.Estado> historialEstados = new ArrayList<>();

    /** * Identificador único autogenerado por la base de datos.
     */
	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** * Fecha en la que se creó el registro de la solicitud.
     */
    private LocalDate fechaCreacion;

    /** * Estado actual de la solicitud (mapeado como String en la base de datos).
     */
    @Enumerated(EnumType.STRING) 
    private Solicitud.Estado estado;
    
    /** * Relación muchos-a-uno con la entidad de Técnico.
     * Representa al técnico que tiene asignada la solicitud en la actualidad.
     */
    @ManyToOne 
    private TecnicoEntity tecnico;

    
    // --- GETTERS Y SETTERS ---

    /** @return El historial completo de estados registrados. */
    public List<Solicitud.Estado> getHistorialEstados() { return historialEstados; }
    
    /** @param historialEstados El nuevo historial de estados a persistir. */
    public void setHistorialEstados(List<Solicitud.Estado> historialEstados) { this.historialEstados = historialEstados; }
	
    /** @return El identificador único de la entidad. */
    public Long getId() { return id; }
    
    /** @param id El identificador para esta entidad. */
    public void setId(Long id) { this.id = id; }
    
    /** @return La fecha de creación persistida. */
    public LocalDate getFechaCreacion() { return fechaCreacion; }
    
    /** @param fechaCreacion La fecha de creación a establecer. */
	public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }
	
    /** @return El estado actual de la solicitud. */
	public Solicitud.Estado getEstado() { return estado; }
	
    /** @param estado El estado a persistir. */
	public void setEstado(Solicitud.Estado estado) { this.estado = estado; }
	
    /** @return La entidad del técnico asignado. */
	public TecnicoEntity getTecnico() { return tecnico; }
	
    /** @param tecnico La entidad del técnico a asociar. */
	public void setTecnico(TecnicoEntity tecnico) { this.tecnico = tecnico; }    
}
