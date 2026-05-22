# Imagen base ligera con el entorno de ejecución de Java 17
FROM eclipse-temurin:17-jre-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el artefacto empaquetado (.jar) generado previamente en target/
COPY target/*.jar app.jar

# Comando de ejecución principal al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]