DROP DATABASE IF EXISTS  iTools_v1p0;
CREATE DATABASE  IF NOT EXISTS iTools_v1p0;
USE iTools_v1p0;


DROP TABLE IF EXISTS Company;
CREATE TABLE IF NOT EXISTS Company (
  CompanyID INT(10) NOT NULL AUTO_INCREMENT,
  CompanyCode VARCHAR(100) NULL,
  CompanyName VARCHAR(100) NULL,
  CompanyType VARCHAR(100) NULL,
  Address VARCHAR(100) NULL,
  Location VARCHAR(100) NULL,
  PRIMARY KEY (CompanyID)
);

INSERT INTO Company(CompanyID, CompanyName, CompanyCode, Address, Location) VALUES 
		(1, "UHCom", "UHCom", "UH Addr", "Location1"),
		(2, "Com1", "Com1", "Com1 Addr", "Location2"),
		(3, "Com2", "Com2", "Com2 Addr", "Location3");


DROP TABLE IF EXISTS Assessor;
CREATE TABLE IF NOT EXISTS Assessor (
  AssessorID INT(10) NOT NULL AUTO_INCREMENT,
  UserName VARCHAR(100) NULL,
  FingerID VARCHAR(100) NULL,
  Password VARCHAR(255) NULL,
  FirstName VARCHAR(255) NULL,
  LastName VARCHAR(255) NULL,
  EmailAddress VARCHAR(255) NOT NULL,
  Address VARCHAR(255) NULL,
  Phone VARCHAR(255) NULL,
  CompanyCode VARCHAR(100) NULL,
  IsActive BOOLEAN NOT NULL,
  LastPassword VARCHAR(255) NULL,
  PRIMARY KEY (AssessorID),
  INDEX UserName (UserName)
);

