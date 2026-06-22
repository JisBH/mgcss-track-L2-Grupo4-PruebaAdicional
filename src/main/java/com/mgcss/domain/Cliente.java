package com.mgcss.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//Esto es un comentario de prueba


/**
 * Entidad de dominio puro que representa a un Cliente dentro del sistema.
 * <p>
 * Un cliente es el actor que origina las solicitudes de mantenimiento y 
 * mantiene un registro inmutable de sus incidencias asociadas.
 * </p>
 */
public class Cliente {
    
    /** Identificador único del cliente en el sistema. */
    private long id;
    
    /** Nombre o razón social del cliente. */
    private String nombre;
    
    /** Colección interna de solicitudes pertenecientes a este cliente. */
    private List<Solicitud> solicitudes = new ArrayList<>();

    /**
     * Constructor para la creación de instancias nuevas de Cliente (sin persistir).
     * * @param nombre El nombre o razón social del cliente. No puede ser nulo ni vacío.
     * @throws ReglaNegocio Si el nombre proporcionado es nulo o está en blanco.
     */
    public Cliente(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ReglaNegocio("El nombre del cliente no puede estar vacío");
        }
        this.nombre = nombre;
    }

    /**
     * Constructor de reconstrucción utilizado por los adaptadores de infraestructura.
     * * @param id     Identificador único proveniente de la base de datos.
     * @param nombre Nombre registrado del cliente.
     */
    public Cliente(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Vincula una nueva solicitud de asistencia técnica a este cliente.
     * Gana consistencia asegurando que no se repitan referencias idénticas.
     * * @param solicitud Instancia de la solicitud a asociar. No puede ser nula.
     * @throws ReglaNegocio Si la solicitud proporcionada es nula.
     */
    public void vincularSolicitud(Solicitud solicitud) {
        if (solicitud == null) {
            throw new ReglaNegocio("No se puede vincular una solicitud nula");
        }
        if (!this.solicitudes.contains(solicitud)) {
            this.solicitudes.add(solicitud);
        }
    }

    /**
     * Obtiene el identificador único del cliente.
     * @return id numérico.
     */
    public long getId() { return id; }

    /**
     * Asigna el identificador único del cliente.
     * @param id nuevo identificador numérico.
     */
    public void setId(long id) { this.id = id; }

    /**
     * Obtiene el nombre del cliente.
     * @return cadena con el nombre.
     */
    public String getNombre() { return nombre; }

    /**
     * Modifica el nombre del cliente.
     * @param nombre nuevo nombre del cliente.
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Devuelve una vista no modificable de las solicitudes asociadas al cliente,
     * protegiendo la colección interna de manipulaciones externas directas.
     * * @return Lista inmutable de objetos {@link Solicitud}.
     */
    public List<Solicitud> getSolicitudes() { return Collections.unmodifiableList(solicitudes); }
}