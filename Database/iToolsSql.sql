DROP DATABASE IF EXISTS  iTools_v1p0;
CREATE DATABASE  IF NOT EXISTS iTools_v1p0;
USE iTools_v1p0;

SET SQL_SAFE_UPDATES = 0;

DROP TABLE IF EXISTS Company;
CREATE TABLE IF NOT EXISTS Company (
  CompanyID INT(10) NOT NULL AUTO_INCREMENT,
  CompanyCode VARCHAR(100) NOT NULL,
  CompanyName VARCHAR(100) NULL,
  CompanyType VARCHAR(100) NULL,
  Address VARCHAR(100) NULL,
  Location VARCHAR(100) NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  INDEX Company_CompanyCode (CompanyCode),
  PRIMARY KEY (CompanyID),
  UNIQUE KEY (CompanyCode)
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
  IsLocked BOOLEAN DEFAULT 0,
  IsActive BOOLEAN NOT NULL,
  LastPassword VARCHAR(255) NULL,
  IsFirstTimeLogin BOOLEAN NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  PRIMARY KEY (AssessorID),
  INDEX UserName (UserName),
  FOREIGN KEY (CompanyCode) REFERENCES Company(CompanyCode)
);

INSERT INTO Assessor(AssessorID, UserName, Password, FirstName, LastName, EmailAddress, CompanyCode, IsActive) VALUES 
	(1, "administrator", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "ADMIN", "quann169@gmail.com", "UHCom", 1),
	(2, "uhadmin1", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "UH", "admin1@aaa.bbb", "UHCom", 1),
	(3, "uhacc1", "e10adc3949ba59abbe56e057f20f883e", "Acc1", "UH", "acc1@aaa.bbb", "UHCom", 1),
	(4, "uhacc2", "e10adc3949ba59abbe56e057f20f883e", "Acc2", "UH", "acc2@aaa.bbb", "UHCom", 1),
	(5, "com1admin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "quannguyen@savarti.com", "Com1", 1),
	(6, "com1user1", "e10adc3949ba59abbe56e057f20f883e", "User1", "Com1", "com1admin@aaa.bbb", "Com1", 1),
	(7, "com1user2", "e10adc3949ba59abbe56e057f20f883e", "User2", "Com1", "com1admin@aaa.bbb", "Com1", 1),
	(8, "com1user3", "e10adc3949ba59abbe56e057f20f883e", "User3", "Com1", "com1admin@aaa.bbb", "Com1", 1),
	(9, "com2admin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com2", "com1admin@aaa.bbb", "Com2", 1),
	(10, "com2user1", "e10adc3949ba59abbe56e057f20f883e", "User1", "Com2", "com1admin@aaa.bbb", "Com2", 1);

update Assessor set IsFirstTimeLogin = 0 where UserName = 'com1admin';

DROP TABLE IF EXISTS Roles;
CREATE TABLE IF NOT EXISTS Roles (
  RoleID INT(10) NOT NULL AUTO_INCREMENT,
  RoleName VARCHAR(100) NULL,
  RoleType INT(10) NULL,
  IsRole INT(10) NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
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
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  PRIMARY KEY (RoleAssessorID),
  INDEX CreatedDate (CreatedDate),
  INDEX RoleID (RoleID),
  INDEX AssessorID (AssessorID),
  FOREIGN KEY (RoleID) REFERENCES Roles(RoleID) ,
  FOREIGN KEY (AssessorID) REFERENCES Assessor(AssessorID) 
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
  MachineName VARCHAR(100) NULL,
  MachineCode VARCHAR(100) NOT NULL,
  Model VARCHAR(100) NULL,
  Location VARCHAR(100) NULL,
  Description VARCHAR(100) NULL,
  CreatedDate DATETIME NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (MachineID),
  INDEX Machine_MachineCode (MachineCode),
  UNIQUE KEY (MachineCode)
);

INSERT INTO Machine(MachineID, MachineName, MachineCode, Location, CreatedDate, IsActive) VALUES 
	(1, "MAC1", "MAC1", "Location1", now(), 1),
	(2, "MAC2", "MAC2", "Location2", now(), 1),
	(3, "MAC3", "MAC3", "Location3", now(), 1),
	(4, "MAC4", "MAC4", "Location4", now(), 1),
	(5, "MAC5", "MAC5", "Location5", now(), 1),
	-- virtual machine to manage Tool in UH
	(6, "UHMAC", "UHMAC", "UHLocation", now(), 1);
	
DROP TABLE IF EXISTS CompanyMachine;
CREATE TABLE IF NOT EXISTS CompanyMachine (
  CompanyMachineID INT(10) NOT NULL AUTO_INCREMENT,
  MachineCode VARCHAR(100) NULL,
  CompanyCode VARCHAR(100) NULL,
  CreatedDate DATETIME NULL,
  IsActive BOOLEAN NOT NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  PRIMARY KEY (CompanyMachineID),
  FOREIGN KEY (MachineCode) REFERENCES Machine(MachineCode),
  FOREIGN KEY (CompanyCode) REFERENCES Company(CompanyCode)
);

INSERT INTO CompanyMachine(CompanyMachineID, MachineCode, CompanyCode, CreatedDate, IsActive) VALUES 
	-- Com1 use Mac1, Mac2
	(1, "MAC1", "Com1", now(), 1),
	(2, "MAC2", "Com1", now(), 1),
	-- Com2 use Mac3, Mac4, Mac5
	(3, "MAC3", "Com2", now(), 1),
	(4, "MAC4", "Com2", now(), 1),
	(5, "MAC5", "Com2", now(), 1),
	-- Move Mac3 from Com1 to Com2
	(6, "MAC3", "Com2", now(), 0),
	-- UH virtual machine
	(7, "UHMAC", "UHCom", now(), 1);

DROP TABLE IF EXISTS Tools;
CREATE TABLE IF NOT EXISTS Tools (
  ToolID INT(10) NOT NULL AUTO_INCREMENT,
  ToolCode VARCHAR(100) NULL,
  Model VARCHAR(100) NULL,
  Barcode VARCHAR(100) NULL,
  Description VARCHAR(100) NULL,
  CreatedDate DATETIME NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (ToolID),
  UNIQUE KEY (ToolCode)
);

INSERT INTO Tools(ToolID, ToolCode, CreatedDate, IsActive) VALUES 
	(1, "CTID1",  now(), 1),
	(2, "CTID2",  now(), 1),
	(3, "CTID3",  now(), 1),
	(4, "CTID4",  now(), 1),
	(5, "CTID5",  now(), 1),
	(6, "CTID6",  now(), 1),
	(7, "CTID7",  now(), 1);
	
DROP TABLE IF EXISTS ToolsMachine;
CREATE TABLE IF NOT EXISTS ToolsMachine (
  ToolsMachineID INT(10) NOT NULL AUTO_INCREMENT,
  ToolCode VARCHAR(100) NOT NULL,
  MachineCode VARCHAR(100) NULL,
  CreatedDate DATETIME NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (ToolsMachineID),
  FOREIGN KEY (ToolCode) REFERENCES Tools(ToolCode),
  FOREIGN KEY (MachineCode) REFERENCES Machine(MachineCode)
);

INSERT INTO ToolsMachine(ToolsMachineID, ToolCode, MachineCode,CreatedDate, IsActive) VALUES 
	-- ToolID at UHCom, do not add to Machine yet
	(1, "CTID1", "UHMAC", now(), 1),
	(2, "CTID2", "UHMAC", now(), 1),
	(3, "CTID3", "UHMAC", now(), 1),
	(4, "CTID4", "UHMAC", now(), 1),
	(5, "CTID5", "UHMAC", now(), 1),
	(6, "CTID6", "UHMAC", now(), 1),
	(7, "CTID7", "UHMAC", now(), 1),
	-- Tool for MAC1
	(8, "CTID1", "MAC1", now(), 1),
	(9, "CTID2", "MAC1", now(), 1),
	(10, "CTID6", "MAC1", now(), 1),
	(11, "CTID4", "MAC1", now(), 1), -- out of stock
	(12, "CTID5", "MAC1", now(), 1), -- out of stock
	-- Tool for MAC2
	(13, "CTID1", "MAC2", now(), 1), -- out of stock
	(14, "CTID2", "MAC2", now(), 1),
	(15, "CTID3", "MAC2", now(), 1),
	(16, "CTID4", "MAC2", now(), 1);
	
	
	

DROP TABLE IF EXISTS ToolsMachineTray;
CREATE TABLE IF NOT EXISTS ToolsMachineTray (
  ToolsMachineTrayID INT(10) NOT NULL AUTO_INCREMENT,
  ToolsMachineID INT(10) NOT NULL,
  TrayIndex VARCHAR(100) NULL,
  Quantity INT(10) NULL,
  CreatedDate DATETIME NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (ToolsMachineTrayID),
  FOREIGN KEY (ToolsMachineID) REFERENCES ToolsMachine(ToolsMachineID)
);

INSERT INTO ToolsMachineTray(ToolsMachineTrayID, ToolsMachineID, TrayIndex, Quantity, CreatedDate, IsActive) VALUES 
	-- ToolID at UHCom, do not add to Machine yet
	(1, 1, NULL, 2, now(), 1),
	(2, 2, NULL, 10, now(), 1),
	(3, 3, NULL, 8, now(), 1),
	(4, 4, NULL, 6, now(), 1),
	(5, 6, NULL, 200, now(), 1),
	(6, 7, NULL, 68, now(), 1),
	/*+ MA1
		TRAY1: T1 - 5
		TRAY2: T6 - 2
		TRAY3: 
		TRAY4: T2 - 5
		TRAY5: T1 - 3
	*/
	(7, 8, "Tray_1", 5, now(), 1),
	(8, 10, "Tray_2", 2, now(), 1),
	(9, 9, "Tray_4", 5, now(), 1),
	(10, 8, "Tray_5", 3, now(), 1),
	/*+ MA2
		TRAY1: 
		TRAY2: T2 - 2
		TRAY3: T3 - 5
		TRAY4: T2 - 5
		TRAY5: T4 - 3
	*/
	(11, 14, "Tray_2", 2, now(), 1),
	(12, 15, "Tray_3", 5, now(), 1),
	(13, 14, "Tray_4", 5, now(), 1),
	(14, 16, "Tray_5", 3, now(), 1)
	/*+ MA3
		TRAY1: 
		TRAY2: 
		TRAY3: 
		TRAY4: T2 - 5
		TRAY5: T1 - 3
	*/
	
	/*+ MA4
		TRAY1: T2 - 5
		TRAY2: T3 - 2
		TRAY3: 
		TRAY4: T4 - 5
		TRAY5: T6 - 3
	*/
	
	/*+ MA5
		TRAY1: T1 - 5
		TRAY2: T6 - 2
		TRAY3: 
		TRAY4: T2 - 5
		TRAY5: T1 - 3
	*/
	;

DROP TABLE IF EXISTS WorkingTransaction;
CREATE TABLE IF NOT EXISTS WorkingTransaction (
  WorkingTransactionID INT(20) NOT NULL AUTO_INCREMENT,
  TransactionDate DATETIME NULL,
  MachineCode VARCHAR(100) NOT NULL,
  CompanyCode VARCHAR(100) NOT NULL,
  AssessorID VARCHAR(100) NOT NULL,
  WOCode VARCHAR(100) NOT NULL,
  OPCode VARCHAR(100) NOT NULL,
  ToolCode VARCHAR(100) NOT NULL,
  TrayIndex VARCHAR(100) NULL,
  Quantity INT(10) NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  TransactionStatus VARCHAR(255) NULL,
  RespondMessage VARCHAR(255) NULL,
  TransactionType VARCHAR(255) NULL,
  PRIMARY KEY (WorkingTransactionID),
  INDEX TransactionDate (TransactionDate),
  INDEX AssessorID (AssessorID),
  INDEX ToolCode (ToolCode),
  FOREIGN KEY (MachineCode) REFERENCES Machine(MachineCode),
  FOREIGN KEY (CompanyCode) REFERENCES Company(CompanyCode),
  FOREIGN KEY (ToolCode) REFERENCES Tools(ToolCode)
);
	
DROP TABLE IF EXISTS MasterLog;
CREATE TABLE IF NOT EXISTS MasterLog (
  LogID INT(20) NOT NULL AUTO_INCREMENT,
  LogDate DATETIME NULL,
  AssessorName VARCHAR(255) NULL,
  TblName VARCHAR(100) NULL,
  RecordID INT(10) NULL,
  ColumnName VARCHAR(100) NULL,
  Action VARCHAR(100) NULL,
  OldValue TEXT NULL,
  NewValue TEXT NULL,
  CompanyCode VARCHAR(100) NULL,
  MachineCode VARCHAR(100) NULL,
  Notes TEXT NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  PRIMARY KEY (LogID),
  INDEX RecordID (RecordID),
  INDEX AssessorName (AssessorName),
  INDEX LogDate (LogDate)
); 


DROP TABLE IF EXISTS PendingAction;
CREATE TABLE IF NOT EXISTS PendingAction (
  PendingActionID INT(20) NOT NULL AUTO_INCREMENT,
  PendingActionDate DATETIME NULL,
  PendingActionName VARCHAR(255) NULL,
  ActionContent TEXT NULL,
  Status VARCHAR(100) NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  PRIMARY KEY (PendingActionID)
);

DROP TABLE IF EXISTS DatabaseVersion;
CREATE TABLE IF NOT EXISTS DatabaseVersion (
  iToolAppDatabase VARCHAR(255) NULL,
  UpdatedDate DATETIME NULL
);

INSERT INTO DatabaseVersion(iToolAppDatabase, UpdatedDate) VALUES ("v1p0", now());


DROP TABLE IF EXISTS SyncHistory;
CREATE TABLE IF NOT EXISTS SyncHistory (
  SyncHistoryID INT(20) NOT NULL AUTO_INCREMENT,
  SyncDate DATETIME NULL,
  Statistic TEXT NULL,
  Status VARCHAR(255) NULL,
  SynType VARCHAR(255) NULL,
  PRIMARY KEY (SyncHistoryID)
);


-- insert first synchistory record 
insert into SyncHistory(SyncDate, Statistic, Status, SynType)
VALUES 
	(now(), "first sync record", "SUCCESS", "FromHost"),
	(now(), "first sync record", "SUCCESS", "FromLocal");



