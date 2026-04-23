package com.mgcss.infraestructure.persistence;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.mgcss.domain.Solicitud;

@Entity
public class SolicitudEntity {
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
