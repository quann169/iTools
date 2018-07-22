-- DROP DATABASE IF EXISTS  iTools_v1p0;
CREATE DATABASE  IF NOT EXISTS iTools_v1p0;
USE iTools_v1p0;

SET FOREIGN_KEY_CHECKS = 0;
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
  IsLocked INT(10) NOT NULL DEFAULT 0,
  IsActive INT(10) NOT NULL DEFAULT 1,
  LastPassword VARCHAR(255) NULL,
  IsFirstTimeLogin BOOLEAN DEFAULT 0,
  FailTimes INT(10) NOT NULL DEFAULT 0,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  PRIMARY KEY (AssessorID),
  INDEX UserName (UserName),
  FOREIGN KEY (CompanyCode) REFERENCES Company(CompanyCode)
);


INSERT INTO Assessor(AssessorID, UserName, Password, FirstName, LastName, EmailAddress, CompanyCode, IsActive) VALUES 
	(1, "admin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "ADMIN", "quann169@gmail.com", "UHCom", 1),
	(2, "uhadmin1", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "UH", "quann169@gmail.com", "UHCom", 1),
	(3, "uhacc1", "e10adc3949ba59abbe56e057f20f883e", "Acc1", "UH", "quann169@gmail.com", "UHCom", 1),
	(4, "uhacc2", "e10adc3949ba59abbe56e057f20f883e", "Acc2", "UH", "quann169@gmail.com", "UHCom", 1),
	(5, "com1admin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "quann169@gmail.com", "Com1", 1),
	(6, "com1user1", "e10adc3949ba59abbe56e057f20f883e", "User1", "Com1", "quann169@gmail.com", "Com1", 1),
	(7, "com1user2", "e10adc3949ba59abbe56e057f20f883e", "User2", "Com1", "quann169@gmail.com", "Com1", 1),
	(8, "com1user3", "e10adc3949ba59abbe56e057f20f883e", "User3", "Com1", "quann169@gmail.com", "Com1", 1),
	(9, "com2admin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com2", "quann169@gmail.com", "Com2", 1),
	(10, "com2user1", "e10adc3949ba59abbe56e057f20f883e", "User1", "Com2", "quann169@gmail.com", "Com2", 1),
    (11, "RO7LQKKG", "e10adc3949ba59abbe56e057f20f883e", "User1", "Com2", "quann169@gmail.com", "Com2", 1);
    
    /*
INSERT INTO Assessor(AssessorID, UserName, Password, FirstName, LastName, EmailAddress, CompanyCode, IsActive) VALUES 
	(1, "admin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "ADMIN", "quann169@gmail.com", "UHCom", 1) -- ,
	-- ,(5, "com1admin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "quann169@gmail.com", "Com1", 1)
	;

update Assessor set IsFirstTimeLogin = 0 where UserName = 'com1admin';
**/
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
  CreatedDate timestamp null,
  IsActive BOOLEAN NOT NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  PRIMARY KEY (RoleAssessorID),
  INDEX CreatedDate (CreatedDate),
  INDEX RoleID (RoleID),
  INDEX AssessorID (AssessorID),
  FOREIGN KEY (RoleID) REFERENCES Roles(RoleID) ,
  FOREIGN KEY (AssessorID) REFERENCES Assessor(AssessorID) 
);

/*
insert into RoleAssessor (RoleID, AssessorID, CreatedDate, IsActive) values (1,1,now(), 1);
insert into RoleAssessor (RoleID, AssessorID, CreatedDate, IsActive) values (2,1,now(), 1);
insert into RoleAssessor (RoleID, AssessorID, CreatedDate, IsActive) values (3,1,now(), 1);
insert into RoleAssessor (RoleID, AssessorID, CreatedDate, IsActive) values (4,1,now(), 1);
*/
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
  CreatedDate timestamp null,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (MachineID),
  INDEX Machine_MachineCode (MachineCode),
  UNIQUE KEY (MachineCode)
);


	
DROP TABLE IF EXISTS CompanyMachine;
CREATE TABLE IF NOT EXISTS CompanyMachine (
  CompanyMachineID INT(10) NOT NULL AUTO_INCREMENT,
  MachineCode VARCHAR(100) NULL,
  CompanyCode VARCHAR(100) NULL,
  CreatedDate timestamp null,
  IsActive BOOLEAN NOT NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  PRIMARY KEY (CompanyMachineID),
  FOREIGN KEY (MachineCode) REFERENCES Machine(MachineCode),
  FOREIGN KEY (CompanyCode) REFERENCES Company(CompanyCode)
);

