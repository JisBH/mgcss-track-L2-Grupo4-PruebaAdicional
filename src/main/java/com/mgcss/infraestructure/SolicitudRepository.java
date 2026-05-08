package com.mgcss.infraestructure;

import java.util.List;
import java.util.Optional;

import com.mgcss.domain.Solicitud;

public interface SolicitudRepository {
    Solicitud save(Solicitud solicitud);
    Optional<Solicitud> findById(Long id);
    List<Solicitud> findAll();
}
