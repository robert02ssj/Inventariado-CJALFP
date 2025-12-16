DROP DATABASE IF EXISTS Inventario;
CREATE DATABASE Inventario CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE Inventario;

-- 1. TIPOS (Para el discriminador)
CREATE TABLE Tipo (
    id_tipo INT PRIMARY KEY, 
    nombre VARCHAR(50) NOT NULL
);
-- Mapeamos los IDs fijos para Java
INSERT INTO Tipo (id_tipo, nombre) VALUES 
(1, 'Ordenador'), 
(2, 'Telefono'), 
(3, 'Pantalla'), 
(4, 'Raton'), 
(5, 'Teclado');

-- 2. DICCIONARIOS
CREATE TABLE Marca (
    id_marca INT PRIMARY KEY AUTO_INCREMENT,
    nombre_fabricante VARCHAR(100) NOT NULL
);
CREATE TABLE Modelo (
    id_modelo INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    id_marca INT NOT NULL,
    FOREIGN KEY (id_marca) REFERENCES Marca(id_marca)
);
CREATE TABLE Estado (
    id_estado INT PRIMARY KEY AUTO_INCREMENT,
    nombre_estado VARCHAR(50) NOT NULL 
);

-- 3. TABLA PADRE: EQUIPOS
-- Según tu dibujo, el número de serie/IMEI genérico podría ir aquí si todos lo tienen,
-- pero para ser estrictos con tu dibujo anterior, dejo los específicos en los hijos.
CREATE TABLE Equipos (
    id_equipo INT PRIMARY KEY AUTO_INCREMENT,
    id_tipo INT NOT NULL,  -- Discriminador
    id_modelo INT NOT NULL,
    id_estado INT NOT NULL,
    observaciones TEXT,
    
    FOREIGN KEY (id_tipo) REFERENCES Tipo(id_tipo),
    FOREIGN KEY (id_modelo) REFERENCES Modelo(id_modelo),
    FOREIGN KEY (id_estado) REFERENCES Estado(id_estado)
);

-- 4. HIJOS (Herencia)

-- A. ORDENADORES (Tiene Crija y Linea)
CREATE TABLE Ordenadores (
    id_equipo INT PRIMARY KEY,
    codigo_crija VARCHAR(50) UNIQUE NOT NULL,
    id_linea VARCHAR(50), -- Según tu dibujo
    FOREIGN KEY (id_equipo) REFERENCES Equipos(id_equipo) ON DELETE CASCADE
);

-- B. PANTALLAS (Tiene Crija)
CREATE TABLE Pantallas (
    id_equipo INT PRIMARY KEY,
    codigo_crija VARCHAR(50) UNIQUE NOT NULL,
    FOREIGN KEY (id_equipo) REFERENCES Equipos(id_equipo) ON DELETE CASCADE
);

-- C. TELEFONOS (Tiene Linea y Booleanos)
CREATE TABLE Telefonos (
    id_equipo INT PRIMARY KEY,
    id_linea VARCHAR(50),
    movilidad BOOLEAN DEFAULT FALSE,
    datos BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_equipo) REFERENCES Equipos(id_equipo) ON DELETE CASCADE
);

-- D. RATONES (Sin atributos extra)
CREATE TABLE Ratones (
    id_equipo INT PRIMARY KEY,
    FOREIGN KEY (id_equipo) REFERENCES Equipos(id_equipo) ON DELETE CASCADE
);

-- E. TECLADOS (Sin atributos extra)
CREATE TABLE Teclados (
    id_equipo INT PRIMARY KEY,
    FOREIGN KEY (id_equipo) REFERENCES Equipos(id_equipo) ON DELETE CASCADE
);

-- 5. USUARIOS E INVENTARIO
CREATE TABLE Usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100),
    ldap VARCHAR(50),
    puesto VARCHAR(100)
);

CREATE TABLE Inventario (
    id_inventario INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    id_equipo INT NOT NULL,
    fecha_asignacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_devolucion DATETIME NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_equipo) REFERENCES Equipos(id_equipo)
);
