# Usamos Java 21 (versión ligera Alpine)
FROM eclipse-temurin:21-jdk-alpine

# Carpeta de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el .jar (Asegúrate de haber hecho 'mvn clean package')
# Como hemos puesto el <finalName>app</finalName>, siempre se llamará así.
COPY target/app.jar app.jar

# Puerto que expone tu app (Spring Boot por defecto usa el 8080)
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
