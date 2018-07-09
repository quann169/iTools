CREATE DEFINER=`root`@`localhost` PROCEDURE `updateFromHost`()
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;


INSERT INTO assessor
	SELECT a.* FROM federated_assessor a
	LEFT OUTER JOIN assessor b ON b.AssessorID = a.AssessorID
	WHERE b.AssessorID IS NULL;
    
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
			where fs.Status = 'SUCCESS' and fs.SynType = 'FromHost'
			order by fs.SyncDate desc LIMIT 1)
	and fa.UpdatedDate > (Case when lA.UpdatedDate is null then '1900-01-01 00:00:00' else lA.UpdatedDate END);  
    
    
#insert missing record from Host to local
    INSERT INTO Company
	SELECT a.* FROM federated_Company a
	LEFT OUTER JOIN Company b ON b.CompanyID = a.CompanyID
	WHERE b.CompanyID IS NULL;
	
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
			where fs.Status = 'SUCCESS' and fs.SynType = 'FromHost'
			order by fs.SyncDate desc LIMIT 1)
	and fC.UpdatedDate > (Case when lC.UpdatedDate is null then '1900-01-01 00:00:00' else lC.UpdatedDate END);  


#insert missing record from Host to local
    INSERT INTO Machine
	SELECT a.* FROM federated_Machine a
	LEFT OUTER JOIN Machine b ON b.MachineID = a.MachineID
	WHERE b.MachineID IS NULL;
	
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
			where fs.Status = 'SUCCESS' and fs.SynType = 'FromHost'
			order by fs.SyncDate desc LIMIT 1)
	and fM.UpdatedDate > (Case when lM.UpdatedDate is null then '1900-01-01 00:00:00' else lM.UpdatedDate END);  

        
select * from CompanyMachine;
#insert missing record from Host to local
    INSERT INTO CompanyMachine
	SELECT a.* FROM federated_CompanyMachine a
	LEFT OUTER JOIN CompanyMachine b ON b.CompanyMachineID = a.CompanyMachineID
	WHERE b.CompanyMachineID IS NULL;
	
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
			where fs.Status = 'SUCCESS' and fs.SynType = 'FromHost'
			order by fs.SyncDate desc LIMIT 1)
	and fCM.UpdatedDate > (Case when lCM.UpdatedDate is null then '1900-01-01 00:00:00' else lCM.UpdatedDate END);  


#insert missing record from Host to local
    INSERT INTO RoleAssessor
	SELECT a.* FROM federated_RoleAssessor a
	LEFT OUTER JOIN RoleAssessor b ON b.RoleAssessorID = a.RoleAssessorID
	WHERE b.RoleAssessorID IS NULL;
	
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
			where fs.Status = 'SUCCESS' and fs.SynType = 'FromHost'
			order by fs.SyncDate desc LIMIT 1)
	and fRA.UpdatedDate > (Case when lRA.UpdatedDate is null then '1900-01-01 00:00:00' else lRA.UpdatedDate END);  
    
    #insert missing record from Host to local
    INSERT INTO Roles
	SELECT a.* FROM federated_Roles a
	LEFT OUTER JOIN Roles b ON b.RoleID = a.RoleID
	WHERE b.RoleID IS NULL;
	
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
			where fs.Status = 'SUCCESS' and fs.SynType = 'FromHost'
			order by fs.SyncDate desc LIMIT 1)
	and fR.UpdatedDate > (Case when lR.UpdatedDate is null then '1900-01-01 00:00:00' else lR.UpdatedDate END);  
    
    
    #insert missing record from Host to local
    INSERT INTO Tools
	SELECT a.* FROM federated_Tools a
	LEFT OUTER JOIN Tools b ON b.ToolID = a.ToolID
	WHERE b.ToolID IS NULL;
	
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
			where fs.Status = 'SUCCESS' and fs.SynType = 'FromHost'
			order by fs.SyncDate desc LIMIT 1)
	and fT.UpdatedDate > (Case when lT.UpdatedDate is null then '1900-01-01 00:00:00' else lT.UpdatedDate END);  
    
    
    #insert missing record from Host to local
    INSERT INTO ToolsMachine
	SELECT a.* FROM federated_ToolsMachine a
	LEFT OUTER JOIN ToolsMachine b ON b.ToolsMachineID = a.ToolsMachineID
	WHERE b.ToolsMachineID IS NULL;
	
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
			where fs.Status = 'SUCCESS' and fs.SynType = 'FromHost'
			order by fs.SyncDate desc LIMIT 1)
	and fTM.UpdatedDate > (Case when lTM.UpdatedDate is null then '1900-01-01 00:00:00' else lTM.UpdatedDate END);  

	insert into synchistory(SyncDate, Statistic, Status, SynType)
		VALUES 
	(sysdate(), "run store sync changed data from host to local", "SUCCESS", "FromHost");
        
	commit;
END