# --- ETAPA 1: CONSTRUCCIÓN (BUILD) ---
# Usamos una imagen que SÍ tiene Maven para compilar
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copiamos solo el pom.xml primero (para aprovechar la caché de Docker)
COPY pom.xml .
# Descargamos dependencias (esto acelera builds futuros)
RUN mvn dependency:go-offline -B

# Copiamos el código fuente
COPY src ./src

# Compilamos y creamos el JAR (Saltando tests para ir rápido)
# Al terminar, tendremos un archivo en /app/target/app.jar
RUN mvn clean package -DskipTests -DfinalName=app


# --- ETAPA 2: EJECUCIÓN (RUN) ---
# Usamos una imagen ligera SOLO con Java (sin Maven)
FROM openjdk:21-jdk-slim

WORKDIR /app

# COPIAMOS el JAR desde la "Etapa 1" (build) a esta etapa
COPY --from=build /app/target/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]