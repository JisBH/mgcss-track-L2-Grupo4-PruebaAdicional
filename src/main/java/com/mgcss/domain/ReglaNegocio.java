package com.mgcss.domain;

/**
 * Excepción de dominio lanzada cuando una operación viola una regla de negocio.
 * * Representa estados inconsistentes o transiciones prohibidas en el ciclo de vida
 * de las entidades (ej. intentar asignar un técnico inactivo o cerrar una solicitud abierta).
 * * Esta excepción es clave para proteger la integridad del modelo de dominio.
 */
public class ReglaNegocio extends RuntimeException {

    /**
     * Construye una nueva excepción con el mensaje de la regla violada.
     *
     * @param mensaje Descripción clara de por qué la operación no es permitida.
     */
    public ReglaNegocio(String mensaje) {
        super(mensaje);
    }
}