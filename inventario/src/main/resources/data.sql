USE Inventario;

-- =======================================================
-- 1. LIMPIEZA PREVIA (Opcional, por si ya tenías basura)
-- =======================================================
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE Inventario;
TRUNCATE TABLE Ordenadores;
TRUNCATE TABLE Telefonos;
TRUNCATE TABLE Pantallas;
TRUNCATE TABLE DockingStations;
TRUNCATE TABLE Ratones;
TRUNCATE TABLE Teclados;
TRUNCATE TABLE Equipos;
TRUNCATE TABLE Modelo;
TRUNCATE TABLE Marca;
TRUNCATE TABLE Linea;
TRUNCATE TABLE Usuario;
-- NO truncar Estado ni Tipo para preservar datos existentes
-- TRUNCATE TABLE Estado;
-- TRUNCATE TABLE Tipo; -- No borramos Tipo porque son fijos (1..6)
SET FOREIGN_KEY_CHECKS = 1;

-- =======================================================
-- 2. DICCIONARIOS BÁSICOS
-- =======================================================

-- Estados (usar INSERT IGNORE para evitar duplicados)
INSERT IGNORE INTO Estado (id_estado, nombre_estado) VALUES
(1, 'Disponible'),
(2, 'Asignado'),
(3, 'En reparación'),
(4, 'De baja');

-- TipoObjeto (usar INSERT IGNORE para evitar duplicados)
INSERT IGNORE INTO Tipo (id_tipo, nombre) VALUES
(1, 'Ordenador'),
(2, 'Teléfono'),
(3, 'Pantalla'),
(4, 'Ratón'),
(5, 'Teclado'),
(6, 'Docking Station');

-- Marcas
INSERT INTO Marca (nombre_fabricante) VALUES 
('HP'), 
('Samsung'), 
('Apple'), 
('Lenovo'), 
('Dell'), 
('Logitech'), 
('Philips');

-- Modelos (Asumiendo IDs de marca: 1=HP, 2=Samsung, 3=Apple, 4=Lenovo, 5=Dell, 6=Logitech, 7=Philips)
INSERT INTO Modelo (nombre, id_marca) VALUES 
('EliteBook 840 G8', 1),      -- ID 1 (HP)
('Galaxy S23', 2),            -- ID 2 (Samsung)
('iPhone 13', 3),             -- ID 3 (Apple)
('ThinkCentre M720', 4),      -- ID 4 (Lenovo - Torre)
('Monitor P2419H', 5),        -- ID 5 (Dell)
('MX Master 3', 6),           -- ID 6 (Logitech)
('Teclado K120', 6),          -- ID 7 (Logitech)
('Docking USB-C G5', 1),      -- ID 8 (HP)
('iPhone 14 Pro', 3),         -- ID 9 (Apple)
('Monitor 273V7', 7);         -- ID 10 (Philips)

-- Líneas Móviles
INSERT INTO Linea (numero_corto, numero_largo, puk, tiene_datos) VALUES 
('1050', '600111222', '12345678', TRUE),  -- ID 1 (Datos)
('2040', '600333444', '87654321', FALSE), -- ID 2 (Voz)
('3030', '600555666', '11223344', TRUE),  -- ID 3 (Datos)
(NULL,   '600999888', '00000000', TRUE);  -- ID 4 (SIM Datos suelta para portátil)

-- Usuarios
INSERT INTO Usuario (nombre, apellidos, ldap) VALUES 
('Juan', 'García Pérez', 'jgarciap'),
('Ana', 'López Martínez', 'alopezm'),
('Carlos', 'Ruiz Ruiz', 'cruizr'),
('María', 'Sánchez Cotán', 'msanchezc'),
('Admin', 'Sistemas', 'admin');

-- =======================================================
-- 3. EQUIPOS (TABLA PADRE)
-- =======================================================
-- OJO: Aquí definimos los IDs manualmente para sincronizar con los hijos.
-- Tipos: 1=Ordenador, 2=Telefono, 3=Pantalla, 4=Raton, 5=Teclado, 6=Docking

-- EQUIPO 1: Portátil HP (Disponible)
INSERT INTO Equipos (id_equipo, id_tipo, id_modelo, id_estado, numero_serie, observaciones) 
VALUES (1, 1, 1, 1, '5CG1234XYZ', 'Portátil de pool de reserva');

-- EQUIPO 2: Portátil HP (Asignado)
INSERT INTO Equipos (id_equipo, id_tipo, id_modelo, id_estado, numero_serie, observaciones) 
VALUES (2, 1, 1, 2, '5CG5678ABC', 'Entregado con maletín');

-- EQUIPO 3: Torre Lenovo (Asignado)
INSERT INTO Equipos (id_equipo, id_tipo, id_modelo, id_estado, numero_serie, observaciones) 
VALUES (3, 1, 4, 2, 'MJ009988', 'PC de sobremesa secretaría');

-- EQUIPO 4: Samsung S23 (Asignado)
INSERT INTO Equipos (id_equipo, id_tipo, id_modelo, id_estado, numero_serie, observaciones) 
VALUES (4, 2, 2, 2, 'IMEI35444333222111', 'Pantalla sin protector');

