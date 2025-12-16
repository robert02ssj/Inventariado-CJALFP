Create Database  Inventario;
use Inventario

/* ==========================================
 1. TABLAS INDEPENDIENTES (Diccionarios)
 Se crean primero porque no dependen de nadie
 ========================================== */

CREATE TABLE Marca (
 id_marca INT PRIMARY KEY AUTO_INCREMENT,
 nombre_fabricante VARCHAR(100) NOT NULL
);

CREATE TABLE Tipo (
  id_tipo INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(50) NOT NULL
);

CREATE TABLE Estado (
  id_estado INT PRIMARY KEY AUTO_INCREMENT,
  nombre_estado VARCHAR(50) NOT NULL -- Ej: 'Disponible', 'Asignado', 'Averiado'
);

CREATE TABLE Usuario (
  id_usuario INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(50) NOT NULL,
  apellidos VARCHAR(100),
  ldap VARCHAR(50), -- Usuario de red/dominio
  puesto VARCHAR(100)
);

/* ==========================================
 2. TABLAS DEPENDIENTES (Nivel 1)
 ========================================== */

-- El Modelo depende de la Marca
CREATE TABLE Modelo (
  id_modelo INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  id_marca INT NOT NULL,
  FOREIGN KEY (id_marca) REFERENCES Marca(id_marca)
);

/* ==========================================
 3. TABLA EQUIPOS (Activos)
 Centraliza la información técnica
 ========================================== */

CREATE TABLE Equipos (
  id_equipo INT PRIMARY KEY AUTO_INCREMENT,
  sn_emei VARCHAR(100) UNIQUE, -- Número de serie único
  linea_crija VARCHAR(100),  -- Campo corregido
  id_modelo INT NOT NULL,   -- Relaciona con Modelo (y a su vez con Marca)
  id_tipo INT NOT NULL,
  id_estado INT NOT NULL,
  FOREIGN KEY (id_modelo) REFERENCES Modelo(id_modelo),
  FOREIGN KEY (id_tipo) REFERENCES Tipo(id_tipo),
  FOREIGN KEY (id_estado) REFERENCES Estado(id_estado)
);

/* ==========================================
 4. TABLA INVENTARIO (Histórico)
 Relación Muchos a Muchos: Usuarios <-> Equipos
 ========================================== */

CREATE TABLE Inventario (
  id_inventario INT PRIMARY KEY AUTO_INCREMENT,
  id_usuario INT NOT NULL,
  id_equipo INT NOT NULL,
  fecha_asignacion DATETIME DEFAULT CURRENT_TIMESTAMP,
  fecha_devolucion DATETIME NULL, -- Se deja NULL mientras el usuario tenga el equipo
  FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
  FOREIGN KEY (id_equipo) REFERENCES Equipos(id_equipo)
);