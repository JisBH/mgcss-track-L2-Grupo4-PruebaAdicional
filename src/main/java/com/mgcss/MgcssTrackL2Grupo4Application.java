package com.mgcss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal y punto de entrada de la aplicación Spring Boot.
 * <p>
 * Al estar ubicada en el paquete raíz (com.mgcss), la anotación @SpringBootApplication 
 * habilita el escaneo automático de componentes. Esto permite que Spring detecte e 
 * inyecte correctamente nuestros controladores (api), servicios de aplicación (service) 
 * y adaptadores de infraestructura (infraestructure).
 * </p>
 */
@SpringBootApplication
public class MgcssTrackL2Grupo4Application {

    public static void main(String[] args) {
        SpringApplication.run(MgcssTrackL2Grupo4Application.class, args);
    }

}