-- EQUIPO 5: iPhone 13 (Averiado)
INSERT INTO Equipos (id_equipo, id_tipo, id_modelo, id_estado, numero_serie, observaciones) 
VALUES (5, 2, 3, 3, 'IMEI998877665544', 'Pantalla rota');

-- EQUIPO 6: Monitor Dell (Asignado)
INSERT INTO Equipos (id_equipo, id_tipo, id_modelo, id_estado, numero_serie, observaciones) 
VALUES (6, 3, 5, 2, 'CN-0H123-74445', NULL);

-- EQUIPO 7: Docking HP (Disponible)
INSERT INTO Equipos (id_equipo, id_tipo, id_modelo, id_estado, numero_serie, observaciones) 
VALUES (7, 6, 8, 1, '8SS776655', NULL);

-- EQUIPO 8: Ratón Logitech (Asignado)
INSERT INTO Equipos (id_equipo, id_tipo, id_modelo, id_estado, numero_serie, observaciones) 
VALUES (8, 4, 6, 2, 'SN-MOUSE-001', NULL);

-- EQUIPO 9: Teclado Logitech (Asignado)
INSERT INTO Equipos (id_equipo, id_tipo, id_modelo, id_estado, numero_serie, observaciones) 
VALUES (9, 5, 7, 2, 'SN-KEY-002', NULL);

-- EQUIPO 10: iPhone 14 Pro (Disponible)
INSERT INTO Equipos (id_equipo, id_tipo, id_modelo, id_estado, numero_serie, observaciones) 
VALUES (10, 2, 9, 1, 'IMEI111222333444', 'Nuevo, en caja');

-- =======================================================
-- 4. EQUIPOS (TABLAS HIJAS)
-- =======================================================

-- ORDENADORES (IDs 1, 2, 3)
-- Equipo 1: Portátil sin SIM (Movilidad TRUE)
INSERT INTO Ordenadores (id_equipo, codigo_crija, id_linea, movilidad) 
VALUES (1, 'CRIJA-2023-001', NULL, TRUE);

-- Equipo 2: Portátil con SIM de datos (ID 4) (Movilidad TRUE)
INSERT INTO Ordenadores (id_equipo, codigo_crija, id_linea, movilidad) 
VALUES (2, 'CRIJA-2023-005', 4, TRUE);

-- Equipo 3: Torre (Movilidad FALSE)
INSERT INTO Ordenadores (id_equipo, codigo_crija, id_linea, movilidad) 
VALUES (3, 'CRIJA-2020-999', NULL, FALSE);


-- TELEFONOS (IDs 4, 5, 10)
-- Equipo 4: Samsung con Línea 1
INSERT INTO Telefonos (id_equipo, id_linea, movilidad) 
VALUES (4, 1, TRUE);

-- Equipo 5: iPhone con Línea 2
INSERT INTO Telefonos (id_equipo, id_linea, movilidad) 
VALUES (5, 2, TRUE);

-- Equipo 10: iPhone sin Línea (Stock)
INSERT INTO Telefonos (id_equipo, id_linea, movilidad) 
VALUES (10, NULL, TRUE);


-- PANTALLAS (ID 6)
INSERT INTO Pantallas (id_equipo, codigo_crija) 
VALUES (6, 'CRIJA-MON-001');


-- DOCKING STATIONS (ID 7)
INSERT INTO DockingStations (id_equipo, mac_address) 
VALUES (7, 'AA:BB:CC:DD:EE:FF');


-- RATONES (ID 8)
INSERT INTO Ratones (id_equipo) VALUES (8);


-- TECLADOS (ID 9)
INSERT INTO Teclados (id_equipo) VALUES (9);

-- =======================================================
-- 5. HISTÓRICO DE INVENTARIO (ASIGNACIONES)
-- =======================================================

-- 1. Juan tiene el Portátil HP (Equipo 2) actualmente
INSERT INTO Inventario (id_usuario, id_equipo, fecha_asignacion, fecha_devolucion) 
VALUES (1, 2, '2023-01-10 09:00:00', NULL);

-- 2. Ana tiene el Samsung (Equipo 4) actualmente
INSERT INTO Inventario (id_usuario, id_equipo, fecha_asignacion, fecha_devolucion) 
VALUES (2, 4, '2023-02-15 10:30:00', NULL);

-- 3. Ana también tiene el Monitor Dell (Equipo 6)
INSERT INTO Inventario (id_usuario, id_equipo, fecha_asignacion, fecha_devolucion) 
VALUES (2, 6, '2023-02-15 10:35:00', NULL);

-- 4. HISTÓRICO: Carlos tuvo el iPhone 13 (Equipo 5) pero se rompió y lo devolvió
INSERT INTO Inventario (id_usuario, id_equipo, fecha_asignacion, fecha_devolucion) 
VALUES (3, 5, '2023-01-01 08:00:00', '2023-06-01 14:00:00');

-- 5. María tiene la Torre Lenovo (Equipo 3)
INSERT INTO Inventario (id_usuario, id_equipo, fecha_asignacion, fecha_devolucion) 
VALUES (4, 3, '2022-11-20 09:00:00', NULL);