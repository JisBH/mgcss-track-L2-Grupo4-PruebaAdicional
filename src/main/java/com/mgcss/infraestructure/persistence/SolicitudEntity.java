package com.mgcss.infraestructure.persistence;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mgcss.domain.Solicitud;

@Entity
public class SolicitudEntity {
	
	@ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Solicitud.Estado> historialEstados = new ArrayList<>();

    // Genera el getter y setter para historialEstados
    public List<Solicitud.Estado> getHistorialEstados() { return historialEstados; }
    public void setHistorialEstados(List<Solicitud.Estado> historialEstados) { this.historialEstados = historialEstados; }
	
	
	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaCreacion;

    @Enumerated(EnumType.STRING) 
    private Solicitud.Estado estado;
    
    @ManyToOne 
    private TecnicoEntity tecnico;

    
    public Long getId() { 
    	return id; 
    }
    public void setId(Long id) { 
    	this.id = id; 
    }
    public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Solicitud.Estado getEstado() {
		return estado;
	}
	public void setEstado(Solicitud.Estado estado) {
		this.estado = estado;
	}
	public TecnicoEntity getTecnico() {
		return tecnico;
	}
	public void setTecnico(TecnicoEntity tecnico) {
		this.tecnico = tecnico;
	}    
    
}
