package com.mgcss.domain;

public class Tecnico {
	 private boolean activo;
	 private long id;
	 
	 	public long getId() {
	    	return id;
	    }

	    public Tecnico(boolean activo) {
	        this.activo = activo;
	    }

	    public boolean isActivo() {
	        return activo;
	    }
}
