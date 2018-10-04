-- MySQL dump 10.13  Distrib 5.7.12, for Win32 (AMD64)
--
-- Host: localhost    Database: itools_v1p0
-- ------------------------------------------------------
-- Server version	5.7.22-log

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
-- Table structure for table `assessor`
--

DROP TABLE IF EXISTS `assessor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `IsLocked` int(10) NOT NULL DEFAULT '0',
  `IsActive` int(10) NOT NULL DEFAULT '1',
  `LastPassword` varchar(255) DEFAULT NULL,
  `IsFirstTimeLogin` tinyint(1) DEFAULT '0',
  `FailTimes` int(10) NOT NULL DEFAULT '0',
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`AssessorID`),
  KEY `UserName` (`UserName`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assessor`
--

LOCK TABLES `assessor` WRITE;
/*!40000 ALTER TABLE `assessor` DISABLE KEYS */;
/*!40000 ALTER TABLE `assessor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `companymachine`
--

DROP TABLE IF EXISTS `companymachine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `companymachine` (
  `CompanyMachineID` int(10) NOT NULL AUTO_INCREMENT,
  `MachineCode` varchar(100) DEFAULT NULL,
  `CompanyCode` varchar(100) DEFAULT NULL,
  `CreatedDate` timestamp NULL DEFAULT NULL,
  `IsActive` tinyint(1) NOT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CompanyMachineID`),
  KEY `MachineCode` (`MachineCode`),
  KEY `CompanyCode` (`CompanyCode`),
  CONSTRAINT `companymachine_ibfk_1` FOREIGN KEY (`MachineCode`) REFERENCES `machine` (`MachineCode`),
  CONSTRAINT `companymachine_ibfk_2` FOREIGN KEY (`CompanyCode`) REFERENCES `company` (`CompanyCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `companymachine`
--

LOCK TABLES `companymachine` WRITE;
/*!40000 ALTER TABLE `companymachine` DISABLE KEYS */;
/*!40000 ALTER TABLE `companymachine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databaseversion`
--

DROP TABLE IF EXISTS `databaseversion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `federated_tools` (
  `ToolID` int(10) NOT NULL AUTO_INCREMENT,
  `ToolCode` varchar(100) DEFAULT NULL,
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine`
--

LOCK TABLES `machine` WRITE;
/*!40000 ALTER TABLE `machine` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `masterlog`
--

DROP TABLE IF EXISTS `masterlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=254 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `masterlog`
--

LOCK TABLES `masterlog` WRITE;
/*!40000 ALTER TABLE `masterlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `masterlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pendingaction`
--

DROP TABLE IF EXISTS `pendingaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
  KEY `AssessorID` (`AssessorID`),
  CONSTRAINT `roleassessor_ibfk_1` FOREIGN KEY (`RoleID`) REFERENCES `roles` (`RoleID`),
  CONSTRAINT `roleassessor_ibfk_2` FOREIGN KEY (`AssessorID`) REFERENCES `assessor` (`AssessorID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roleassessor`
--

LOCK TABLES `roleassessor` WRITE;
/*!40000 ALTER TABLE `roleassessor` DISABLE KEYS */;
/*!40000 ALTER TABLE `roleassessor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `synchistory` (
  `SyncHistoryID` int(20) NOT NULL AUTO_INCREMENT,
  `SyncDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Statistic` text,
  `Status` varchar(255) DEFAULT NULL,
  `SynType` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`SyncHistoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=6420 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `synchistory`
--

LOCK TABLES `synchistory` WRITE;
/*!40000 ALTER TABLE `synchistory` DISABLE KEYS */;
INSERT INTO `synchistory` VALUES (1,'2018-08-11 06:02:36','first sync record','SUCCESS','HostToLocal'),(2,'2018-08-11 06:02:36','first sync record','SUCCESS','LocalToHost');
/*!40000 ALTER TABLE `synchistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tools`
--

DROP TABLE IF EXISTS `tools`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tools` (
  `ToolID` int(10) NOT NULL AUTO_INCREMENT,
  `ToolCode` varchar(100) DEFAULT NULL,
  `Model` varchar(100) DEFAULT NULL,
  `Barcode` varchar(100) DEFAULT NULL,
  `Description` varchar(100) DEFAULT NULL,
  `CreatedDate` timestamp NULL DEFAULT NULL,
  `UpdatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `IsActive` tinyint(1) NOT NULL,
  PRIMARY KEY (`ToolID`),
  UNIQUE KEY `ToolCode` (`ToolCode`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tools`
--

LOCK TABLES `tools` WRITE;
/*!40000 ALTER TABLE `tools` DISABLE KEYS */;
/*!40000 ALTER TABLE `tools` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `toolsmachine`
--

DROP TABLE IF EXISTS `toolsmachine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=341 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `toolsmachinetray`
--

LOCK TABLES `toolsmachinetray` WRITE;
/*!40000 ALTER TABLE `toolsmachinetray` DISABLE KEYS */;
/*!40000 ALTER TABLE `toolsmachinetray` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workingtransaction`
--

DROP TABLE IF EXISTS `workingtransaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  KEY `TransactionDate` (`TransactionDate`),
  KEY `AssessorID` (`AssessorID`),
  KEY `ToolCode` (`ToolCode`),
  KEY `MachineCode` (`MachineCode`),
  KEY `CompanyCode` (`CompanyCode`),
  CONSTRAINT `workingtransaction_ibfk_1` FOREIGN KEY (`MachineCode`) REFERENCES `machine` (`MachineCode`),
  CONSTRAINT `workingtransaction_ibfk_2` FOREIGN KEY (`CompanyCode`) REFERENCES `company` (`CompanyCode`),
  CONSTRAINT `workingtransaction_ibfk_3` FOREIGN KEY (`ToolCode`) REFERENCES `tools` (`ToolCode`)
) ENGINE=InnoDB AUTO_INCREMENT=1433 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workingtransaction`
--

LOCK TABLES `workingtransaction` WRITE;
/*!40000 ALTER TABLE `workingtransaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `workingtransaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'itools_v1p0'
--

--
-- Dumping routines for database 'itools_v1p0'
--
/*!50003 DROP PROCEDURE IF EXISTS `SyncHostToLocal` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `SyncHostToLocal`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	-- DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	-- DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
    
    set @finalResult = "========Start=======\n";
    
    #insert missing record from Host to local
	INSERT INTO Company
	SELECT a.* FROM federated_Company a
	LEFT OUTER JOIN Company b ON b.CompanyID = a.CompanyID
	WHERE b.CompanyID IS NULL;
	
	set @insertCompany = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "insertCompany: ", @insertCompany );
	
    #update all record from Host to local
	update Company lC
	left join federated_Company fC on lC.CompanyID = fC.CompanyID
		set lC.CompanyCode = fC.CompanyCode,
			lC.CompanyName = fC.CompanyName,
			lC.CompanyType = fC.CompanyType,
            lC.Address = fC.Address,
            lC.Location = fC.Location,
            lC.UpdatedDate = sysdate()
		where lC.CompanyID in (select CompanyID from federated_Company)
        and fC.UpdatedDate > 
		(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
			from synchistory fs
			where fs.Status = 'SUCCESS' and fs.SynType = 'HostToLocal'
			order by fs.SyncDate desc LIMIT 1)
	and fC.UpdatedDate > (Case when lC.UpdatedDate is null then '1900-01-01 00:00:00' else lC.UpdatedDate END);  
	
	set @updateCompany = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateCompany: ", @updateCompany );
	
	INSERT INTO assessor
		SELECT a.* FROM federated_assessor a
		LEFT OUTER JOIN assessor b ON b.AssessorID = a.AssessorID
		WHERE b.AssessorID IS NULL;
		
	set @insertAssessor = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "insertAssessor: ", @insertAssessor );
    
	update assessor lA
	left join federated_assessor fa on lA.AssessorID = fa.AssessorID
		set lA.UserName = fa.UserName,
			lA.FingerID = fa.FingerID,
			lA.Password = fa.Password,
			lA.FirstName = fa.FirstName,
			lA.LastName = fa.LastName,
			lA.EmailAddress = fa.EmailAddress,
			lA.Address = fa.Address,
			lA.Phone = fa.Phone,
			lA.CompanyCode = fa.CompanyCode,
			lA.IsLocked = fa.IsLocked,
			lA.IsActive = fa.IsActive,
			lA.LastPassword = fa.LastPassword,
			lA.IsFirstTimeLogin = fa.IsFirstTimeLogin,
			lA.UpdatedDate = sysdate()
		where lA.AssessorID in (select AssessorID from federated_assessor) 
		and fa.UpdatedDate > 
			(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
				from synchistory fs
				where fs.Status = 'SUCCESS' and fs.SynType = 'HostToLocal'
				order by fs.SyncDate desc LIMIT 1)
		and fa.UpdatedDate > (Case when lA.UpdatedDate is null then '1900-01-01 00:00:00' else lA.UpdatedDate END);  
	
	set @updateAssessor = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateAssessor: ", @updateAssessor );
		
	

	#insert missing record from Host to local
    INSERT INTO Machine
	SELECT a.* FROM federated_Machine a
	LEFT OUTER JOIN Machine b ON b.MachineID = a.MachineID
	WHERE b.MachineID IS NULL;
	
	set @insertMachine = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "insertMachine: ", @insertMachine );
	
    #update all record from Host to local
	update Machine lM
	left join federated_Machine fM on lM.MachineID = fM.MachineID
		set lM.MachineName = fM.MachineName,
			lM.MachineCode = fM.MachineCode,
            lM.Model = fM.Model,
            lM.Location = fM.Location,
            lM.Description = fM.Description,
			lM.CreatedDate = fM.CreatedDate,
            lM.UpdatedDate = sysdate(),
            lM.IsActive = fM.IsActive
		where lM.MachineID in (select MachineID from federated_Machine)
        and fM.UpdatedDate > 
		(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
			from synchistory fs
			where fs.Status = 'SUCCESS' and fs.SynType = 'HostToLocal'
			order by fs.SyncDate desc LIMIT 1)
	and fM.UpdatedDate > (Case when lM.UpdatedDate is null then '1900-01-01 00:00:00' else lM.UpdatedDate END);  
	
	set @updateMachine = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateMachine: ", @updateMachine );
        
	select * from CompanyMachine;
	#insert missing record from Host to local
		INSERT INTO CompanyMachine
		SELECT a.* FROM federated_CompanyMachine a
		LEFT OUTER JOIN CompanyMachine b ON b.CompanyMachineID = a.CompanyMachineID
		WHERE b.CompanyMachineID IS NULL;
		
	set @insertCompanyMachine = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "insertCompanyMachine: ", @insertCompanyMachine );
	
    #update all record from Host to local
	update CompanyMachine lCM
		left join federated_CompanyMachine fCM on lCM.CompanyMachineID = fCM.CompanyMachineID
			set lCM.CompanyCode = fCM.CompanyCode,
				lCM.MachineCode = fCM.MachineCode,
				lCM.CreatedDate = fCM.CreatedDate,
				lCM.IsActive = fCM.IsActive,
				lCM.UpdatedDate = sysdate()
			where lCM.CompanyMachineID in (select CompanyMachineID from federated_CompanyMachine)
			and fCM.UpdatedDate > 
			(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
				from synchistory fs
				where fs.Status = 'SUCCESS' and fs.SynType = 'HostToLocal'
				order by fs.SyncDate desc LIMIT 1)
		and fCM.UpdatedDate > (Case when lCM.UpdatedDate is null then '1900-01-01 00:00:00' else lCM.UpdatedDate END);  
	
	set @updateCompanyMachine = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateCompanyMachine: ", @updateCompanyMachine );

	#insert missing record from Host to local
    INSERT INTO RoleAssessor
		SELECT a.* FROM federated_RoleAssessor a
		LEFT OUTER JOIN RoleAssessor b ON b.RoleAssessorID = a.RoleAssessorID
		WHERE b.RoleAssessorID IS NULL;
		
	set @insertRoleAssessor = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "insertRoleAssessor: ", @insertRoleAssessor );
	
    #update all record from Host to local
	update RoleAssessor lRA
	left join federated_RoleAssessor fRA on lRA.RoleAssessorID = fRA.RoleAssessorID
		set lRA.RoleID = fRA.RoleID,
			lRA.AssessorID = fRA.AssessorID,
			lRA.CreatedDate = fRA.CreatedDate,
            lRA.IsActive = fRA.IsActive,
            lRA.UpdatedDate = sysdate()
		where lRA.RoleAssessorID in (select RoleAssessorID from federated_RoleAssessor)
		and fRA.UpdatedDate > 
		(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
			from synchistory fs
			where fs.Status = 'SUCCESS' and fs.SynType = 'HostToLocal'
			order by fs.SyncDate desc LIMIT 1)
	and fRA.UpdatedDate > (Case when lRA.UpdatedDate is null then '1900-01-01 00:00:00' else lRA.UpdatedDate END);  
    
	set @updateRoleAssessor = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateRoleAssessor: ", @updateRoleAssessor );
	
    #insert missing record from Host to local
    INSERT INTO Roles
	SELECT a.* FROM federated_Roles a
	LEFT OUTER JOIN Roles b ON b.RoleID = a.RoleID
	WHERE b.RoleID IS NULL;
	
	set @insertRole = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "insertRole: ", @insertRole);
	
    #update all record from Host to local
	update Roles lR
	left join federated_Roles fR on lR.RoleID = fR.RoleID
		set lR.RoleName = fR.RoleName,
			lR.RoleType = fR.RoleType,
            lR.IsRole = fR.IsRole,
            lR.UpdatedDate = sysdate()
		where lR.RoleID in (select RoleID from federated_Roles)
        and fR.UpdatedDate > 
		(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
			from synchistory fs
			where fs.Status = 'SUCCESS' and fs.SynType = 'HostToLocal'
			order by fs.SyncDate desc LIMIT 1)
	and fR.UpdatedDate > (Case when lR.UpdatedDate is null then '1900-01-01 00:00:00' else lR.UpdatedDate END);  
    
	set @updateRole = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateRole: ", @updateRole );
    
    #insert missing record from Host to local
    INSERT INTO Tools
	SELECT a.* FROM federated_Tools a
	LEFT OUTER JOIN Tools b ON b.ToolID = a.ToolID
	WHERE b.ToolID IS NULL;
	
	set @insertTools = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "insertTools: ", @insertTools);
	
    #update all record from Host to local
	update Tools lT
	left join federated_Tools fT on lT.ToolID = fT.ToolID
		set lT.ToolCode = fT.ToolCode,
			lT.Model = fT.Model,
            lT.Barcode = fT.Barcode,
            lT.Description = fT.Description,
			lT.CreatedDate = fT.CreatedDate,
            lT.UpdatedDate = sysdate(),
            lT.IsActive = fT.IsActive
		where lT.ToolID in (select ToolID from federated_Tools)
        and fT.UpdatedDate > 
		(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
			from synchistory fs
			where fs.Status = 'SUCCESS' and fs.SynType = 'HostToLocal'
			order by fs.SyncDate desc LIMIT 1)
	and fT.UpdatedDate > (Case when lT.UpdatedDate is null then '1900-01-01 00:00:00' else lT.UpdatedDate END);  
    
	set @updateTools = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateTools: ", @updateTools );
    /*
	
	
	#insert missing record from Host to local
    INSERT INTO ToolsMachineTray
	SELECT a.* FROM federated_ToolsMachineTray a
	LEFT OUTER JOIN ToolsMachineTray b ON b.ToolsMachineTrayID = a.ToolsMachineTrayID
	WHERE b.ToolsMachineTrayID IS NULL;
	
	set @insertToolsMachineTray = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "insertToolsMachineTray: ", @insertToolsMachineTray);
	
		
	
	
    #update all record from Host to local
	update ToolsMachineTray lTM
	left join federated_ToolsMachineTray fTM on lTM.ToolsMachineTrayID = fTM.ToolsMachineTrayID
		set lTM.ToolsMachineID = fTM.ToolsMachineID,
            lTM.TrayIndex = fTM.TrayIndex,
			lTM.Quantity = fTM.Quantity,
            lTM.UpdatedDate = sysdate()
		where lTM.ToolsMachineTrayID in (select ToolsMachineTrayID from federated_ToolsMachineTray)
        and fTM.UpdatedDate > 
		(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
			from synchistory fs
			where fs.Status = 'SUCCESS' and fs.SynType = 'HostToLocal'
			order by fs.SyncDate desc LIMIT 1)
	and fTM.UpdatedDate > (Case when lTM.UpdatedDate is null then '1900-01-01 00:00:00' else lTM.UpdatedDate END);  
	
	set @updateToolsMachineTray = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateToolsMachineTray: ", @updateToolsMachineTray );
	*/
	
	
	insert into synchistory(SyncDate, Statistic, Status, SynType)
		VALUES 
	(now(), concat("run store sync changed data from host to local: ", @finalResult), "SUCCESS", "FromHost");
       
	insert into masterlog(AssessorName,Action, Notes, CompanyCode, MachineCode) values ("System", "SyncFromHost", @finalResult, CompanyCode, MachineCode);
	commit;
	
	set  returnResult = @finalResult;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
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
	START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	INSERT INTO assessor
		SELECT a.* FROM federated_assessor a
		LEFT OUTER JOIN assessor b ON b.AssessorID = a.AssessorID
		WHERE b.AssessorID IS NULL;
		
	set @insertAssessor = (SELECT ROW_COUNT());
    set @finalResult = concat("insertAssessor: ", @insertAssessor );
    
	update assessor lA
	left join federated_assessor fa on lA.AssessorID = fa.AssessorID
		set lA.UserName = fa.UserName,
			lA.FingerID = fa.FingerID,
			lA.Password = fa.Password,
			lA.FirstName = fa.FirstName,
			lA.LastName = fa.LastName,
			lA.EmailAddress = fa.EmailAddress,
			lA.Address = fa.Address,
			lA.Phone = fa.Phone,
			lA.CompanyCode = fa.CompanyCode,
			lA.IsLocked = fa.IsLocked,
			lA.IsActive = fa.IsActive,
			lA.LastPassword = fa.LastPassword,
			lA.IsFirstTimeLogin = fa.IsFirstTimeLogin,
			lA.UpdatedDate = sysdate()
		where lA.AssessorID in (select AssessorID from federated_assessor) 
		and fa.UpdatedDate > 
			(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
				from synchistory fs
				where fs.Status = 'SUCCESS' and fs.SynType = 'SyncHostToLocal_Assessor'
				order by fs.SyncDate desc LIMIT 1)
		and fa.UpdatedDate > (Case when lA.UpdatedDate is null then '1900-01-01 00:00:00' else lA.UpdatedDate END);  
	
	set @updateAssessor = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateAssessor: ", @updateAssessor );
	
	insert into synchistory(SyncDate, Statistic, Status, SynType)
		VALUES 
	(now(), @finalResult, "SUCCESS", "SyncHostToLocal_Assessor");
	commit;
	
	set  returnResult = @finalResult;
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
	START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	INSERT INTO Company
	SELECT a.* FROM federated_Company a
	LEFT OUTER JOIN Company b ON b.CompanyID = a.CompanyID
	WHERE b.CompanyID IS NULL;
	
	set @insertCompany = (SELECT ROW_COUNT());
    set @finalResult = concat("insertCompany: ", @insertCompany );
	
	update Company lC
	left join federated_Company fC on lC.CompanyID = fC.CompanyID
		set lC.CompanyCode = fC.CompanyCode,
			lC.CompanyName = fC.CompanyName,
			lC.CompanyType = fC.CompanyType,
            lC.Address = fC.Address,
            lC.Location = fC.Location,
            lC.UpdatedDate = sysdate()
		where lC.CompanyID in (select CompanyID from federated_Company)
        and fC.UpdatedDate > 
		(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
			from synchistory fs
			where fs.Status = 'SUCCESS' and fs.SynType = 'SyncHostToLocal_Company'
			order by fs.SyncDate desc LIMIT 1)
	and fC.UpdatedDate > (Case when lC.UpdatedDate is null then '1900-01-01 00:00:00' else lC.UpdatedDate END);  
	
	set @updateCompany = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateCompany: ", @updateCompany );
	
	insert into synchistory(SyncDate, Statistic, Status, SynType)
		VALUES 
	(now(), @finalResult, "SUCCESS", "SyncHostToLocal_Company");
	commit;
	
	set  returnResult = @finalResult;
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
	START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	select * from CompanyMachine;
		INSERT INTO CompanyMachine
		SELECT a.* FROM federated_CompanyMachine a
		LEFT OUTER JOIN CompanyMachine b ON b.CompanyMachineID = a.CompanyMachineID
		WHERE b.CompanyMachineID IS NULL;
		
	set @insertCompanyMachine = (SELECT ROW_COUNT());
    set @finalResult = concat("insertCompanyMachine: ", @insertCompanyMachine );
	
    #update all record from Host to local
	update CompanyMachine lCM
		left join federated_CompanyMachine fCM on lCM.CompanyMachineID = fCM.CompanyMachineID
			set lCM.CompanyCode = fCM.CompanyCode,
				lCM.MachineCode = fCM.MachineCode,
				lCM.CreatedDate = fCM.CreatedDate,
				lCM.IsActive = fCM.IsActive,
				lCM.UpdatedDate = sysdate()
			where lCM.CompanyMachineID in (select CompanyMachineID from federated_CompanyMachine)
			and fCM.UpdatedDate > 
			(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
				from synchistory fs
				where fs.Status = 'SUCCESS' and fs.SynType = 'SyncHostToLocal_CompanyMachine'
				order by fs.SyncDate desc LIMIT 1)
		and fCM.UpdatedDate > (Case when lCM.UpdatedDate is null then '1900-01-01 00:00:00' else lCM.UpdatedDate END);  
	
	set @updateCompanyMachine = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateCompanyMachine: ", @updateCompanyMachine );

	
	insert into synchistory(SyncDate, Statistic, Status, SynType)
		VALUES 
	(now(), @finalResult, "SUCCESS", "SyncHostToLocal_CompanyMachine");
	commit;
	
	set  returnResult = @finalResult;
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
	START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	INSERT INTO Machine
	SELECT a.* FROM federated_Machine a
	LEFT OUTER JOIN Machine b ON b.MachineID = a.MachineID
	WHERE b.MachineID IS NULL;
	
	set @insertMachine = (SELECT ROW_COUNT());
    set @finalResult = concat("insertMachine: ", @insertMachine );
	
	
	update Machine lM
	left join federated_Machine fM on lM.MachineID = fM.MachineID
		set lM.MachineName = fM.MachineName,
			lM.MachineCode = fM.MachineCode,
            lM.Model = fM.Model,
            lM.Location = fM.Location,
            lM.Description = fM.Description,
			lM.CreatedDate = fM.CreatedDate,
            lM.UpdatedDate = sysdate(),
            lM.IsActive = fM.IsActive
		where lM.MachineID in (select MachineID from federated_Machine)
        and fM.UpdatedDate > 
		(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
			from synchistory fs
			where fs.Status = 'SUCCESS' and fs.SynType = 'SyncHostToLocal_Machine'
			order by fs.SyncDate desc LIMIT 1)
	and fM.UpdatedDate > (Case when lM.UpdatedDate is null then '1900-01-01 00:00:00' else lM.UpdatedDate END);  
	
	set @updateMachine = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateMachine: ", @updateMachine );
	
	insert into synchistory(SyncDate, Statistic, Status, SynType)
		VALUES 
	(now(), @finalResult, "SUCCESS", "SyncHostToLocal_Machine");
	commit;
	
	set  returnResult = @finalResult;
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
	START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	INSERT INTO RoleAssessor
		SELECT a.* FROM federated_RoleAssessor a
		LEFT OUTER JOIN RoleAssessor b ON b.RoleAssessorID = a.RoleAssessorID
		WHERE b.RoleAssessorID IS NULL;
		
	set @insertRoleAssessor = (SELECT ROW_COUNT());
    set @finalResult = concat("insertRoleAssessor: ", @insertRoleAssessor );
	
    #update all record from Host to local
	update RoleAssessor lRA
	left join federated_RoleAssessor fRA on lRA.RoleAssessorID = fRA.RoleAssessorID
		set lRA.RoleID = fRA.RoleID,
			lRA.AssessorID = fRA.AssessorID,
			lRA.CreatedDate = fRA.CreatedDate,
            lRA.IsActive = fRA.IsActive,
            lRA.UpdatedDate = sysdate()
		where lRA.RoleAssessorID in (select RoleAssessorID from federated_RoleAssessor)
		and fRA.UpdatedDate > 
		(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
			from synchistory fs
			where fs.Status = 'SUCCESS' and fs.SynType = 'SyncHostToLocal_RoleAssessor'
			order by fs.SyncDate desc LIMIT 1)
	and fRA.UpdatedDate > (Case when lRA.UpdatedDate is null then '1900-01-01 00:00:00' else lRA.UpdatedDate END);  
    
	set @updateRoleAssessor = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateRoleAssessor: ", @updateRoleAssessor );
	
	insert into synchistory(SyncDate, Statistic, Status, SynType)
		VALUES 
	(now(), @finalResult, "SUCCESS", "SyncHostToLocal_RoleAssessor");
	commit;
	
	set  returnResult = @finalResult;
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
	START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	INSERT INTO Tools
	SELECT a.* FROM federated_Tools a
	LEFT OUTER JOIN Tools b ON b.ToolID = a.ToolID
	WHERE b.ToolID IS NULL;
	
	set @insertTools = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "insertTools: ", @insertTools);
	
    #update all record from Host to local
	update Tools lT
	left join federated_Tools fT on lT.ToolID = fT.ToolID
		set lT.ToolCode = fT.ToolCode,
			lT.Model = fT.Model,
            lT.Barcode = fT.Barcode,
            lT.Description = fT.Description,
			lT.CreatedDate = fT.CreatedDate,
            lT.UpdatedDate = sysdate(),
            lT.IsActive = fT.IsActive
		where lT.ToolID in (select ToolID from federated_Tools)
        and fT.UpdatedDate > 
		(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
			from synchistory fs
			where fs.Status = 'SUCCESS' and fs.SynType = 'SyncHostToLocal_Tools'
			order by fs.SyncDate desc LIMIT 1)
	and fT.UpdatedDate > (Case when lT.UpdatedDate is null then '1900-01-01 00:00:00' else lT.UpdatedDate END);  
    
	set @updateTools = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateTools: ", @updateTools );
	
	insert into synchistory(SyncDate, Statistic, Status, SynType)
		VALUES 
	(now(), @finalResult, "SUCCESS", "SyncHostToLocal_Tools");
	commit;
	
	set  returnResult = @finalResult;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SyncLocalToHost` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `SyncLocalToHost`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	-- DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	-- DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;

#insert missing record from Local to Host
    INSERT INTO federated_MasterLog
	SELECT a.* FROM MasterLog a
	LEFT OUTER JOIN federated_MasterLog b ON b.LogID = a.LogID
	WHERE b.LogID IS NULL;
	
	set @insertMasterLog = (SELECT ROW_COUNT());
    set @finalResult = concat("insertMasterLog: ", @insertMasterLog );
	
    #update all record from Local to Host
	update federated_MasterLog
	left join MasterLog on MasterLog.LogID = MasterLog.LogID
		set federated_MasterLog.LogDate = MasterLog.LogDate,
			federated_MasterLog.AssessorName = MasterLog.AssessorName,
			federated_MasterLog.TblName = MasterLog.TblName,
            federated_MasterLog.RecordID = MasterLog.RecordID,
            federated_MasterLog.ColumnName = MasterLog.ColumnName,
            federated_MasterLog.Action = MasterLog.Action,
            federated_MasterLog.OldValue = MasterLog.OldValue,
            federated_MasterLog.NewValue = MasterLog.NewValue,
            federated_MasterLog.CompanyCode = MasterLog.CompanyCode,
            federated_MasterLog.MachineCode = MasterLog.MachineCode,
            federated_MasterLog.Notes = MasterLog.Notes,
            federated_MasterLog.UpdatedDate = sysdate()
		where federated_MasterLog.LogID in (select LogID from MasterLog)
        and MasterLog.UpdatedDate > 
		(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
			from synchistory fs
			where fs.Status = 'SUCCESS' and fs.SynType = 'LocalToHost'
			order by fs.SyncDate desc LIMIT 1)
	and MasterLog.UpdatedDate > (Case when federated_MasterLog.UpdatedDate is null then '1900-01-01 00:00:00' else federated_MasterLog.UpdatedDate END);  
    
	set @updateMasterLog = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateMasterLog: ", @updateMasterLog );
    
#insert missing record from Local to Host
    INSERT INTO federated_ToolsMachineTray
	SELECT a.* FROM ToolsMachineTray a
	LEFT OUTER JOIN federated_ToolsMachineTray b ON b.ToolsMachineTrayID = a.ToolsMachineTrayID
	WHERE b.ToolsMachineTrayID IS NULL;
	
	set @insertToolsMachineTray = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "insertToolsMachineTray: ", @insertToolsMachineTray );
	
    #update all record from Local to Host
	update federated_ToolsMachineTray
	left join ToolsMachineTray on ToolsMachineTray.ToolsMachineTrayID = federated_ToolsMachineTray.ToolsMachineTrayID
		set federated_ToolsMachineTray.MachineCode = ToolsMachineTray.MachineCode,
			federated_ToolsMachineTray.ToolCode = ToolsMachineTray.ToolCode,
			federated_ToolsMachineTray.TrayIndex = ToolsMachineTray.TrayIndex,
			federated_ToolsMachineTray.Quantity = ToolsMachineTray.Quantity,
            federated_ToolsMachineTray.CreatedDate = ToolsMachineTray.CreatedDate,
            federated_ToolsMachineTray.UpdatedDate = ToolsMachineTray.UpdatedDate,
            federated_ToolsMachineTray.IsActive = ToolsMachineTray.IsActive
		where federated_ToolsMachineTray.ToolsMachineTrayID in (select ToolsMachineTrayID from ToolsMachineTray)
        and ToolsMachineTray.UpdatedDate > 
		(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
			from synchistory fs
			where fs.Status = 'SUCCESS' and fs.SynType = 'LocalToHost'
			order by fs.SyncDate desc LIMIT 1)
	and ToolsMachineTray.UpdatedDate > (Case when federated_ToolsMachineTray.UpdatedDate is null then '1900-01-01 00:00:00' else federated_ToolsMachineTray.UpdatedDate END);  
   
   set @updateToolsMachineTray = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateToolsMachineTray: ", @updateToolsMachineTray );
        
    #insert missing record from Local to Host
    INSERT INTO federated_WorkingTransaction
	SELECT a.* FROM WorkingTransaction a
	LEFT OUTER JOIN federated_WorkingTransaction b ON b.WorkingTransactionID = a.WorkingTransactionID
	WHERE b.WorkingTransactionID IS NULL;
	
	set @insertWorkingTransaction = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "insertWorkingTransaction: ", @insertWorkingTransaction );
	
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
			where fs.Status = 'SUCCESS' and fs.SynType = 'LocalToHost'
			order by fs.SyncDate desc LIMIT 1)
	and WorkingTransaction.UpdatedDate > (Case when federated_WorkingTransaction.UpdatedDate is null then '1900-01-01 00:00:00' else federated_WorkingTransaction.UpdatedDate END);  
   
   set @updateWorkingTransaction = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateWorkingTransaction: ", @updateWorkingTransaction );
   
   insert into synchistory(SyncDate, Statistic, Status, SynType)
		VALUES 
	(now(), concat("run store sync changed data from host to local: ", @finalResult), "SUCCESS", "FromLocal");
	
	#insert missing record from Local to Host
    INSERT INTO federated_synchistory
	SELECT a.* FROM synchistory a
	LEFT OUTER JOIN federated_synchistory b ON b.SyncHistoryID = a.SyncHistoryID
	WHERE b.SyncHistoryID IS NULL;
	
       
	insert into masterlog(AssessorName,Action, Notes, CompanyCode, MachineCode) values ("System", "SyncFromLocal", @finalResult, CompanyCode, MachineCode);
            
	commit;
	
	set  returnResult = @finalResult;
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
	START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;

    INSERT INTO federated_ToolsMachineTray
	SELECT a.* FROM ToolsMachineTray a
	LEFT OUTER JOIN federated_ToolsMachineTray b ON b.ToolsMachineTrayID = a.ToolsMachineTrayID
	WHERE b.ToolsMachineTrayID IS NULL;
	
	set @insertToolsMachineTray = (SELECT ROW_COUNT());
    set @finalResult = concat("insertToolsMachineTray: ", @insertToolsMachineTray );
	
    #update all record from Local to Host
	update federated_ToolsMachineTray
	left join ToolsMachineTray on ToolsMachineTray.ToolsMachineTrayID = federated_ToolsMachineTray.ToolsMachineTrayID
		set federated_ToolsMachineTray.MachineCode = ToolsMachineTray.MachineCode,
			federated_ToolsMachineTray.ToolCode = ToolsMachineTray.ToolCode,
			federated_ToolsMachineTray.TrayIndex = ToolsMachineTray.TrayIndex,
			federated_ToolsMachineTray.Quantity = ToolsMachineTray.Quantity,
            federated_ToolsMachineTray.CreatedDate = ToolsMachineTray.CreatedDate,
            federated_ToolsMachineTray.UpdatedDate = ToolsMachineTray.UpdatedDate,
            federated_ToolsMachineTray.IsActive = ToolsMachineTray.IsActive
		where federated_ToolsMachineTray.ToolsMachineTrayID in (select ToolsMachineTrayID from ToolsMachineTray)
        and ToolsMachineTray.UpdatedDate > 
		(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
			from synchistory fs
			where fs.Status = 'SUCCESS' and fs.SynType = 'SyncLocalToHost_ToolsMachineTray'
			order by fs.SyncDate desc LIMIT 1)
	and ToolsMachineTray.UpdatedDate > (Case when federated_ToolsMachineTray.UpdatedDate is null then '1900-01-01 00:00:00' else federated_ToolsMachineTray.UpdatedDate END);  
	
	
	set @updateToolsMachineTray = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateToolsMachineTray: ", @updateToolsMachineTray );
	
	insert into synchistory(SyncDate, Statistic, Status, SynType)
		VALUES 
	(now(), @finalResult, "SUCCESS", "SyncLocalToHost_ToolsMachineTray");
	
	commit;
	
	set  returnResult = @finalResult;
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
	
	INSERT INTO federated_WorkingTransaction
	SELECT a.* FROM WorkingTransaction a
	LEFT OUTER JOIN federated_WorkingTransaction b ON b.WorkingTransactionID = a.WorkingTransactionID
	WHERE b.WorkingTransactionID IS NULL;
	
	set @insertWorkingTransaction = (SELECT ROW_COUNT());
    set @finalResult = concat("insertWorkingTransaction: ", @insertWorkingTransaction );
	
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

-- Dump completed on 2018-10-04 17:27:38
