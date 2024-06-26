# Usar una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo en /app
WORKDIR /app

# Copiar el archivo JAR del servicio en el contenedor
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar el JAR del servicio
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]