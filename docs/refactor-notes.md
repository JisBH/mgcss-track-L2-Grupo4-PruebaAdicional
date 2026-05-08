# Notas de Refactorización - Sesion 8

## Problema 1: Excepciones Genéricas e Inconsistencia en Tests

1. **Problema identificado:** 

Uso de `IllegalArgumentException` e `IllegalStateException` genéricos en la lógica de dominio (`Solicitud`) y servicios (`SolicitudService`). Inconsistencia en los tests al atrapar estas excepciones genéricas (se usa `RuntimeException` en un lado y `IllegalArgumentException` en otro).

2. **Métrica asociada:** 
Code Smells (Manejo de errores genérico), Maintainability Rating.

3. **Riesgo potencial si no se corrige:** 
Dificulta el manejo de errores en capas superiores (como controladores REST, que no sabrán si un `IllegalArgumentException` es por un 404 Not Found o un 400 Bad Request). Los tests son frágiles y confusos.


## Problema 2: Código Muerto (Speculative Generality)

1. **Problema identificado:** 
La clase `Cliente.java` está vacía, no se utiliza en ninguna parte del dominio y requiere configuración extra en el `pom.xml` para no arruinar la cobertura.

2. **Métrica asociada:** 
Code Smells (Dead code), Technical Debt.

3. **Riesgo potencial si no se corrige:** 
Aumenta la carga cognitiva al leer el proyecto y ensucia las métricas de cobertura reales.



## Resultados tras la refactorización

1. **Qué métrica mejoró:** 

- Se eliminó el Code Smell de "Dead Code" (Clase vacía).
   - Se redujo el acoplamiento y se mejoró el "Maintainability Rating" al sustituir excepciones genéricas de Java 	 por excepciones de dominio (Domain Exceptions).
   - Se limpió el POM, eliminando exclusiones innecesarias de JaCoCo.
   
2. **Qué técnica de refactor se aplicó:** 
   - Remove Dead Code.
   - Replace Exception with Custom Domain Exception (mejora de semántica).
   - Estandarización de assertions en los Tests.
   
3. **Qué beneficio aporta a mantenimiento futuro:** 
- Ahora, si exponemos nuestra API mediante controladores REST, podemos tener un `@ControllerAdvice` que capture `EntidadNoEncontradaException` y devuelva un 404 (Not Found) automáticamente, y capture `ReglaNegocioException` para devolver un 400 (Bad Request). Con excepciones genéricas, esto habría sido imposible de distinguir. 
   - La eliminación de código muerto reduce la confusión de futuros desarrolladores al explorar el paquete de dominio.