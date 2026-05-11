package com.mgcss.service;

import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de aplicación para la gestión de Técnicos.
 */
@Service
public class TecnicoService {

    private final TecnicoRepository tecnicoRepo;

    public TecnicoService(TecnicoRepository tecnicoRepo) {
        this.tecnicoRepo = tecnicoRepo;
    }

    /**
     * Recupera todos los técnicos registrados en el sistema.
     * @return Lista de técnicos.
     */
    public List<Tecnico> listarTecnicos() {
        return tecnicoRepo.findAll();
    }

    /**
     * Crea un nuevo técnico en el sistema.
     * @param activo Estado inicial del técnico.
     * @return El técnico creado.
     */
    public Tecnico crearTecnico(boolean activo) {
        Tecnico nuevo = new Tecnico(activo);
        return tecnicoRepo.save(nuevo);
    }
}