CREATE DATABASE  IF NOT EXISTS `Inventario` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `Inventario`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: Inventario
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `DockingStations`
--

DROP TABLE IF EXISTS `DockingStations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DockingStations` (
  `mac_address` varchar(50) DEFAULT NULL,
  `id_equipo` int NOT NULL,
  PRIMARY KEY (`id_equipo`),
  UNIQUE KEY `UKrf4drl2aat8mr3rfep0tppub8` (`mac_address`),
  CONSTRAINT `FKf2yo3sw5ul0r8l4prrlbtunhd` FOREIGN KEY (`id_equipo`) REFERENCES `Equipos` (`id_equipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DockingStations`
--

LOCK TABLES `DockingStations` WRITE;
/*!40000 ALTER TABLE `DockingStations` DISABLE KEYS */;
/*!40000 ALTER TABLE `DockingStations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Equipos`
--

DROP TABLE IF EXISTS `Equipos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Equipos` (
  `id_tipo` int NOT NULL,
  `id_equipo` int NOT NULL AUTO_INCREMENT,
  `numero_serie` varchar(100) DEFAULT NULL,
  `observaciones` text,
  `id_estado` int NOT NULL,
  `id_modelo` int NOT NULL,
  PRIMARY KEY (`id_equipo`),
  UNIQUE KEY `UKs3fe4fwnk9qbskr9te1modi9c` (`numero_serie`),
  KEY `FKiotp77akted9kcgw1vmdccenq` (`id_estado`),
  KEY `FKfudxj0bws8sxk3xvnb9mw7sok` (`id_modelo`),
  KEY `FKnwjkqbgnwey69hmsams9ulqku` (`id_tipo`),
  CONSTRAINT `FKfudxj0bws8sxk3xvnb9mw7sok` FOREIGN KEY (`id_modelo`) REFERENCES `Modelo` (`id_modelo`),
  CONSTRAINT `FKiotp77akted9kcgw1vmdccenq` FOREIGN KEY (`id_estado`) REFERENCES `Estado` (`id_estado`),
  CONSTRAINT `FKnwjkqbgnwey69hmsams9ulqku` FOREIGN KEY (`id_tipo`) REFERENCES `Tipo` (`id_tipo`),
  CONSTRAINT `Equipos_chk_1` CHECK ((`id_tipo` in (6,1,3,4,5,2)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Equipos`
--

LOCK TABLES `Equipos` WRITE;
/*!40000 ALTER TABLE `Equipos` DISABLE KEYS */;
/*!40000 ALTER TABLE `Equipos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Estado`
--

DROP TABLE IF EXISTS `Estado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Estado` (
  `id_estado` int NOT NULL AUTO_INCREMENT,
  `nombre_estado` varchar(50) NOT NULL,
  PRIMARY KEY (`id_estado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Estado`
--

LOCK TABLES `Estado` WRITE;
/*!40000 ALTER TABLE `Estado` DISABLE KEYS */;
/*!40000 ALTER TABLE `Estado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Inventario`
--

DROP TABLE IF EXISTS `Inventario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Inventario` (
  `id_inventario` int NOT NULL AUTO_INCREMENT,
  `fecha_asignacion` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_devolucion` datetime(6) DEFAULT NULL,
  `id_equipo` int NOT NULL,
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id_inventario`),
  KEY `FK7t6b88ggsn87d6hlyb2kx812v` (`id_equipo`),
  KEY `FK12upe6cs9ucgal7a5u74ojv1e` (`id_usuario`),
  CONSTRAINT `FK12upe6cs9ucgal7a5u74ojv1e` FOREIGN KEY (`id_usuario`) REFERENCES `Usuario` (`id_usuario`),
  CONSTRAINT `FK7t6b88ggsn87d6hlyb2kx812v` FOREIGN KEY (`id_equipo`) REFERENCES `Equipos` (`id_equipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Inventario`
--

LOCK TABLES `Inventario` WRITE;
/*!40000 ALTER TABLE `Inventario` DISABLE KEYS */;
/*!40000 ALTER TABLE `Inventario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Linea`
--

DROP TABLE IF EXISTS `Linea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Linea` (
  `id_linea` int NOT NULL AUTO_INCREMENT,
  `numero_corto` varchar(20) DEFAULT NULL,
  `numero_largo` varchar(20) DEFAULT NULL,
  `puk` varchar(20) DEFAULT NULL,
  `tiene_datos` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id_linea`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Linea`
--

LOCK TABLES `Linea` WRITE;
/*!40000 ALTER TABLE `Linea` DISABLE KEYS */;
/*!40000 ALTER TABLE `Linea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Marca`
--

DROP TABLE IF EXISTS `Marca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Marca` (
  `id_marca` int NOT NULL AUTO_INCREMENT,
  `nombre_fabricante` varchar(100) NOT NULL,
  PRIMARY KEY (`id_marca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Marca`
--

LOCK TABLES `Marca` WRITE;
/*!40000 ALTER TABLE `Marca` DISABLE KEYS */;
/*!40000 ALTER TABLE `Marca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Modelo`
--

DROP TABLE IF EXISTS `Modelo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Modelo` (
  `id_modelo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `id_marca` int NOT NULL,
  PRIMARY KEY (`id_modelo`),
  KEY `FKjvx22k5gwe3iiwgltmjgvepab` (`id_marca`),
  CONSTRAINT `FKjvx22k5gwe3iiwgltmjgvepab` FOREIGN KEY (`id_marca`) REFERENCES `Marca` (`id_marca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Modelo`
--

LOCK TABLES `Modelo` WRITE;
/*!40000 ALTER TABLE `Modelo` DISABLE KEYS */;
/*!40000 ALTER TABLE `Modelo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Ordenadores`
--

DROP TABLE IF EXISTS `Ordenadores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Ordenadores` (
  `codigo_crija` varchar(50) DEFAULT NULL,
  `movilidad` bit(1) DEFAULT NULL,
  `id_equipo` int NOT NULL,
  `id_linea` int DEFAULT NULL,
  PRIMARY KEY (`id_equipo`),
  UNIQUE KEY `UKmt8b1ipq3i3yff2gvbj14211o` (`codigo_crija`),
  KEY `FK4cxbay08d2rxh7ab2hh6swa71` (`id_linea`),
  CONSTRAINT `FK4cxbay08d2rxh7ab2hh6swa71` FOREIGN KEY (`id_linea`) REFERENCES `Linea` (`id_linea`),
  CONSTRAINT `FKh85ikjxnfiw7aptf354fcc4o2` FOREIGN KEY (`id_equipo`) REFERENCES `Equipos` (`id_equipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ordenadores`
--

LOCK TABLES `Ordenadores` WRITE;
/*!40000 ALTER TABLE `Ordenadores` DISABLE KEYS */;
/*!40000 ALTER TABLE `Ordenadores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pantallas`
--

DROP TABLE IF EXISTS `Pantallas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Pantallas` (
  `codigo_crija` varchar(50) DEFAULT NULL,
  `id_equipo` int NOT NULL,
  PRIMARY KEY (`id_equipo`),
  UNIQUE KEY `UKt0u8yp26qmabjekcp5fecy0eb` (`codigo_crija`),
  CONSTRAINT `FK9tw8vx0yh3w2tr3a1eyewnlcp` FOREIGN KEY (`id_equipo`) REFERENCES `Equipos` (`id_equipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pantallas`
--

LOCK TABLES `Pantallas` WRITE;
/*!40000 ALTER TABLE `Pantallas` DISABLE KEYS */;
/*!40000 ALTER TABLE `Pantallas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Ratones`
--

DROP TABLE IF EXISTS `Ratones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Ratones` (
  `id_equipo` int NOT NULL,
  PRIMARY KEY (`id_equipo`),
  CONSTRAINT `FK8bnwwx2gbhs2ignkbd52mahig` FOREIGN KEY (`id_equipo`) REFERENCES `Equipos` (`id_equipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ratones`
--

LOCK TABLES `Ratones` WRITE;
/*!40000 ALTER TABLE `Ratones` DISABLE KEYS */;
/*!40000 ALTER TABLE `Ratones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Teclados`
--

DROP TABLE IF EXISTS `Teclados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Teclados` (
  `id_equipo` int NOT NULL,
  PRIMARY KEY (`id_equipo`),
  CONSTRAINT `FK8eg5j9ocv764020ir5e8srs1f` FOREIGN KEY (`id_equipo`) REFERENCES `Equipos` (`id_equipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Teclados`
--

LOCK TABLES `Teclados` WRITE;
/*!40000 ALTER TABLE `Teclados` DISABLE KEYS */;
/*!40000 ALTER TABLE `Teclados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Telefonos`
--

DROP TABLE IF EXISTS `Telefonos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Telefonos` (
  `movilidad` bit(1) DEFAULT NULL,
  `id_equipo` int NOT NULL,
  `id_linea` int DEFAULT NULL,
  PRIMARY KEY (`id_equipo`),
  KEY `FKeou55ublxqhffdxl0e79lo3at` (`id_linea`),
  CONSTRAINT `FK91w84md7t3i109cghr3l3yiu7` FOREIGN KEY (`id_equipo`) REFERENCES `Equipos` (`id_equipo`),
  CONSTRAINT `FKeou55ublxqhffdxl0e79lo3at` FOREIGN KEY (`id_linea`) REFERENCES `Linea` (`id_linea`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Telefonos`
--

LOCK TABLES `Telefonos` WRITE;
/*!40000 ALTER TABLE `Telefonos` DISABLE KEYS */;
/*!40000 ALTER TABLE `Telefonos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tipo`
--

DROP TABLE IF EXISTS `Tipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Tipo` (
  `id_tipo` int NOT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id_tipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tipo`
--

LOCK TABLES `Tipo` WRITE;
/*!40000 ALTER TABLE `Tipo` DISABLE KEYS */;
/*!40000 ALTER TABLE `Tipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Usuario`
--

DROP TABLE IF EXISTS `Usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(100) DEFAULT NULL,
  `ldap` varchar(50) DEFAULT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Usuario`
--

LOCK TABLES `Usuario` WRITE;
/*!40000 ALTER TABLE `Usuario` DISABLE KEYS */;
INSERT INTO `Usuario` VALUES (1,'Fernández Díaz','roberto.fernandez','Roberto'),(2,'Mateo García','josel.mateo','Jose Luis'),(3,'Toro Ibañez','josemanuel.toro','Jose Manuel'),(4,'Gomez Gazquez','rebeca.gomez','Rebeca'),(5,'Molina Temino','amaria.molina','Ana Maria'),(6,'Ramos Paris','luzv.ramos','Luz Veronica'),(7,'Fresneda Clement','mariad.fresneda','Maria Dolores'),(8,'Muñoz del Pozo Baños','albertoj.munozpozo','Alberto Jesus'),(9,'Sabio Villegas','patricia.sabio','Patricia'),(10,'Acosta Alcoba','carmenm.acosta','Carmen Maria'),(11,'Martinez Torres','marial.martinez.torres','Maria Luisa'),(12,'Aparicio Navarro','mariam.aparicio','Maria del Mar'),(13,'Garcia Martinez','blancam.garcia','Blanca del Mar'),(14,'Torres Carallor','concepcion.torres','Concepcion'),(15,'Lopez Lopez','cristinam.lopez','Cristina Maria'),(16,'Perez Hernandez','mariaj.perez.hernandez','Maria Jose'),(17,'Ruiz Daimiel','mdolores.ruiz','Maria Dolores'),(18,'Garcia Reina','mariac.garcia.reina','Maria del Carmen'),(19,'Alarcon Gonzalez','mariav.alarcon','Maria Victoria'),(20,'Guzman Martinez','amelia.guzman','Amelia'),(21,'Gerez Garcia','esther.gerez','Ester'),(22,'Suarez Ruano','mariai.suarez','Inmaculada'),(23,'Bautista de los Santos','josei.bautista','Jose Ignacio'),(24,'Sanchez Soriano','juanf.sanchez.soriano','Juan Francisco'),(25,'Garcia Cazorla','mmar.garcia.c','Maria del Mar'),(26,'Carbayo Gorriz','marial.carbayo','Maria Luisa'),(27,'Arnedo Garrigos','mariat.arnedo','Maria Teresa'),(28,'Requena Toribio','carmenm.requena','Carmen Maria'),(29,'Lopez Martinez','javier.lopez','Javier'),(30,'Garcia Segura','manuel.garcia.segura','Manuel'),(31,'Pimentel Asensio','pilar.pimentel','Pilar'),(32,'Ortega Nuñez','irene.ortega.nunez','Irene'),(33,'Bonachera Villegas','josea.bonachera','Jose Antonio'),(34,'Rodriguez Garcia','mariai.rodriguez.garcia','Maria Isabel'),(35,'Sanchez-Sicilia Garcia-Alix','anai.sanchezsicilia','Ana Inmaculada'),(36,'Gonzalez Espinosa','carmen.gonzalez.espinosa','Carmen'),(37,'Gomez de las Heras','rafaelp.gomez','Rafael Pedro'),(38,'Santos Espigares','rafaelj.santos','Rafael');
/*!40000 ALTER TABLE `Usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-16 14:10:10
