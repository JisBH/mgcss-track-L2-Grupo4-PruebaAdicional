package com.mgcss.domain;

/**
 * Excepción de dominio lanzada cuando no se encuentra una entidad requerida.
 * * Se utiliza típicamente en los servicios de aplicación cuando una búsqueda por ID
 * en el {@link SolicitudRepository} o {@link TecnicoRepository} no devuelve resultados.
 * * Al ser una {@link RuntimeException}, permite un manejo limpio sin ensuciar las
 * firmas de los métodos del dominio.
 */
public class EntidadNoEncontrada extends RuntimeException {

    /**
     * Construye una nueva excepción con un mensaje detallado.
     *
     * @param mensaje Descripción del error, usualmente indicando el tipo de entidad y el ID buscado.
     */
    public EntidadNoEncontrada(String mensaje) {
        super(mensaje);
    }
}
