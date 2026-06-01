[![CI](https://github.com/xexprex/mgcss-track-L2-Grupo4/actions/workflows/.ci.yml/badge.svg)](https://github.com/xexprex/mgcss-track-L2-Grupo4/actions/workflows/.ci.yml)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=xexprex_mgcss-track-L2-Grupo4&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=xexprex_mgcss-track-L2-Grupo4)

## Descripción del Proyecto
Es una plataforma diseñada para gestionar el ciclo de vida completo de las solicitudes de mantenimiento técnico. Permite registrar incidencias asociadas a clientes, asignar técnicos, registrar el historial de los estados y realizar el seguimiento continuo (`ABIERTA`, `EN_PROCESO`, `CERRADA`, con posibilidad de reapertura).

El proyecto se destaca por una **Arquitectura Hexagonal (Puertos y Adaptadores)** que aísla completamente la lógica de negocio de las dependencias tecnológicas.

---

## Arquitectura y Diseño

### 1. Capa de Dominio (`com.mgcss.domain`)
Contiene las reglas de negocio puras, entidades e interfaces de repositorio (puertos).
Utiliza excepciones de dominio personalizadas (`ReglaNegocio`, `EntidadNoEncontrada`) para proteger la integridad.
* **Entidades**: `Solicitud`, `Tecnico` y `Cliente`.
* **Interfaces**: `SolicitudRepository`, `TecnicoRepository`, y `ClienteRepository`.

### 2. Capa de Aplicación (`com.mgcss.service`)
Orquesta los casos de uso, comunicándose con el dominio y la infraestructura a través de interfaces.
* **Servicios**: `SolicitudService`, `TecnicoService`, y `ClienteService`.

### 3. Capa de Infraestructura (`com.mgcss.infraestructure`)
Implementación de los adaptadores de persistencia y mapeo de entidades JPA.
* **Adaptadores**: `SolicitudRepositoryAdapter`, `TecnicoRepositoryAdapter` y `ClienteRepositoryAdapter`.

### 4. Capa de API / Presentación (`com.mgcss.api`)
Controladores REST y DTOs (`records`) para la comunicación con el exterior.
* **Controladores**: `SolicitudController`, `TecnicoController`, `ClienteController`.

---
## Tecnologías Utilizadas
* **Lenguaje:** Java 17
* **Framework:** Spring Boot
* **Persistencia:** Spring Data JPA, PostgreSQL / H2 
* **Documentación API:** SpringDoc OpenAPI (Swagger)
* **Testing:** JUnit 5, Mockito, JaCoCo
* **DevOps:** Docker, GitHub Actions (CI/CD Automatizado), SonarCloud

## Instalación y Ejecución
### Opción A: Ejecución Local en Memoria (H2 Database)
Utiliza una base de datos en memoria.
1. Abre una terminal en la raíz del proyecto.
2. Compila y ejecuta con Maven:
   ./mvnw spring-boot:run
3. Acceder a la web: http://localhost:8080/index.html
4. Acceder a Swagger: http://localhost:8080/swagger-ui/index.html

### Opción B: Ejecución con Docker (PostgreSQL)
Ideal para pruebas simulando un entorno real de producción,se sube automáticamente a Docker Hub con cada nueva Release.

1. Tener Docker instalado.
2. Ejecuta el siguiente comando para construir la imagen y levantar los contenedores:

    docker-compose up -d 
   
4. La aplicación estará disponible en el puerto 9090 (según configuración de compose):

    Interfaz Web: http://localhost:9090/index.html
    Swagger: http://localhost:9090/swagger-ui/index.html

   
## Testing y Calidad de Código
El proyecto tiene pruebas automatizada.

* **Tests de Dominio**: Pruebas unitarias de lógica pura y comportamiento de estados con JUnit 5.
* **Tests de Servicios**: Uso de Mockito para probar la lógica orquestada sin tocar base de datos.
* **Tests de Controladores (@WebMvcTest)**: Verificación rápida de la capa HTTP, validaciones JSON y endpoints.
* **Tests de Persistencia (@DataJpaTest)**: Verificación de los repositorios y mapeos contra una base de datos real (H2).
* **Cobertura de Código**: JaCoCo integrada en el workflow de GitHub Actions.
