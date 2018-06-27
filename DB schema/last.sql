CREATE DATABASE  IF NOT EXISTS `sysprog` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `sysprog`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: sysprog
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.21-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activation`
--

DROP TABLE IF EXISTS `activation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `code` varchar(45) NOT NULL,
  `recordDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activation`
--

LOCK TABLES `activation` WRITE;
/*!40000 ALTER TABLE `activation` DISABLE KEYS */;
/*!40000 ALTER TABLE `activation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ban`
--

DROP TABLE IF EXISTS `ban`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ban` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `explanation` varchar(45) NOT NULL,
  `ban_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `banned` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`,`user_id`),
  KEY `fk_ban_user1_idx` (`user_id`),
  CONSTRAINT `fk_ban_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ban`
--

LOCK TABLES `ban` WRITE;
/*!40000 ALTER TABLE `ban` DISABLE KEYS */;
INSERT INTO `ban` VALUES (1,2,'Spam','2017-05-16 22:51:22','2017-05-18 22:51:22',1);
/*!40000 ALTER TABLE `ban` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complaint`
--

DROP TABLE IF EXISTS `complaint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `complaint` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `location_id` int(11) NOT NULL,
  `support_type_id` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `complainant_user_id` int(11) NOT NULL,
  `complaint_time` datetime DEFAULT NULL,
  `complaint_text` varchar(255) NOT NULL,
  `support_user_id` int(11) DEFAULT NULL,
  `response_time` datetime DEFAULT NULL,
  `response_text` varchar(255) DEFAULT NULL,
  `child_id` int(11) DEFAULT NULL,
  `ended` tinyint(4) DEFAULT NULL,
  `photo` mediumblob,
  `ack` tinyint(4) DEFAULT NULL,
  `reported` tinyint(4) DEFAULT NULL,
  `ack_time` datetime DEFAULT NULL,
  `assign_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`location_id`,`support_type_id`,`complainant_user_id`),
  KEY `fk_complaint_location1_idx` (`location_id`),
  KEY `fk_complaint_support_type1_idx` (`support_type_id`),
  KEY `fk_complaint_complaint1_idx` (`parent_id`),
  KEY `fk_complaint_user1_idx` (`complainant_user_id`),
  KEY `fk_complaint_user2_idx` (`support_user_id`),
  KEY `fk_complaint_complaint2_idx` (`child_id`),
  CONSTRAINT `fk_complaint_complaint1` FOREIGN KEY (`parent_id`) REFERENCES `complaint` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_complaint_complaint2` FOREIGN KEY (`child_id`) REFERENCES `complaint` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_complaint_location1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_complaint_support_type1` FOREIGN KEY (`support_type_id`) REFERENCES `support_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_complaint_user1` FOREIGN KEY (`complainant_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_complaint_user2` FOREIGN KEY (`support_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaint`
--

LOCK TABLES `complaint` WRITE;
/*!40000 ALTER TABLE `complaint` DISABLE KEYS */;
INSERT INTO `complaint` VALUES (1,2,1,NULL,2,'2017-05-16 22:03:12','Fakülte girişindeki cam kırılmış.',4,'2017-05-16 22:13:45','Cam takıldı.',NULL,1,NULL,1,0,'2017-05-16 22:13:09','2017-05-16 22:10:13'),(2,2,1,NULL,3,'2017-05-16 22:10:51','Bölümün arka kapısında cam kırılmış.',4,'2017-05-16 22:16:24','Cam kalmadığı için takamıyoruz.',3,1,NULL,1,0,'2017-05-16 22:13:15','2017-05-16 22:12:36'),(3,1,2,2,3,'2017-05-16 22:16:24','Cam alınması gerekiyor.',5,'2017-05-16 22:44:13','10 tane cam aldım.',7,1,NULL,1,0,'2017-05-16 22:44:00','2017-05-16 22:31:05'),(7,2,1,3,3,'2017-05-16 22:44:14','Bölümün arka kapısında cam kırılmış.',4,'2017-05-16 22:47:15','Cam takıldığı için ortalık dağıldı.',8,1,NULL,1,0,'2017-05-16 22:46:10','2017-05-16 22:45:39'),(8,2,3,7,3,'2017-05-16 22:48:10','Yerdeki cam kırıklarının temizlenmesi gerekiyor.',6,'2017-05-16 22:58:31','Temizlendi.',NULL,1,NULL,1,0,'2017-05-16 22:58:22','2017-05-16 22:48:19'),(10,1,3,NULL,2,'2017-05-16 22:49:25','Yerde cam kırıkları var2.',NULL,'2017-05-16 22:57:55','Spam.',NULL,1,NULL,0,0,'2017-05-16 22:57:55','2017-05-16 22:57:55');
/*!40000 ALTER TABLE `complaint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(60) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_location_location1_idx` (`parent_id`),
  CONSTRAINT `fk_location_location1` FOREIGN KEY (`parent_id`) REFERENCES `location` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Davutpaşa',NULL),(2,'Elektrik Elektronik Fakültesi',1),(3,'Bilgisayar mühendisliği',2);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `location_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`user_id`,`location_id`),
  KEY `fk_manager_user1_idx` (`user_id`),
  KEY `fk_manager_location1_idx` (`location_id`),
  CONSTRAINT `fk_manager_location1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_manager_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES (1,7,1);
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `complaint_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`user_id`,`complaint_id`),
  KEY `fk_notification_user1_idx` (`user_id`),
  KEY `fk_notification_complaint1_idx` (`complaint_id`),
  CONSTRAINT `fk_notification_complaint1` FOREIGN KEY (`complaint_id`) REFERENCES `complaint` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_notification_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `support_type`
--

DROP TABLE IF EXISTS `support_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `support_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `support_type`
--

LOCK TABLES `support_type` WRITE;
/*!40000 ALTER TABLE `support_type` DISABLE KEYS */;
INSERT INTO `support_type` VALUES (1,'Camcı'),(2,'Depo'),(3,'Temizlik');
/*!40000 ALTER TABLE `support_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supporter`
--

DROP TABLE IF EXISTS `supporter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supporter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `support_type_id` int(11) NOT NULL,
  `location_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`user_id`,`support_type_id`,`location_id`),
  KEY `fk_supporter_user1_idx` (`user_id`),
  KEY `fk_supporter_support_type1_idx` (`support_type_id`),
  KEY `fk_supporter_location1_idx` (`location_id`),
  CONSTRAINT `fk_supporter_location1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_supporter_support_type1` FOREIGN KEY (`support_type_id`) REFERENCES `support_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_supporter_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supporter`
--

LOCK TABLES `supporter` WRITE;
/*!40000 ALTER TABLE `supporter` DISABLE KEYS */;
INSERT INTO `supporter` VALUES (1,4,1,2),(2,5,2,1),(3,6,3,2);
/*!40000 ALTER TABLE `supporter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','$2a$06$nHPC8aapNP9MJXMhxMrKdOflT12LSsLMnvQdUbnxTCzC1fhkOAS3e','mertaydar@outlook.com',1),(2,'user1','$2a$10$D3HJcmjJB7J8sdgJRZ/AMOLeTeiYW8qzehqBTKlAWAY/1XAEp.cNO','mertaydar@outlook.com',0),(3,'user2','$2a$10$KXrUR4iOHIsR8BOpGrxWqONiNRnDXHYJtqiVUqunUwcowFn69Mhym','mertaydar@outlook.com',1),(4,'sup1','$2a$10$7XRLOpyKH.nOQFTNIrYvuOm4Fukn5MyEd0RCA10c9T/FfSIHqeH8e','mertaydar@outlook.com',1),(5,'sup2','$2a$10$jqFLHRPbqWyy09DGeuqRM.ODcOpr1I3c7RBtpsaEgsxYgG/oDIWrC','mertaydar@outlook.com',1),(6,'sup3','$2a$10$zIDNf4DGkEGK5mB9Y/k8au8wW4rl3MwDRquQg8sa8.iVVAPb1aHGe','mertaydar@outlook.com',1),(7,'manager','$2a$10$svQiUXn9.dk3.tXMln3Cge7Z0XVsCJ/n6D.v4KiE95XwN/OPwUtkq','mertaydar@outlook.com',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`,`user_id`),
  KEY `fk_user_roles_user_idx` (`user_id`),
  CONSTRAINT `fk_user_roles_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1,'ADMIN'),(2,2,'USER'),(3,3,'USER'),(4,4,'USER'),(5,5,'USER'),(6,6,'USER'),(7,4,'SUPPORT'),(8,5,'SUPPORT'),(9,6,'SUPPORT'),(10,7,'USER'),(11,7,'MANAGER');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-17  0:17:40
