package com.mgcss.api.dto;

/**
 * DTO (Data Transfer Object) previsto para la creación o actualización de solicitudes.
 * * Actualmente se encuentra sin parámetros ya que el sistema crea solicitudes básicas por defecto.
 * Sin embargo, se mantiene esta estructura para soportar una fácil ampliación en el futuro
 * (por ejemplo, si se requiere recibir una "descripción" del problema, "nivel de urgencia", o "clienteId").
 */
public record SolicitudRequestDTO() {
}