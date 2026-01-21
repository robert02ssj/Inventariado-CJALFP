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
INSERT INTO `Usuario` VALUES (1,'Fernández Díaz','roberto.fernandez','Roberto'),(2,'Mateo García','josel.mateo','Jose Luis'),(3,'Toro Ibañez','josemanuel.toro','Jose Manuel'),(4,'Gomez Gazquez','rebeca.gomez','Rebeca'),(5,'Molina Temino','amaria.molina','Ana Maria'),(6,'Ramos Paris','luzv.ramos','Luz Veronica'),(7,'Fresneda Clement','mariad.fresneda','Maria Dolores'),(8,'Muñoz del Pozo Baños','albertoj.munozpozo','Alberto Jesus'),(9,'Sabio Villegas','patricia.sabio','Patricia'),(10,'Acosta Alcoba','carmenm.acosta','Carmen Maria'),(11,'Martinez Torres','marial.martinez.torres','Maria Luisa'),(12,'Aparicio Navarro','mariam.aparicio','Maria del Mar'),(13,'Garcia Martinez','blancam.garcia','Blanca del Mar'),(14,'Torres Carallor','concepcion.torres','Concepcion'),(15,'Lopez Lopez','cristinam.lopez','Cristina Maria'),(16,'Perez Hernandez','mariaj.perez.hernandez','Maria Jose'),(17,'Ruiz Daimiel','mdolores.ruiz','Maria Dolores'),(18,'Garcia Reina','mariac.garcia.reina','Maria del Carmen'),(19,'Alarcon Gonzalez','mariav.alarcon','Maria Victoria'),(20,'Guzman Martinez','amelia.guzman','Amelia'),(21,'Gerez Garcia','esther.gerez','Ester'),(22,'Suarez Ruano','mariai.suarez','Inmaculada'),(23,'Bautista de los Santos','josei.bautista','Jose Ignacio'),(24,'Sanchez Soriano','juanf.sanchez.soriano','Juan Francisco'),(25,'Garcia Cazorla','mmar.garcia.c','Maria del Mar'),(26,'Carbayo Gorriz','marial.carbayo','Maria Luisa'),(27,'Arnedo Garrigos','mariat.arnedo','Maria Teresa'),(28,'Requena Toribio','carmenm.requena','Carmen Maria'),(29,'Lopez Martinez','javier.lopez','Javier'),(30,'Garcia Segura','manuel.garcia.segura','Manuel'),(31,'Pimentel Asensio','pilar.pimentel','Pilar'),(32,'Ortega Nuñez','irene.ortega.nunez','Irene'),(33,'Bonachera Villegas','josea.bonachera','Jose Antonio'),(34,'Rodriguez Garcia','mariai.rodriguez.garcia','Maria Isabel'),(35,'Sanchez-Sicilia Garcia-Alix','anai.sanchezsicilia','Ana Inmaculada'),(36,'Gonzalez Espinosa','carmen.gonzalez.espinosa','Carmen'),(37,'Gomez de las Heras','rafaelp.gomez','Rafael Pedro'),(38,'Santos Espigares','rafaelj.santos','Rafael');


ALTER TABLE Inventario 
ADD COLUMN ruta_pdf VARCHAR(255) AFTER fecha_devolucion;