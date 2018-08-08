
INSERT INTO Company(CompanyID, CompanyName, CompanyCode, Address, Location) VALUES 
		(2, "Com1", "Com1", "Com1 Addr", "Location2"),
		(3, "Com2", "Com2", "Com2 Addr", "Location3");

INSERT INTO Assessor(AssessorID, UserName, Password, FirstName, LastName, EmailAddress, CompanyCode, IsActive) VALUES 

	(100, "com1adfgsdmin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "quann169@gmail.com", "Com1", 1),
	(42542, "com1gdfadmin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "quann169@gmail.com", "Com1", 1),
	(52345, "comdgg1admin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "quann169@gmail.com", "Com1", 1),
	(745, "com1adcbmin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "quann169@gmail.com", "Com1", 1),
	(75686, "combxcb1admin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "quann169@gmail.com", "Com1", 1),
	(4234, "comvxb1admin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "quann169@gmail.com", "Com1", 1),
	(3464, "com1adbxcbmin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "quann169@gmail.com", "Com1", 1),
	(424, "com1abxcbdmin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "quann169@gmail.com", "Com1", 1),
	(6436, "com1azsdmin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "quann169@gmail.com", "Com1", 1),
	(4224, "com1xvxdmin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "quann169@gmail.com", "Com1", 1),
	(63456, "com1sfdsadmin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "quann169@gmail.com", "Com1", 1),
	(46347, "com1assdmin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "quann169@gmail.com", "Com1", 1),
	(421345, "comf1admin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "quann169@gmail.com", "Com1", 1),
	(232, "com1adssfmin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "quann169@gmail.com", "Com1", 1),


	-- (1, "admin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "ADMIN", "quann169@gmail.com", "UHCom", 1),
	(2, "uhadmin1", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "UH", "quann169@gmail.com", "UHCom", 1),
	(3, "uhacc1", "e10adc3949ba59abbe56e057f20f883e", "Acc1", "UH", "quannguyen@savarti.com", "UHCom", 1),
	(4, "uhacc2", "e10adc3949ba59abbe56e057f20f883e", "Acc2", "UH", "quann169@gmail.com", "UHCom", 1),
	(5, "com1admin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com1", "quann169@gmail.com", "Com1", 1),
	(6, "com1user1", "e10adc3949ba59abbe56e057f20f883e", "Nguyen", "Quan", "quann169@gmail.com", "Com1", 1),
	(7, "com1user2", "e10adc3949ba59abbe56e057f20f883e", "Tran Quang Hoang", "Tien", "quann169@gmail.com", "Com1", 1),
	(8, "com1user3", "e10adc3949ba59abbe56e057f20f883e", "User3", "Com1", "quann169@gmail.com", "Com1", 1),
	(9, "com2admin", "e10adc3949ba59abbe56e057f20f883e", "ADMIN", "Com2", "quann169@gmail.com", "Com2", 1),
	(10, "com2user1", "e10adc3949ba59abbe56e057f20f883e", "User1", "Com2", "quann169@gmail.com", "Com2", 1),
    (11, "RO7LQKKG", "e10adc3949ba59abbe56e057f20f883e", "User1", "Com2", "quann169@gmail.com", "Com2", 1);

-- update Assessor set IsFirstTimeLogin = 1 where UserName = 'com1admin';

-- Admin UH has Admin, SubAdmin, Accounting, PutIns, TakeOver, UpdateReport
INSERT INTO RoleAssessor(RoleAssessorID, RoleID, AssessorID, CreatedDate, IsActive) VALUES 
	-- (1, 1, 2, now(), 1),
	-- (2, 2, 2, now(), 1),
	-- (3, 3, 2, now(), 1),
	-- (4, 6, 2, now(), 1),
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

INSERT INTO Machine(MachineID, MachineName, MachineCode, Location, CreatedDate, IsActive) VALUES 
	(1, "MAC1", "MAC1", "Location1", now(), 1),
	(2, "MAC2", "MAC2", "Location2", now(), 1),
	(3, "MAC3", "MAC3", "Location3", now(), 1),
	(4, "MAC4", "MAC4", "Location4", now(), 1),
	(5, "MAC5", "MAC5", "Location5", now(), 1)
	-- virtual machine to manage Tool in UH
	-- (6, "UHMAC", "UHMAC", "UHLocation", now(), 1)
	;
	
INSERT INTO CompanyMachine(CompanyMachineID, MachineCode, CompanyCode, CreatedDate, IsActive) VALUES 
	-- Com1 use Mac1, Mac2
	(1, "MAC1", "Com1", now(), 1),
	(2, "MAC2", "Com1", now(), 1),
	-- Com2 use Mac3, Mac4, Mac5
	(3, "MAC3", "Com2", now(), 1),
	(4, "MAC4", "Com2", now(), 1),
	(5, "MAC5", "Com2", now(), 1),
	-- Move Mac3 from Com1 to Com2
	(6, "MAC3", "Com2", now(), 0)
	-- UH virtual machine
	-- (7, "UHMAC", "UHCom", now(), 1)
	;

INSERT INTO Tools(ToolID, ToolCode, CreatedDate, IsActive) VALUES 
	(1, "ACTID111",  now(), 1),
	(2, "BCTID211",  now(), 1),
	(3, "CCTID311",  now(), 1),
	(4, "DCTID4",  now(), 1),
	(5, "ECTID5",  now(), 1),
	(6, "FCTID6",  now(), 1),
	(7, "GCTID7",  now(), 1);
	

INSERT INTO ToolsMachineTray(ToolsMachineTrayID, MachineCode, ToolCode, TrayIndex, Quantity, CreatedDate, IsActive) VALUES 
	-- ToolID at UHCom, do not add to Machine yet
	-- (1, 1, NULL, 2, now(), 1),
	-- (2, 2, NULL, 10, now(), 1),
	-- (3, 3, NULL, 8, now(), 1),
	-- (4, 4, NULL, 6, now(), 1),
	-- (5, 6, NULL, 200, now(), 1),
	-- (6, 7, NULL, 68, now(), 1),
	/*+ MA1
		TRAY1: T1 - 5
		TRAY2: T6 - 2
		TRAY3: 
		TRAY4: T2 - 5
		TRAY5: T1 - 3
	*/
	(7, "MAC1", "ACTID111", "Tray_01", 5, now(), 1),
	(8, "MAC1", "FCTID6", "Tray_02", 2, now(), 1),
	(9, "MAC1", "DCTID4", "Tray_04", 5, now(), 1),
	(10, "MAC1", "ACTID111", "Tray_05", 3, now(), 1),
	/*+ MA2
		TRAY1: 
		TRAY2: T2 - 2
		TRAY3: T3 - 5
		TRAY4: T2 - 5
		TRAY5: T4 - 3
	*/
	(11, "MAC2", "BCTID211", "Tray_02", 2, now(), 1),
	(12, "MAC2", "CCTID311", "Tray_03", 5, now(), 1),
	(13, "MAC2", "BCTID211", "Tray_04", 5, now(), 1),
	(14, "MAC2", "DCTID4", "Tray_05", 3, now(), 1)
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


SET FOREIGN_KEY_CHECKS = 1;