INSERT INTO Assessor(AssessorID, UserName, Password, FirstName, LastName, EmailAddress, CompanyCode, IsActive) VALUES 
	(1, "admin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "ADMIN", "quann169@gmail.com", NULL, 1),
	(2, "uhadmin1", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "UH", "admin1@aaa.bbb", "UHCom", 1),
	(3, "uhacc1", "e10adc3949ba59abbe56e057f20f883e", "Acc1", "UH", "acc1@aaa.bbb", "UHCom", 1),
	(4, "uhacc2", "e10adc3949ba59abbe56e057f20f883e", "Acc2", "UH", "acc2@aaa.bbb", "UHCom", 1),
	(5, "com1admin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "com1admin@aaa.bbb", "Com1", 1),
	(6, "com1user1", "e10adc3949ba59abbe56e057f20f883e", "User1", "Com1", "com1admin@aaa.bbb", "Com1", 1),
	(7, "com1user2", "e10adc3949ba59abbe56e057f20f883e", "User2", "Com1", "com1admin@aaa.bbb", "Com1", 1),
	(8, "com1user3", "e10adc3949ba59abbe56e057f20f883e", "User3", "Com1", "com1admin@aaa.bbb", "Com1", 1),
	(9, "com2admin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com2", "com1admin@aaa.bbb", 3, 1),
	(10, "com2user1", "e10adc3949ba59abbe56e057f20f883e", "User1", "Com2", "com1admin@aaa.bbb", 3, 1);


DROP TABLE IF EXISTS Roles;
CREATE TABLE IF NOT EXISTS Roles (
  RoleID INT(10) NOT NULL AUTO_INCREMENT,
  RoleName VARCHAR(100) NULL,
  RoleType INT(10) NULL,
  IsRole INT(10) NULL,
  PRIMARY KEY (RoleID)
);
INSERT INTO Roles(RoleID, RoleName, IsRole) VALUES 
	(1, "Admin", 1),
	(2, "SubAdmin", 1),
	(3, "Accounting", 1),
	(4, "Emp", 1),
	(5, "Other", 1),
	(6, "PutIns", 0),
	(7, "TakeOver", 0),
	(8, "EditTransaction", 0);



DROP TABLE IF EXISTS RoleAssessor;
CREATE TABLE IF NOT EXISTS RoleAssessor (
  RoleAssessorID INT(10) NOT NULL AUTO_INCREMENT,
  RoleID INT(10) NULL,
  AssessorID INT(10) NULL,
  CreatedDate DATETIME NULL,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (RoleAssessorID),
  INDEX CreatedDate (CreatedDate),
  INDEX RoleID (RoleID),
  INDEX AssessorID (AssessorID),
  FOREIGN KEY (RoleID) REFERENCES Roles(RoleID) ON DELETE CASCADE,
  FOREIGN KEY (AssessorID) REFERENCES Assessor(AssessorID) ON DELETE CASCADE
);

-- Admin UH has Admin, SubAdmin, Accounting, PutIns, TakeOver, UpdateReport
INSERT INTO RoleAssessor(RoleAssessorID, RoleID, AssessorID, CreatedDate, IsActive) VALUES 
	(1, 1, 2, now(), 1),
	(2, 2, 2, now(), 1),
	(3, 3, 2, now(), 1),
	(4, 6, 2, now(), 1),
	(5, 7, 2, now(), 1),
	(6, 8, 2, now(), 1),	
-- Acc1 UH has Accounting, PutIns, TakeOver
	(7, 3, 3, now(), 1),
	(8, 6, 3, now(), 1),
	(9, 7, 3, now(), 1),
-- Acc2 UH has Accounting, PutIns, UpdateReport
	(10, 3, 4, now(), 1),
	(11, 7, 4, now(), 1),
	(12, 8, 4, now(), 1),
-- Admin Com1 has SubAdmin, Emp
	(13, 2, 5, now(), 1),
	(14, 4, 5, now(), 1),
-- Com1 emp
	(15, 4, 6, now(), 1),
	(16, 4, 7, now(), 1),
	(17, 4, 8, now(), 1),
-- Admin Com2 has SubAdmin, Emp
	(18, 2, 9, now(), 1),
	(19, 4, 9, now(), 1),
-- Com2 emp
	(20, 4, 10, now(), 1);

DROP TABLE IF EXISTS Machine;
CREATE TABLE IF NOT EXISTS Machine (
  MachineID INT(10) NOT NULL AUTO_INCREMENT,
  Name VARCHAR(100) NULL,
  MachineCode VARCHAR(100) NULL,
  Model VARCHAR(100) NULL,
  Location VARCHAR(100) NULL,
  Description VARCHAR(100) NULL,
  CreatedDate DATETIME NULL,
  UpdatedDate DATETIME NULL,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (MachineID)
);

INSERT INTO Machine(MachineID, Name, MachineCode, Location, CreatedDate, IsActive) VALUES 
	(1, "MAC1", "MAC1", "Location1", now(), 1),
	(2, "MAC2", "MAC2", "Location2", now(), 1),
	(3, "MAC3", "MAC3", "Location3", now(), 1),
	(4, "MAC4", "MAC4", "Location4", now(), 1),
	(5, "MAC5", "MAC5", "Location5", now(), 1);
	
DROP TABLE IF EXISTS CompanyMachine;
CREATE TABLE IF NOT EXISTS CompanyMachine (
  CompanyMachineID INT(10) NOT NULL AUTO_INCREMENT,
  MachineID INT(10) NULL,
  CompanyID INT(10) NULL,
  CreatedDate DATETIME NULL,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (CompanyMachineID),
  FOREIGN KEY (MachineID) REFERENCES Machine(MachineID),
  FOREIGN KEY (CompanyID) REFERENCES Company(CompanyID)
);

INSERT INTO CompanyMachine(CompanyMachineID, MachineID, CompanyID, CreatedDate, IsActive) VALUES 
	-- Com1 use Mac1, Mac2
	(1, 1, 2, now(), 1),
	(2, 2, 2, now(), 1),
	-- Com2 use Mac3, Mac4, Mac5
	(3, 3, 3, now(), 1),
	(4, 4, 3, now(), 1),
	(5, 5, 3, now(), 1),
	-- Move Mac3 from Com1 to Com2
	(6, 3, 2, now(), 0);

DROP TABLE IF EXISTS Tools;
CREATE TABLE IF NOT EXISTS Tools (
  ToolID INT(10) NOT NULL AUTO_INCREMENT,
  Name VARCHAR(100) NULL,
  Model VARCHAR(100) NULL,
  Barcode VARCHAR(100) NULL,
  Description VARCHAR(100) NULL,
  CreatedDate DATETIME NULL,
  UpdatedDate DATETIME NULL,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (ToolID)
);

INSERT INTO Tools(ToolID, Name, CreatedDate, IsActive) VALUES 
	(1, "CTID1",  now(), 1),
	(2, "CTID2",  now(), 1),
	(3, "CTID3",  now(), 1),
	(4, "CTID4",  now(), 1),
	(5, "CTID5",  now(), 1),
	(6, "CTID6",  now(), 1),
	(7, "CTID7",  now(), 1);
	
DROP TABLE IF EXISTS ToolsMachineTray;
CREATE TABLE IF NOT EXISTS ToolsMachineTray (
  ToolsMachineTrayID INT(10) NOT NULL AUTO_INCREMENT,
  ToolID INT(10) NOT NULL,
  MachineID INT(10) NULL,
  TrayIndex VARCHAR(100) NULL,
  Quantity INT(10) NULL,
  CreatedDate DATETIME NULL,
  UpdatedDate DATETIME NULL,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (ToolsMachineTrayID),
  FOREIGN KEY (ToolID) REFERENCES Tools(ToolID),
  FOREIGN KEY (MachineID) REFERENCES Machine(MachineID)
);

INSERT INTO ToolsMachineTray(ToolsMachineTrayID, ToolID, MachineID, TrayIndex, Quantity, CreatedDate, IsActive) VALUES 
	-- ToolID at UHCom, do not add to Machine yet
	(1, 1, NULL, NULL, 2, now(), 1),
	(2, 2, NULL, NULL, 10, now(), 1),
	(3, 3, NULL, NULL, 8, now(), 1),
	(4, 4, NULL, NULL, 6, now(), 1),
	(5, 6, NULL, NULL, 200, now(), 1),
	(6, 7, NULL, NULL, 68, now(), 1),
	/*+ MA1
		TRAY1: T1 - 5
		TRAY2: T6 - 2
		TRAY3: 
		TRAY4: T2 - 5
		TRAY5: T1 - 3
	*/
	(7, 1, 1, 1, 5, now(), 1),
	(8, 6, 1, 2, 2, now(), 1),
	(9, 2, 1, 4, 5, now(), 1),
	(10, 1, 1, 5, 3, now(), 1),
	/*+ MA2
		TRAY1: 
		TRAY2: T2 - 2
		TRAY3: T3 - 5
		TRAY4: T2 - 5
		TRAY5: T4 - 3
	*/
	(11, 2, 2, 2, 2, now(), 1),
	(12, 3, 2, 3, 5, now(), 1),
	(13, 2, 2, 4, 5, now(), 1),
	(14, 4, 2, 5, 3, now(), 1),
	/*+ MA3
		TRAY1: 
		TRAY2: 
		TRAY3: 
		TRAY4: T2 - 5
		TRAY5: T1 - 3
	*/
	(112, 2, 3, 4, 5, now(), 1),
	(113, 1, 3, 5, 3, now(), 1),
	/*+ MA4
		TRAY1: T2 - 5
		TRAY2: T3 - 2
		TRAY3: 
		TRAY4: T4 - 5
		TRAY5: T6 - 3
	*/
	(114, 2, 4, 1, 5, now(), 1),
	(15, 3, 4, 2, 2, now(), 1),
	(16, 4, 4, 4, 5, now(), 1),
	(17, 6, 4, 6, 3, now(), 1),
	/*+ MA5
		TRAY1: T1 - 5
		TRAY2: T6 - 2
		TRAY3: 
		TRAY4: T2 - 5
		TRAY5: T1 - 3
	*/
	(18, 1, 5, 1, 5, now(), 1),
	(19, 6, 5, 2, 2, now(), 1),
	(20, 2, 5, 4, 5, now(), 1),
	(21, 1, 5, 6, 3, now(), 1);

DROP TABLE IF EXISTS WorkingTransaction;
CREATE TABLE IF NOT EXISTS WorkingTransaction (
  WorkingTransactionID INT(20) NOT NULL AUTO_INCREMENT,
  TransactionDate DATETIME NULL,
  AssessorID INT(10) NOT NULL,
  WOCode INT(10) NOT NULL,
  OPCode INT(10) NOT NULL,
  ToolID INT(10) NOT NULL,
  TrayIndex VARCHAR(100) NULL,
  UpdatedDate DATETIME NULL,
  RespondMessage VARCHAR(255) NULL,
  TransactionType VARCHAR(255) NULL,
  PRIMARY KEY (WorkingTransactionID),
  INDEX TransactionDate (TransactionDate),
  INDEX AssessorID (AssessorID),
  INDEX ToolID (ToolID),
  FOREIGN KEY (AssessorID) REFERENCES Assessor(AssessorID),
  FOREIGN KEY (ToolID) REFERENCES Tools(ToolID)
);
	
DROP TABLE IF EXISTS MasterLog;
CREATE TABLE IF NOT EXISTS MasterLog (
  LogID INT(20) NOT NULL AUTO_INCREMENT,
  LogDate DATETIME NULL,
  AssessorName VARCHAR(255) NULL,
  TblName VARCHAR(100) NULL,
  RecordID INT(10) NULL,
  ColumnName VARCHAR(100) NULL,
  OldValue TEXT NULL,
  NewValue TEXT NULL,
  LogStatus TINYINT(3) UNSIGNED NULL,
  ApprovedBy VARCHAR(255) NULL,
  ApprovedDate DATETIME NULL,
  PRIMARY KEY (LogID),
  INDEX RecordID (RecordID),
  INDEX ApprovedDate (ApprovedDate),
  INDEX AssessorName (AssessorName),
  INDEX LogDate (LogDate)
); 

