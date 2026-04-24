package com.mgcss.domain;

import java.time.LocalDate;

public class Solicitud {
	private long id;
	private LocalDate fechaCreacion;
	private Estado estado;
	private Tecnico tecnico;
	private java.util.List<Estado> historialEstados = new java.util.ArrayList<>();

	public enum Estado {
		ABIERTA, EN_PROCESO, CERRADA
	}

	public Solicitud() {
		this.estado = Estado.ABIERTA;
		this.historialEstados.add(this.estado);
	}

	public Estado getEstado() {
		return estado;
	}

	public void iniciarProceso() {
		this.estado = Estado.EN_PROCESO;
		this.historialEstados.add(this.estado);
	}

	public void cerrar() {
		if (estado != Estado.EN_PROCESO) {
			throw new ReglaNegocio("La solicitud debe estar en proceso para poder cerrarse");
		}
		estado = Estado.CERRADA;
		this.historialEstados.add(this.estado);
	}

	public void asignarTecnico(Tecnico tecnico) {
		if (!tecnico.isActivo()) {
			throw new ReglaNegocio("El técnico debe estar activo para ser asignado");
		}
		this.tecnico = tecnico;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void reabrir() {
		if (estado != Estado.CERRADA) {
			throw new RuntimeException("Solo se pueden reabrir solicitudes cerradas");
		}
		this.estado = Estado.EN_PROCESO;
		this.historialEstados.add(this.estado);
	}
	public java.util.List<Estado> getHistorialEstados() {
        return java.util.Collections.unmodifiableList(historialEstados);
    }
}
