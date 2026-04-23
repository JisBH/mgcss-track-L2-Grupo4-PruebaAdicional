package com.mgcss.infraestructure.persistence;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.mgcss.domain.Solicitud;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SolicitudEntity {
	
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaCreacion;

    @Enumerated(EnumType.STRING) 
    private Solicitud.Estado estado;
    
    @ManyToOne 
    private TecnicoEntity tecnico;
    
}
