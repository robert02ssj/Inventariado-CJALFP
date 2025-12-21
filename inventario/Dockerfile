# Usamos una imagen base oficial y estable de Java 21
FROM openjdk:21-jdk-slim

# Creamos la carpeta dentro del contenedor
WORKDIR /app

# Copiamos el JAR generado (que gracias al pom.xml se llama app.jar)
COPY target/app.jar app.jar

# Exponemos el puerto
EXPOSE 8080

# Comando para iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