/*
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
*/

DROP TABLE IF EXISTS Tools;
CREATE TABLE IF NOT EXISTS Tools (
  ToolID INT(10) NOT NULL AUTO_INCREMENT,
  ToolCode VARCHAR(100) NULL,
  Model VARCHAR(100) NULL,
  Barcode VARCHAR(100) NULL,
  Description VARCHAR(100) NULL,
  CreatedDate timestamp null,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (ToolID),
  UNIQUE KEY (ToolCode)
);

/*
INSERT INTO Tools(ToolID, ToolCode, CreatedDate, IsActive) VALUES 
	(1, "CTID1",  now(), 1),
	(2, "CTID2",  now(), 1),
	(3, "CTID3",  now(), 1),
	(4, "CTID4",  now(), 1),
	(5, "CTID5",  now(), 1),
	(6, "CTID6",  now(), 1),
	(7, "CTID7",  now(), 1);
*/
DROP TABLE IF EXISTS ToolsMachine;
CREATE TABLE IF NOT EXISTS ToolsMachine (
  ToolsMachineID INT(10) NOT NULL AUTO_INCREMENT,
  ToolCode VARCHAR(100) NOT NULL,
  MachineCode VARCHAR(100) NULL,
  CreatedDate timestamp null,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (ToolsMachineID),
  FOREIGN KEY (ToolCode) REFERENCES Tools(ToolCode),
  FOREIGN KEY (MachineCode) REFERENCES Machine(MachineCode)
);
/*
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
*/
	
	

DROP TABLE IF EXISTS ToolsMachineTray;
CREATE TABLE IF NOT EXISTS ToolsMachineTray (
  ToolsMachineTrayID INT(10) NOT NULL AUTO_INCREMENT,
  ToolsMachineID INT(10) NOT NULL,
  TrayIndex VARCHAR(100) NULL,
  Quantity INT(10) NULL,
  CreatedDate timestamp null,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (ToolsMachineTrayID),
  FOREIGN KEY (ToolsMachineID) REFERENCES ToolsMachine(ToolsMachineID)
);

/*
INSERT INTO ToolsMachineTray(ToolsMachineTrayID, ToolsMachineID, TrayIndex, Quantity, CreatedDate, IsActive) VALUES 
	-- ToolID at UHCom, do not add to Machine yet
	(1, 1, NULL, 2, now(), 1),
	(2, 2, NULL, 10, now(), 1),
	(3, 3, NULL, 8, now(), 1),
	(4, 4, NULL, 6, now(), 1),
	(5, 6, NULL, 200, now(), 1),
	(6, 7, NULL, 68, now(), 1),
	-- + MA1
	--	TRAY1: T1 - 5
	--	TRAY2: T6 - 2
	--	TRAY3: 
	--	TRAY4: T2 - 5
	--	TRAY5: T1 - 3
	--
	(7, 8, "Tray_1", 5, now(), 1),
	(8, 10, "Tray_2", 2, now(), 1),
	(9, 9, "Tray_4", 5, now(), 1),
	(10, 8, "Tray_5", 3, now(), 1),
	--+ MA2
		TRAY1: 
		TRAY2: T2 - 2
		TRAY3: T3 - 5
		TRAY4: T2 - 5
		TRAY5: T4 - 3
	--
	(11, 14, "Tray_2", 2, now(), 1),
	(12, 15, "Tray_3", 5, now(), 1),
	(13, 14, "Tray_4", 5, now(), 1),
	(14, 16, "Tray_5", 3, now(), 1)
	--+ MA3
		TRAY1: 
		TRAY2: 
		TRAY3: 
		TRAY4: T2 - 5
		TRAY5: T1 - 3
	--
	
	--+ MA4
		TRAY1: T2 - 5
		TRAY2: T3 - 2
		TRAY3: 
		TRAY4: T4 - 5
		TRAY5: T6 - 3
	--
	
	--+ MA5
		TRAY1: T1 - 5
		TRAY2: T6 - 2
		TRAY3: 
		TRAY4: T2 - 5
		TRAY5: T1 - 3
	--
	;
*/
DROP TABLE IF EXISTS WorkingTransaction;
CREATE TABLE IF NOT EXISTS WorkingTransaction (
  WorkingTransactionID INT(20) NOT NULL AUTO_INCREMENT,
  TransactionDate timestamp null,
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
  LogDate timestamp null,
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
  PendingActionDate timestamp not null default current_timestamp,
  PendingActionName VARCHAR(255) NULL,
  ActionContent TEXT NULL,
  Status VARCHAR(100) NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  PRIMARY KEY (PendingActionID)
);

