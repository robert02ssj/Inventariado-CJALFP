# Sistema de Adjuntos PDF para Asignaciones de Equipos

## 📋 Descripción

Este módulo implementa la funcionalidad para adjuntar archivos PDF a las asignaciones de equipos. Los archivos adjuntos típicamente contienen actas de entrega firmadas, documentos de responsabilidad, etc.

## 🎯 Características Implementadas

### 1. Subida de PDF al crear asignación
- Campo opcional en el formulario de asignación
- Validación de tipo de archivo (solo PDF)
- Tamaño máximo: 10 MB
- Nombres de archivo únicos basados en timestamp

### 2. Descarga de PDF desde histórico
- Enlace "📥 PDF" visible si existe archivo adjunto
- Texto "Sin documento" si no hay PDF
- Descarga directa al hacer clic

### 3. Almacenamiento seguro
- Archivos guardados en `uploads/asignaciones/`
- Carpeta creada automáticamente si no existe
- Ruta almacenada en base de datos

## 🔧 Instalación

### Paso 1: Actualizar la Base de Datos

**IMPORTANTE**: Debes ejecutar este script SQL **ANTES** de usar la aplicación:

```sql
USE Inventario;

ALTER TABLE Inventario 
ADD COLUMN ruta_pdf VARCHAR(255) AFTER fecha_devolucion;
```

También puedes ejecutar el archivo `add-pdf-column.sql` incluido:

```bash
mysql -u root -p < add-pdf-column.sql
```

### Paso 2: Compilar y Ejecutar

```bash
cd inventario
mvn clean package
java -jar target/app.jar
```

## 📂 Estructura de Archivos

```
inventario/
├── src/main/java/com/cjalfp/inventario/
│   ├── controller/
│   │   └── InventarioController.java      # Endpoints de upload/download
│   ├── model/
│   │   └── Inventario.java                # Modelo con campo rutaPdf
│   └── service/
│       └── FileStorageService.java        # Lógica de almacenamiento
├── src/main/resources/
│   ├── application.properties             # Configuración de multipart
│   └── templates/inventario/
│       ├── formulario.html                # Form con input file
│       └── lista.html                     # Tabla con columna PDF
├── uploads/                               # Carpeta de archivos (gitignored)
│   └── asignaciones/
│       └── asignacion_XXXXXXXXXX.pdf
└── add-pdf-column.sql                     # Script SQL de migración
```

## 🔒 Seguridad

✅ **Validaciones Implementadas**:
- Solo acepta archivos con `content-type: application/pdf`
- Tamaño máximo limitado a 10MB
- Nombres de archivo únicos (evita sobrescrituras)
- Validación de existencia antes de descargar
- No permite path traversal

## 🧪 Pruebas

### Prueba Manual

1. **Asignar equipo con PDF**:
   - Ir a `/inventario/asignar`
   - Seleccionar usuario y equipo
   - Adjuntar un PDF de prueba
   - Click "Asignar Equipo"
   - Verificar mensaje de éxito

2. **Visualizar en listado**:
   - Ir a `/inventario`
   - Verificar que aparece el botón "📥 PDF"
   - Click para descargar
   - Verificar que el PDF se descarga correctamente

3. **Asignar sin PDF**:
   - Crear una asignación sin adjuntar archivo
   - Verificar que aparece "Sin documento"
   - No debe generar errores

## 📝 Uso de la API

### Endpoints

#### Crear asignación con PDF

```http
POST /inventario/guardar
Content-Type: multipart/form-data

usuario.id: 123
equipo.id: 456
pdfFile: [archivo.pdf]
```

#### Descargar PDF

```http
GET /inventario/descargar-pdf/{id}
```

Respuesta:
- **200 OK**: Descarga el PDF
- **404 Not Found**: No existe la asignación o no tiene PDF
- **500 Internal Server Error**: Error al leer el archivo

## ⚙️ Configuración

### application.properties

```properties
# Configuración de subida de archivos
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

### Cambiar directorio de uploads

En `FileStorageService.java`:

```java
private final String uploadDir = "uploads/asignaciones/";
```

Para producción, considera usar una ruta absoluta:

```java
private final String uploadDir = "/var/app/uploads/asignaciones/";
```

## 🐛 Solución de Problemas

### Error: "No se pudo crear el directorio de uploads"

**Causa**: Permisos insuficientes en el sistema de archivos.

**Solución**:
```bash
mkdir -p uploads/asignaciones
chmod 755 uploads/asignaciones
```

### Error al guardar: "El archivo debe ser un PDF"

**Causa**: El archivo no tiene el content-type correcto.

**Solución**: Asegúrate de subir un archivo PDF válido, no una imagen o documento renombrado.

### Error: "Column 'ruta_pdf' doesn't exist"

**Causa**: No se ejecutó el script SQL de migración.

**Solución**: Ejecuta `add-pdf-column.sql` en MySQL.

## 📚 Tecnologías Utilizadas

- **Spring Boot 4.0.0**: Framework principal
- **Spring MVC**: Manejo de formularios multipart
- **Thymeleaf**: Motor de plantillas
- **MySQL**: Base de datos
- **Java NIO**: Operaciones de archivos

## 🔄 Mejoras Futuras

- [ ] Eliminar PDF al eliminar asignación
- [ ] Vista previa del PDF en modal
- [ ] Soporte para múltiples archivos
- [ ] Almacenamiento en S3/cloud
- [ ] Compresión automática de PDFs grandes
- [ ] Registro de auditoría de uploads

## 👥 Contribución

Si necesitas modificar esta funcionalidad:

1. Los cambios en el modelo requieren migración SQL
2. Actualiza los tests si modificas la lógica
3. Documenta cambios en este README

## 📄 Licencia

Este módulo es parte del proyecto Inventariado-CJALFP.
