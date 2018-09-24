DELIMITER $$
DROP PROCEDURE IF EXISTS `SyncLocalToHost_ToolsMachineTray`;
DROP PROCEDURE IF EXISTS `SyncLocalToHost_WorkingTransaction`;
DROP PROCEDURE IF EXISTS `SyncHostToLocal_Company`;
DROP PROCEDURE IF EXISTS `SyncHostToLocal_Assessor`;
DROP PROCEDURE IF EXISTS `SyncHostToLocal_Machine`;
DROP PROCEDURE IF EXISTS `SyncHostToLocal_CompanyMachine`;
DROP PROCEDURE IF EXISTS `SyncHostToLocal_RoleAssessor`;
DROP PROCEDURE IF EXISTS `SyncHostToLocal_Tools`;



CREATE PROCEDURE `SyncLocalToHost_ToolsMachineTray`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
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
END$$

CREATE PROCEDURE `SyncLocalToHost_WorkingTransaction`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
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
END$$



CREATE PROCEDURE `SyncHostToLocal_Company`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
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
END$$



CREATE PROCEDURE `SyncHostToLocal_Assessor`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
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
END$$



CREATE PROCEDURE `SyncHostToLocal_Machine`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
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
END$$


CREATE PROCEDURE `SyncHostToLocal_CompanyMachine`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
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
END$$



CREATE PROCEDURE `SyncHostToLocal_RoleAssessor`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
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
END$$




CREATE PROCEDURE `SyncHostToLocal_Tools`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
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
END$$

