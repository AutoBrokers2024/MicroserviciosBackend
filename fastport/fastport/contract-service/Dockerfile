# Usar una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo en /app
WORKDIR /app

# Copiar el archivo JAR del servicio en el contenedor
COPY target/contract-service-0.0.1-SNAPSHOT.jar contract-service.jar

# Exponer el puerto 8085
EXPOSE 8085

# Comando para ejecutar el JAR del servicio
ENTRYPOINT ["java", "-jar", "contract-service.jar"]