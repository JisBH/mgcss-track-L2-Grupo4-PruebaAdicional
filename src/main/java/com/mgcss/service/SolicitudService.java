package com.mgcss.service;

import com.mgcss.domain.*;
import com.mgcss.infraestructure.SolicitudRepository;



public class SolicitudService {
    private final SolicitudRepository solicitudRepo;
    private final TecnicoRepository tecnicoRepo;

    // Inyección por constructor: clave para la testeabilidad
    public SolicitudService(SolicitudRepository solicitudRepo, TecnicoRepository tecnicoRepo) {
        this.solicitudRepo = solicitudRepo;
        this.tecnicoRepo = tecnicoRepo;
    }

    public void asignarTecnico(Long solicitudId, Long tecnicoId) {
        // Orquestación: Obtener de infra, decidir en dominio, guardar en infra
        Solicitud solicitud = solicitudRepo.findById(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
        
        Tecnico tecnico = tecnicoRepo.findById(tecnicoId)
                .orElseThrow(() -> new IllegalArgumentException("Técnico no encontrado"));

        // Delegación al dominio 
        solicitud.asignarTecnico(tecnico);
        
        solicitudRepo.save(solicitud);
    }
}