DROP TABLE IF EXISTS DatabaseVersion;
CREATE TABLE IF NOT EXISTS DatabaseVersion (
  iToolAppDatabase VARCHAR(255) NULL,
  UpdatedDate timestamp not null default current_timestamp
);

INSERT INTO DatabaseVersion(iToolAppDatabase, UpdatedDate) VALUES ("v1p0", now());


DROP TABLE IF EXISTS SyncHistory;
CREATE TABLE IF NOT EXISTS SyncHistory (
  SyncHistoryID INT(20) NOT NULL AUTO_INCREMENT,
  SyncDate timestamp not null default current_timestamp,
  Statistic TEXT NULL,
  Status VARCHAR(255) NULL,
  SynType VARCHAR(255) NULL,
  PRIMARY KEY (SyncHistoryID)
);


INSERT INTO Company(CompanyID, CompanyName, CompanyCode, Address, Location) VALUES 
		(1, "UHCom", "UHCom", "UH Addr", "Location1") -- ,
		-- (2, "Com1", "Com1", "Com1 Addr", "Location2"),
		-- (3, "Com2", "Com2", "Com2 Addr", "Location3")
		;

INSERT INTO Machine(MachineID, MachineName, MachineCode, Location, CreatedDate, IsActive) VALUES 
	-- (1, "MAC1", "MAC1", "Location1", now(), 1),
	-- (2, "MAC2", "MAC2", "Location2", now(), 1) -- ,
	-- (3, "MAC3", "MAC3", "Location3", now(), 1),
	-- (4, "MAC4", "MAC4", "Location4", now(), 1),
	-- (5, "MAC5", "MAC5", "Location5", now(), 1),
	-- virtual machine to manage Tool in UH
	(6, "UHMAC", "UHMAC", "UHLocation", now(), 1);

-- insert first synchistory record 
insert into SyncHistory(SyncDate, Statistic, Status, SynType)
VALUES 
	(now(), "first sync record", "SUCCESS", "HostToLocal"),
	(now(), "first sync record", "SUCCESS", "LocalToHost");

DROP TABLE IF EXISTS federated_Company;
CREATE TABLE IF NOT EXISTS federated_Company (
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
)
ENGINE=FEDERATED
DEFAULT CHARSET=UTF8
CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.49:3306/tqteamne_iTools/Company';

DROP TABLE IF EXISTS federated_Assessor;
CREATE TABLE IF NOT EXISTS federated_Assessor (
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
  FailTimes INT(10) NOT NULL DEFAULT 0,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  PRIMARY KEY (AssessorID),
  INDEX UserName (UserName),
  FOREIGN KEY (CompanyCode) REFERENCES Company(CompanyCode)
)ENGINE=FEDERATED
DEFAULT CHARSET=UTF8
CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.49:3306/tqteamne_iTools/Assessor';

DROP TABLE IF EXISTS federated_Roles;
CREATE TABLE IF NOT EXISTS federated_Roles (
  RoleID INT(10) NOT NULL AUTO_INCREMENT,
  RoleName VARCHAR(100) NULL,
  RoleType INT(10) NULL,
  IsRole INT(10) NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  PRIMARY KEY (RoleID)
)ENGINE=FEDERATED
DEFAULT CHARSET=UTF8
CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.49:3306/tqteamne_iTools/Roles';

DROP TABLE IF EXISTS federated_RoleAssessor;
CREATE TABLE IF NOT EXISTS federated_RoleAssessor (
  RoleAssessorID INT(10) NOT NULL AUTO_INCREMENT,
  RoleID INT(10) NULL,
  AssessorID INT(10) NULL,
  CreatedDate timestamp null,
  IsActive BOOLEAN NOT NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  PRIMARY KEY (RoleAssessorID),
  INDEX CreatedDate (CreatedDate),
  INDEX RoleID (RoleID),
  INDEX AssessorID (AssessorID),
  FOREIGN KEY (RoleID) REFERENCES Roles(RoleID) ,
  FOREIGN KEY (AssessorID) REFERENCES Assessor(AssessorID)
)ENGINE=FEDERATED
DEFAULT CHARSET=UTF8
CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.49:3306/tqteamne_iTools/RoleAssessor';

