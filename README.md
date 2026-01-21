# Sistema de Inventariado CJALFP

Sistema de gestión de inventario de equipos informáticos desarrollado con Spring Boot.

## 📋 Estados del Sistema

El sistema trabaja con **3 estados** para los equipos:

### Estados Disponibles

1. **Disponible** (id=1)
   - Equipo en almacén, listo para asignar
   - Estado inicial de equipos nuevos
   - Estado tras devolución de equipos

2. **Asignado** (id=2)
   - Equipo en uso por un usuario
   - Se asigna automáticamente al crear una asignación
   - Vuelve a "Disponible" al devolver el equipo

3. **Averiado/Retirado** (id=3)
   - Equipo fuera de servicio
   - Se marca manualmente cuando un equipo está averiado o dado de baja

### Flujo de Estados

```
[Nuevo Equipo] → Disponible (1)
                      ↓
                 Asignado (2) ←→ Disponible (1)
                      ↓
              Averiado/Retirado (3)
```

## 🛠️ Tipos de Equipos

El sistema gestiona 6 tipos de objetos:

1. Ordenador (id=1)
2. Teléfono (id=2)
3. Pantalla (id=3)
4. Ratón (id=4)
5. Teclado (id=5)
6. Docking Station (id=6)

## 🚀 Tecnologías

- **Java 17**
- **Spring Boot 4.0.0**
- **Spring Data JPA**
- **Spring Security**
- **Thymeleaf**
- **MySQL**
- **Lombok**
- **Maven**

## 📦 Instalación

1. Clonar el repositorio
2. Configurar la base de datos MySQL
3. Ejecutar los scripts SQL (`schema.sql` y `data.sql`)
4. Compilar el proyecto:
   ```bash
   cd inventario
   mvn clean install
   ```
5. Ejecutar la aplicación:
   ```bash
   mvn spring-boot:run
   ```

## 📊 Funcionalidades

- ✅ Gestión de equipos (CRUD completo)
- ✅ Asignación de equipos a usuarios
- ✅ Devolución de equipos
- ✅ Dashboard estadístico con métricas en tiempo real
- ✅ Consultas de equipos disponibles
- ✅ Histórico de asignaciones
- ✅ Búsqueda y filtrado de equipos
- ✅ Gestión de usuarios
- ✅ Gestión de marcas, modelos y líneas

## 🔒 Características de Seguridad

- Control de errores robusto con `orElseThrow()`
- Validación de estados antes de operaciones
- Mensajes de error descriptivos
- Manejo de excepciones en todas las operaciones críticas

## 📝 Notas Técnicas

- Los IDs de Estado son **fijos y críticos**: 1=Disponible, 2=Asignado, 3=Averiado/Retirado
- El código es **dinámico**: funciona correctamente aunque se modifique el número de estados
- Las estadísticas se calculan **dinámicamente** sin asumir cantidad fija de estados
- El cambio de estado de equipos es **automático** en asignaciones y devoluciones
