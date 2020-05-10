-- MySQL dump 10.13  Distrib 5.7.30, for Linux (x86_64)
--
-- Host: localhost    Database: biskit
-- ------------------------------------------------------
-- Server version	5.7.30-0ubuntu0.18.04.1

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
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_price` double DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `stock_count` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES (1,349.5,'Ahaglow S Foam FaceWash',30),(3,249.5,'Deodrant',20),(4,58999,'OnePlus 8',10);
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `count` bigint(20) DEFAULT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_status` varchar(255) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,294995,5,4,'CANCELLED',1),(2,294995,5,4,'CANCELLED',2),(3,58999,1,4,'CANCELLED',3),(4,58999,1,4,'CANCELLED',4),(5,249.5,1,3,'CANCELLED',5),(6,1247.5,5,3,'CANCELLED',6),(7,1747.5,5,1,'CANCELLED',6),(8,1747.5,5,1,'CANCELLED',7),(9,1996,8,3,'CANCELLED',7),(10,117998,2,4,'CANCELLED',7);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_status` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'CANCELLED',2,'2020-05-10 15:22:49','2020-05-10 16:21:49'),(2,'CANCELLED',2,'2020-05-10 15:24:17','2020-05-10 16:21:54'),(3,'CANCELLED',2,'2020-05-10 15:42:08','2020-05-10 16:21:58'),(4,'CANCELLED',2,'2020-05-10 15:58:10','2020-05-10 16:20:57'),(5,'CANCELLED',2,'2020-05-10 16:23:18','2020-05-10 16:39:43'),(6,'CANCELLED',2,'2020-05-10 17:23:39','2020-05-10 17:27:57'),(7,'CANCELLED',8,'2020-05-10 19:21:56','2020-05-10 20:12:13');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ix_usr_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'BisKit Street','2020-05-09 15:29:10','asperanza23@gmail.com','Gaurav Atul','$2a$10$jsL0i61wGz1xL4AdXDnQlO1rasz8Qt/BFAGr2SE4K2i3BrOCsePxe','7059814138','800008','ROLE_ADMIN'),(2,'BisKit Street','2020-05-10 10:33:01','firstCustomer@mail.com','First Customer','$2a$10$jsL0i61wGz1xL4AdXDnQlO1rasz8Qt/BFAGr2SE4K2i3BrOCsePxe','1234567890','800008','ROLE_CUSTOMER'),(4,'BisKit Street','2020-05-10 10:57:49','secondCustomer@mail.com','Second Customer','$2a$10$jsL0i61wGz1xL4AdXDnQlO1rasz8Qt/BFAGr2SE4K2i3BrOCsePxe','1234567890','800008','ROLE_CUSTOMER'),(5,'BisKit Street','2020-05-10 18:47:20','admin@mail.com','Admin','$2a$10$amJi/GH4A2ChIH6h.O/Ko.T3ZqQ5uUoycZDobYXcSHVwe3T0iVVv.','1234567890','800008','ROLE_ADMIN'),(6,'BisKit Street','2020-05-10 18:51:18','thirdCustomer@mail.com','Third Customer','$2a$10$/r.Oj2TGENYHiBKFexv2XeNTSTJdKYJv67uRxoYahD7QYGX46GZh6','1234567890','800008','ROLE_CUSTOMER'),(8,'Karol bagh,delhi','2020-05-10 19:06:00','helloimkk17@gmail.com','kunal karan','$2a$10$CTW8kN9iuctauou6Fdugk.X0LOVwe/pKi170jZFXfKPj9g9z9QMm2','8810407064','201007','ROLE_CUSTOMER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'biskit'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-10 20:51:26