DROP TABLE IF EXISTS federated_Machine;
CREATE TABLE IF NOT EXISTS federated_Machine (
  MachineID INT(10) NOT NULL AUTO_INCREMENT,
  MachineName VARCHAR(100) NULL,
  MachineCode VARCHAR(100) NOT NULL,
  Model VARCHAR(100) NULL,
  Location VARCHAR(100) NULL,
  Description VARCHAR(100) NULL,
  CreatedDate timestamp null,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (MachineID),
  INDEX Machine_MachineCode (MachineCode),
  UNIQUE KEY (MachineCode)
)ENGINE=FEDERATED
DEFAULT CHARSET=UTF8
CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.49:3306/tqteamne_iTools/Machine';

DROP TABLE IF EXISTS federated_CompanyMachine;
CREATE TABLE IF NOT EXISTS federated_CompanyMachine (
  CompanyMachineID INT(10) NOT NULL AUTO_INCREMENT,
  MachineCode VARCHAR(100) NULL,
  CompanyCode VARCHAR(100) NULL,
  CreatedDate timestamp not null default current_timestamp,
  IsActive BOOLEAN NOT NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  PRIMARY KEY (CompanyMachineID),
  FOREIGN KEY (MachineCode) REFERENCES Machine(MachineCode),
  FOREIGN KEY (CompanyCode) REFERENCES Company(CompanyCode)
)ENGINE=FEDERATED
DEFAULT CHARSET=UTF8
CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.49:3306/tqteamne_iTools/CompanyMachine';

DROP TABLE IF EXISTS federated_Tools;
CREATE TABLE IF NOT EXISTS federated_Tools (
  ToolID INT(10) NOT NULL AUTO_INCREMENT,
  ToolCode VARCHAR(100) NULL,
  Model VARCHAR(100) NULL,
  Barcode VARCHAR(100) NULL,
  Description VARCHAR(100) NULL,
  CreatedDate timestamp null,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (ToolID),
  UNIQUE KEY (ToolCode)
)ENGINE=FEDERATED
DEFAULT CHARSET=UTF8
CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.49:3306/tqteamne_iTools/Tools';

DROP TABLE IF EXISTS federated_ToolsMachine;
CREATE TABLE IF NOT EXISTS federated_ToolsMachine (
  ToolsMachineID INT(10) NOT NULL AUTO_INCREMENT,
  ToolCode VARCHAR(100) NOT NULL,
  MachineCode VARCHAR(100) NULL,
  CreatedDate timestamp not null default current_timestamp,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (ToolsMachineID),
  FOREIGN KEY (ToolCode) REFERENCES Tools(ToolCode),
  FOREIGN KEY (MachineCode) REFERENCES Machine(MachineCode)
)ENGINE=FEDERATED
DEFAULT CHARSET=UTF8
CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.49:3306/tqteamne_iTools/ToolsMachine';

DROP TABLE IF EXISTS federated_ToolsMachineTray;
CREATE TABLE IF NOT EXISTS federated_ToolsMachineTray (
  ToolsMachineTrayID INT(10) NOT NULL AUTO_INCREMENT,
  ToolsMachineID INT(10) NOT NULL,
  TrayIndex VARCHAR(100) NULL,
  Quantity INT(10) NULL,
  CreatedDate timestamp null,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (ToolsMachineTrayID),
  FOREIGN KEY (ToolsMachineID) REFERENCES ToolsMachine(ToolsMachineID)
)ENGINE=FEDERATED
DEFAULT CHARSET=UTF8
CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.49:3306/tqteamne_iTools/ToolsMachineTray';

DROP TABLE IF EXISTS federated_WorkingTransaction;
CREATE TABLE IF NOT EXISTS federated_WorkingTransaction (
  WorkingTransactionID INT(20) NOT NULL AUTO_INCREMENT,
  TransactionDate timestamp not null default current_timestamp,
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
)ENGINE=FEDERATED
DEFAULT CHARSET=UTF8
CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.49:3306/tqteamne_iTools/WorkingTransaction';

