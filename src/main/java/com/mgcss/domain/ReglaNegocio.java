package com.mgcss.domain;

public class ReglaNegocio extends RuntimeException {
    public ReglaNegocio(String mensaje) {
        super(mensaje);
    }
}