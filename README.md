[![CI](https://github.com/xexprex/mgcss-track-L2-Grupo4/actions/workflows/.ci.yml/badge.svg)](https://github.com/xexprex/mgcss-track-L2-Grupo4/actions/workflows/.ci.yml)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=xexprex_mgcss-track-L2-Grupo4&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=xexprex_mgcss-track-L2-Grupo4)

## Descripción del Proyecto
Es una plataforma diseñada para gestionar el ciclo de vida de las solicitudes de mantenimiento técnico. Permite registrar incidencias, asignar técnicos y realizar un seguimiento de los cambios de estado (`ABIERTA`, `EN_PROCESO`, `CERRADA`).

El proyecto destaca por una **Arquitectura Hexagonal (Puertos y Adaptadores)** que aísla completamente la lógica de negocio de las dependencias tecnológicas.

---

## Arquitectura y Diseño

### 1. Capa de Dominio (`com.mgcss.domain`)
Contiene las reglas de negocio puras, entidades e interfaces de repositorio (puertos).
* **Entidades**: `Solicitud` y `Tecnico`.
* **Interfaces**: `SolicitudRepository` y `TecnicoRepository`.

### 2. Capa de Aplicación (`com.mgcss.service`)
Orquesta los casos de uso, comunicándose con el dominio y la infraestructura a través de interfaces.
* **Servicios**: `SolicitudService` y `TecnicoService`.

### 3. Capa de Infraestructura (`com.mgcss.infraestructure`)
Implementación de los adaptadores de persistencia y mapeo de entidades JPA.
* **Adaptadores**: `SolicitudRepositoryAdapter` y `TecnicoRepositoryAdapter`.

### 4. Capa de API / Presentación (`com.mgcss.api`)
Controladores REST y DTOs (`records`) para la comunicación con el exterior.

---

## 🚀 Instalación y Ejecución

2.  **Ejecutar**:
    spring-boot:run
    
4.  **Acceder a la web**: http://localhost:8080/index.html

---

## Testing

* **Tests de Dominio**: Pruebas unitarias de lógica pura con JUnit 5.
* **Tests de Controladores**: Tests unitarios utilizando `Mock` con Mockito, eliminando la carga del contexto de Spring para mayor velocidad.
* **Tests de Persistencia**: Tests de integración con `@DataJpaTest` y base de datos H2 en memoria.
