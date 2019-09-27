CREATE DATABASE  IF NOT EXISTS `itools_v1p0` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `itools_v1p0`;
-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: itools_v1p0
-- ------------------------------------------------------
-- Server version	5.7.23-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `assessor`
--

DROP TABLE IF EXISTS `assessor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `assessor` (
  `AssessorID` int(10) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(100) DEFAULT NULL,
  `FingerID` varchar(100) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `FirstName` varchar(255) DEFAULT NULL,
  `LastName` varchar(255) DEFAULT NULL,
  `EmailAddress` varchar(255) NOT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `Phone` varchar(255) DEFAULT NULL,
  `CompanyCode` varchar(100) DEFAULT NULL,
  `MachineCode` varchar(100) DEFAULT NULL,
  `IsLocked` int(10) NOT NULL DEFAULT '0',
  `IsActive` int(10) NOT NULL DEFAULT '1',
  `LastPassword` varchar(255) DEFAULT NULL,
  `IsFirstTimeLogin` tinyint(1) DEFAULT '0',
  `FailTimes` int(10) NOT NULL DEFAULT '0',
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`AssessorID`),
  KEY `UserName` (`UserName`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assessor`
--


--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `company` (
  `CompanyID` int(10) NOT NULL AUTO_INCREMENT,
  `CompanyCode` varchar(100) NOT NULL,
  `CompanyName` varchar(100) DEFAULT NULL,
  `CompanyType` varchar(100) DEFAULT NULL,
  `Address` varchar(100) DEFAULT NULL,
  `Location` varchar(100) DEFAULT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CompanyID`),
  UNIQUE KEY `CompanyCode` (`CompanyCode`),
  KEY `Company_CompanyCode` (`CompanyCode`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--


--
-- Table structure for table `companymachine`
--

DROP TABLE IF EXISTS `companymachine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `companymachine` (
  `CompanyMachineID` int(10) NOT NULL AUTO_INCREMENT,
  `MachineCode` varchar(100) DEFAULT NULL,
  `CompanyCode` varchar(100) DEFAULT NULL,
  `CreatedDate` timestamp NULL DEFAULT NULL,
  `IsActive` tinyint(1) NOT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CompanyMachineID`),
  KEY `MachineCode` (`MachineCode`),
  KEY `CompanyCode` (`CompanyCode`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `databaseversion`
--

DROP TABLE IF EXISTS `databaseversion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `databaseversion` (
  `iToolAppDatabase` varchar(255) DEFAULT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databaseversion`
--

LOCK TABLES `databaseversion` WRITE;
/*!40000 ALTER TABLE `databaseversion` DISABLE KEYS */;
INSERT INTO `databaseversion` VALUES ('v1p0','2018-08-11 06:02:35');
/*!40000 ALTER TABLE `databaseversion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `federated_assessor`
--

DROP TABLE IF EXISTS `federated_assessor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `federated_assessor` (
  `AssessorID` int(10) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(100) DEFAULT NULL,
  `FingerID` varchar(100) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `FirstName` varchar(255) DEFAULT NULL,
  `LastName` varchar(255) DEFAULT NULL,
  `EmailAddress` varchar(255) NOT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `Phone` varchar(255) DEFAULT NULL,
  `CompanyCode` varchar(100) DEFAULT NULL,
  `MachineCode` varchar(100) DEFAULT NULL,
  `IsLocked` tinyint(1) DEFAULT '0',
  `IsActive` tinyint(1) NOT NULL,
  `LastPassword` varchar(255) DEFAULT NULL,
  `IsFirstTimeLogin` tinyint(1) DEFAULT NULL,
  `FailTimes` int(10) NOT NULL DEFAULT '0',
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`AssessorID`),
  KEY `UserName` (`UserName`)
) ENGINE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.47:3306/tqteamne_iTools/Assessor';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `federated_company`
--

DROP TABLE IF EXISTS `federated_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `federated_company` (
  `CompanyID` int(10) NOT NULL AUTO_INCREMENT,
  `CompanyCode` varchar(100) NOT NULL,
  `CompanyName` varchar(100) DEFAULT NULL,
  `CompanyType` varchar(100) DEFAULT NULL,
  `Address` varchar(100) DEFAULT NULL,
  `Location` varchar(100) DEFAULT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CompanyID`),
  UNIQUE KEY `CompanyCode` (`CompanyCode`),
  KEY `Company_CompanyCode` (`CompanyCode`)
) ENGINE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.47:3306/tqteamne_iTools/Company';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `federated_companymachine`
--

DROP TABLE IF EXISTS `federated_companymachine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `federated_companymachine` (
  `CompanyMachineID` int(10) NOT NULL AUTO_INCREMENT,
  `MachineCode` varchar(100) DEFAULT NULL,
  `CompanyCode` varchar(100) DEFAULT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `IsActive` tinyint(1) NOT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CompanyMachineID`),
  KEY `MachineCode` (`MachineCode`),
  KEY `CompanyCode` (`CompanyCode`)
) ENGINE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.47:3306/tqteamne_iTools/CompanyMachine';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `federated_databaseversion`
--

DROP TABLE IF EXISTS `federated_databaseversion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `federated_databaseversion` (
  `iToolAppDatabase` varchar(255) DEFAULT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.47:3306/tqteamne_iTools/DatabaseVersion';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `federated_machine`
--

DROP TABLE IF EXISTS `federated_machine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `federated_machine` (
  `MachineID` int(10) NOT NULL AUTO_INCREMENT,
  `MachineName` varchar(100) DEFAULT NULL,
  `MachineCode` varchar(100) NOT NULL,
  `Model` varchar(100) DEFAULT NULL,
  `Location` varchar(100) DEFAULT NULL,
  `Description` varchar(100) DEFAULT NULL,
  `CreatedDate` timestamp NULL DEFAULT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `IsActive` tinyint(1) NOT NULL,
  PRIMARY KEY (`MachineID`),
  UNIQUE KEY `MachineCode` (`MachineCode`),
  KEY `Machine_MachineCode` (`MachineCode`)
) ENGINE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.47:3306/tqteamne_iTools/Machine';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `federated_masterlog`
--

DROP TABLE IF EXISTS `federated_masterlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `federated_masterlog` (
  `LogID` int(20) NOT NULL AUTO_INCREMENT,
  `LogDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `AssessorName` varchar(255) DEFAULT NULL,
  `TblName` varchar(100) DEFAULT NULL,
  `RecordID` int(10) DEFAULT NULL,
  `ColumnName` varchar(100) DEFAULT NULL,
  `Action` varchar(100) DEFAULT NULL,
  `OldValue` text,
  `NewValue` text,
  `CompanyCode` varchar(100) DEFAULT NULL,
  `MachineCode` varchar(100) DEFAULT NULL,
  `Notes` text,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`LogID`),
  KEY `RecordID` (`RecordID`),
  KEY `AssessorName` (`AssessorName`),
  KEY `LogDate` (`LogDate`)
) ENGINE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.47:3306/tqteamne_iTools/MasterLog';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `federated_pendingaction`
--

DROP TABLE IF EXISTS `federated_pendingaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `federated_pendingaction` (
  `PendingActionID` int(20) NOT NULL AUTO_INCREMENT,
  `PendingActionDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PendingActionName` varchar(255) DEFAULT NULL,
  `ActionContent` text,
  `Status` varchar(100) DEFAULT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`PendingActionID`)
) ENGINE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.47:3306/tqteamne_iTools/PendingAction';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `federated_roleassessor`
--

DROP TABLE IF EXISTS `federated_roleassessor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `federated_roleassessor` (
  `RoleAssessorID` int(10) NOT NULL AUTO_INCREMENT,
  `RoleID` int(10) DEFAULT NULL,
  `AssessorID` int(10) DEFAULT NULL,
  `CreatedDate` timestamp NULL DEFAULT NULL,
  `IsActive` tinyint(1) NOT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`RoleAssessorID`),
  KEY `CreatedDate` (`CreatedDate`),
  KEY `RoleID` (`RoleID`),
  KEY `AssessorID` (`AssessorID`)
) ENGINE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.47:3306/tqteamne_iTools/RoleAssessor';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `federated_roles`
--

DROP TABLE IF EXISTS `federated_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `federated_roles` (
  `RoleID` int(10) NOT NULL AUTO_INCREMENT,
  `RoleName` varchar(100) DEFAULT NULL,
  `RoleType` int(10) DEFAULT NULL,
  `IsRole` int(10) DEFAULT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`RoleID`)
) ENGINE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.47:3306/tqteamne_iTools/Roles';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `federated_synchistory`
--

DROP TABLE IF EXISTS `federated_synchistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `federated_synchistory` (
  `SyncHistoryID` int(20) NOT NULL AUTO_INCREMENT,
  `SyncDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Statistic` text,
  `Status` varchar(255) DEFAULT NULL,
  `SynType` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`SyncHistoryID`)
) ENGINE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.47:3306/tqteamne_iTools/SyncHistory';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `federated_tools`
--

DROP TABLE IF EXISTS `federated_tools`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `federated_tools` (
  `ToolID` int(10) NOT NULL AUTO_INCREMENT,
  `ToolCode` varchar(100) DEFAULT NULL,
  `CompanyCode` varchar(100) DEFAULT NULL,
  `Model` varchar(100) DEFAULT NULL,
  `Barcode` varchar(100) DEFAULT NULL,
  `Description` varchar(100) DEFAULT NULL,
  `CreatedDate` timestamp NULL DEFAULT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `IsActive` tinyint(1) NOT NULL,
  PRIMARY KEY (`ToolID`),
  UNIQUE KEY `ToolCode` (`ToolCode`)
) ENGINE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.47:3306/tqteamne_iTools/Tools';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `federated_toolsmachine`
--

DROP TABLE IF EXISTS `federated_toolsmachine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `federated_toolsmachine` (
  `ToolsMachineID` int(10) NOT NULL AUTO_INCREMENT,
  `ToolCode` varchar(100) NOT NULL,
  `MachineCode` varchar(100) DEFAULT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `IsActive` tinyint(1) NOT NULL,
  PRIMARY KEY (`ToolsMachineID`)
) ENGINE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.47:3306/tqteamne_iTools/ToolsMachine';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `federated_toolsmachinetray`
--

DROP TABLE IF EXISTS `federated_toolsmachinetray`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `federated_toolsmachinetray` (
  `ToolsMachineTrayID` int(10) NOT NULL AUTO_INCREMENT,
  `MachineCode` varchar(100) NOT NULL,
  `ToolCode` varchar(100) NOT NULL,
  `TrayIndex` varchar(100) NOT NULL,
  `Quantity` int(10) DEFAULT NULL,
  `CreatedDate` timestamp NULL DEFAULT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `IsActive` tinyint(1) NOT NULL,
  PRIMARY KEY (`ToolsMachineTrayID`)
) ENGINE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.47:3306/tqteamne_iTools/ToolsMachineTray';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `federated_workingtransaction`
--

DROP TABLE IF EXISTS `federated_workingtransaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `federated_workingtransaction` (
  `WorkingTransactionID` int(20) NOT NULL AUTO_INCREMENT,
  `TransactionDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MachineCode` varchar(100) NOT NULL,
  `CompanyCode` varchar(100) NOT NULL,
  `AssessorID` varchar(100) NOT NULL,
  `WOCode` varchar(100) NOT NULL,
  `OPCode` varchar(100) NOT NULL,
  `ToolCode` varchar(100) NOT NULL,
  `TrayIndex` varchar(100) DEFAULT NULL,
  `Quantity` int(10) DEFAULT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `TransactionStatus` varchar(255) DEFAULT NULL,
  `RespondMessage` varchar(255) DEFAULT NULL,
  `TransactionType` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`WorkingTransactionID`),
  UNIQUE KEY `workingtransaction_unique` (`TransactionDate`,`MachineCode`,`CompanyCode`,`AssessorID`,`TransactionType`),
  KEY `TransactionDate` (`TransactionDate`),
  KEY `AssessorID` (`AssessorID`),
  KEY `ToolCode` (`ToolCode`),
  KEY `MachineCode` (`MachineCode`),
  KEY `CompanyCode` (`CompanyCode`)
) ENGINE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.47:3306/tqteamne_iTools/WorkingTransaction';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `machine`
--

DROP TABLE IF EXISTS `machine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `machine` (
  `MachineID` int(10) NOT NULL AUTO_INCREMENT,
  `MachineName` varchar(100) DEFAULT NULL,
  `MachineCode` varchar(100) NOT NULL,
  `Model` varchar(100) DEFAULT NULL,
  `Location` varchar(100) DEFAULT NULL,
  `Description` varchar(100) DEFAULT NULL,
  `CreatedDate` timestamp NULL DEFAULT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `IsActive` tinyint(1) NOT NULL,
  PRIMARY KEY (`MachineID`),
  UNIQUE KEY `MachineCode` (`MachineCode`),
  KEY `Machine_MachineCode` (`MachineCode`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `masterlog`
--

DROP TABLE IF EXISTS `masterlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `masterlog` (
  `LogID` int(20) NOT NULL AUTO_INCREMENT,
  `LogDate` timestamp NULL DEFAULT NULL,
  `AssessorName` varchar(255) DEFAULT NULL,
  `TblName` varchar(100) DEFAULT NULL,
  `RecordID` int(10) DEFAULT NULL,
  `ColumnName` varchar(100) DEFAULT NULL,
  `Action` varchar(100) DEFAULT NULL,
  `OldValue` text,
  `NewValue` text,
  `CompanyCode` varchar(100) DEFAULT NULL,
  `MachineCode` varchar(100) DEFAULT NULL,
  `Notes` text,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`LogID`),
  KEY `RecordID` (`RecordID`),
  KEY `AssessorName` (`AssessorName`),
  KEY `LogDate` (`LogDate`)
) ENGINE=InnoDB AUTO_INCREMENT=195 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `masterlog`
--

--
-- Table structure for table `pendingaction`
--

DROP TABLE IF EXISTS `pendingaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `pendingaction` (
  `PendingActionID` int(20) NOT NULL AUTO_INCREMENT,
  `PendingActionDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PendingActionName` varchar(255) DEFAULT NULL,
  `ActionContent` text,
  `Status` varchar(100) DEFAULT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`PendingActionID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pendingaction`
--

LOCK TABLES `pendingaction` WRITE;
/*!40000 ALTER TABLE `pendingaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `pendingaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roleassessor`
--

DROP TABLE IF EXISTS `roleassessor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `roleassessor` (
  `RoleAssessorID` int(10) NOT NULL AUTO_INCREMENT,
  `RoleID` int(10) DEFAULT NULL,
  `AssessorID` int(10) DEFAULT NULL,
  `CreatedDate` timestamp NULL DEFAULT NULL,
  `IsActive` tinyint(1) NOT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`RoleAssessorID`),
  KEY `CreatedDate` (`CreatedDate`),
  KEY `RoleID` (`RoleID`),
  KEY `AssessorID` (`AssessorID`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `roles` (
  `RoleID` int(10) NOT NULL AUTO_INCREMENT,
  `RoleName` varchar(100) DEFAULT NULL,
  `RoleType` int(10) DEFAULT NULL,
  `IsRole` int(10) DEFAULT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`RoleID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Admin',NULL,1,'2018-10-04 10:27:09'),(2,'SubAdmin',NULL,1,'2018-10-04 10:27:09'),(3,'Accounting',NULL,1,'2018-10-04 10:27:09'),(4,'Emp',NULL,1,'2018-10-04 10:27:09'),(5,'Other',NULL,1,'2018-10-04 10:27:09'),(6,'PutIns',NULL,0,'2018-10-04 10:27:09'),(7,'TakeOver',NULL,0,'2018-10-04 10:27:09'),(8,'EditTransaction',NULL,0,'2018-10-04 10:27:09');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `synchistory`
--

DROP TABLE IF EXISTS `synchistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `synchistory` (
  `SyncHistoryID` int(20) NOT NULL AUTO_INCREMENT,
  `SyncDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Statistic` text,
  `Status` varchar(255) DEFAULT NULL,
  `SynType` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`SyncHistoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=13774 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tools`
--

DROP TABLE IF EXISTS `tools`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tools` (
  `ToolID` int(10) NOT NULL AUTO_INCREMENT,
  `ToolCode` varchar(100) DEFAULT NULL,
  `CompanyCode` varchar(100) DEFAULT NULL,
  `Model` varchar(100) DEFAULT NULL,
  `Barcode` varchar(100) DEFAULT NULL,
  `Description` varchar(100) DEFAULT NULL,
  `CreatedDate` timestamp NULL DEFAULT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `IsActive` tinyint(1) NOT NULL,
  PRIMARY KEY (`ToolID`),
  UNIQUE KEY `Tools_Unique` (`ToolCode`,`CompanyCode`)
) ENGINE=InnoDB AUTO_INCREMENT=502 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `toolsmachine`
--

DROP TABLE IF EXISTS `toolsmachine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `toolsmachine` (
  `ToolsMachineID` int(10) NOT NULL AUTO_INCREMENT,
  `ToolCode` varchar(100) NOT NULL,
  `MachineCode` varchar(100) DEFAULT NULL,
  `CreatedDate` timestamp NULL DEFAULT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `IsActive` tinyint(1) NOT NULL,
  PRIMARY KEY (`ToolsMachineID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `toolsmachine`
--

LOCK TABLES `toolsmachine` WRITE;
/*!40000 ALTER TABLE `toolsmachine` DISABLE KEYS */;
/*!40000 ALTER TABLE `toolsmachine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `toolsmachinetray`
--

DROP TABLE IF EXISTS `toolsmachinetray`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `toolsmachinetray` (
  `ToolsMachineTrayID` int(10) NOT NULL AUTO_INCREMENT,
  `MachineCode` varchar(100) NOT NULL,
  `ToolCode` varchar(100) NOT NULL,
  `TrayIndex` varchar(100) NOT NULL,
  `Quantity` int(10) DEFAULT NULL,
  `CreatedDate` timestamp NULL DEFAULT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `IsActive` tinyint(1) NOT NULL,
  PRIMARY KEY (`ToolsMachineTrayID`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `workingtransaction`
--

DROP TABLE IF EXISTS `workingtransaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `workingtransaction` (
  `WorkingTransactionID` int(20) NOT NULL AUTO_INCREMENT,
  `TransactionDate` timestamp NULL DEFAULT NULL,
  `MachineCode` varchar(100) NOT NULL,
  `CompanyCode` varchar(100) NOT NULL,
  `AssessorID` varchar(100) NOT NULL,
  `WOCode` varchar(100) NOT NULL,
  `OPCode` varchar(100) NOT NULL,
  `ToolCode` varchar(100) NOT NULL,
  `TrayIndex` varchar(100) DEFAULT NULL,
  `Quantity` int(10) DEFAULT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `TransactionStatus` varchar(255) DEFAULT NULL,
  `RespondMessage` varchar(255) DEFAULT NULL,
  `TransactionType` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`WorkingTransactionID`),
  UNIQUE KEY `workingtransaction_unique` (`TransactionDate`,`MachineCode`,`CompanyCode`,`AssessorID`,`TransactionType`),
  KEY `TransactionDate` (`TransactionDate`),
  KEY `AssessorID` (`AssessorID`),
  KEY `ToolCode` (`ToolCode`),
  KEY `MachineCode` (`MachineCode`),
  KEY `CompanyCode` (`CompanyCode`)
) ENGINE=InnoDB AUTO_INCREMENT=1420 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Dumping routines for database 'itools_v1p0'
--
/*!50003 DROP PROCEDURE IF EXISTS `SyncHostToLocal_Assessor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `SyncHostToLocal_Assessor`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	-- DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	-- DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	DECLARE count_rows INT;
    START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	select count(*) INTO count_rows from federated_assessor;
	
	if (count_rows > 0) then
	
		delete from assessor where AssessorID > 0;
		
		INSERT INTO assessor
			SELECT a.* FROM federated_assessor a
			LEFT OUTER JOIN assessor b ON b.Username = a.Username
			WHERE b.Username IS NULL;
			
		set @insertAssessor = (SELECT ROW_COUNT());
		set @finalResult = concat(": insertAssessor: ", @insertAssessor );
		
		insert into synchistory(SyncDate, Statistic, Status, SynType)
			VALUES 
		(now(), @finalResult, "SUCCESS", "SyncHostToLocal_Assessor");
		
		
		set  returnResult = @finalResult;
	else 
        set returnResult = "";
    end if;
	
	commit;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SyncHostToLocal_Company` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `SyncHostToLocal_Company`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	-- DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	-- DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	DECLARE count_rows INT;
    START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	
	
	select count(*) INTO count_rows from federated_Company;
	
	if (count_rows > 0) then
	
		delete from company where CompanyID > 0;
		
		INSERT INTO Company
		SELECT a.* FROM federated_Company a
		LEFT OUTER JOIN Company b ON b.CompanyID = a.CompanyID
		WHERE b.CompanyID IS NULL;
		
		set @insertCompany = (SELECT ROW_COUNT());
		set @finalResult = concat("insertCompany: ", @insertCompany );
		
		insert into synchistory(SyncDate, Statistic, Status, SynType)
			VALUES 
		(now(), @finalResult, "SUCCESS", "SyncHostToLocal_Company");
		
		set  returnResult = @finalResult;
	else 
        set returnResult = "";
    end if;
	
	commit;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SyncHostToLocal_CompanyMachine` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `SyncHostToLocal_CompanyMachine`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	-- DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	-- DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	DECLARE count_rows INT;
    START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	
	
	select count(*) INTO count_rows from federated_CompanyMachine;
	
	if (count_rows > 0) then
		delete from CompanyMachine where CompanyMachineID > 0;
		
		select * from CompanyMachine;
			INSERT INTO CompanyMachine
			SELECT a.* FROM federated_CompanyMachine a
			LEFT OUTER JOIN CompanyMachine b ON b.CompanyMachineID = a.CompanyMachineID
			WHERE b.CompanyMachineID IS NULL;
			
		set @insertCompanyMachine = (SELECT ROW_COUNT());
		set @finalResult = concat("insertCompanyMachine: ", @insertCompanyMachine );
		
		
		insert into synchistory(SyncDate, Statistic, Status, SynType)
			VALUES 
		(now(), @finalResult, "SUCCESS", "SyncHostToLocal_CompanyMachine");
		
		
		set  returnResult = @finalResult;
	
	else 
        set returnResult = "";
    end if;
	
	commit;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SyncHostToLocal_Machine` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `SyncHostToLocal_Machine`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	-- DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	-- DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	DECLARE count_rows INT;
    START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	
	
	select count(*) INTO count_rows from federated_Machine;
	
	if (count_rows > 0) then
	
		delete from Machine where MachineID > 0;
		
		INSERT INTO Machine
		SELECT a.* FROM federated_Machine a
		LEFT OUTER JOIN Machine b ON b.MachineCode = a.MachineCode
		WHERE b.MachineID IS NULL;
		
		set @insertMachine = (SELECT ROW_COUNT());
		set @finalResult = concat("insertMachine: ", @insertMachine );
		
		insert into synchistory(SyncDate, Statistic, Status, SynType)
			VALUES 
		(now(), @finalResult, "SUCCESS", "SyncHostToLocal_Machine");
		
		
		set  returnResult = @finalResult;
	else 
        set returnResult = "";
    end if;
	
	commit;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SyncHostToLocal_RoleAssessor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `SyncHostToLocal_RoleAssessor`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	-- DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	-- DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	DECLARE count_rows INT;
    START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	
	
	select count(*) INTO count_rows from federated_RoleAssessor;
	
	if (count_rows > 0) then
		delete from RoleAssessor where RoleAssessorID > 0;
		
		INSERT INTO RoleAssessor
			SELECT a.* FROM federated_RoleAssessor a
			LEFT OUTER JOIN RoleAssessor b ON b.RoleAssessorID = a.RoleAssessorID
			WHERE b.RoleAssessorID IS NULL;
			
		set @insertRoleAssessor = (SELECT ROW_COUNT());
		set @finalResult = concat(": insertRoleAssessor: ", @insertRoleAssessor );
		
		insert into synchistory(SyncDate, Statistic, Status, SynType)
			VALUES 
		(now(), @finalResult, "SUCCESS", "SyncHostToLocal_RoleAssessor");
		
		
		set  returnResult = @finalResult;
	else 
        set returnResult = "";
    end if;
	
	commit;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SyncHostToLocal_Tools` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `SyncHostToLocal_Tools`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	-- DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	-- DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	DECLARE count_rows INT;
    START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	select count(*) INTO count_rows from federated_Tools;
	
	if (count_rows > 0) then
		delete from Tools where ToolID > 0;
		
		INSERT INTO Tools
		SELECT a.* FROM federated_Tools a
		LEFT OUTER JOIN Tools b ON b.ToolID = a.ToolID
		WHERE b.ToolID IS NULL;
		
		set @insertTools = (SELECT ROW_COUNT());
		set @finalResult = concat("insertTools: ", @insertTools);
		
		insert into synchistory(SyncDate, Statistic, Status, SynType)
			VALUES 
		(now(), @finalResult, "SUCCESS", "SyncHostToLocal_Tools");
		
		
		set  returnResult = @finalResult;
	else 
        set returnResult = "";
    end if;
	
	commit;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SyncLocalToHost_ToolsMachineTray` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `SyncLocalToHost_ToolsMachineTray`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	-- DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	-- DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	DECLARE count_rows INT;
    START TRANSACTION;
		
    SET SQL_SAFE_UPDATES = 0;
	
	
	select count(*) INTO count_rows from federated_ToolsMachineTray;
	
	if (count_rows > 0) then
		delete from federated_ToolsMachineTray where federated_ToolsMachineTray.MachineCode = MachineCode;
		
		INSERT INTO federated_ToolsMachineTray (MachineCode, ToolCode, TrayIndex, Quantity, CreatedDate, UpdatedDate, isActive)
		SELECT a.MachineCode,a.ToolCode, a.TrayIndex, a.Quantity, a.CreatedDate, a.UpdatedDate, a.isActive FROM ToolsMachineTray a
		
		WHERE a.MachineCode = MachineCode;
		
		set @insertToolsMachineTray = (SELECT ROW_COUNT());
		set @finalResult = concat("insertToolsMachineTray: ", @insertToolsMachineTray );
		
		
		insert into synchistory(SyncDate, Statistic, Status, SynType)
			VALUES 
		(now(), @finalResult, "SUCCESS", "SyncLocalToHost_ToolsMachineTray");
		
		
		set  returnResult = @finalResult;
		
	else 
        set returnResult = "";
    end if;
	
	commit;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SyncLocalToHost_WorkingTransaction` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `SyncLocalToHost_WorkingTransaction`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	-- DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	-- DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	-- delete from federated_WorkingTransaction where federated_WorkingTransaction.MachineCode = MachineCode;
	
	
	INSERT INTO federated_WorkingTransaction (TransactionDate, MachineCode, CompanyCode, AssessorID, WOCode, OPCode, ToolCode, TrayIndex, Quantity, TransactionStatus, UpdatedDate, RespondMessage, TransactionType)
	SELECT a.TransactionDate, a.MachineCode, a.CompanyCode, a.AssessorID, a.WOCode, a.OPCode, a.ToolCode, a.TrayIndex, a.Quantity, a.TransactionStatus, a.UpdatedDate, a.RespondMessage, a.TransactionType 
    FROM WorkingTransaction a
    where a.TransactionDate > CASE WHEN (select TransactionDate 
								from federated_WorkingTransaction 
                                where federated_WorkingTransaction.CompanyCode = CompanyCode 
										and federated_WorkingTransaction.MachineCode = MachineCode
								order by TransactionDate desc limit 1) IS NULL
            THEN "1970-01-01 01:01:01"
            ELSE (select TransactionDate 
								from federated_WorkingTransaction 
                                where federated_WorkingTransaction.CompanyCode = CompanyCode 
										and federated_WorkingTransaction.MachineCode = MachineCode
								order by TransactionDate desc limit 1)
            END
    
    
    ;
	
	set @insertWorkingTransaction = (SELECT ROW_COUNT());
    set @finalResult = concat("insertWorkingTransaction: ", @insertWorkingTransaction );
	
	/*
    #update all record from Local to Host
	update federated_WorkingTransaction
	left join WorkingTransaction on WorkingTransaction.WorkingTransactionID = federated_WorkingTransaction.WorkingTransactionID
		set federated_WorkingTransaction.TransactionDate = WorkingTransaction.TransactionDate,
			federated_WorkingTransaction.MachineCode = WorkingTransaction.MachineCode,
			federated_WorkingTransaction.CompanyCode = WorkingTransaction.CompanyCode,
			federated_WorkingTransaction.AssessorID = WorkingTransaction.AssessorID,
			federated_WorkingTransaction.WOCode = WorkingTransaction.WOCode,
            federated_WorkingTransaction.OPCode = WorkingTransaction.OPCode,
            federated_WorkingTransaction.ToolCode = WorkingTransaction.ToolCode,
            federated_WorkingTransaction.TrayIndex = WorkingTransaction.TrayIndex,
            federated_WorkingTransaction.Quantity = WorkingTransaction.Quantity,
            federated_WorkingTransaction.TransactionStatus = WorkingTransaction.TransactionStatus,
            federated_WorkingTransaction.UpdatedDate = WorkingTransaction.UpdatedDate,
            federated_WorkingTransaction.RespondMessage = WorkingTransaction.RespondMessage,
            federated_WorkingTransaction.TransactionType = WorkingTransaction.TransactionType
		where federated_WorkingTransaction.WorkingTransactionID in (select WorkingTransactionID from WorkingTransaction)
        and WorkingTransaction.UpdatedDate > 
		(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
			from synchistory fs
			where fs.Status = 'SUCCESS' and fs.SynType = 'SyncLocalToHost_WorkingTransaction'
			order by fs.SyncDate desc LIMIT 1)
	and WorkingTransaction.UpdatedDate > (Case when federated_WorkingTransaction.UpdatedDate is null then '1900-01-01 00:00:00' else federated_WorkingTransaction.UpdatedDate END);  
   
   set @updateWorkingTransaction = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateWorkingTransaction: ", @updateWorkingTransaction );
	*/
	insert into synchistory(SyncDate, Statistic, Status, SynType)
		VALUES 
	(now(), @finalResult, "SUCCESS", "SyncLocalToHost_WorkingTransaction");
	commit;
	
	set  returnResult = @finalResult;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-29 18:43:12
