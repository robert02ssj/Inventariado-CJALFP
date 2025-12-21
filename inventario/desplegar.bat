@echo off
:: Esto arregla los caracteres raros para usar UTF-8
chcp 65001 > nul

echo ==========================================
echo 🚀 DESPLIEGUE TOTAL (MODO DOCKER PURO)
echo ==========================================
echo.
echo El contenedor se encargara de compilar el codigo...
echo ☕ Esto puede tardar un poco la primera vez (descargando Maven y Java).

echo.
echo 🐳 Levantando servicios...
docker-compose down
:: El flag --build fuerza a Docker a leer el Dockerfile y recompilar
docker-compose up --build -d

echo.
echo ==========================================
echo 🎉 ¡LISTO!
echo 🌍 Entra en: http://localhost:8080
echo ==========================================
pause