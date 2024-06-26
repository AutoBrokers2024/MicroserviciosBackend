# Usar una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo en /app
WORKDIR /app

# Copiar el archivo JAR del servicio en el contenedor
COPY target/discovery-service-0.0.1-SNAPSHOT.jar discovery-service.jar

# Exponer el puerto 8761
EXPOSE 8761

# Comando para ejecutar el JAR del servicio
ENTRYPOINT ["java", "-jar", "discovery-service.jar"]
