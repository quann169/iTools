use itools_v1p0;
--
-- Dumping routines for database 'itools_v1p0'
--
/*!50003 DROP PROCEDURE IF EXISTS `SyncHostToLocal_Assessor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `SyncHostToLocal_Assessor`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	-- DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	-- DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	DECLARE count_rows INT;
    START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	select count(*) INTO count_rows from federated_assessor;
	
	if (count_rows > 0) then
	
		delete from assessor where AssessorID > 0;
		
		INSERT INTO assessor
			SELECT a.* FROM federated_assessor a
			LEFT OUTER JOIN assessor b ON b.Username = a.Username
			WHERE b.Username IS NULL;
			
		set @insertAssessor = (SELECT ROW_COUNT());
		set @finalResult = concat(": insertAssessor: ", @insertAssessor );
		
		insert into synchistory(SyncDate, Statistic, Status, SynType)
			VALUES 
		(now(), @finalResult, "SUCCESS", "SyncHostToLocal_Assessor");
		
		
		set  returnResult = @finalResult;
	else 
        set returnResult = "";
    end if;
	
	commit;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SyncHostToLocal_Company` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `SyncHostToLocal_Company`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	-- DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	-- DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	DECLARE count_rows INT;
    START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	
	
	select count(*) INTO count_rows from federated_Company;
	
	if (count_rows > 0) then
	
		delete from company where CompanyID > 0;
		
		INSERT INTO Company
		SELECT a.* FROM federated_Company a
		LEFT OUTER JOIN Company b ON b.CompanyID = a.CompanyID
		WHERE b.CompanyID IS NULL;
		
		set @insertCompany = (SELECT ROW_COUNT());
		set @finalResult = concat("insertCompany: ", @insertCompany );
		
		insert into synchistory(SyncDate, Statistic, Status, SynType)
			VALUES 
		(now(), @finalResult, "SUCCESS", "SyncHostToLocal_Company");
		
		set  returnResult = @finalResult;
	else 
        set returnResult = "";
    end if;
	
	commit;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SyncHostToLocal_CompanyMachine` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `SyncHostToLocal_CompanyMachine`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	-- DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	-- DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	DECLARE count_rows INT;
    START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	
	
	select count(*) INTO count_rows from federated_CompanyMachine;
	
	if (count_rows > 0) then
		delete from CompanyMachine where CompanyMachineID > 0;
		
		select * from CompanyMachine;
			INSERT INTO CompanyMachine
			SELECT a.* FROM federated_CompanyMachine a
			LEFT OUTER JOIN CompanyMachine b ON b.CompanyMachineID = a.CompanyMachineID
			WHERE b.CompanyMachineID IS NULL;
			
		set @insertCompanyMachine = (SELECT ROW_COUNT());
		set @finalResult = concat("insertCompanyMachine: ", @insertCompanyMachine );
		
		
		insert into synchistory(SyncDate, Statistic, Status, SynType)
			VALUES 
		(now(), @finalResult, "SUCCESS", "SyncHostToLocal_CompanyMachine");
		
		
		set  returnResult = @finalResult;
	
	else 
        set returnResult = "";
    end if;
	
	commit;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SyncHostToLocal_Machine` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `SyncHostToLocal_Machine`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	-- DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	-- DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	DECLARE count_rows INT;
    START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	
	
	select count(*) INTO count_rows from federated_Machine;
	
	if (count_rows > 0) then
	
		delete from Machine where MachineID > 0;
		
		INSERT INTO Machine
		SELECT a.* FROM federated_Machine a
		LEFT OUTER JOIN Machine b ON b.MachineCode = a.MachineCode
		WHERE b.MachineID IS NULL;
		
		set @insertMachine = (SELECT ROW_COUNT());
		set @finalResult = concat("insertMachine: ", @insertMachine );
		
		insert into synchistory(SyncDate, Statistic, Status, SynType)
			VALUES 
		(now(), @finalResult, "SUCCESS", "SyncHostToLocal_Machine");
		
		
		set  returnResult = @finalResult;
	else 
        set returnResult = "";
    end if;
	
	commit;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SyncHostToLocal_RoleAssessor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `SyncHostToLocal_RoleAssessor`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	-- DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	-- DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	DECLARE count_rows INT;
    START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	
	
	select count(*) INTO count_rows from federated_RoleAssessor;
	
	if (count_rows > 0) then
		delete from RoleAssessor where RoleAssessorID > 0;
		
		INSERT INTO RoleAssessor
			SELECT a.* FROM federated_RoleAssessor a
			LEFT OUTER JOIN RoleAssessor b ON b.RoleAssessorID = a.RoleAssessorID
			WHERE b.RoleAssessorID IS NULL;
			
		set @insertRoleAssessor = (SELECT ROW_COUNT());
		set @finalResult = concat(": insertRoleAssessor: ", @insertRoleAssessor );
		
		insert into synchistory(SyncDate, Statistic, Status, SynType)
			VALUES 
		(now(), @finalResult, "SUCCESS", "SyncHostToLocal_RoleAssessor");
		
		
		set  returnResult = @finalResult;
	else 
        set returnResult = "";
    end if;
	
	commit;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SyncHostToLocal_Tools` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `SyncHostToLocal_Tools`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	-- DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	-- DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	DECLARE count_rows INT;
    START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	select count(*) INTO count_rows from federated_Tools;
	
	if (count_rows > 0) then
		delete from Tools where ToolID > 0;
		
		INSERT INTO Tools
		SELECT a.* FROM federated_Tools a
		LEFT OUTER JOIN Tools b ON b.ToolID = a.ToolID
		WHERE b.ToolID IS NULL;
		
		set @insertTools = (SELECT ROW_COUNT());
		set @finalResult = concat("insertTools: ", @insertTools);
		
		insert into synchistory(SyncDate, Statistic, Status, SynType)
			VALUES 
		(now(), @finalResult, "SUCCESS", "SyncHostToLocal_Tools");
		
		
		set  returnResult = @finalResult;
	else 
        set returnResult = "";
    end if;
	
	commit;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SyncLocalToHost_ToolsMachineTray` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `SyncLocalToHost_ToolsMachineTray`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	-- DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	-- DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	DECLARE count_rows INT;
    START TRANSACTION;
		
    SET SQL_SAFE_UPDATES = 0;
	
	
	select count(*) INTO count_rows from federated_ToolsMachineTray;
	
	if (count_rows > 0) then
		delete from federated_ToolsMachineTray where federated_ToolsMachineTray.MachineCode = MachineCode;
		
		INSERT INTO federated_ToolsMachineTray (MachineCode, ToolCode, TrayIndex, Quantity, CreatedDate, UpdatedDate, isActive)
		SELECT a.MachineCode,a.ToolCode, a.TrayIndex, a.Quantity, a.CreatedDate, a.UpdatedDate, a.isActive FROM ToolsMachineTray a
		
		WHERE a.MachineCode = MachineCode;
		
		set @insertToolsMachineTray = (SELECT ROW_COUNT());
		set @finalResult = concat("insertToolsMachineTray: ", @insertToolsMachineTray );
		
		
		insert into synchistory(SyncDate, Statistic, Status, SynType)
			VALUES 
		(now(), @finalResult, "SUCCESS", "SyncLocalToHost_ToolsMachineTray");
		
		
		set  returnResult = @finalResult;
		
	else 
        set returnResult = "";
    end if;
	
	commit;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SyncLocalToHost_WorkingTransaction` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `SyncLocalToHost_WorkingTransaction`(IN CompanyCode VARCHAR(255), IN MachineCode VARCHAR(255), OUT returnResult TEXT)
BEGIN
	-- DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	-- DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
	DECLARE count_rows INT;
    START TRANSACTION;
	
    SET SQL_SAFE_UPDATES = 0;
	
	
	
	select count(*) INTO count_rows from federated_WorkingTransaction;
	
	if (count_rows > 0) then
		delete from federated_WorkingTransaction where federated_WorkingTransaction.MachineCode = MachineCode;
		
		
		INSERT INTO federated_WorkingTransaction (TransactionDate, MachineCode, CompanyCode, AssessorID, WOCode, OPCode, ToolCode, TrayIndex, Quantity, TransactionStatus, UpdatedDate, RespondMessage, TransactionType)
		SELECT a.TransactionDate, a.MachineCode, a.CompanyCode, a.AssessorID, a.WOCode, a.OPCode, a.ToolCode, a.TrayIndex, a.Quantity, a.TransactionStatus, a.UpdatedDate, a.RespondMessage, a.TransactionType FROM WorkingTransaction a
		LEFT OUTER JOIN federated_WorkingTransaction b ON  b.CompanyCode = a.CompanyCode and b.MachineCode = a.MachineCode
		WHERE b.WorkingTransactionID IS NULL;
		
		set @insertWorkingTransaction = (SELECT ROW_COUNT());
		set @finalResult = concat("insertWorkingTransaction: ", @insertWorkingTransaction );
		
		insert into synchistory(SyncDate, Statistic, Status, SynType)
			VALUES 
		(now(), @finalResult, "SUCCESS", "SyncLocalToHost_WorkingTransaction");
		
		
		set  returnResult = @finalResult;
	else 
        set returnResult = "";
    end if;
	
	commit;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;