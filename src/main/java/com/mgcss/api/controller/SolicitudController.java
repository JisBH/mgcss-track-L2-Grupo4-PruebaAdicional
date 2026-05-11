package com.mgcss.api.controller;

import com.mgcss.api.dto.AsignarTecnicoRequestDTO;
import com.mgcss.api.dto.SolicitudResponseDTO;
import com.mgcss.domain.Solicitud;
import com.mgcss.service.SolicitudService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST (Adaptador Primario) que expone los casos de uso de las solicitudes.
 * Gestiona las peticiones HTTP, mapea los DTOs hacia el dominio y devuelve las respuestas.
 */
@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    /**
     * Endpoint para crear una nueva solicitud.
     * @return Respuesta HTTP 201 (Created) con los datos de la solicitud creada.
     */
    @PostMapping
    public ResponseEntity<SolicitudResponseDTO> crear() {
        Solicitud nueva = solicitudService.crearSolicitud();
        return ResponseEntity.status(HttpStatus.CREATED).body(mapearADTO(nueva));
    }

    /**
     * Endpoint para consultar una solicitud por su identificador.
     * @param id ID de la solicitud en la ruta.
     * @return Respuesta HTTP 200 (OK) con la solicitud encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudResponseDTO> consultar(@PathVariable Long id) {
        Solicitud solicitud = solicitudService.consultarSolicitud(id);
        return ResponseEntity.ok(mapearADTO(solicitud));
    }

    /**
     * NUEVO: Endpoint para listar todas las solicitudes. (Muy útil para la futura web)
     * @return Respuesta HTTP 200 (OK) con una lista de solicitudes.
     */
    @GetMapping
    public ResponseEntity<List<SolicitudResponseDTO>> listarTodas() {
        List<SolicitudResponseDTO> lista = solicitudService.listarSolicitudes().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    /**
     * Endpoint para cambiar el estado de una solicitud a EN_PROCESO.
     * @param id ID de la solicitud.
     * @return Respuesta HTTP 204 (No Content) si se realiza con éxito.
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id) {
        solicitudService.cambiarEstado(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para reabrir una solicitud previamente cerrada.
     * @param id ID de la solicitud.
     * @return Respuesta HTTP 204 (No Content).
     */
    @PatchMapping("/{id}/reabrir")
    public ResponseEntity<Void> reabrir(@PathVariable Long id) {
        solicitudService.reabrirSolicitud(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para asignar un técnico a una solicitud.
     * @param id ID de la solicitud.
     * @param request DTO que contiene el ID del técnico.
     * @return Respuesta HTTP 200 (OK).
     */
    @PostMapping("/{id}/tecnico")
    public ResponseEntity<Void> asignarTecnico(
            @PathVariable Long id, 
            @Valid @RequestBody AsignarTecnicoRequestDTO request) { 
        
        solicitudService.asignarTecnico(id, request.tecnicoId());
        return ResponseEntity.ok().build();
    }

    // --- MÉTODOS DE MAPEO INTERNO ---

    /**
     * Convierte un objeto de dominio (Solicitud) a un objeto de transferencia de datos (DTO).
     */
    private SolicitudResponseDTO mapearADTO(Solicitud solicitud) {
        List<String> historialStr = solicitud.getHistorialEstados().stream()
                .map(Enum::name)
                .toList();

        Long tecnicoId = (solicitud.getTecnico() != null) ? solicitud.getTecnico().getId() : null;

        return new SolicitudResponseDTO(
            solicitud.getId(),
            solicitud.getEstado().name(),
            solicitud.getFechaCreacion(),
            tecnicoId,
            historialStr
        );
    }
}
