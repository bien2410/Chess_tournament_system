CREATE DATABASE  IF NOT EXISTS `kttkpm` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `kttkpm`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: kttkpm
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `tblchitiettrandau`
--

DROP TABLE IF EXISTS `tblchitiettrandau`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblchitiettrandau` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mauCo` varchar(45) NOT NULL,
  `diem` float DEFAULT NULL,
  `idKyThu` int NOT NULL,
  `idTranDau` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblchitiettrandau`
--

LOCK TABLES `tblchitiettrandau` WRITE;
/*!40000 ALTER TABLE `tblchitiettrandau` DISABLE KEYS */;
INSERT INTO `tblchitiettrandau` VALUES (1,'den',1,1,1),(2,'trang',0,2,1),(3,'den',0.5,3,2),(4,'trang',0.5,4,2),(5,'den',0,5,3),(6,'trang',1,6,3),(7,'den',0.5,7,4),(8,'trang',0.5,8,4),(9,'den',0,2,5),(10,'trang',1,5,5),(11,'den',0.5,6,6),(12,'trang',0.5,1,6),(13,'den',1,3,7),(14,'trang',0,7,7),(15,'den',0.5,4,8),(16,'trang',0.5,8,8),(57,'den',0.5,2,21),(58,'trang',0.5,1,21),(59,'den',0.5,4,22),(60,'trang',0.5,3,22),(61,'den',0.5,6,23),(62,'trang',0.5,5,23),(63,'den',0.5,8,24),(64,'trang',0.5,7,24),(73,'den',0,4,29),(74,'trang',1,2,29),(75,'den',0.5,8,30),(76,'trang',0.5,6,30),(77,'den',0,3,31),(78,'trang',1,1,31),(79,'den',0.5,7,32),(80,'trang',0.5,5,32);
/*!40000 ALTER TABLE `tblchitiettrandau` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblgiaidau`
--

DROP TABLE IF EXISTS `tblgiaidau`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblgiaidau` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ten` varchar(255) NOT NULL,
  `diaDiem` varchar(255) NOT NULL,
  `ngayBatDau` date NOT NULL,
  `ngayKetThuc` date NOT NULL,
  `moTa` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblgiaidau`
--

LOCK TABLES `tblgiaidau` WRITE;
/*!40000 ALTER TABLE `tblgiaidau` DISABLE KEYS */;
INSERT INTO `tblgiaidau` VALUES (1,'giai co vua','ha noi','2024-03-26','2024-04-24','gi do'),(2,'giai co cua hau','ninh binh','2024-03-27','2024-04-25','ko co gi'),(3,'giai co tuong','PTIT','2024-04-13','2024-04-26','mh');
/*!40000 ALTER TABLE `tblgiaidau` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblkythu`
--

DROP TABLE IF EXISTS `tblkythu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblkythu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ten` varchar(255) NOT NULL,
  `elo` float NOT NULL,
  `email` varchar(255) NOT NULL,
  `sdt` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblkythu`
--

LOCK TABLES `tblkythu` WRITE;
/*!40000 ALTER TABLE `tblkythu` DISABLE KEYS */;
INSERT INTO `tblkythu` VALUES (1,'Pham Trong Bien',241,'ptbien264@gmail.com','0387948017'),(2,'Nguyen Van A',200,'a@gmail.com','0132464859'),(3,'Tran Van B',186,'b@gmail.com','0646824826'),(4,'Nguyen Thi C ',163,'c@gmail.com','0316471612'),(5,'Dinh Thai D',166,'d@gmail.com','0624854943'),(6,'Hoang Van E',156,'e@gmail.com','0319181612'),(7,'Pham Hoang F',103,'f@gmail.com','0319448412'),(8,'Do Ngoc G',99,'g@gmail.com','0695194626');
/*!40000 ALTER TABLE `tblkythu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblkythugiaidau`
--

DROP TABLE IF EXISTS `tblkythugiaidau`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblkythugiaidau` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idGiaiDau` int NOT NULL,
  `idKyThu` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblkythugiaidau`
--

LOCK TABLES `tblkythugiaidau` WRITE;
/*!40000 ALTER TABLE `tblkythugiaidau` DISABLE KEYS */;
INSERT INTO `tblkythugiaidau` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,1,7),(8,1,8),(9,2,1),(10,2,2),(11,2,3),(12,2,4),(13,2,5),(14,2,6),(15,2,7),(16,2,8);
/*!40000 ALTER TABLE `tblkythugiaidau` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblnguoidung`
--

DROP TABLE IF EXISTS `tblnguoidung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblnguoidung` (
  `id` int NOT NULL AUTO_INCREMENT,
  `taiKhoan` varchar(255) NOT NULL,
  `matKhau` varchar(255) NOT NULL,
  `hoTen` varchar(255) NOT NULL,
  `vaiTro` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `taiKhoan_UNIQUE` (`taiKhoan`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblnguoidung`
--

LOCK TABLES `tblnguoidung` WRITE;
/*!40000 ALTER TABLE `tblnguoidung` DISABLE KEYS */;
INSERT INTO `tblnguoidung` VALUES (1,'bien','bien','Pham Trong Bien','quan ly');
/*!40000 ALTER TABLE `tblnguoidung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbltrandau`
--

DROP TABLE IF EXISTS `tbltrandau`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbltrandau` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idVongDau` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbltrandau`
--

LOCK TABLES `tbltrandau` WRITE;
/*!40000 ALTER TABLE `tbltrandau` DISABLE KEYS */;
INSERT INTO `tbltrandau` VALUES (1,1),(2,1),(3,1),(4,1),(5,2),(6,2),(7,2),(8,2),(21,9),(22,9),(23,9),(24,9),(29,10),(30,10),(31,10),(32,10);
/*!40000 ALTER TABLE `tbltrandau` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblvongdau`
--

DROP TABLE IF EXISTS `tblvongdau`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblvongdau` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ten` varchar(45) NOT NULL,
  `thoiGian` date NOT NULL,
  `idGiaiDau` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblvongdau`
--

LOCK TABLES `tblvongdau` WRITE;
/*!40000 ALTER TABLE `tblvongdau` DISABLE KEYS */;
INSERT INTO `tblvongdau` VALUES (1,'1','2024-03-26',1),(2,'2','2024-03-28',1),(3,'3','2024-04-15',1),(4,'4','2024-04-17',1),(5,'5','2024-04-18',1),(6,'6','2024-04-19',1),(7,'7','2024-04-21',1),(8,'8','2024-04-23',1),(9,'1','2024-04-02',2),(10,'2','2024-04-04',2),(11,'3','2024-04-06',2),(12,'4','2024-04-08',2);
/*!40000 ALTER TABLE `tblvongdau` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-18 23:56:17
