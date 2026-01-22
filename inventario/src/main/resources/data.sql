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
(3, 'Averiado/Retirado');


-- TipoObjeto (usar INSERT IGNORE para evitar duplicados)
INSERT IGNORE INTO Tipo (id_tipo, nombre) VALUES
(1, 'Ordenador'),
(2, 'Teléfono'),
(3, 'Pantalla'),
(4, 'Ratón'),
(5, 'Teclado'),
(6, 'Docking Station');


-- Usuarios
INSERT INTO `Usuario` VALUES
(1,'Roberto','Fernández Díaz','roberto.fernandez'),
(2,'Jose Luis','Mateo García','josel.mateo'),
(3,'Jose Manuel','Toro Ibañez','josemanuel.toro'),
(4,'Rebeca','Gomez Gazquez','rebeca.gomez'),
(5,'Ana Maria','Molina Temino','amaria.molina'),
(6,'Luz Veronica','Ramos Paris','luzv.ramos'),
(7,'Maria Dolores','Fresneda Clement','mariad.fresneda'),
(8,'Alberto Jesus','Muñoz del Pozo Baños','albertoj.munozpozo'),
(9,'Patricia','Sabio Villegas','patricia.sabio'),
(10,'Carmen Maria','Acosta Alcoba','carmenm.acosta'),
(11,'Maria Luisa','Martinez Torres','marial.martinez.torres'),
(12,'Maria del Mar','Aparicio Navarro','mariam.aparicio'),
(13,'Blanca del Mar','Garcia Martinez','blancam.garcia'),
(14,'Concepcion','Torres Carallor','concepcion.torres'),
(15,'Cristina Maria','Lopez Lopez','cristinam.lopez'),
(16,'Maria Jose','Perez Hernandez','mariaj.perez.hernandez'),
(17,'Maria Dolores','Ruiz Daimiel','mdolores.ruiz'),
(18,'Maria del Carmen','Garcia Reina','mariac.garcia.reina'),
(19,'Maria Victoria','Alarcon Gonzalez','mariav.alarcon'),
(20,'Amelia','Guzman Martinez','amelia.guzman'),
(21,'Ester','Gerez Garcia','esther.gerez'),
(22,'Inmaculada','Suarez Ruano','mariai.suarez'),
(23,'Jose Ignacio','Bautista de los Santos','josei.bautista'),
(24,'Juan Francisco','Sanchez Soriano','juanf.sanchez.soriano'),
(25,'Maria del Mar','Garcia Cazorla','mmar.garcia.c'),
(26,'Maria Luisa','Carbayo Gorriz','marial.carbayo'),
(27,'Maria Teresa','Arnedo Garrigos','mariat.arnedo'),
(28,'Carmen Maria','Requena Toribio','carmenm.requena'),
(29,'Javier','Lopez Martinez','javier.lopez'),
(30,'Manuel','Garcia Segura','manuel.garcia.segura'),
(31,'Pilar','Pimentel Asensio','pilar.pimentel'),
(32,'Irene','Ortega Nuñez','irene.ortega.nunez'),
(33,'Jose Antonio','Bonachera Villegas','josea.bonachera'),
(34,'Maria Isabel','Rodriguez Garcia','mariai.rodriguez.garcia'),
(35,'Ana Inmaculada','Sanchez-Sicilia Garcia-Alix','anai.sanchezsicilia'),
(36,'Carmen','Gonzalez Espinosa','carmen.gonzalez.espinosa'),
(37,'Rafael Pedro','Gomez de las Heras','rafaelp.gomez'),
(38,'Rafael','Santos Espigares','rafaelj.santos');


ALTER TABLE Inventario 
ADD COLUMN ruta_pdf VARCHAR(255) AFTER fecha_devolucion;