DROP TABLE IF EXISTS federated_MasterLog;
CREATE TABLE IF NOT EXISTS federated_MasterLog (
  LogID INT(20) NOT NULL AUTO_INCREMENT,
  LogDate timestamp not null default current_timestamp,
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
)ENGINE=FEDERATED
DEFAULT CHARSET=UTF8
CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.49:3306/tqteamne_iTools/MasterLog';

DROP TABLE IF EXISTS federated_PendingAction;
CREATE TABLE IF NOT EXISTS federated_PendingAction (
  PendingActionID INT(20) NOT NULL AUTO_INCREMENT,
  PendingActionDate timestamp not null default current_timestamp,
  PendingActionName VARCHAR(255) NULL,
  ActionContent TEXT NULL,
  Status VARCHAR(100) NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  PRIMARY KEY (PendingActionID)
)ENGINE=FEDERATED
DEFAULT CHARSET=UTF8
CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.49:3306/tqteamne_iTools/PendingAction';

DROP TABLE IF EXISTS federated_DatabaseVersion;
CREATE TABLE IF NOT EXISTS federated_DatabaseVersion (
  iToolAppDatabase VARCHAR(255) NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp
)ENGINE=FEDERATED
DEFAULT CHARSET=UTF8
CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.49:3306/tqteamne_iTools/DatabaseVersion';

DROP TABLE IF EXISTS federated_SyncHistory;
CREATE TABLE IF NOT EXISTS federated_SyncHistory (
  SyncHistoryID INT(20) NOT NULL AUTO_INCREMENT,
  SyncDate timestamp not null default current_timestamp,
  Statistic TEXT NULL,
  Status VARCHAR(255) NULL,
  SynType VARCHAR(255) NULL,
  PRIMARY KEY (SyncHistoryID)
)ENGINE=FEDERATED
DEFAULT CHARSET=UTF8
CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.49:3306/tqteamne_iTools/SyncHistory';

DELIMITER $$

DROP PROCEDURE IF EXISTS `SyncLocalToHost`;
DROP PROCEDURE IF EXISTS `SyncHostToLocal`;

CREATE PROCEDURE `SyncLocalToHost`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
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
		set federated_ToolsMachineTray.ToolsMachineID = ToolsMachineTray.ToolsMachineID,
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
END$$

CREATE PROCEDURE `SyncHostToLocal`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
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
    
    #insert missing record from Host to local
    INSERT INTO ToolsMachine
	SELECT a.* FROM federated_ToolsMachine a
	LEFT OUTER JOIN ToolsMachine b ON b.ToolsMachineID = a.ToolsMachineID
	WHERE b.ToolsMachineID IS NULL;
	
	set @insertToolsMachine = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "insertToolsMachine: ", @insertToolsMachine);
	
    #update all record from Host to local
	update ToolsMachine lTM
	left join federated_ToolsMachine fTM on lTM.ToolsMachineID = fTM.ToolsMachineID
		set lTM.ToolCode = fTM.ToolCode,
            lTM.MachineCode = fTM.MachineCode,
			lTM.CreatedDate = fTM.CreatedDate,
            lTM.UpdatedDate = sysdate(),
            lTM.IsActive = fTM.IsActive
		where lTM.ToolsMachineID in (select ToolsMachineID from federated_ToolsMachine)
        and fTM.UpdatedDate > 
		(select (Case when fs.SyncDate is null then '1900-01-01 00:00:00' else fs.SyncDate END) 
			from synchistory fs
			where fs.Status = 'SUCCESS' and fs.SynType = 'HostToLocal'
			order by fs.SyncDate desc LIMIT 1)
	and fTM.UpdatedDate > (Case when lTM.UpdatedDate is null then '1900-01-01 00:00:00' else lTM.UpdatedDate END);  
	
	set @updateToolsMachine = (SELECT ROW_COUNT());
    set @finalResult = concat(@finalResult, ",", "\n", "updateToolsMachine: ", @updateToolsMachine );
	
	
	
	
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
	
	
	
	insert into synchistory(SyncDate, Statistic, Status, SynType)
		VALUES 
	(now(), concat("run store sync changed data from host to local: ", @finalResult), "SUCCESS", "FromHost");
       
	insert into masterlog(AssessorName,Action, Notes, CompanyCode, MachineCode) values ("System", "SyncFromHost", @finalResult, CompanyCode, MachineCode);
	commit;
	
	set  returnResult = @finalResult;
END$$



DELIMITER $
	
	
	
SET FOREIGN_KEY_CHECKS = 1;
