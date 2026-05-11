package com.mgcss.api.controller;

import com.mgcss.domain.Tecnico;
import com.mgcss.service.TecnicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para exponer la información de los Técnicos.
 */
@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    /**
     * Obtiene la lista de todos los técnicos.
     */
    @GetMapping
    public ResponseEntity<List<Tecnico>> listar() {
        return ResponseEntity.ok(tecnicoService.listarTecnicos());
    }

    /**
     * Crea un técnico por defecto (activo) para poder hacer pruebas.
     */
    @PostMapping
    public ResponseEntity<Tecnico> crear() {
        Tecnico nuevo = tecnicoService.crearTecnico(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
}
