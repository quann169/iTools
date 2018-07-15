CREATE TABLE  IF NOT EXISTS federated_Company (
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


CREATE TABLE IF NOT EXISTS federated_RoleAssessor (
  RoleAssessorID INT(10) NOT NULL AUTO_INCREMENT,
  RoleID INT(10) NULL,
  AssessorID INT(10) NULL,
  CreatedDate timestamp not null default current_timestamp,
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


CREATE TABLE IF NOT EXISTS federated_Machine (
  MachineID INT(10) NOT NULL AUTO_INCREMENT,
  MachineName VARCHAR(100) NULL,
  MachineCode VARCHAR(100) NOT NULL,
  Model VARCHAR(100) NULL,
  Location VARCHAR(100) NULL,
  Description VARCHAR(100) NULL,
  CreatedDate timestamp not null default current_timestamp,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (MachineID),
  INDEX Machine_MachineCode (MachineCode),
  UNIQUE KEY (MachineCode)
)ENGINE=FEDERATED
DEFAULT CHARSET=UTF8
CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.49:3306/tqteamne_iTools/Machine';


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


CREATE TABLE IF NOT EXISTS federated_Tools (
  ToolID INT(10) NOT NULL AUTO_INCREMENT,
  ToolCode VARCHAR(100) NULL,
  Model VARCHAR(100) NULL,
  Barcode VARCHAR(100) NULL,
  Description VARCHAR(100) NULL,
  CreatedDate timestamp not null default current_timestamp,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (ToolID),
  UNIQUE KEY (ToolCode)
)ENGINE=FEDERATED
DEFAULT CHARSET=UTF8
CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.49:3306/tqteamne_iTools/Tools';


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


CREATE TABLE IF NOT EXISTS federated_ToolsMachineTray (
  ToolsMachineTrayID INT(10) NOT NULL AUTO_INCREMENT,
  ToolsMachineID INT(10) NOT NULL,
  TrayIndex VARCHAR(100) NULL,
  Quantity INT(10) NULL,
  CreatedDate timestamp not null default current_timestamp,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp,
  IsActive BOOLEAN NOT NULL,
  PRIMARY KEY (ToolsMachineTrayID),
  FOREIGN KEY (ToolsMachineID) REFERENCES ToolsMachine(ToolsMachineID)
)ENGINE=FEDERATED
DEFAULT CHARSET=UTF8
CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.49:3306/tqteamne_iTools/ToolsMachineTray';


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

CREATE TABLE IF NOT EXISTS federated_DatabaseVersion (
  iToolAppDatabase VARCHAR(255) NULL,
  UpdatedDate timestamp not null default current_timestamp on update current_timestamp
)ENGINE=FEDERATED
DEFAULT CHARSET=UTF8
CONNECTION='mysql://tqteamne_admin:Admin123@112.213.89.49:3306/tqteamne_iTools/DatabaseVersion';

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