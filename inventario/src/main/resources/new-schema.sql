DROP DATABASE IF EXISTS Inventario;
CREATE DATABASE Inventario CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE Inventario;

-- 1. TIPOS (Discriminador)
CREATE TABLE Tipo (
    id_tipo INT PRIMARY KEY, 
    nombre VARCHAR(50) NOT NULL
);
INSERT INTO Tipo VALUES 
(1, 'Ordenador'), 
(2, 'Telefono'), 
(3, 'Pantalla'), 
(4, 'Raton'), 
(5, 'Teclado'),
(6, 'Docking Station'); -- <--- NUEVO TIPO

-- 2. DICCIONARIOS / TABLAS INDEPENDIENTES
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
CREATE TABLE Linea (
    id_linea INT PRIMARY KEY AUTO_INCREMENT,
    numero_corto VARCHAR(20),
    numero_largo VARCHAR(20),
    puk VARCHAR(20),
    tiene_datos BOOLEAN DEFAULT FALSE
);

-- 3. TABLA PADRE: EQUIPOS
CREATE TABLE Equipos (
    id_equipo INT PRIMARY KEY AUTO_INCREMENT,
    id_tipo INT NOT NULL,
    id_modelo INT NOT NULL,
    id_estado INT NOT NULL,
    numero_serie VARCHAR(100) UNIQUE, -- S/N heredado también por la Docking
    observaciones TEXT,
    
    FOREIGN KEY (id_tipo) REFERENCES Tipo(id_tipo),
    FOREIGN KEY (id_modelo) REFERENCES Modelo(id_modelo),
    FOREIGN KEY (id_estado) REFERENCES Estado(id_estado)
);

-- 4. HIJOS (Herencia)

-- ORDENADORES
CREATE TABLE Ordenadores (
    id_equipo INT PRIMARY KEY,
    codigo_crija VARCHAR(50) UNIQUE,
    id_linea INT, 
    FOREIGN KEY (id_equipo) REFERENCES Equipos(id_equipo) ON DELETE CASCADE,
    FOREIGN KEY (id_linea) REFERENCES Linea(id_linea)
);

-- TELEFONOS
CREATE TABLE Telefonos (
    id_equipo INT PRIMARY KEY,
    id_linea INT,
    movilidad BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_equipo) REFERENCES Equipos(id_equipo) ON DELETE CASCADE,
    FOREIGN KEY (id_linea) REFERENCES Linea(id_linea)
);

-- PANTALLAS
CREATE TABLE Pantallas (
    id_equipo INT PRIMARY KEY,
    codigo_crija VARCHAR(50) UNIQUE,
    FOREIGN KEY (id_equipo) REFERENCES Equipos(id_equipo) ON DELETE CASCADE
);

-- RATONES, TECLADOS (Simples)
CREATE TABLE Ratones (
    id_equipo INT PRIMARY KEY,
    FOREIGN KEY (id_equipo) REFERENCES Equipos(id_equipo) ON DELETE CASCADE
);
CREATE TABLE Teclados (
    id_equipo INT PRIMARY KEY,
    FOREIGN KEY (id_equipo) REFERENCES Equipos(id_equipo) ON DELETE CASCADE
);

-- DOCKING STATIONS (NUEVO HIJO)
CREATE TABLE DockingStations (
    id_equipo INT PRIMARY KEY,
    mac_address VARCHAR(50) UNIQUE, -- El dato específico
    FOREIGN KEY (id_equipo) REFERENCES Equipos(id_equipo) ON DELETE CASCADE
);

-- 5. USUARIOS E INVENTARIO
CREATE TABLE Usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100),
    ldap VARCHAR(50)
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
