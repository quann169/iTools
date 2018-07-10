CREATE PROCEDURE `SyncLocalToHost`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
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
END