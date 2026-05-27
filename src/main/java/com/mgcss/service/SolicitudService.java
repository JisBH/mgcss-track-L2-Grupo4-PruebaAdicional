package com.mgcss.service;

import com.mgcss.domain.ClienteRepository;
import com.mgcss.domain.EntidadNoEncontrada;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.SolicitudRepository;
import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de aplicación para la gestión de Solicitudes.
 * Orquesta la lógica de negocio coordinando las entidades del dominio y los puertos de persistencia.
 * * Nota: No conoce detalles de bases de datos ni de infraestructura, solo interactúa con las interfaces del dominio.
 */
@Service
public class SolicitudService {
    
    private final SolicitudRepository solicitudRepo;
    private final TecnicoRepository tecnicoRepo;
    private final ClienteRepository clienteRepo;

    /**
     * Inyecta las dependencias necesarias. Spring proporcionará automáticamente los adaptadores
     * de infraestructura que implementan estas interfaces.
     */
    public SolicitudService(SolicitudRepository solicitudRepo, TecnicoRepository tecnicoRepo, ClienteRepository clienteRepo) {
        this.solicitudRepo = solicitudRepo;
        this.tecnicoRepo = tecnicoRepo;
        this.clienteRepo = clienteRepo;
    }

    /**
     * Crea una nueva solicitud en el sistema con estado inicial ABIERTA.
     * @return La solicitud creada y persistida.
     */
    public Solicitud crearSolicitud(String descripcion, Long clienteId) {
        com.mgcss.domain.Cliente cliente = clienteRepo.findById(clienteId)
                .orElseThrow(() -> new EntidadNoEncontrada("Cliente no encontrado"));
                
        Solicitud solicitud = new Solicitud();
        solicitud.setDescripcion(descripcion);
        solicitud.setCliente(cliente);
        
        return solicitudRepo.save(solicitud);
    }
    
    /**
     * Consulta una solicitud específica por su ID.
     * @param id Identificador de la solicitud.
     * @return La solicitud encontrada.
     * @throws EntidadNoEncontrada Si no existe una solicitud con ese ID.
     */
    public Solicitud consultarSolicitud(long id) {
        return solicitudRepo.findById(id)
                .orElseThrow(() -> new EntidadNoEncontrada("Solicitud no encontrada con el ID: " + id));
    }

    /**
     * Recupera el listado completo de todas las solicitudes.
     * @return Lista de solicitudes.
     */
    public List<Solicitud> listarSolicitudes(){
        return solicitudRepo.findAll();
    }
    
    /**
     * Cambia el estado de una solicitud a EN_PROCESO.
     * @param id Identificador de la solicitud a modificar.
     */
    public void cambiarEstado(Long id) {
        Solicitud solicitud = consultarSolicitud(id);
        solicitud.iniciarProceso();
        solicitudRepo.save(solicitud);
    }
    
    /**
     * Reabre una solicitud que previamente fue cerrada, volviéndola a estado EN_PROCESO.
     * @param id Identificador de la solicitud.
     */
    public void reabrirSolicitud(Long id) {
        Solicitud solicitud = consultarSolicitud(id);
        solicitud.reabrir();
        solicitudRepo.save(solicitud);
    }  

    /**
     * Asigna un técnico a una solicitud específica. 
     * Aplica la regla de negocio que verifica si el técnico está activo antes de guardar.
     * * @param solicitudId ID de la solicitud.
     * @param tecnicoId ID del técnico a asignar.
     * @throws EntidadNoEncontrada Si la solicitud o el técnico no existen.
     */
    public void asignarTecnico(Long solicitudId, Long tecnicoId) {
        Solicitud solicitud = consultarSolicitud(solicitudId); // Reutilizamos el método existente
        
        Tecnico tecnico = tecnicoRepo.findById(tecnicoId)
                .orElseThrow(() -> new EntidadNoEncontrada("Técnico no encontrado con el ID: " + tecnicoId));
        
        solicitud.asignarTecnico(tecnico);        
        solicitudRepo.save(solicitud);
    }
    
    /**
     * Cierra formalmente una solicitud y persiste el cambio en la base de datos.
     * <p>
     * Este método recupera la solicitud, ejecuta la transición de estado a CERRADA
     * en el modelo de dominio (lo cual dispara las validaciones internas) y guarda
     * el estado actualizado a través del repositorio.
     * </p>
     *
     * @param id Identificador único de la solicitud a cerrar.
     * @throws EntidadNoEncontrada Si la solicitud no existe en el sistema.
     * @throws ReglaNegocio Si la solicitud no cumple los requisitos para cerrarse.
     */
    public void cerrarSolicitud(Long id) {
        Solicitud solicitud = consultarSolicitud(id);
        solicitud.cerrar(); // Lógica de negocio (cambia estado y añade al historial)
        solicitudRepo.save(solicitud); // Persistencia a través del adaptador
    }
}
