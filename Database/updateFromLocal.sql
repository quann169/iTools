CREATE DEFINER=`tqteamne_admin`@`116.102.20.72` PROCEDURE `updateFromLocal`()
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
    
    #insert missing record from Local to Host
    INSERT INTO MasterLog
	SELECT a.* FROM local_MasterLog a
	LEFT OUTER JOIN MasterLog b ON b.LogID = a.LogID
	WHERE b.LogID IS NULL;
	
    #update all record from Local to Host
	update MasterLog
	left join local_MasterLog on MasterLog.LogID = local_MasterLog.LogID
		set MasterLog.LogDate = local_MasterLog.LogDate,
			MasterLog.AssessorName = local_MasterLog.AssessorName,
			MasterLog.TblName = local_MasterLog.TblName,
            MasterLog.RecordID = local_MasterLog.RecordID,
            MasterLog.ColumnName = local_MasterLog.ColumnName,
            MasterLog.Action = local_MasterLog.Action,
            MasterLog.OldValue = local_MasterLog.OldValue,
            MasterLog.NewValue = local_MasterLog.NewValue,
            MasterLog.CompanyCode = local_MasterLog.CompanyCode,
            MasterLog.MachineCode = local_MasterLog.MachineCode,
            MasterLog.Notes = local_MasterLog.Notes
		where MasterLog.LogID in (select LogID from local_MasterLog);
        
	#insert missing record from Local to Host
    INSERT INTO ToolsMachineTray
	SELECT a.* FROM local_ToolsMachineTray a
	LEFT OUTER JOIN ToolsMachineTray b ON b.ToolsMachineTrayID = a.ToolsMachineTrayID
	WHERE b.ToolsMachineTrayID IS NULL;
	
    #update all record from Local to Host
	update ToolsMachineTray
	left join local_ToolsMachineTray on ToolsMachineTray.ToolsMachineTrayID = local_ToolsMachineTray.ToolsMachineTrayID
		set ToolsMachineTray.ToolsMachineID = local_ToolsMachineTray.ToolsMachineID,
			ToolsMachineTray.TrayIndex = local_ToolsMachineTray.TrayIndex,
			ToolsMachineTray.Quantity = local_ToolsMachineTray.Quantity,
            ToolsMachineTray.CreatedDate = local_ToolsMachineTray.CreatedDate,
            ToolsMachineTray.UpdatedDate = local_ToolsMachineTray.UpdatedDate,
            ToolsMachineTray.IsActive = local_ToolsMachineTray.IsActive
		where ToolsMachineTray.ToolsMachineTrayID in (select ToolsMachineTrayID from local_ToolsMachineTray);
        
    #insert missing record from Local to Host
    INSERT INTO WorkingTransaction
	SELECT a.* FROM local_WorkingTransaction a
	LEFT OUTER JOIN WorkingTransaction b ON b.WorkingTransactionID = a.WorkingTransactionID
	WHERE b.WorkingTransactionID IS NULL;
	
    #update all record from Local to Host
	update WorkingTransaction
	left join local_WorkingTransaction on WorkingTransaction.WorkingTransactionID = local_WorkingTransaction.WorkingTransactionID
		set WorkingTransaction.TransactionDate = local_WorkingTransaction.TransactionDate,
			WorkingTransaction.AssessorID = local_WorkingTransaction.AssessorID,
			WorkingTransaction.WOCode = local_WorkingTransaction.WOCode,
            WorkingTransaction.OPCode = local_WorkingTransaction.OPCode,
            WorkingTransaction.ToolCode = local_WorkingTransaction.ToolCode,
            WorkingTransaction.TrayIndex = local_WorkingTransaction.TrayIndex,
            WorkingTransaction.UpdatedDate = local_WorkingTransaction.UpdatedDate,
            WorkingTransaction.RespondMessage = local_WorkingTransaction.RespondMessage,
            WorkingTransaction.TransactionType = local_WorkingTransaction.TransactionType
		where WorkingTransaction.WorkingTransactionID in (select WorkingTransactionID from local_WorkingTransaction);
            
	commit;
END