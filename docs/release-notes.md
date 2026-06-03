# Release Notes - Análisis de Versionado Semántico

## Determinación de la Próxima Versión

* **Versión Actual Baseline:** v1.0.0
* **Próxima Versión Propuesta:** v1.1.0

### Justificación (MAJOR.MINOR.PATCH):

* **¿Por qué es 1.1.0 (MINOR)?**
  De acuerdo con la especificación obligatoria de la asignatura, el incremento se realiza en el bloque **MINOR** debido a que se han integrado nuevas funcionalidades lógicas al sistema que extienden sus capacidades operativas, pero manteniendo en todo momento la compatibilidad con el diseño original hacia atrás.

  No corresponde una versión de tipo *MAJOR* porque no se han introducido cambios disruptivos que rompan la compatibilidad del sistema base. Tampoco califica como un simple *PATCH*, ya que las modificaciones van más allá de la mera corrección de errores o bugs puntuales, implicando un desarrollo funcional e histórico evidente dentro del repositorio.

### Desglose de Cambios Técnicos Justificativos:

1. **Estructuración Modular del Repositorio:** Se transformó la arquitectura para alojar de manera independiente los entornos del `backend` y el `frontend`. Esta reorganización facilita la evolución y el mantenimiento del ciclo de vida del software de forma limpia.
2. **Inclusión del Sistema de Autenticación (Login):** Se completó y unificó la lógica necesaria para permitir el inicio de sesión de usuarios dentro de la aplicación, aportando una nueva característica de negocio (*feature*).
3. **Cifrado y Seguridad de Credenciales:** Se integraron algoritmos específicos en el backend para la encriptación y protección de contraseñas, elevando el estándar de seguridad sin alterar el comportamiento de los componentes previos.
4. **Automatización del Proceso de Entrega (Continuous Delivery):** Se diseñaron los pipelines funcionales a través de GitHub Actions en la raíz para garantizar la trazabilidad completa entre commits, tags y la generación automática de artefactos (`.jar`).

