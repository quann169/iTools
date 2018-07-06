CREATE DEFINER=`root`@`localhost` PROCEDURE `updateFromLocal`()
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
    
    #insert missing record from Local to Host
    INSERT INTO MasterLog
	SELECT a.* FROM federated_MasterLog a
	LEFT OUTER JOIN MasterLog b ON b.LogID = a.LogID
	WHERE b.LogID IS NULL;
	
    #update all record from Local to Host
	update MasterLog
	left join federated_MasterLog on MasterLog.LogID = federated_MasterLog.LogID
		set MasterLog.LogDate = federated_MasterLog.LogDate,
			MasterLog.AssessorName = federated_MasterLog.AssessorName,
			MasterLog.TblName = federated_MasterLog.TblName,
            MasterLog.RecordID = federated_MasterLog.RecordID,
            MasterLog.ColumnName = federated_MasterLog.ColumnName,
            MasterLog.Action = federated_MasterLog.Action,
            MasterLog.OldValue = federated_MasterLog.OldValue,
            MasterLog.NewValue = federated_MasterLog.NewValue,
            MasterLog.CompanyCode = federated_MasterLog.CompanyCode,
            MasterLog.MachineCode = federated_MasterLog.MachineCode,
            MasterLog.Notes = federated_MasterLog.Notes
		where MasterLog.LogID in (select LogID from federated_MasterLog);
        
	#insert missing record from Local to Host
    INSERT INTO ToolsMachineTray
	SELECT a.* FROM federated_ToolsMachineTray a
	LEFT OUTER JOIN ToolsMachineTray b ON b.ToolsMachineTrayID = a.ToolsMachineTrayID
	WHERE b.ToolsMachineTrayID IS NULL;
	
    #update all record from Local to Host
	update ToolsMachineTray
	left join federated_ToolsMachineTray on ToolsMachineTray.ToolsMachineTrayID = federated_ToolsMachineTray.ToolsMachineTrayID
		set ToolsMachineTray.ToolsMachineID = federated_ToolsMachineTray.ToolsMachineID,
			ToolsMachineTray.TrayIndex = federated_ToolsMachineTray.TrayIndex,
			ToolsMachineTray.Quantity = federated_ToolsMachineTray.Quantity,
            ToolsMachineTray.CreatedDate = federated_ToolsMachineTray.CreatedDate,
            ToolsMachineTray.UpdatedDate = federated_ToolsMachineTray.UpdatedDate,
            ToolsMachineTray.IsActive = federated_ToolsMachineTray.IsActive
		where ToolsMachineTray.ToolsMachineTrayID in (select ToolsMachineTrayID from federated_ToolsMachineTray);
        
    #insert missing record from Local to Host
    INSERT INTO WorkingTransaction
	SELECT a.* FROM federated_WorkingTransaction a
	LEFT OUTER JOIN WorkingTransaction b ON b.WorkingTransactionID = a.WorkingTransactionID
	WHERE b.WorkingTransactionID IS NULL;
	
    #update all record from Local to Host
	update WorkingTransaction
	left join federated_WorkingTransaction on WorkingTransaction.WorkingTransactionID = federated_WorkingTransaction.WorkingTransactionID
		set WorkingTransaction.TransactionDate = federated_WorkingTransaction.TransactionDate,
			WorkingTransaction.AssessorID = federated_WorkingTransaction.AssessorID,
			WorkingTransaction.WOCode = federated_WorkingTransaction.WOCode,
            WorkingTransaction.OPCode = federated_WorkingTransaction.OPCode,
            WorkingTransaction.ToolCode = federated_WorkingTransaction.ToolCode,
            WorkingTransaction.TrayIndex = federated_WorkingTransaction.TrayIndex,
            WorkingTransaction.UpdatedDate = federated_WorkingTransaction.UpdatedDate,
            WorkingTransaction.RespondMessage = federated_WorkingTransaction.RespondMessage,
            WorkingTransaction.TransactionType = federated_WorkingTransaction.TransactionType
		where WorkingTransaction.WorkingTransactionID in (select WorkingTransactionID from federated_WorkingTransaction);
            
	commit;
END