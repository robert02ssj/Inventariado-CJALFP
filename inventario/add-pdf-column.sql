-- ================================================
-- SCRIPT SQL: Agregar columna ruta_pdf a tabla Inventario
-- ================================================
-- Este script debe ejecutarse MANUALMENTE en MySQL
-- antes de usar la funcionalidad de carga de archivos PDF
-- ================================================

USE Inventario;

-- Agregar columna para almacenar la ruta del archivo PDF
ALTER TABLE Inventario 
ADD COLUMN ruta_pdf VARCHAR(255) AFTER fecha_devolucion;

-- Verificar que la columna fue agregada correctamente
DESCRIBE Inventario;
