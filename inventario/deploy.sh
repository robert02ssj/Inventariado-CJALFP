#!/bin/bash

echo "=========================================="
echo "🚀 INICIANDO DESPLIEGUE AUTOMATICO"
echo "=========================================="

echo ""
echo "🚧 Paso 1: Generando el ejecutable .JAR..."
./mvnw clean package -DskipTests

# Comprobamos si el comando anterior funcionó (exit code 0)
if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Build correcto."
    
    echo ""
    echo "🐳 Paso 2: Levantando contenedores con Docker..."
    docker-compose down
    docker-compose up --build -d
    
    echo ""
    echo "=========================================="
    echo "🎉 ¡LISTO! Tu aplicación está corriendo."
    echo "🌍 Entra en: http://localhost:8080"
    echo "=========================================="
else
    echo ""
    echo "❌ ERROR: Falló la construcción del JAR."
    exit 1
fi
