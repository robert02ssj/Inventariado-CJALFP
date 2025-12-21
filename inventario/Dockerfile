# --- ETAPA 1: CONSTRUCCIÓN (BUILD) ---
# Usamos Maven con Java 21 (Eclipse Temurin) para compilar
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copiamos archivos de dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el código fuente
COPY src ./src

# Compilamos y creamos el JAR
RUN mvn clean package -DskipTests -DfinalName=app


# --- ETAPA 2: EJECUCIÓN (RUN) ---
# CAMBIO IMPORTANTE: Usamos eclipse-temurin JRE (más ligero y estable que openjdk-slim)
FROM eclipse-temurin:21-jre

WORKDIR /app

# COPIAMOS el JAR desde la "Etapa 1"
COPY --from=build /app/target/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]