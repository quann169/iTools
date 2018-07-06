CREATE DEFINER=`root`@`localhost` PROCEDURE `updateFromHost`()
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
    
    #insert missing record from Host to local
    INSERT INTO federated_assessor
	SELECT a.* FROM assessor a
	LEFT OUTER JOIN federated_assessor b ON b.AssessorID = a.AssessorID
	WHERE b.AssessorID IS NULL;
	
    #update all record from Host to local
	update federated_assessor lA
	left join assessor on lA.AssessorID = assessor.AssessorID
		set lA.UserName = assessor.UserName,
			lA.FingerID = assessor.FingerID,
			lA.Password = assessor.Password,
            lA.FirstName = assessor.FirstName,
            lA.LastName = assessor.LastName,
            lA.EmailAddress = assessor.EmailAddress,
            lA.Address = assessor.Address,
            lA.Phone = assessor.Phone,
            lA.CompanyCode = assessor.CompanyCode,
            lA.IsLocked = assessor.IsLocked,
            lA.IsActive = assessor.IsActive,
            lA.LastPassword = assessor.LastPassword,
            lA.IsFirstTimeLogin = assessor.IsFirstTimeLogin
		where lA.AssessorID in (select AssessorID from assessor);
        
	#insert missing record from Host to local
    INSERT INTO federated_Company
	SELECT a.* FROM Company a
	LEFT OUTER JOIN federated_Company b ON b.CompanyID = a.CompanyID
	WHERE b.CompanyID IS NULL;
	
    #update all record from Host to local
	update federated_Company lC
	left join Company on lC.CompanyID = Company.CompanyID
		set lC.CompanyCode = Company.CompanyCode,
			lC.CompanyName = Company.CompanyName,
			lC.CompanyType = Company.CompanyType,
            lC.Address = Company.Address,
            lC.Location = Company.Location
		where lC.CompanyID in (select CompanyID from Company);
        
	#insert missing record from Host to local
    INSERT INTO federated_CompanyMachine
	SELECT a.* FROM CompanyMachine a
	LEFT OUTER JOIN federated_CompanyMachine b ON b.CompanyMachineID = a.CompanyMachineID
	WHERE b.CompanyMachineID IS NULL;
	
    #update all record from Host to local
	update federated_CompanyMachine lCM
	left join CompanyMachine on lCM.CompanyMachineID = CompanyMachine.CompanyMachineID
		set lCM.CompanyCode = CompanyMachine.CompanyCode,
			lCM.MachineCode = CompanyMachine.MachineCode,
			lCM.CreatedDate = CompanyMachine.CreatedDate,
            lCM.IsActive = CompanyMachine.IsActive
		where lCM.CompanyMachineID in (select CompanyMachineID from CompanyMachine);
    
    #insert missing record from Host to local
    INSERT INTO federated_Machine
	SELECT a.* FROM Machine a
	LEFT OUTER JOIN federated_Machine b ON b.MachineID = a.MachineID
	WHERE b.MachineID IS NULL;
	
    #update all record from Host to local
	update federated_Machine lM
	left join Machine on lM.MachineID = Machine.MachineID
		set lM.MachineName = Machine.MachineName,
			lM.MachineCode = Machine.MachineCode,
            lM.Model = Machine.Model,
            lM.Location = Machine.Location,
            lM.Description = Machine.Description,
			lM.CreatedDate = Machine.CreatedDate,
            lM.UpdatedDate = Machine.UpdatedDate,
            lM.IsActive = Machine.IsActive
		where lM.MachineID in (select MachineID from Machine);
        
	#insert missing record from Host to local
    INSERT INTO federated_RoleAssessor
	SELECT a.* FROM RoleAssessor a
	LEFT OUTER JOIN federated_RoleAssessor b ON b.RoleAssessorID = a.RoleAssessorID
	WHERE b.RoleAssessorID IS NULL;
	
    #update all record from Host to local
	update federated_RoleAssessor lRA
	left join RoleAssessor on lRA.RoleAssessorID = RoleAssessor.RoleAssessorID
		set lRA.RoleID = RoleAssessor.RoleID,
			lRA.AssessorID = RoleAssessor.AssessorID,
			lRA.CreatedDate = RoleAssessor.CreatedDate,
            lRA.IsActive = RoleAssessor.IsActive
		where lRA.RoleAssessorID in (select RoleAssessorID from RoleAssessor);
    
    #insert missing record from Host to local
    INSERT INTO federated_Roles
	SELECT a.* FROM Roles a
	LEFT OUTER JOIN federated_Roles b ON b.RoleID = a.RoleID
	WHERE b.RoleID IS NULL;
	
    #update all record from Host to local
	update federated_Roles lR
	left join Roles on lR.RoleID = Roles.RoleID
		set lR.RoleName = Roles.RoleName,
			lR.RoleType = Roles.RoleType,
            lR.IsRole = Roles.IsRole
		where lR.RoleID in (select RoleID from Roles);
    
    #insert missing record from Host to local
    INSERT INTO federated_Tools
	SELECT a.* FROM Tools a
	LEFT OUTER JOIN federated_Tools b ON b.ToolID = a.ToolID
	WHERE b.ToolID IS NULL;
	
    #update all record from Host to local
	update federated_Tools lT
	left join Tools on lT.ToolID = Tools.ToolID
		set lT.ToolCode = Tools.ToolCode,
			lT.Model = Tools.Model,
            lT.Barcode = Tools.Barcode,
            lT.Description = Tools.Description,
			lT.CreatedDate = Tools.CreatedDate,
            lT.UpdatedDate = Tools.UpdatedDate,
            lT.IsActive = Tools.IsActive
		where lT.ToolID in (select ToolID from Tools);
    
    #insert missing record from Host to local
    INSERT INTO federated_ToolsMachine
	SELECT a.* FROM ToolsMachine a
	LEFT OUTER JOIN federated_ToolsMachine b ON b.ToolsMachineID = a.ToolsMachineID
	WHERE b.ToolsMachineID IS NULL;
	
    #update all record from Host to local
	update federated_ToolsMachine lTM
	left join ToolsMachine on lTM.ToolsMachineID = ToolsMachine.ToolsMachineID
		set lTM.ToolCode = ToolsMachine.ToolCode,
            lTM.MachineCode = ToolsMachine.MachineCode,
			lTM.CreatedDate = ToolsMachine.CreatedDate,
            lTM.UpdatedDate = ToolsMachine.UpdatedDate,
            lTM.IsActive = ToolsMachine.IsActive
		where lTM.ToolsMachineID in (select ToolsMachineID from ToolsMachine);
        
	commit;
END