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

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @PostMapping
    public ResponseEntity<SolicitudResponseDTO> crear() {
        Solicitud nueva = solicitudService.crearSolicitud();
        return ResponseEntity.status(HttpStatus.CREATED).body(mapearADTO(nueva));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudResponseDTO> consultar(@PathVariable Long id) {
        Solicitud solicitud = solicitudService.consultarSolicitud(id);
        return ResponseEntity.ok(mapearADTO(solicitud));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id) {
        solicitudService.cambiarEstado(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reabrir")
    public ResponseEntity<Void> reabrir(@PathVariable Long id) {
        solicitudService.reabrirSolicitud(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/tecnico")
    public ResponseEntity<Void> asignarTecnico(
            @PathVariable Long id, 
            @Valid @RequestBody AsignarTecnicoRequestDTO request) { 
        
        solicitudService.asignarTecnico(id, request.tecnicoId());
        return ResponseEntity.ok().build();
    }


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
