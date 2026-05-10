package com.mgcss.service;

import com.mgcss.domain.*;
import com.mgcss.infraestructure.SolicitudRepository;
import java.util.List;

public class SolicitudService {
    private final SolicitudRepository solicitudRepo;
    private final TecnicoRepository tecnicoRepo;

    public SolicitudService(SolicitudRepository solicitudRepo, TecnicoRepository tecnicoRepo) {
        this.solicitudRepo = solicitudRepo;
        this.tecnicoRepo = tecnicoRepo;
    }

    public Solicitud crearSolicitud() {
    	Solicitud  solicitud = new Solicitud();
    	return solicitudRepo.save(solicitud);
    }
    
    public Solicitud consultarSolicitud(long id) {
    	return solicitudRepo.findById(id)
    			.orElseThrow(() -> new EntidadNoEncontrada("Solicitud no encontrada"));
    }

    public List<Solicitud> listarSolicitudes(){
    	return solicitudRepo.findAll();
    }
    
    public void cambiarEstado(Long id) {
        Solicitud solicitud = consultarSolicitud(id);
        solicitud.iniciarProceso();
        solicitudRepo.save(solicitud);
    }
    
    public void reabrirSolicitud(Long id) {
        Solicitud solicitud = consultarSolicitud(id);
        solicitud.reabrir();
        solicitudRepo.save(solicitud);
    }  
    public void asignarTecnico(Long solicitudId, Long tecnicoId) {
        Solicitud solicitud = solicitudRepo.findById(solicitudId)
                .orElseThrow(() -> new EntidadNoEncontrada("Solicitud no encontrada"));
        Tecnico tecnico = tecnicoRepo.findById(tecnicoId)
                .orElseThrow(() -> new EntidadNoEncontrada("Técnico no encontrado"));
        solicitud.asignarTecnico(tecnico);        
        solicitudRepo.save(solicitud);
    }
}
